from fastapi import FastAPI
from pydantic import BaseModel
from typing import List
import pandas as pd
import numpy as np
import uvicorn
from sklearn.feature_extraction.text import TfidfVectorizer
from sklearn.preprocessing import MinMaxScaler
from sklearn.metrics.pairwise import cosine_similarity
from fastapi.middleware.cors import CORSMiddleware
# kobert.py에서 predict 함수 import
from kobert import predict
#
from fastapi.middleware.cors import CORSMiddleware

app = FastAPI()

app.add_middleware(
    CORSMiddleware,
    allow_origins=["http://localhost:5173"],
    allow_credentials=True,
    allow_methods=["*"],
    allow_headers=["*"],
)

# 입력데이터 형식 정의
class Texts(BaseModel):
    texts: List[str]

@app.post("/predict")
def predict_contract(texts: Texts):
    results = []
    # 3. 받은 텍스트 리스트를 순회하며 예측 수행
    for text in texts.texts:
        pred = predict(text)  # kobert.py에 정의된 예측 함수 호출
        label = "사기" if pred == 1 else "정상"  # 예측값 0,1을 의미있는 문자열로 변환
        results.append({"text": text, "result": label})

    # 4. 결과 리스트를 JSON으로 반환
    return {"predictions": results}

# --- 추천 시스템 관련 코드 시작 ---
data_path = '../../mysql-files/listing.csv'

df = pd.read_csv(data_path)
df['features'] = df[['transaction_type', 'listing', 'area_info', 'floor_info', 'direction']].astype(str).agg(' '.join, axis=1)

vectorizer = TfidfVectorizer()
tfidf_matrix = vectorizer.fit_transform(df['features'])

scaler = MinMaxScaler()
numeric_features = scaler.fit_transform(df[['deposit']])

combined_features = np.hstack([tfidf_matrix.toarray(), numeric_features])

def recommend_for_user(liked_ids, top_n=6):
    liked_indices = []
    for pid in liked_ids:
        matches = df.index[df['id'] == pid].tolist()
        if matches:
            liked_indices.append(matches[0])

    if not liked_indices:
        return pd.DataFrame()

    liked_vectors = combined_features[liked_indices]
    user_vector = liked_vectors.mean(axis=0).reshape(1, -1)

    sim_scores = cosine_similarity(user_vector, combined_features).flatten()

    for idx in liked_indices:
        sim_scores[idx] = -1

    top_indices = sim_scores.argsort()[::-1][:top_n]
    return df.iloc[top_indices]

class RecommendRequest(BaseModel):
    likedListings: List[int]

@app.post("/recommend")
def recommend(request: RecommendRequest):
    recommended_df = recommend_for_user(request.likedListings, top_n=6)
    print("Liked IDs:", request.likedListings)
    print("Recommended IDs:", recommended_df['id'].tolist())
    if recommended_df.empty:
        return {"recommendations": []}
    else:
        return {"recommendations": recommended_df.to_dict(orient="records")}
