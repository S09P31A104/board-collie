import requests
from bs4 import BeautifulSoup
import re
import pandas as pd

def fetch_data(start_idx, end_idx):
    results = []

    for idx in range(start_idx, end_idx):
        url = f'https://divedice.net/kor/board/wiki?viewMode=view&ca=&sel_search=&txt_search=&orderby=&page=1&idx={idx}'
        response = requests.get(url)

        if response.status_code == 200:
            soup = BeautifulSoup(response.text, 'html.parser')

            sbj = soup.find('h5', class_='sbj')
            sbj_text = sbj.get_text(strip=True) if sbj else "Not Found"
            sbj_text = re.sub(r'\s+', ' ', sbj_text)  # 연속된 화이트 스페이스를 공백 하나로 변환

            memo_wrap = soup.find('div', class_='memoWrap')
            p_tag = memo_wrap.find('p').get_text(strip=True) if memo_wrap and memo_wrap.find('p') else "Not Found"
            p_tag = re.sub(r'\s+', ' ', p_tag)  # 연속된 화이트 스페이스를 공백 하나로 변환

            results.append({'idx': idx, 'sbj': sbj_text, 'memo_wrap': p_tag})
        else:
            print(f"Failed to fetch idx {idx}: Status code {response.status_code}")

    df = pd.DataFrame(results)
    return df

# 사용 예
df = fetch_data(781, 1051)
print(df)
