package com.learn.cloud.gcp.pubsub.service;
import com.google.auth.oauth2.AccessToken;
import com.google.auth.oauth2.GoogleCredentials;

import java.io.IOException;
import java.util.Collections;

public class FakeGCSCredentials extends GoogleCredentials {

    private final String accessKey;
    private final String secretKey;

    public FakeGCSCredentials(String accessKey, String secretKey) {
        this.accessKey = accessKey;
        this.secretKey = secretKey;
    }

    @Override
    public AccessToken refreshAccessToken() throws IOException {
        return new AccessToken("fake-token", null);
    }

    @Override
    public String getAuthenticationType() {
        return "Fake GCS Authentication";
    }

    public Iterable<String> getScopes() {
        return Collections.emptyList();
    }

    @Override
    public void refreshIfExpired() throws IOException {
    }

    public boolean hasAccessToken() {
        return true;
    }



}