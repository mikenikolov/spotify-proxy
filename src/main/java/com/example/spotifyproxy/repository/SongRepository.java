package com.example.spotifyproxy.repository;

import com.example.spotifyproxy.model.Song;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface SongRepository extends JpaRepository<Song, Long> {
    List<Song> findAllBySpotifySongIdIn(Collection<@NonNull String> songSpotifyId);
}
