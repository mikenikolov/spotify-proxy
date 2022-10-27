package com.example.spotifyproxy.service.mapper;

import com.example.spotifyproxy.model.Song;

import java.util.List;

public interface SongResponseParser {
    List<Song> parseSongsByArtist(String json);
}
