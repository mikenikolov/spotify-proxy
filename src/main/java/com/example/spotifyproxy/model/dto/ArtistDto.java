package com.example.spotifyproxy.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.List;

@Setter
@Getter
@Accessors(chain = true)
public class ArtistDto {
    @JsonProperty("artist_name")
    private String artistName;
    @JsonProperty("spotify_artist_id")
    private String spotifyArtistId;
    @JsonProperty("followers")
    private int followers;
    @JsonProperty("genres")
    private List<GenreDto> genres;
    @JsonProperty("songs")
    private List<SongDto> songs;
}
