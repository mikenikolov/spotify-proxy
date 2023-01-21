package com.example.spotifyproxy.service.component;

import com.example.spotifyproxy.model.Artist;

public interface ExistsEntityChecker {
    Artist processExistsGenres(Artist artist);

    Artist processExistsSongs(Artist artist);
}
