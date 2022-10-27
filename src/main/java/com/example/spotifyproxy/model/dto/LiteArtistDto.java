package com.example.spotifyproxy.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class LiteArtistDto {
    @JsonProperty("artist_name")
    private String artistName;
    @JsonProperty("spotify_artist_id")
    private String spotifyArtistId;
}
