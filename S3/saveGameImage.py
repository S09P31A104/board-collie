import boto3
import csv
from s3_secret import AWS_ACCESS_KEY_ID, AWS_SECRET_ACCESS_KEY, S3_BUCKET_NAME
import os
import time
import requests

# AWS 계정 및 S3 버킷 정보를 secret.py 파일에서 가져오기

# S3 클라이언트 생성
s3 = boto3.client('s3', aws_access_key_id=AWS_ACCESS_KEY_ID, aws_secret_access_key=AWS_SECRET_ACCESS_KEY)

# CSV 파일 읽기
csv_file = 'game_image_url.csv'
output_csv_file = 'game_image.csv'
image_directory = 'game/image/'

# CSV 파일 경로 설정
csv_directory = r'C:\ssafy3\rec\S09P31A104'
csv_file = os.path.join(csv_directory, 'game_image_url.csv')

output_directory = r'C:\ssafy3\rec\S09P31A104\S3'
output_csv_file = os.path.join(output_directory, 'game_image.csv')
image_directory = 'game/image/'

# 새로운 CSV 파일 생성 및 헤더 작성
with open(output_csv_file, 'w', newline='', encoding='utf-8') as csvfile:
    fieldnames = ['s3_image_url', 's3_image_title']  # 필드명을 모두 정의
    csvwriter = csv.DictWriter(csvfile, fieldnames=fieldnames)
    csvwriter.writeheader()

    with open(csv_file, 'r', encoding='utf-8-sig') as csvfile:
        csvreader = csv.DictReader(csvfile)
        for row in csvreader:
            game_image_url = row['game_image_url']
            image_title = row['game_title']

            image_key = game_image_url.split('/')[-1]  #파일이름
            image_path = r'C:\ssafy3\rec\S09P31A104\S3\images'  # 이미지를 저장할 디렉토리 경로
            image_file_path = os.path.join(image_path, image_key) # 이미지 저장할 파일 경로 생성

            # 이미지 다운로드 및 저장
            response = requests.get(game_image_url)
            if response.status_code == 200:
                with open(image_file_path, 'wb') as img_file:
                    img_file.write(response.content)

                # S3에 이미지 업로드
                s3_image_key = f'{image_directory}{image_title}.jpg'  # S3에 저장할 경로+이미지 파일 이름
                s3.upload_file(Filename= image_file_path , Bucket=S3_BUCKET_NAME, Key=s3_image_key)

                # 이미지 파일 삭제
                os.remove(image_file_path)

                # S3에 업로드된 이미지 URL 생성
                s3_image_url = f'https://s3.ap-northeast-2.amazonaws.com/{S3_BUCKET_NAME}/{s3_image_key}'

                # S3 이미지 URL을 CSV 파일에 추가
                csvwriter.writerow({'s3_image_title': image_title, 's3_image_url': s3_image_url})