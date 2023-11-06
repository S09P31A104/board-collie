from bs4 import BeautifulSoup
import re
import pandas as pd
import numpy as np


# 정적 html파일 읽기
def read_html_file(file_name):
    with open(file_name, 'r', encoding='utf-8') as f:
        return f.read()


# Game
def extract_game_times(strings):
    result = []
    for s in strings:
        match = re.search(r'게임시간(\d+)분', s) 
        if match:
            result.append(int(match.group(1)))
    return result


def extract_game_members(strings):
    result = []
    for s in strings:
        r = list(map(int, filter(None, re.split(r'\D+', s))))
        result.append(r)
    return result


def extract_game_info(soup):
    game_titles_en = [span.get_text(strip=True) for span in soup.find_all('span', class_='game-title-en')]
    game_titles_kor = [span.get_text(strip=True) for span in soup.find_all('span', class_='game-title')]
    game_info_list = [span.get_text(strip=True) for span in soup.find_all('span', class_='content-rule')]

    game_times = game_info_list[2::3]
    game_members = game_info_list[1::3]

    game_times_extracted = [int(re.search(r'게임시간(\d+)분', time).group(1)) for time in game_times]

    game_members_extracted = [list(map(int, filter(None, re.split(r'\D+', members)))) for members in game_members]
    min_game_members = [members[0] for members in game_members_extracted]
    max_game_members = [members[1] if len(members) > 1 else members[0] for members in game_members_extracted]

    game_ids = list(range(len(game_titles_en)))

    return pd.DataFrame({
        'game_id': game_ids,
        'game_title_eng': game_titles_en,
        'game_title_kor': game_titles_kor,
        'game_time': game_times_extracted,
        'game_min_player': min_game_members,
        'game_max_player': max_game_members
    })


def get_game_data(file_name):
    html = read_html_file(file_name)
    soup = BeautifulSoup(html, 'html.parser')
    data = extract_game_info(soup)

    pd.set_option('display.max_rows', None)  # 행의 최대 출력 수를 무제한으로 설정
    pd.set_option('display.max_columns', None)  # 열의 최대 출력 수를 무제한으로 설정
    pd.set_option('display.width', None)  # 출력 너비를 터미널의 너비에 맞게 설정
    pd.set_option('display.max_colwidth', None)  # 열의 최대 너비를 무제한으로 설정

    # 빈 문자열을 NaN으로 변환
    data.replace("", np.nan, inplace=True)

    # NaN 값을 포함하는 모든 행을 삭제
    data = data.dropna()

    # 상위 344개의 행 선택
    data = data[:344]

    return data

# HTML에서 이미지 URL 추출
def extract_game_image_urls(soup):
    # 'game-thumb' 클래스를 가진 div 요소 찾기
    game_thumb_divs = soup.find_all('div', class_='game-thumb')

    # 이미지 URL을 저장할 리스트 초기화
    image_urls = []

    # 각 'game-thumb' 요소에서 'img' 태그를 찾아 이미지 URL 추출
    for div in game_thumb_divs:
        img = div.find('img')
        if img and 'src' in img.attrs:
            image_url = img['src']
            image_urls.append(image_url)
    return image_urls

def get_game_image_urls(file_name):
    html = read_html_file(file_name)
    soup = BeautifulSoup(html, 'html.parser')
    image_urls= extract_game_image_urls(soup)

    return pd.DataFrame({'game_image_url': image_urls})

