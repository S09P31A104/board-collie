from redbotton_crawler import get_game_data
from bgg_crawler import fetch_boardgames
import pandas as pd

pd.set_option('display.max_rows', None)  # 행의 최대 출력 수를 무제한으로 설정
pd.set_option('display.max_columns', None)  # 열의 최대 출력 수를 무제한으로 설정
pd.set_option('display.width', None)  # 출력 너비를 터미널의 너비에 맞게 설정
pd.set_option('display.max_colwidth', None)  # 열의 최대 너비를 무제한으로 설정

data = pd.read_csv('game.csv')

boardgame_list = data['English Title'].tolist()

df = fetch_boardgames(boardgame_list[:])

print(df)

df.to_csv('game_tag.csv', index=False, encoding='utf-8-sig')

# print(boardgame_list)

# data.to_csv('game.csv', index=False, encoding='utf-8-sig')
