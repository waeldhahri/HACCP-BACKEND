package com.example.haccpbackend.s3;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "aws")
public class AwsConfigProperties {
    private String accessKey;
    private String secretKey;
    private String region;
    private S3 s3 = new S3();

    public static class S3 {
        private String bucketName;

        // getters et setters
        public String getBucketName() { return bucketName; }
        public void setBucketName(String bucketName) { this.bucketName = bucketName; }
    }

    // getters et setters
    public String getAccessKey() { return accessKey; }
    public void setAccessKey(String accessKey) { this.accessKey = accessKey; }

    public String getSecretKey() { return secretKey; }
    public void setSecretKey(String secretKey) { this.secretKey = secretKey; }

    public String getRegion() { return region; }
    public void setRegion(String region) { this.region = region; }

    public S3 getS3() { return s3; }
    public void setS3(S3 s3) { this.s3 = s3; }
}