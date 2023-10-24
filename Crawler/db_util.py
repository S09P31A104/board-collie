import pandas as pd
from sqlalchemy import create_engine


# df to mysql
def save_to_mysql(data, table_name, user, password, endpoint, database, port, if_exists='replace'):
    df = pd.DataFrame(data)
    connection_str = f'mysql+pymysql://{user}:{password}@{endpoint}:{port}/{database}'
    engine = create_engine(connection_str, echo=False)
    df.to_sql(name=table_name, con=engine, index=False, if_exists=if_exists)
    print(f"Data has been saved to {database}.{table_name}")