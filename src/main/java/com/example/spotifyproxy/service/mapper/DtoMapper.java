package com.example.spotifyproxy.service.mapper;

public interface DtoMapper<D, M> {
    D toDto(M artist);
}
