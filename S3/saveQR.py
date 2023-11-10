import csv
from s3_secret import S3_BUCKET_NAME
import os

# S3 버킷 이름 및 이미지 디렉토리 정보
s3_bucket_name = S3_BUCKET_NAME
image_directory = 'chatbot/qr/'

# CSV 파일 경로 및 이름 지정
output_directory = r'C:\ssafy3\rec\S09P31A104\S3'
output_csv_file = os.path.join(output_directory, 'qr_image_urls.csv')


# 새로운 CSV 파일 생성 및 헤더 작성
with open(output_csv_file, 'w', newline='', encoding='utf-8') as csvfile:
    fieldnames = ['qr_image_url']  # 필드명을 정의
    csvwriter = csv.DictWriter(csvfile, fieldnames=fieldnames)
    csvwriter.writeheader()

    # QR 이미지 이름이 'QR0'부터 'QR345'까지 순차적으로 있는 경우
    for i in range(346):  # 0부터 345까지의 숫자를 반복
        image_name = f'QR{i}'  # 이미지 파일 이름 생성
        s3_image_key = f'{image_directory}{image_name}.jpg'  # S3에 저장된 이미지 파일 경로 및 이름
        qr_image_url = f'https://s3.ap-northeast-2.amazonaws.com/{s3_bucket_name}/{s3_image_key}'

        # S3 이미지 URL을 CSV 파일에 추가
        csvwriter.writerow({'qr_image_url': qr_image_url})
