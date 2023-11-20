from transformers import BertModel, BertTokenizer
import pandas as pd
import torch
import pandas as pd
from sklearn.feature_extraction.text import TfidfVectorizer
import numpy as np
import json
from scipy.sparse import load_npz
import pickle


def get_model_and_tokenizer():
    tokenizer = BertTokenizer.from_pretrained('monologg/kobert')
    model = BertModel.from_pretrained('monologg/kobert')
    return model, tokenizer



def get_component():
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

    return game_vectors, bert_embeddings, tfidf_matrix, tfidf_vectorizer

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
