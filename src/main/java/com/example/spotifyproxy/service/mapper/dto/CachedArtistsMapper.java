package com.example.spotifyproxy.service.mapper.dto;

import com.example.spotifyproxy.model.Artist;
import com.example.spotifyproxy.model.dto.CachedArtistsDto;
import com.example.spotifyproxy.model.dto.LiteArtistDto;
import com.example.spotifyproxy.service.mapper.DtoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class CachedArtistsMapper implements DtoMapper<CachedArtistsDto, Page<Artist>> {
    @Value("${pageable.domain}")
    private String domainName;
    private final DtoMapper<LiteArtistDto, Artist> artistMapper;

    @Override
    public CachedArtistsDto toDto(Page<Artist> page) {
        List<LiteArtistDto> liteArtists = page.stream()
                .map(artistMapper::toDto)
                .collect(Collectors.toList());
        String endpoint = domainName + "/search?page=";
        int totalPages = page.isEmpty() ? 1 : page.getTotalPages();
        int currPage = page.getNumber() + 1;
        int nextPage = page.hasNext() ? currPage + 1 : currPage;
        int prevPage = page.isFirst() ? 1 : currPage - 1;
        return new CachedArtistsDto()
                .setArtists(liteArtists)
                .setCurrentPage(currPage)
                .setTotalPages(totalPages)
                .setNextPage(endpoint + nextPage)
                .setPreviousPage(endpoint + prevPage)
                .setIsLastPage(page.isLast());
    }
}
