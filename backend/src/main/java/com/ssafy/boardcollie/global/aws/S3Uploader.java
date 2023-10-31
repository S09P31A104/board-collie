package com.ssafy.boardcollie.global.aws;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.ssafy.boardcollie.global.exception.GlobalRuntimeException;
import java.io.File;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class S3Uploader {

    private final AmazonS3Client amazonS3Client;
    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    public String upload(String fileName, File file) {
        if (file == null) {
            return null;
        }

        amazonS3Client.putObject(new PutObjectRequest(bucket, fileName, file).withCannedAcl(
                CannedAccessControlList.PublicRead));
        removeFile(file);
        return amazonS3Client.getUrl(bucket, fileName).toString();

    }

    private void removeFile(File file) {
        if (!file.delete()) {
            throw new GlobalRuntimeException("이미지 파일 삭제 실패", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
