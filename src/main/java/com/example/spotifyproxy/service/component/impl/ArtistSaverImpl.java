package com.example.spotifyproxy.service.component.impl;

import com.example.spotifyproxy.model.Artist;
import com.example.spotifyproxy.repository.ArtistRepository;
import com.example.spotifyproxy.service.component.ArtistSaver;
import com.example.spotifyproxy.service.component.ExistsEntityChecker;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

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
        artist.setArtistName(artist.getArtistName().toLowerCase());
        Optional<Artist> savedArtist = artistRepository.findArtistByArtistName(artist.getArtistName());
        if (savedArtist.isPresent()) {
            return savedArtist.get();
        }
        Artist processedArtist = existsEntityChecker.processExistsGenres(artist);
        processedArtist = existsEntityChecker.processExistsSongs(processedArtist);
        artistRepository.save(processedArtist);
        log.info(String.format("[APP] The artist with ID {%s} was saved to cache", artist.getSpotifyId()));
        return processedArtist;
    }
}
