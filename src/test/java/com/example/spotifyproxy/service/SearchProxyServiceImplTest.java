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
public class SearchProxyServiceImplTest {
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
        Mockito.when(searchService.findArtistByName(Mockito.anyString()))
                .thenReturn(expectedArtist);
        Artist artist = searchServiceProxy.findArtistByName("some-artist");
        Assertions.assertNotNull(artist.getId());

        Optional<Artist> optionalArtistFromDb = artistRepository.findById(expectedArtist.getId());
        Assertions.assertTrue(optionalArtistFromDb.isPresent(), "Artist was not saved to cache");
        Artist artistFromDb = optionalArtistFromDb.get();

        Assertions.assertEquals(expectedArtist.getArtistName(), artistFromDb.getArtistName());
        Assertions.assertEquals(expectedArtist.getFollowers(), artistFromDb.getFollowers());
        Assertions.assertEquals(expectedArtist.getSpotifyId(), artistFromDb.getSpotifyId());
    }

    @Test
    @Sql("/script/init_one_artist_exists.sql")
    void findArtistByName_Found_Ok() {
        Artist artist = searchServiceProxy.findArtistByName("some-artist");
        Assertions.assertNotNull(artist.getId());
        Assertions.assertEquals(expectedArtist.getArtistName(), artist.getArtistName());
        Assertions.assertEquals(expectedArtist.getFollowers(), artist.getFollowers());
    }

    @Test
    @Sql("/script/init_one_artist_exists.sql")
    void findArtistById_Found_Ok() {
        Artist artist = searchServiceProxy.findArtistById("SomeArtistId");
        Assertions.assertNotNull(artist.getId());
        Assertions.assertEquals(expectedArtist.getArtistName(), artist.getArtistName());
        Assertions.assertEquals(expectedArtist.getFollowers(), artist.getFollowers());
    }

    @Test
    void findArtistById_NotFound_Ok() {
        Mockito.when(searchService.findArtistById(Mockito.anyString()))
                .thenReturn(expectedArtist);
        Artist artist = searchServiceProxy.findArtistById("SomeArtistId");
        Assertions.assertNotNull(artist.getId());

        Optional<Artist> optionalArtistFromDb = artistRepository.findById(expectedArtist.getId());
        Assertions.assertTrue(optionalArtistFromDb.isPresent(), "Artist was not saved to cache");
        Artist artistFromDb = optionalArtistFromDb.get();

        Assertions.assertEquals(expectedArtist.getArtistName(), artistFromDb.getArtistName());
        Assertions.assertEquals(expectedArtist.getFollowers(), artistFromDb.getFollowers());
        Assertions.assertEquals(expectedArtist.getSpotifyId(), artistFromDb.getSpotifyId());
    }
}
