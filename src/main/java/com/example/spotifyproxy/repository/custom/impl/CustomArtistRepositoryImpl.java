package com.example.spotifyproxy.repository.custom.impl;

import com.example.spotifyproxy.model.Artist;
import com.example.spotifyproxy.repository.custom.CustomArtistRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.Optional;

@Repository
public class CustomArtistRepositoryImpl implements CustomArtistRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    @Override
    public Optional<Artist> findArtistByArtistName(String artistName) {
        Artist artist;
        try {
            artist = entityManager.createQuery("select a from Artist a left join fetch a.genres where a.artistName = :name", Artist.class)
                    .setParameter("name", artistName)
                    .getSingleResult();
            artist = entityManager.createQuery("select a from Artist a left join fetch a.songs where a = :artist", Artist.class)
                    .setParameter("artist", artist)
                    .getSingleResult();
        } catch (NoResultException ex) {
            return Optional.empty();
        }
        return Optional.of(artist);
    }

    @Transactional
    @Override
    public Optional<Artist> findArtistBySpotifyId(String spotifyId) {
        Artist artist;
        try {
            artist = entityManager.createQuery("select a from Artist a left join fetch a.genres where a.spotifyId = :spotifyId", Artist.class)
                    .setParameter("spotifyId", spotifyId)
                    .getSingleResult();
            artist = entityManager.createQuery("select a from Artist a left join fetch a.songs where a = :artist", Artist.class)
                    .setParameter("artist", artist)
                    .getSingleResult();

        } catch (NoResultException ex) {
            return Optional.empty();
        }
        return Optional.ofNullable(artist);
    }
}
