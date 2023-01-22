package com.example.spotifyproxy.service.component;

import com.example.spotifyproxy.model.Genre;
import com.example.spotifyproxy.model.Song;

import java.util.List;

public interface ExistsEntityChecker {
    List<Genre> processExistsGenres(List<Genre> artistGenres);

    List<Song> processExistsSongs(List<Song> artistSongs);
}
