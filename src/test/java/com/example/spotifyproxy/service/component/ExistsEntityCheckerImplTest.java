package com.example.spotifyproxy.service.component;

import com.example.spotifyproxy.model.Artist;
import com.example.spotifyproxy.model.Genre;
import com.example.spotifyproxy.model.Song;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@AutoConfigureTestDatabase
@Transactional
public class ExistsEntityCheckerImplTest {
    @Autowired
    private ExistsEntityChecker checker;
    private List<Genre> genres;
    private List<Song> songs;

    @BeforeEach
    void setUp() {
        genres = new ArrayList<>();
        genres.add(new Genre().setName("pop"));
        genres.add(new Genre().setName("house"));
        songs = new ArrayList<>();
        songs.add(new Song().setName("firstSong").setSpotifySongId("firstSongId"));
        songs.add(new Song().setName("secondSong").setSpotifySongId("secondSongId"));
    }

    @Test
    void processExistsGenres_Not_Found_Ok() {
        Artist artist = new Artist()
                .setArtistName("Some Artist")
                .setGenres(genres);

        List<Genre> processedGenres = checker.processExistsGenres(artist.getGenres());

        Assertions.assertEquals(2, processedGenres.size(), "Collection size of genres is different");
        Assertions.assertNull(processedGenres.get(0).getId(), "Should be null because not found in cache");
        Assertions.assertNull(processedGenres.get(1).getId(), "Should be null because not found in cache");
    }

    @Test
    @Sql("/script/init_one_genre_exists.sql")
    void processExistsGenres_Found_Ok() {
        Artist artist = new Artist()
                .setArtistName("Some Artist")
                .setGenres(genres);

        List<Genre> processedGenres = checker.processExistsGenres(artist.getGenres());

        Assertions.assertEquals(2, processedGenres.size(), "Collection size of genres is different");
        Assertions.assertNull(processedGenres.get(0).getId(), "'House' genre should not contains id field as it's not found");
        Assertions.assertNotNull(processedGenres.get(1).getId(), "'Pop' genre should contains id field as it's found");
    }

    @Test
    void processExistsSongs_Not_Found_Ok() {
        Artist artist = new Artist()
                .setArtistName("Some Artist")
                .setSongs(songs);

        List<Song> processedSongs = checker.processExistsSongs(artist.getSongs());

        Assertions.assertEquals(2, processedSongs.size(), "Collection size of songs is different");
        Assertions.assertNull(processedSongs.get(0).getId(), "Should be null because not found in cache");
        Assertions.assertNull(processedSongs.get(1).getId(), "Should be null because not found in cache");
    }

    @Test
    @Sql("/script/init_one_song_exists.sql")
    void processExistsSongs_Found_Ok() {
        Artist artist = new Artist()
                .setArtistName("Some Artist")
                .setSongs(songs);

        List<Song> processedSongs = checker.processExistsSongs(artist.getSongs());

        Assertions.assertEquals(2, processedSongs.size(), "Collection size of genres is different");
        Assertions.assertNull(processedSongs.get(0).getId(), "Second song should not contains id field as it's not found");
        Assertions.assertNotNull(processedSongs.get(1).getId(), "First song must contains id field as it's found");
    }
}
