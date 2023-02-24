package com.example.spotifyproxy.service.component;

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

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.both;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;

@SpringBootTest
@AutoConfigureTestDatabase
@Transactional
class ExistsEntityCheckerImplTest {
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
        List<Genre> processedGenres = checker.processExistsGenres(genres);

        Assertions.assertEquals(2, processedGenres.size(), "Collection size of genres is different");
        assertThat(processedGenres, containsInAnyOrder(
                both(hasProperty("name", is("house")))
                        .and(hasProperty("id", is(nullValue()))),
                both(hasProperty("name", is("pop")))
                        .and(hasProperty("id", is(nullValue())))
        ));
    }

    @Test
    @Sql("/script/init_one_genre_exists.sql")
    void processExistsGenres_Found_Ok() {
        List<Genre> processedGenres = checker.processExistsGenres(genres);

        Assertions.assertEquals(2, processedGenres.size(), "Collection size of genres is different");
        assertThat(processedGenres, containsInAnyOrder(
                both(hasProperty("name", is("house")))
                        .and(hasProperty("id", is(nullValue()))),

                both(hasProperty("name", is("pop")))
                        .and(hasProperty("id", is(1L)))
        ));
    }

    @Test
    void processExistsSongs_Not_Found_Ok() {
        List<Song> processedSongs = checker.processExistsSongs(songs);

        Assertions.assertEquals(2, processedSongs.size(), "Collection size of songs is different");
        assertThat(processedSongs, containsInAnyOrder(
                both(hasProperty("name", is("firstSong")))
                        .and(hasProperty("id", is(nullValue())))
                        .and(hasProperty("spotifySongId", is("firstSongId"))),

                both(hasProperty("name", is("secondSong")))
                        .and(hasProperty("id", is(nullValue())))
                        .and(hasProperty("spotifySongId", is("secondSongId")))
        ));
    }

    @Test
    @Sql("/script/init_one_song_exists.sql")
    void processExistsSongs_Found_Ok() {
        List<Song> processedSongs = checker.processExistsSongs(songs);

        assertThat(processedSongs, containsInAnyOrder(
                both(hasProperty("name", is("firstSong")))
                        .and(hasProperty("id", is(1L)))
                        .and(hasProperty("spotifySongId", is("firstSongId"))),

                both(hasProperty("name", is("secondSong")))
                        .and(hasProperty("id", is(nullValue())))
                        .and(hasProperty("spotifySongId", is("secondSongId")))
        ));
    }
}
