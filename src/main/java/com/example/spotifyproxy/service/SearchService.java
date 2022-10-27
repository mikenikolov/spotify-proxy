package com.example.spotifyproxy.service;

import com.example.spotifyproxy.model.Artist;

public interface SearchService {
    Artist findArtistById(String spotifyId);

    Artist findArtistByName(String artistName);
}
