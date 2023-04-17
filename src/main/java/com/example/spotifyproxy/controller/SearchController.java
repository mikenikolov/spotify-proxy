package com.example.spotifyproxy.controller;
import com.example.spotifyproxy.annotation.SpotifyProxyService;
import com.example.spotifyproxy.model.Artist;
import com.example.spotifyproxy.model.dto.ArtistDto;
import com.example.spotifyproxy.model.dto.CachedArtistsDto;
import com.example.spotifyproxy.service.SearchCacheService;
import com.example.spotifyproxy.service.SearchService;
import com.example.spotifyproxy.service.mapper.DtoMapper;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/search")
public class SearchController {
    private final DtoMapper<ArtistDto, Artist> artistMapper;
    private final DtoMapper<CachedArtistsDto, Page<Artist>> cachedArtistsMapper;
    private final SearchCacheService cacheService;
    private final SearchService searchService;

    public SearchController(DtoMapper<ArtistDto, Artist> artistMapper,
                            DtoMapper<CachedArtistsDto, Page<Artist>> cachedArtistsMapper,
                            SearchCacheService cacheService,
                            @SpotifyProxyService SearchService searchService) {
        this.artistMapper = artistMapper;
        this.cachedArtistsMapper = cachedArtistsMapper;
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
    public CachedArtistsDto showAllCached(@RequestParam(value = "page", defaultValue = "1") Integer page) {
        Page<Artist> artists = cacheService.getAllCashed(page);
        return cachedArtistsMapper.toDto(artists);
    }
}
