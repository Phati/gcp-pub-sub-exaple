package com.learn.cloud.gcp.pubsub.controller;


import com.learn.cloud.gcp.pubsub.service.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StorageController {

    @Autowired
    private StorageService storageService;

    @GetMapping("/create-bucket")
    public String createBucket(@RequestParam String bucketName) {
        storageService.createBucket(bucketName);
        return "Bucket created successfully!";
    }

    @GetMapping("/upload-file")
    public String uploadFile(@RequestParam String bucketName, @RequestParam String fileName, @RequestParam String content) {
        try {
            storageService.uploadFile(bucketName, fileName, content);
            return "File uploaded successfully!";
        } catch (Exception e) {
            return "Error uploading file: " + e.getMessage();
        }
    }

    @GetMapping("/download-file")
    public String downloadFile(@RequestParam String bucketName, @RequestParam String fileName) {
        try {
            return storageService.downloadFile(bucketName, fileName);
        } catch (Exception e) {
            return "Error downloading file: " + e.getMessage();
        }
    }
}
