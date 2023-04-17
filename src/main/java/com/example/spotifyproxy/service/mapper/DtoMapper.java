package com.example.spotifyproxy.service.mapper;

public interface DtoMapper<R, E> {
    R toDto(E entity);
}
