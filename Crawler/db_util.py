import pandas as pd
import ast
from sqlalchemy import create_engine


# df to mysql
def save_to_mysql(data, table_name, user, password, endpoint, port, database, if_exists='replace'):
    df = pd.DataFrame(data)
    connection_str = f'mysql+pymysql://{user}:{password}@{endpoint}:{port}/{database}'
    engine = create_engine(connection_str, echo=False)
    df.to_sql(name=table_name, con=engine, index=False, if_exists=if_exists)
    print(f"Data has been saved to {database}.{table_name}")


def save_cross_table(user, password, endpoint, port, database):
    connection_str = f'mysql+pymysql://{user}:{password}@{endpoint}:{port}/{database}'
    engine = create_engine(connection_str, echo=False)

    df = pd.read_csv('game_tag_2.csv')

    df['tags'] = df['tags'].apply(ast.literal_eval)

    game_ids = pd.read_sql('SELECT game_id, game_title_eng FROM game', con=engine)
    tag_ids = pd.read_sql('SELECT tag_id, sbj_eng FROM tag', con=engine)

    game_name_to_id = dict(zip(game_ids['game_title_eng'], game_ids['game_id']))
    tag_name_to_id = dict(zip(tag_ids['sbj_eng'], tag_ids['tag_id']))

    cross_table = pd.DataFrame(columns=['game_id', 'tag_id'])

    for index, row in df.iterrows():
        game_id = game_name_to_id[row['game_id']]
        for tag in row['tags']:
            tag_id = tag_name_to_id[tag]
            cross_table = cross_table._append({'game_id': game_id, 'tag_id': tag_id}, ignore_index=True)

    cross_table.to_sql('game_tag', con=engine, if_exists='replace', index=False)


def get_game_tag_df(user, password, endpoint, port, database):
    connection_str = f'mysql+pymysql://{user}:{password}@{endpoint}:{port}/{database}'
    engine = create_engine(connection_str, echo=False)

    tags_df = pd.read_sql('SELECT sbj_eng FROM tag', con=engine)

    tag_set = set(tags_df['sbj_eng'])

    game_tag_df = pd.read_csv('game_tag.csv')

    game_tag_df['tags'] = game_tag_df['tags'].apply(eval)

    game_tag_df['tags'] = game_tag_df['tags'].apply(lambda tags: [tag for tag in tags if tag in tag_set])

    game_tag_df.to_csv('game_tag_2.csv', index=False, encoding='utf-8-sig')
    return game_tag_df
