package com.example.spotifyproxy.service;

import com.example.spotifyproxy.model.Artist;
import org.springframework.data.domain.Page;

public interface SearchCacheService {
    Page<Artist> getAllCashed(Integer pageIndex);
}
