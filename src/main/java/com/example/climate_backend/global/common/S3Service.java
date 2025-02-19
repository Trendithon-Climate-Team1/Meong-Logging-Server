package com.example.climate_backend.global.common;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.GetUrlRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;
import java.net.URI;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class S3Service {

    private final S3Client s3Client;

    @Value("${spring.cloud.aws.s3.bucket}")
    private String bucket;

    public String uploadImage(MultipartFile file)  {
        String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
        PutObjectRequest objectRequest = PutObjectRequest.builder()
                .bucket(bucket)
                .key(fileName)
                .contentType(file.getContentType())
                .contentLength(file.getSize())
                .build();
        try {
            s3Client.putObject(objectRequest, RequestBody.fromBytes(file.getBytes()));
        } catch (IOException e) {
            throw new RuntimeException("이미지 저장에 실패하였습니다.");
        }
        return getFileUrl(fileName);
    }

    public void deleteImage(String imageUrl) {
        String splitStr = ".com/";
        String fileName = imageUrl.substring(imageUrl.lastIndexOf(splitStr) + splitStr.length());
        String key = URLDecoder.decode(fileName, StandardCharsets.UTF_8);
        System.out.println("bucket = " + bucket);
        System.out.println("key = " + key);
        if(isObjectExists(bucket, key)) System.out.println("있음");
        else System.out.println("없음");
        DeleteObjectRequest deleteObjectRequest = DeleteObjectRequest.builder()
                .bucket(bucket)
                .key(key)
                .build();
        s3Client.deleteObject(deleteObjectRequest);
    }
    private boolean isObjectExists(String bucket, String key) {
        try {
            s3Client.headObject(builder -> builder.bucket(bucket).key(key).build());
            return true;
        } catch (Exception e) {
            return false; // 객체가 존재하지 않으면 정상 삭제됨
        }
    }
    private String getFileUrl(String fileName){
        GetUrlRequest request = GetUrlRequest.builder()
                .bucket(bucket)
                .key(fileName)
                .build();
        return s3Client.utilities().getUrl(request).toString();
    }

}