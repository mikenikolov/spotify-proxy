package com.example.spotifyproxy.controller;

import com.example.spotifyproxy.annotation.SpotifyProxyService;
import com.example.spotifyproxy.model.Artist;
import com.example.spotifyproxy.model.dto.ArtistDto;
import com.example.spotifyproxy.model.dto.LiteArtistDto;
import com.example.spotifyproxy.service.SearchCacheService;
import com.example.spotifyproxy.service.SearchService;
import com.example.spotifyproxy.service.mapper.DtoMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/v1/search")
public class SearchController {
    private final DtoMapper<ArtistDto, Artist> artistMapper;
    private final DtoMapper<LiteArtistDto, Artist> liteArtistMapper;
    private final SearchCacheService cacheService;
    private final SearchService searchService;
    @Value("${pageable.size}")
    private Integer pageSize;

    public SearchController(DtoMapper<ArtistDto, Artist> artistMapper,
                            DtoMapper<LiteArtistDto, Artist> liteArtistMapper,
                            SearchCacheService cacheService,
                            @SpotifyProxyService SearchService searchService) {
        this.artistMapper = artistMapper;
        this.liteArtistMapper = liteArtistMapper;
        this.cacheService = cacheService;
        this.searchService = searchService;
    }

    @GetMapping("/id/{id}")
    public ArtistDto findArtistById(@PathVariable(name = "id") String spotifyId) {
        Artist artist = searchService.findArtistById(spotifyId);
        return artistMapper.toDto(artist);
    }

    @GetMapping("/name/{name}")
    public ArtistDto findArtistByName(@PathVariable(name = "name") String name) {
        Artist artist = searchService.findArtistByName(name);
        return artistMapper.toDto(artist);
    }

    @GetMapping
    public List<LiteArtistDto> showAllCached(@RequestParam(value = "page", defaultValue = "1") Integer page) {
        Pageable pageable = PageRequest.of(page - 1, pageSize);
        List<Artist> artist = cacheService.getAllCashed(pageable);
        return artist.stream()
                .map(liteArtistMapper::toDto)
                .collect(Collectors.toList());
    }
}
