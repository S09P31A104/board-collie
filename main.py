from fastapi import FastAPI
from fastapi.middleware.cors import CORSMiddleware
from typing import List
from pydantic import BaseModel
from Recommend import recommend
import db

app = FastAPI()
origins = ["*"]  # 모든 도메인에서의 요청을 허용

app.add_middleware(
    CORSMiddleware,
    allow_origins=origins,
    allow_credentials=True,
    allow_methods=["*"],
    allow_headers=["*"],
)

class NumbersInput(BaseModel):
    params: List[int]


@app.get("/")
def read_root():
    return {"Hello": "World"}


@app.post("/api/s2/recommend")
def recommend_game(params: NumbersInput):
    recommend_games = []
    games = recommend.get_recommended_game_list(list(params))
    # 데이터베이스 연결
    db_connection = db.create_server_connection()
    cursor = db_connection.cursor()

    for game in games:
        game_id = game['game_id']
        similarity = game['similarity']
        # SQL 쿼리에서 game_id를 사용하여 특정 게임 정보를 조회
        cursor.execute("SELECT game_title_kor, game_image FROM game WHERE game_id = %s", (game_id,))
        game_data = cursor.fetchone()
        if game_data:
            game_info = {
                'game_id': game_id,
                'similarity': similarity,
                'game_title_kor': game_data[0],
                'game_image': game_data[1]
            }
            recommend_games.append(game_info)

    cursor.close()
    return {"games": recommend_games}
