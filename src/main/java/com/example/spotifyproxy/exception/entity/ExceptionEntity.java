package com.example.spotifyproxy.exception.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@Data
public class ExceptionEntity {
    @JsonProperty("status")
    private String status;
    @JsonProperty("status_code")
    private int statusCode;
    @JsonProperty("error_message")
    private String errorMessage;
}
