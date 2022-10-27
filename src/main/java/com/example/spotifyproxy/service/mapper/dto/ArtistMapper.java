package com.example.spotifyproxy.service.mapper.dto;

import com.example.spotifyproxy.model.Artist;
import com.example.spotifyproxy.model.dto.ArtistDto;
import com.example.spotifyproxy.model.dto.GenreDto;
import com.example.spotifyproxy.model.dto.SongDto;
import com.example.spotifyproxy.service.mapper.DtoMapper;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class ArtistMapper implements DtoMapper<ArtistDto, Artist> {

    @Override
    public ArtistDto toDto(Artist artist) {
        return new ArtistDto()
                .setSpotifyArtistId(artist.getSpotifyId())
                .setArtistName(artist.getArtistName())
                .setFollowers(artist.getFollowers())
                .setSongs(artist.getSongs().stream()
                        .map(s -> new SongDto(s.getId(), s.getName(), s.getSpotifySongId()))
                        .collect(Collectors.toList()))
                .setGenres(artist.getGenres().stream()
                        .map(g -> new GenreDto(g.getName()))
                        .collect(Collectors.toList()));
    }
}
