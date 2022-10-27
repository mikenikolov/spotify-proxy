package com.example.spotifyproxy.service.impl;

import com.example.spotifyproxy.model.Artist;
import com.example.spotifyproxy.repository.ArtistRepository;
import com.example.spotifyproxy.service.SearchCacheService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class SearchCacheServiceImpl implements SearchCacheService {
    private ArtistRepository artistRepository;

    @Override
    public List<Artist> getAllCashed(Pageable pageable) {
        return artistRepository.findAll(pageable).getContent();
    }
}
