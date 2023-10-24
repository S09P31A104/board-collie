from selenium import webdriver
from selenium.webdriver.common.by import By
from selenium.webdriver.support.ui import WebDriverWait
from selenium.webdriver.support import expected_conditions as EC
from selenium.webdriver.chrome.options import Options as ChromeOptions
from selenium.webdriver.chrome.service import Service
from bs4 import BeautifulSoup
import pandas as pd
from tqdm import tqdm
import os


pd.set_option('display.max_rows', None)  # 행의 최대 출력 수를 무제한으로 설정
pd.set_option('display.max_columns', None)  # 열의 최대 출력 수를 무제한으로 설정
pd.set_option('display.width', None)  # 출력 너비를 터미널의 너비에 맞게 설정
pd.set_option('display.max_colwidth', None)  # 열의 최대 너비를 무제한으로 설정


def fetch_game_data(driver, game_url):
    driver.get(game_url)
    WebDriverWait(driver, 10).until(EC.presence_of_element_located((By.CSS_SELECTOR, 'span.ng-binding.gameplay-weight-light')))
    soup = BeautifulSoup(driver.page_source, 'html.parser')

    # weight 값을 가져옵니다.
    weight_span = soup.find('span', class_='ng-binding gameplay-weight-light')
    weight = float(weight_span.get_text(strip=True)) if weight_span else None

    # tags 값을 가져옵니다.
    ul = soup.find('ul', class_='outline outline-col-xs-block outline-border')
    if ul:
        lis = ul.find_all('li')
        if len(lis) >= 16:  # 14번째와 15번째 li가 존재하는지 확인합니다.
            divs_14 = lis[13].find_all('div', class_='ng-scope')
            divs_15 = lis[14].find_all('div', class_='ng-scope')
            tags = [a.get_text(strip=True) for div in divs_14 + divs_15 for a in div.find_all('a')]
        else:
            tags = []
    else:
        tags = []

    return {'weight': weight, 'tags': tags}

def fetch_boardgames(search_terms):
    results = []

    # Chrome 옵션 설정
    options = ChromeOptions()
    options.add_argument('--headless')
    options.add_argument('--disable-gpu')
    options.add_argument('--window-size=1920x1080')
    options.add_argument('--no-sandbox')
    options.add_argument('--disable-dev-shm-usage')

    # 현재 스크립트가 실행되는 디렉토리의 경로를 가져옵니다.
    current_directory = os.path.dirname(os.path.realpath(__file__))

    # chromedriver의 경로를 지정합니다.
    driver_path = os.path.join(current_directory, 'chromedriver')

    service = Service(executable_path='chromedriver.exe')

    # Chrome 드라이버 초기화
    driver = webdriver.Chrome(service=service, options=options)
    driver.implicitly_wait(10)

    try:
        for term in tqdm(search_terms, desc='Fetching Boardgames', unit='term'):
            url = f'https://boardgamegeek.com/geeksearch.php?action=search&q={term}&objecttype=boardgame'
            driver.get(url)
            WebDriverWait(driver, 10).until(EC.presence_of_element_located((By.CSS_SELECTOR, 'div#results_objectname1 a')))

            soup = BeautifulSoup(driver.page_source, 'html.parser')
            game_link = soup.select_one('div#results_objectname1 a')

            if game_link:
                href = game_link.get('href')
                game_url = 'https://boardgamegeek.com' + href + '/credits#boardgamemechanic'
                game_data = fetch_game_data(driver, game_url)
                results.append({'game_id': term, **game_data})
            else:
                print(f"No game found for search term {term}")
    finally:
        driver.quit()

    df = pd.DataFrame(results)
    return df

# 검색할 게임 이름의 리스트
search_terms = ['7 Wonders Duel']

# 데이터 프레임 생성
df = fetch_boardgames(search_terms)

# 결과 출력
print(df)
