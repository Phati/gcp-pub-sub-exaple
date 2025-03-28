package com.learn.cloud.gcp.pubsub.service;

import com.google.cloud.storage.Blob;
import com.google.cloud.storage.Bucket;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.nio.file.Files;
import java.nio.file.Paths;

@Service
public class StorageService {

    private Storage storage;

    @Value("${google.cloud.project-id}")
    private String projectId;

    @Value("${google.cloud.storage.endpoint}")
    private String endpoint;

    @Value("${google.cloud.storage.access-key}")
    private String accessKey;

    @Value("${google.cloud.storage.secret-key}")
    private String secretKey;

    @PostConstruct
    public void init() {
        // Initialize the Google Cloud Storage client with Fake GCS server endpoint
        storage = StorageOptions.newBuilder()
                .setProjectId(projectId)
                .setHost(endpoint)
                .setCredentials(new FakeGCSCredentials(accessKey, secretKey))  // Custom Fake GCS credentials
                .build()
                .getService();
    }

    // Method to create a bucket
    public void createBucket(String bucketName) {
        Bucket bucket = storage.create(Bucket.newBuilder(bucketName).build());
        System.out.println("Bucket " + bucket.getName() + " created.");
    }

    // Method to upload a file to a bucket
    public void uploadFile(String bucketName, String fileName, String content) throws Exception {
        Bucket bucket = storage.get(bucketName);
        if (bucket == null) {
            throw new Exception("Bucket does not exist.");
        }

        Blob blob = bucket.create(fileName, content.getBytes());
        System.out.println("File " + blob.getName() + " uploaded.");
    }

    // Method to download a file from the bucket
    public String downloadFile(String bucketName, String fileName) throws Exception {
        Bucket bucket = storage.get(bucketName);
        if (bucket == null) {
            throw new Exception("Bucket does not exist.");
        }

        Blob blob = bucket.get(fileName);
        if (blob == null) {
            throw new Exception("File does not exist.");
        }

        byte[] content = blob.getContent();
        return new String(content);
    }
}