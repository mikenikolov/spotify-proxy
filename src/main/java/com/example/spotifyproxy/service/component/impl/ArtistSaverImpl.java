package com.example.spotifyproxy.service.component.impl;

import com.example.spotifyproxy.model.Artist;
import com.example.spotifyproxy.model.Genre;
import com.example.spotifyproxy.model.Song;
import com.example.spotifyproxy.repository.ArtistRepository;
import com.example.spotifyproxy.service.component.ArtistSaver;
import com.example.spotifyproxy.service.component.ExistsEntityChecker;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@Component
@AllArgsConstructor
public class ArtistSaverImpl implements ArtistSaver {
    private final ExistsEntityChecker existsEntityChecker;
    private final ArtistRepository artistRepository;

    @Transactional
    @Override
    public Artist saveArtist(Artist artist) {
        Optional<Artist> savedArtist = artistRepository.findArtistByArtistName(artist.getArtistName());
        if (savedArtist.isPresent()) {
            return savedArtist.get();
        }
        List<Genre> genres = artist.getGenres();
        List<Song> songs = artist.getSongs();
        artist.setGenres(existsEntityChecker.processExistsGenres(genres));
        artist.setSongs(existsEntityChecker.processExistsSongs(songs));
        artistRepository.save(artist);
        log.info(String.format("[APP] The artist with ID {%s} was saved to cache", artist.getSpotifyId()));
        return artist;
    }
}
