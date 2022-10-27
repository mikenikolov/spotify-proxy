package com.example.spotifyproxy.service.mapper.dto;

import com.example.spotifyproxy.model.Artist;
import com.example.spotifyproxy.model.dto.LiteArtistDto;
import com.example.spotifyproxy.service.mapper.DtoMapper;
import org.springframework.stereotype.Component;

@Component
public class LiteArtistMapper implements DtoMapper<LiteArtistDto, Artist> {
    @Override
    public LiteArtistDto toDto(Artist artist) {
        return new LiteArtistDto()
                .setArtistName(artist.getArtistName())
                .setSpotifyArtistId(artist.getSpotifyId());
    }
}
