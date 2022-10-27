package com.example.spotifyproxy.service.component;

public interface RequestSender {
    String sendGetRequest(String url, String... params);
}
