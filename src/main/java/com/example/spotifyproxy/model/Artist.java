package com.example.spotifyproxy.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "artists")
@Getter
@Setter
@Accessors(chain = true)
@ToString
public class Artist {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "artist_name")
    private String artistName;
    @Column(name = "spotify_artist_id")
    private String spotifyId;
    private int followers;
    @ManyToMany(cascade = CascadeType.PERSIST)
    @JoinTable(joinColumns = @JoinColumn(name = "artist_id"),
            inverseJoinColumns = @JoinColumn(name = "genre_id"))
    @ToString.Exclude
    private List<Genre> genres;
    @ManyToMany(cascade = CascadeType.PERSIST)
    @JoinTable(joinColumns = @JoinColumn(name = "artist_id"),
            inverseJoinColumns = @JoinColumn(name = "song_id"))
    @ToString.Exclude
    private List<Song> songs;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Artist artist = (Artist) o;
        return followers == artist.followers
                && Objects.equals(id, artist.id)
                && Objects.equals(artistName, artist.artistName)
                && Objects.equals(spotifyId, artist.spotifyId)
                && Objects.equals(genres, artist.genres)
                && Objects.equals(songs, artist.songs);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, artistName, spotifyId, followers, genres, songs);
    }


}
