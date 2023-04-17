package com.example.spotifyproxy.service.impl;

import com.example.spotifyproxy.exception.IncorrectPageException;
import com.example.spotifyproxy.model.Artist;
import com.example.spotifyproxy.repository.ArtistRepository;
import com.example.spotifyproxy.service.SearchCacheService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SearchCacheServiceImpl implements SearchCacheService {
    @Value("${pageable.size}")
    private Integer pageSize;
    private final ArtistRepository artistRepository;

    @Override
    public Page<Artist> getAllCashed(Integer pageIndex) {
        if (pageIndex < 1) {
            throw new IncorrectPageException("Incorrect page number! Must be greater than zero.");
        }
        Pageable pageable = PageRequest.of(pageIndex - 1, pageSize);
        Page<Artist> artists = artistRepository.findAll(pageable);
        if (pageIndex > artists.getTotalPages() && !artists.isFirst()) {
            throw new IncorrectPageException("Incorrect page number! Must be less or equal total pages.");
        }
        return artists;
    }
}
