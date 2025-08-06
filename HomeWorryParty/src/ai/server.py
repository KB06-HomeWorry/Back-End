from fastapi import FastAPI
from pydantic import BaseModel
from typing import List
import uvicorn

# kobert.py에서 predict 함수 import
from kobert import predict
#
from fastapi.middleware.cors import CORSMiddleware

app = FastAPI()

app.add_middleware(
    CORSMiddleware,
    allow_origins=["*"],
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