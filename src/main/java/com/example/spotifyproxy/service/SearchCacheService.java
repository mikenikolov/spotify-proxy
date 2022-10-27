package com.example.spotifyproxy.service;

import com.example.spotifyproxy.model.Artist;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface SearchCacheService {
    List<Artist> getAllCashed(Pageable pageable);
}
