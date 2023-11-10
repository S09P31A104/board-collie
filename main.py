from fastapi import FastAPI, HTTPException
from typing import List
from pydantic import BaseModel, conlist

app = FastAPI()

class NumbersInput(BaseModel):
    params: List[int]


@app.get("/")
def read_root():
    return {"Hello": "World"}

@app.post("/api/s2/recommend")
def recommend_game(input: NumbersInput):
    print(input)
    recommend_games = []

    return {"games": recommend_games}