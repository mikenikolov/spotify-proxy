package com.example.spotifyproxy.service.impl;

import com.example.spotifyproxy.annotation.SpotifyService;
import com.example.spotifyproxy.annotation.SpotifyProxyService;
import com.example.spotifyproxy.model.Artist;
import com.example.spotifyproxy.repository.ArtistRepository;
import com.example.spotifyproxy.service.component.ArtistSaver;
import com.example.spotifyproxy.service.SearchService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@AllArgsConstructor
@SpotifyProxyService
public class SearchServiceProxyImpl implements SearchService {
    private ArtistRepository artistRepository;
    @SpotifyService
    private SearchService searchService;
    private ArtistSaver artistSaver;

    @Override
    public Artist findArtistById(String artistId) {
        Optional<Artist> artistFromDb = artistRepository.findArtistBySpotifyId(artistId);
        if (artistFromDb.isPresent()) {
            log.info(String.format("[CACHE] Artist with ID {%s} successfully found", artistFromDb.get().getSpotifyId()));
            return artistFromDb.get();
        }
        Artist artist = searchService.findArtistById(artistId);
        log.info(String.format("[SPOTIFY API] Artist with ID {%s} successfully found", artist.getSpotifyId()));
        return artistSaver.saveArtist(artist);
    }

    @Override
    public Artist findArtistByName(String artistName) {
        artistName = artistName.toLowerCase();
        Optional<Artist> artistFromDb = artistRepository.findArtistByArtistName(artistName);
        if (artistFromDb.isPresent()) {
            log.info(String.format("[CACHE] Artist with ID {%s} successfully found", artistFromDb.get().getSpotifyId()));
            return artistFromDb.get();
        }
        Artist artist = searchService.findArtistByName(artistName);
        log.info(String.format("[SPOTIFY API] Artist with ID {%s} successfully found", artist.getSpotifyId()));
        return artistSaver.saveArtist(artist);
    }
}
