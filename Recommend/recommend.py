from sklearn.metrics.pairwise import cosine_similarity
from scipy.spatial.distance import cosine
from sklearn.metrics.pairwise import cosine_similarity
import numpy as np
import torch
import init
import pandas as pd

model, tokenizer = init.get_model_and_tokenizer()

game_vectors, bert_embeddings, tfidf_matrix, tfidf_vectorizer = init.get_component()

device = torch.device("cuda" if torch.cuda.is_available() else "cpu")

game_df = pd.read_csv('statics/game_df.csv')

text_template = [
    [
        "장시간 집중을 요구하는 게임들. 플레이 시간은 보통 몇 시간에 이르며, 게임의 세부적인 요소와 긴 게임 진행 과정을 즐길 수 있는 플레이어에게 적합합니다.",
        "빠른 진행과 즉각적인 재미를 제공하는 게임들. 이 게임들은 간단한 규칙과 짧은 플레이 시간을 가지고 있어, 누구나 쉽게 참여하고 즐길 수 있습니다.",
        "적당한 길이와 깊이를 가진 게임들. 이 게임들은 한 시간에서 두 시간 정도의 플레이 시간을 가지며, 게임의 균형 잡힌 복잡성과 전략적 요소를 제공합니다."
    ],
    [
        "고도의 전략적 사고와 심층적인 게임 플레이를 요구하는 게임들. 이 게임들은 복잡한 규칙과 다양한 게임 메커니즘을 포함하며, 플레이어는 게임 도중 수많은 결정을 내려야 합니다.",
        "적절한 수준의 도전과 전략적 사고를 제공하는 게임들. 이 게임들은 이해하기 쉬운 규칙을 가지면서도 플레이어의 결정에 따라 다양한 결과를 초래할 수 있습니다.",
        "빠르게 배울 수 있고 쉽게 즐길 수 있는 게임들. 이 게임들은 간단한 규칙과 직관적인 게임 플레이를 특징으로 하며, 모든 연령과 경험 수준의 플레이어에게 적합합니다."
    ],
    [
        "전략적 사고와 계획을 필요로 하는 게임들. 게임은 복잡하고 다양한 규칙들을 가지고 있으며, 플레이어들은 게임의 진행에 따라 전략을 조정해야 함. 이러한 게임은 보통 중간에서 긴 게임 시간을 필요로 하며, 플레이어들은 게임의 진행에 따라 다양한 결정을 내려야 함.",
        "집단적 상호작용을 독려하는 게임들. 쉽게 게임을 준비할 수 있고 규칙이 간단하며, 많은 사람들이 참여할 수 있고 게임 시간이 짧음",
        "게임의 플레이 시간이 보통인 게임"],
    [
        "전략적 사고와 장기적인 계획을 중시하는 게임들. 이 게임들은 플레이어가 게임의 시작부터 끝까지 전략을 세워야 하며, 세심한 계획과 전술적 결정이 중요합니다. 우연성이나 운의 영향이 적어 플레이어의 선택과 전략이 게임 결과에 큰 영향을 미칩니다.",
        "운과 기회가 게임 결과에 큰 영향을 미치는 게임들. 이 게임들은 주사위 굴리기, 카드 뽑기 등의 우연성 요소가 포함되어 있어 예측 불가능한 결과와 흥미진진한 전개를 제공합니다. 전략적 요소도 있지만, 운이 좋은 순간에 의존하여 게임의 흐름을 바꿀 수 있는 게임입니다.",
        "계획과 운, 두 가지 요소가 균형 잡히게 통합된 게임들. 이 게임들은 전략적 사고와 운의 요소가 적절히 혼합되어 있어 다양한 게임 스타일을 수용합니다. 플레이어는 전략적 계획을 세우면서도 운의 요소로 인한 예기치 못한 상황에 유연하게 대응해야 합니다."
    ],
    [
        "플레이어 간의 활발한 상호작용을 중시하는 게임들. 이 게임들은 협력, 협상, 경쟁 또는 다른 플레이어와의 직접적인 상호 작용을 포함합니다. 플레이어들은 서로 영향을 주고받으며 게임을 진행하고, 사회적 상호작용이 게임의 핵심 요소로 작용합니다.",
        "플레이어 간의 직접적인 상호작용이 적은 게임들. 이 게임들은 주로 개인의 전략과 계획에 중점을 두며, 플레이어는 주로 자신의 게임 보드나 카드에 집중합니다. 서로 간의 직접적인 경쟁보다는 개별적인 전략 수립이 중요한 역할을 합니다.",
        "플레이어 간의 상호작용 정도가 다양한 게임들. 이 게임들은 상호작용이 많은 게임과 적은 게임 사이의 중간 정도를 제공합니다. 플레이어는 필요에 따라 다른 플레이어와 상호작용하거나 개인의 전략에 집중할 수 있으며, 게임은 다양한 상호작용 수준을 수용합니다."
    ]
]


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


# 유저 입력을 벡터화하기 위한 함수
def vectorize_input(user_input, tfidf_vectorizer, tokenizer, model, device):
    # 텍스트를 전처리하고 TF-IDF 벡터화
    user_tfidf = tfidf_vectorizer.transform([user_input]).toarray()
    user_tfidf *= 8

    # 텍스트를 토크나이징하고 BERT 임베딩 계산
    user_bert = bert_embed([user_input], tokenizer, model, device)
    user_bert_weight = 5  # BERT 벡터에 적용할 가중치
    user_bert *= user_bert_weight

    # 두 벡터를 결합
    user_vector = np.hstack((user_tfidf, user_bert))

    return user_vector


def recommend_game(user_vector, game_vectors, min_players, max_players, games_df):
    # 인원수에 맞는 게임 필터링
    eligible_games = games_df[(games_df['game_min_player'] <= min_players) &
                              (games_df['game_max_player'] >= max_players)]

    # 유사도 계산
    similarities = {}
    for game_id, game_vector in game_vectors.items():
        if game_id in eligible_games['game_id'].values:
            similarity = cosine_similarity(user_vector, game_vector.reshape(1, -1))[0][0]
            similarities[game_id] = similarity

    # 유사도가 높은 순으로 정렬
    sorted_game_ids = sorted(similarities, key=similarities.get, reverse=True)

    # 상위 6개 게임 추출
    top_games = sorted_game_ids[:6]

    # 게임 ID, 이름 및 일치율을 포함한 결과 생성
    result = []
    for game_id in top_games:
        game_info = eligible_games[eligible_games['game_id'] == game_id]
        similarity_score = similarities[game_id]
        result.append({'game_id': game_id, 'similarity': similarity_score})

    return result


def get_recommended_game_list(params):
    text = ""
    input_params = params[0][1]
    player = input_params[0]

    for i, number in enumerate(input_params[1:]):
        text += text_template[i][number]

    user_vector = vectorize_input(text, tfidf_vectorizer, tokenizer, model, device)

    return recommend_game(user_vector, game_vectors, player, player, game_df)
