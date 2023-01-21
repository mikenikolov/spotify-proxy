package com.example.spotifyproxy.service.component.impl;

import com.example.spotifyproxy.model.Artist;
import com.example.spotifyproxy.model.Genre;
import com.example.spotifyproxy.model.Song;
import com.example.spotifyproxy.repository.GenreRepository;
import com.example.spotifyproxy.repository.SongRepository;
import com.example.spotifyproxy.service.component.ExistsEntityChecker;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class ExistsEntityCheckerImpl implements ExistsEntityChecker {
    private GenreRepository genreRepository;
    private SongRepository songRepository;

    @Override
    public Artist processExistsGenres(Artist artist) {
        List<String> artistGenres = artist.getGenres().stream()
                .map(Genre::getName)
                .collect(Collectors.toList());
        List<Genre> existsGenres = genreRepository.findAllByNameIn(artistGenres);
        for (Genre eg : existsGenres) {
            artist.getGenres().removeIf(e -> e.getName().equals(eg.getName()));
            artist.getGenres().add(eg);
        }
        return artist;
    }

    @Override
    public Artist processExistsSongs(Artist artist) {
        List<String> artistSongs = artist.getSongs().stream()
                .map(Song::getSpotifySongId)
                .collect(Collectors.toList());
        List<Song> existsSongs = songRepository.findAllBySpotifySongIdIn(artistSongs);
        for (Song es : existsSongs) {
            artist.getSongs().removeIf(e -> e.getSpotifySongId().equals(es.getSpotifySongId()));
            artist.getSongs().add(es);
        }
        return artist;
    }
}
