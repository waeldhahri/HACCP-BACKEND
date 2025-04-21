package com.example.haccpbackend.s3;


import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.ObjectCannedACL;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;

@Service
public class S3Service {



    private final S3Client s3Client;
    private final String bucketName;

    public S3Service(AwsConfigProperties config) {
        AwsBasicCredentials awsCreds = AwsBasicCredentials.create(config.getAccessKey(), config.getSecretKey());
        this.s3Client = S3Client.builder()
                .region(Region.of(config.getRegion()))
                .credentialsProvider(StaticCredentialsProvider.create(awsCreds))
                .build();
        this.bucketName = config.getS3().getBucketName();

        System.out.println(config.getAccessKey());
        System.out.println(config.getSecretKey());
        System.out.println(config.getRegion());
        System.out.println(this.bucketName);


    }




/*

    public String uploadFile(MultipartFile file, String keyName) throws IOException {

        PutObjectRequest putOb = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(keyName)
                .acl("public-read")
                .contentType(file.getContentType())
                .build();

        s3Client.putObject(putOb, RequestBody.fromBytes(file.getBytes()));

        return s3Client.utilities().getUrl(builder -> builder.bucket(bucketName).key(keyName)).toExternalForm();


    }*/


    public String uploadFile(MultipartFile file, String keyName) {
        try {
            PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(keyName)
                    .acl(ObjectCannedACL.PUBLIC_READ)
                    .contentType(file.getContentType())
                    .build();

            s3Client.putObject(putObjectRequest, RequestBody.fromInputStream(file.getInputStream(), file.getSize()));

            return s3Client.utilities()
                    .getUrl(builder -> builder.bucket(bucketName).key(keyName))
                    .toString();
        } catch (IOException e) {
            throw new RuntimeException("Erreur d'upload", e);
        }
    }









}