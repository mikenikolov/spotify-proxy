package com.example.spotifyproxy.service.impl;

import com.example.spotifyproxy.annotation.SpotifyService;
import com.example.spotifyproxy.model.Artist;
import com.example.spotifyproxy.model.Song;
import com.example.spotifyproxy.service.SearchService;
import com.example.spotifyproxy.service.component.RequestSender;
import com.example.spotifyproxy.service.mapper.ArtistResponseParser;
import com.example.spotifyproxy.service.mapper.SongResponseParser;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@SpotifyService
@RequiredArgsConstructor
public class SearchServiceImpl implements SearchService {
    private final ArtistResponseParser artistParser;
    private final SongResponseParser songResponseParser;
    private final RequestSender requestSender;
    @Value("${spotify.api.artist.id}")
    private String spotifyArtistIdUrl;
    @Value("${spotify.api.artist.name}")
    private String spotifyArtistNameUrl;
    @Value("${spotify.api.artist.top.tracks}")
    private String spotifyArtistTopTracksUrl;

    @Override
    public Artist findArtistById(String artistId) {
        String json = requestSender.sendGetRequest(spotifyArtistIdUrl, artistId);
        Artist artist = artistParser.parseArtistById(json);
        artist.setSongs(findSongsByArtist(artistId));
        return artist;
    }

    @Override
    public Artist findArtistByName(String name) {
        String json = requestSender.sendGetRequest(spotifyArtistNameUrl, name);
        Artist artist = artistParser.parseArtistByName(json);
        artist.setSongs(findSongsByArtist(artist.getSpotifyId()));
        return artist;
    }


    private List<Song> findSongsByArtist(String artistId) {
        String json = requestSender.sendGetRequest(spotifyArtistTopTracksUrl, artistId);
        return songResponseParser.parseSongsByArtist(json);
    }
}
