import ast

from redbotton_crawler import get_game_data
from bgg_crawler import fetch_boardgames
from redbotton_crawler import get_game_image_urls
import pandas as pd
from db_util import save_to_mysql
import db_util
import secret

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


# 이미지 URL을 CSV 파일로 저장
def export_image_urls_to_csv():
    df = get_game_image_urls('C:/ssafy3/rec/S09P31A104/Crawler/redbotton.html')
    df.to_csv('game_image_url.csv', index=False, encoding='utf-8-sig')


export_image_urls_to_csv()
#game_df = csv_to_pandas('game.csv')
#mechanism_df = csv_to_pandas('mechanism.csv')


#db_util.save_to_mysql(game_df, 'game', secret.DB_USERNAME, secret.DB_PASSWORD, secret.DB_ENDPOINT, secret.DB_PORT,secret.DB_NAME)
#db_util.save_to_mysql(mechanism_df, 'tag', secret.DB_USERNAME, secret.DB_PASSWORD, secret.DB_ENDPOINT, secret.DB_PORT,secret.DB_NAME)
#db_util.save_to_mysql(game_image_urls_df, 'image',secret.DB_USERNAME, secret.DB_PASSWORD, secret.DB_ENDPOINT, secret.DB_PORT, secret.DB_NAME)

#db_util.save_cross_table(secret.DB_USERNAME, secret.DB_PASSWORD, secret.DB_ENDPOINT, secret.DB_PORT, secret.DB_NAME)

