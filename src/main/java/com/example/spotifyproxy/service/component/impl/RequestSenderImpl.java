package com.example.spotifyproxy.service.component.impl;

import com.example.spotifyproxy.exception.ResponseParserException;
import com.example.spotifyproxy.exception.UnauthorizedException;
import com.example.spotifyproxy.service.component.RequestSender;
import lombok.RequiredArgsConstructor;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.regex.Pattern;

@Component
@RequiredArgsConstructor
public class RequestSenderImpl implements RequestSender {
    private final CloseableHttpClient httpClient;

    @Override
    public String sendGetRequest(String url, String... params) {
        Pattern pattern = Pattern.compile("%s");
        if (params != null && pattern.matcher(url).find() && params.length > 0) {
            for (int i = 0; i < params.length; i++) {
                params[i] = URLEncoder.encode(params[i], StandardCharsets.UTF_8);
            }
            url = String.format(url, params);
        }
        HttpGet req = new HttpGet(url);
        String spotifyToken = SecurityContextHolder.getContext().getAuthentication().getName();
        req.setHeader("Authorization", "Bearer " + spotifyToken);
        try (CloseableHttpResponse resp = httpClient.execute(req)) {
            if (resp.getStatusLine().getStatusCode() == 401) {
                throw new UnauthorizedException("Unauthorized request. You need to login as Spotify user");
            }
            return EntityUtils.toString(resp.getEntity());
        } catch (IOException e) {
            throw new ResponseParserException("Error during GET request", e);
        }
    }
}
