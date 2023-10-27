import ast

from redbotton_crawler import get_game_data
from bgg_crawler import fetch_boardgames
from redbotton_crawler import get_game_data
import pandas as pd
from db_util import save_to_mysql
import db_util
import secret
import openai
from tqdm import tqdm

openai.api_key = secret.OPENAI_API_KEY

pd.set_option('display.max_rows', None)  # 행의 최대 출력 수를 무제한으로 설정
pd.set_option('display.max_columns', None)  # 열의 최대 출력 수를 무제한으로 설정
pd.set_option('display.width', None)  # 출력 너비를 터미널의 너비에 맞게 설정
pd.set_option('display.max_colwidth', None)  # 열의 최대 너비를 무제한으로 설정


def csv_to_pandas(filename):
    return pd.read_csv(filename)


def export_game_to_csv():
    data = get_game_data('redbotton.html')

    data.to_csv('game.csv', index=False, encoding='utf-8-sig')


def export_game_tag_to_csv(data):
    boardgame_list = data['game_title_eng'].tolist()

    df = fetch_boardgames(boardgame_list[:])

    df.to_csv('game_tag.csv', index=False, encoding='utf-8-sig')


def get_dataframe():
    # Game Dataframe 가져오기
    game_df = csv_to_pandas('game.csv')

    mechanism_df = csv_to_pandas('mechanism.csv')

    return game_df, mechanism_df


def save_to_db():
    db_util.save_to_mysql(game_df, 'game', secret.DB_USERNAME, secret.DB_PASSWORD, secret.DB_ENDPOINT, secret.DB_PORT,
                          secret.DB_NAME)
    db_util.save_to_mysql(mechanism_df, 'tag', secret.DB_USERNAME, secret.DB_PASSWORD, secret.DB_ENDPOINT,
                          secret.DB_PORT,
                          secret.DB_NAME)

    db_util.save_cross_table(secret.DB_USERNAME, secret.DB_PASSWORD, secret.DB_ENDPOINT, secret.DB_PORT, secret.DB_NAME)


game_df, mechanism_df = get_dataframe()
save_to_db()

game_df['game_detail'] = ''
game_df['game_evaluation'] = ''

try:
    # 각 게임에 대해 ChatGPT API 호출
    for index, row in tqdm(game_df.iterrows(), total=len(game_df), desc="Processing Games"):
        # 게임 설명 요청
        prompt_detail = [
            {"role": "user",
             "content": f"보드게임 \"{row['game_title_eng']}\"에 대한 설명을 보드게임의 메커니즘에 기반하여 약 200자 분량으로 설명해 줘. 이 때, 응답의{row['game_title_eng']}는 {row['game_title_kor']}로 치환해서 적어 줘."}
        ]
        response_detail = openai.ChatCompletion.create(
            model="gpt-4",
            messages=prompt_detail,
            max_tokens=600
        )
        print(f"\nGame Detail : {response_detail['choices'][0]['message']['content'].strip()}")

        game_df.at[index, 'game_detail'] = response_detail['choices'][0]['message']['content'].strip()

        # 게임 평가 요청
        prompt_evaluation = [
            {"role": "user",
             "content": f"보드게임 \"{row['game_title_eng']}\"에 대한 사용자 평가를 취합하여 존댓말을 사용한 약 200자 분량의 문장으로 요약해 줘. 이 때, 응답의{row['game_title_eng']}는 {row['game_title_kor']}로 치환해서 적어 줘."}
        ]
        response_evaluation = openai.ChatCompletion.create(
            model="gpt-4",
            messages=prompt_evaluation,
            max_tokens=600
        )
        print(f"\nGame Evaluation : {response_evaluation['choices'][0]['message']['content'].strip()}")
        game_df.at[index, 'game_evaluation'] = response_evaluation['choices'][0]['message']['content'].strip()

        if index >= 99:  # 0부터 시작하므로 99로 설정
            break
except Exception as e:
    print(f"An error occurred: {str(e)}")
    game_df.to_csv('game_2.csv', index=False, encoding='utf-8-sig')
    print("The current data has been saved to game_2.csv")
    exit()

# 결과 확인
print(game_df)
