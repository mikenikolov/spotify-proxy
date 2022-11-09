package com.example.spotifyproxy.repository.custom;

import com.example.spotifyproxy.model.Artist;

import java.util.Optional;

public interface CustomArtistRepository {
    Optional<Artist> findArtistByArtistName(String artistName);

    Optional<Artist> findArtistBySpotifyId(String spotifyId);
}
