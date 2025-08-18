from fastapi import FastAPI
from pydantic import BaseModel
from typing import List
import pandas as pd
import numpy as np
import uvicorn
from sklearn.feature_extraction.text import TfidfVectorizer
from sklearn.preprocessing import MinMaxScaler
from sklearn.metrics.pairwise import cosine_similarity
#fastapi 서버 라이브러리 import
from fastapi.middleware.cors import CORSMiddleware
# kobert.py에서 predict 함수 import
from kobert import predict

app = FastAPI()

# CORS 설정
app.add_middleware(
    CORSMiddleware,
    allow_origins=["http://localhost:5173"], # 허용할 프론트엔드 주소
    allow_credentials=True,
    allow_methods=["*"],
    allow_headers=["*"],
)

# 입력데이터 형식 정의
class Texts(BaseModel):
    texts: List[str]

# --- 계약서 분석 예측 결과 전송 ---
@app.post("/predict")
def predict_contract(texts: Texts):
    results = []
    # 받은 텍스트 리스트를 순회하며 예측 수행
    for text in texts.texts:
        pred = predict(text)  # kobert.py에 정의된 예측 함수 호출
        label = "사기" if pred == 1 else "정상"  # 예측값 0,1을 의미있는 문자열로 변환
        results.append({"text": text, "result": label})

    # 결과 리스트를 JSON으로 반환
    return {"predictions": results}

# --- 추천 시스템 관련 코드 시작 ---
data_path = '../../mysql-files/listing.csv'

df = pd.read_csv(data_path)
df['features'] = df[['transaction_type', 'listing', 'area_info', 'floor_info', 'direction']].astype(str).agg(' '.join, axis=1)

# TF-IDF 벡터로 변환
vectorizer = TfidfVectorizer()
tfidf_matrix = vectorizer.fit_transform(df['features']) #각 매물의 텍스트 특성을 벡터로 변환한 행렬

# 숫자형 특징 정규화
scaler = MinMaxScaler()
numeric_features = scaler.fit_transform(df[['deposit']])

# 특징 결합
combined_features = np.hstack([tfidf_matrix.toarray(), numeric_features])

# 사용자 맞춤 추천 함수
def recommend_for_user(liked_ids, top_n=6):
    liked_indices = []
    for pid in liked_ids:
        matches = df.index[df['id'] == pid].tolist()
        if matches:
            liked_indices.append(matches[0])
    # 찜한 매물 없을 경우 빈 데이터프레임 반환
    if not liked_indices:
        return pd.DataFrame()

    liked_vectors = combined_features[liked_indices]
    user_vector = liked_vectors.mean(axis=0).reshape(1, -1) # 매물 특징을 평균 내서 사용자의 취향 벡터 생성

    sim_scores = cosine_similarity(user_vector, combined_features).flatten() # 코사인 유사도로 각 매물과 사용자 벡터 비교

    for idx in liked_indices:
        sim_scores[idx] = -1

    top_indices = sim_scores.argsort()[::-1][:top_n]
    return df.iloc[top_indices]

class RecommendRequest(BaseModel):
    likedListings: List[int]

# 클라이언트가 post 요청 보내면 recommend() 함수 실행됨
@app.post("/recommend")
def recommend(request: RecommendRequest):
    recommended_df = recommend_for_user(request.likedListings, top_n=6) # 추천 함수 실행
    print("Liked IDs:", request.likedListings)
    print("Recommended IDs:", recommended_df['id'].tolist())
    if recommended_df.empty:
        return {"recommendations": []}
    else:
        return {"recommendations": recommended_df.to_dict(orient="records")}
