import mysql.connector
from mysql.connector import Error
import secret


def create_server_connection(host_name=secret.DB_ENDPOINT, user_name=secret.DB_USERNAME, user_password=secret.DB_PASSWORD,
                             db_name=secret.DB_NAME):
    connection = None
    try:
        connection = mysql.connector.connect(
            host=host_name,
            user=user_name,
            passwd=user_password,
            database=db_name
        )
        print("MySQL Database connection successful")
    except Error as err:
        print(f"Error: '{err}'")

    return connection
