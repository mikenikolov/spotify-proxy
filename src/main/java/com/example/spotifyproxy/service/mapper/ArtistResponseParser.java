package com.example.spotifyproxy.service.mapper;

import com.example.spotifyproxy.model.Artist;

public interface ArtistResponseParser {
    Artist parseArtistById(String json);

    Artist parseArtistByName(String json);
}
