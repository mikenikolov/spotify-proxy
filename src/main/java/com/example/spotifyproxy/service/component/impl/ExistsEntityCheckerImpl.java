package com.example.spotifyproxy.service.component.impl;

import com.example.spotifyproxy.model.Genre;
import com.example.spotifyproxy.model.Song;
import com.example.spotifyproxy.repository.GenreRepository;
import com.example.spotifyproxy.repository.SongRepository;
import com.example.spotifyproxy.service.component.ExistsEntityChecker;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class ExistsEntityCheckerImpl implements ExistsEntityChecker {
    private GenreRepository genreRepository;
    private SongRepository songRepository;

    @Override
    public List<Genre> processExistsGenres(List<Genre> artistGenres) {
        List<String> genreNames = artistGenres.stream()
                .map(Genre::getName)
                .collect(Collectors.toList());
        List<Genre> existsGenres = genreRepository.findAllByNameIn(genreNames);
        List<Genre> processedGenres = new ArrayList<>(artistGenres);
        for (Genre eg : existsGenres) {
            processedGenres.removeIf(e -> e.getName().equals(eg.getName()));
            processedGenres.add(eg);
        }
        return processedGenres;
    }

    @Override
    public List<Song> processExistsSongs(List<Song> artistSongs) {
        List<String> songSpotifyIds = artistSongs.stream()
                .map(Song::getSpotifySongId)
                .collect(Collectors.toList());
        List<Song> existsSongs = songRepository.findAllBySpotifySongIdIn(songSpotifyIds);
        List<Song> processedSongs = new ArrayList<>(artistSongs);
        for (Song es : existsSongs) {
            processedSongs.removeIf(e -> e.getName().equals(es.getName()));
            processedSongs.add(es);
        }
        return processedSongs;
    }
}
