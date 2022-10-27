package com.example.spotifyproxy.repository;

import com.example.spotifyproxy.model.Genre;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface GenreRepository extends JpaRepository<Genre, Long> {
    List<Genre> findAllByNameIn(Collection<String> name);
}
