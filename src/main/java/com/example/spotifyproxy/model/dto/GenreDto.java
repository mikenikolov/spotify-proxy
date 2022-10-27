package com.example.spotifyproxy.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class GenreDto {
    @JsonProperty("name")
    private String genreName;
}
