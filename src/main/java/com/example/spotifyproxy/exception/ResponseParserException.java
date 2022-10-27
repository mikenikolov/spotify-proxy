package com.example.spotifyproxy.exception;

public class ResponseParserException extends RuntimeException {
    public ResponseParserException(String message, Throwable cause) {
        super(message, cause);
    }
}
