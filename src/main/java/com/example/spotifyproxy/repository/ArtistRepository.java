package com.example.spotifyproxy.repository;

import com.example.spotifyproxy.model.Artist;
import com.example.spotifyproxy.repository.custom.CustomArtistRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ArtistRepository extends JpaRepository<Artist, Long>, CustomArtistRepository {
    Optional<Artist> findArtistBySpotifyId(String spotifyId);

    Optional<Artist> findArtistByArtistName(String artistName);

    Page<Artist> findAll(Pageable pageable);
}
