from transformers import BertModel, BertTokenizer
import pandas as pd
import torch
import pandas as pd
from sklearn.feature_extraction.text import TfidfVectorizer
import numpy as np
import json
from scipy.sparse import load_npz
import pickle

# 데이터가 큰 경우 콘솔에 모두 출력하기 위해 옵션 설정
pd.set_option('display.max_rows', None)
pd.set_option('display.max_columns', None)
pd.set_option('display.width', None)
pd.set_option('display.max_colwidth', None)


def get_model_and_tokenizer():
    tokenizer = BertTokenizer.from_pretrained('monologg/kobert')
    model = BertModel.from_pretrained('monologg/kobert')
    print("Successfully Return Model and Tokenizer")
    return model, tokenizer


model, tokenizer = get_model_and_tokenizer()

# 데이터 로드
games = pd.read_csv('csv/game.csv')
tags = pd.read_csv('csv/mechanism.csv')
game_tags = pd.read_csv('csv/game_tag.csv')
with open('csv/stopwords.txt', 'r', encoding='utf-8') as f:
    stopwords = [line.strip() for line in f]

games['combined_info'] = (games['game_title_kor'] + ' ' +
                          games['game_detail'] + ' ' +
                          games['game_evaluation'])

# 태그 ID와 게임 ID를 기반으로 게임 데이터와 태그 데이터 합치기
merged = pd.merge(game_tags, tags, on='tag_id', how='left')

# 병합된 데이터를 'combined_info'에 추가
for _, row in merged.iterrows():
    if pd.notnull(row['tag_description']):
        game_id = row['game_id']
        tag_info = row['tag_description'].lower()
        games.loc[games['game_id'] == game_id, 'combined_info'] += ' ' + tag_info


# TF-IDF 벡터화를 위한 함수 (이전과 동일하게 CPU에서 수행됨)
def tfidf_vectorize(texts):
    tfidf_vectorizer = TfidfVectorizer()
    tfidf_matrix = tfidf_vectorizer.fit_transform(texts)
    return tfidf_vectorizer, tfidf_matrix


# koBERT 임베딩 생성 함수 (GPU 사용)
def bert_embed(texts, tokenizer, model, device):
    # 평균 임베딩을 계산하기 위한 함수
    def mean_pooling(token_embeddings, attention_mask):
        input_mask_expanded = attention_mask.unsqueeze(-1).expand(token_embeddings.size()).float()
        sum_embeddings = torch.sum(token_embeddings * input_mask_expanded, 1)
        sum_mask = torch.clamp(input_mask_expanded.sum(1), min=1e-9)
        return sum_embeddings / sum_mask

    # 텍스트를 토큰화하고 BERT 임베딩 계산 (GPU 사용)
    encoded_input = tokenizer(texts, padding=True, truncation=True, return_tensors='pt', max_length=512).to(device)
    with torch.no_grad():
        model_output = model(**encoded_input)

    # 평균 임베딩을 사용하여 문장 벡터 생성
    sentence_embeddings = mean_pooling(model_output.last_hidden_state, encoded_input['attention_mask'])

    # GPU에서 계산된 데이터를 CPU로 이동
    return sentence_embeddings.cpu().numpy()


def load_game_vectors(file_path):
    with open(file_path, 'r', encoding='utf-8') as f:
        data = json.load(f)

    # 키 값을 숫자로 변환
    converted_data = {int(k): np.array(v) for k, v in data.items()}
    return converted_data


def load_npy_file(file_path):
    return np.load(file_path)


def load_sparse_matrix(file_path):
    return load_npz(file_path)


def load_pickle_file(file_path):
    with open(file_path, 'rb') as f:
        return pickle.load(f)


game_vectors = load_game_vectors('statics/game_vectors.json')

bert_embeddings = load_npy_file('statics/bert_embeddings.npy')

tfidf_matrix = load_sparse_matrix('statics/tfidf_matrix.npz')

tfidf_vectorizer = load_pickle_file('statics/tfidf_vectorizer.pkl')

# # 전처리된 텍스트 데이터 벡터화 (CPU에서 수행)
# tfidf_vectorizer, tfidf_matrix = tfidf_vectorize(games['combined_info'])
#
# # koBERT 임베딩 생성 (GPU 사용)
# bert_embeddings = bert_embed(games['combined_info'].tolist(), tokenizer, model, device)
#
# # TF-IDF 벡터와 koBERT 임베딩을 결합 (CPU에서 수행)
# combined_embeddings = np.hstack((tfidf_matrix.toarray(), bert_embeddings))
#
# # 결과를 dictionary 형태로 저장 (CPU에서 수행)
# game_vectors = {game_id: combined_embeddings[index] for index, game_id in enumerate(games['game_id'])}
#
# print(game_vectors[0][1])
