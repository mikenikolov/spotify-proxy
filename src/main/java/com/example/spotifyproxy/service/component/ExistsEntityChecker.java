package com.example.spotifyproxy.service.component;

import com.example.spotifyproxy.model.Artist;

public interface ExistsEntityChecker {
    void processExistsGengres(Artist artist);

    void processExistsSongs(Artist artist);
}
