package com.example.spotifyproxy.service;

import com.example.spotifyproxy.model.Artist;
import com.example.spotifyproxy.repository.ArtistRepository;
import com.example.spotifyproxy.service.component.ArtistSaver;
import com.example.spotifyproxy.service.impl.SearchServiceImpl;
import com.example.spotifyproxy.service.impl.SearchServiceProxyImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Optional;

@SpringBootTest
@AutoConfigureTestDatabase
@Transactional
class SearchProxyServiceImplTest {
    @Mock
    private SearchServiceImpl searchService;
    @Autowired
    private ArtistRepository artistRepository;
    @Autowired
    private ArtistSaver artistSaver;
    private SearchServiceProxyImpl searchServiceProxy;
    private Artist expectedArtist;

    @BeforeEach
    void setUp() {
        expectedArtist = new Artist()
                .setArtistName("some-artist")
                .setSongs(new ArrayList<>())
                .setGenres(new ArrayList<>())
                .setFollowers(5000)
                .setSpotifyId("SomeArtistId");
        searchServiceProxy = new SearchServiceProxyImpl(artistRepository, searchService, artistSaver);
    }

    @Test
    void findArtistByName_NotFound_Ok() {
        Mockito.when(searchService.findArtistByName("some-artist"))
                .thenReturn(expectedArtist);
        Artist artist = searchServiceProxy.findArtistByName("some-artist");
        Assertions.assertNotNull(artist.getId(), "Artist ID is null");

        Optional<Artist> artistFromDb = artistRepository.findById(artist.getId());
        Assertions.assertTrue(artistFromDb.isPresent(), "Artist with returned ID was not found");

        Assertions.assertEquals(expectedArtist, artist, "Expected artist and returned one from method are different");
        Assertions.assertEquals(expectedArtist, artistFromDb.get(), "Expected artist and returned one from DB are different");
    }

    @Test
    @Sql("/script/init_one_artist_exists.sql")
    void findArtistByName_Found_Ok() {
        Artist artist = searchServiceProxy.findArtistByName("some-artist");
        Assertions.assertNotNull(artist.getId(), "Artist ID is null");
        Assertions.assertEquals(expectedArtist.getArtistName(), artist.getArtistName(), "Artist name is different");
        Assertions.assertEquals(expectedArtist.getFollowers(), artist.getFollowers(), "Artist follower number is different");
    }

    @Test
    @Sql("/script/init_one_artist_exists.sql")
    void findArtistById_Found_Ok() {
        Artist artist = searchServiceProxy.findArtistById("SomeArtistId");
        Assertions.assertNotNull(artist.getId(), "Artist ID is null");
        Assertions.assertEquals(expectedArtist.getArtistName(), artist.getArtistName(), "Artist name is different");
        Assertions.assertEquals(expectedArtist.getFollowers(), artist.getFollowers(), "Artist follower number is different");
    }

    @Test
    void findArtistById_NotFound_Ok() {
        Mockito.when(searchService.findArtistById("SomeArtistId"))
                .thenReturn(expectedArtist);
        Artist artist = searchServiceProxy.findArtistById("SomeArtistId");
        Assertions.assertNotNull(artist.getId(), "Artist ID is null");

        Optional<Artist> artistFromDb = artistRepository.findById(artist.getId());
        Assertions.assertTrue(artistFromDb.isPresent(), "Artist with returned ID was not found");

        Assertions.assertEquals(expectedArtist, artist, "Expected artist and returned one from method are different");
        Assertions.assertEquals(expectedArtist, artistFromDb.get(), "Expected artist and returned one from DB are different");
    }
}
