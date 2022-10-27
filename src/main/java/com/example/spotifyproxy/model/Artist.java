package com.example.spotifyproxy.model;

import lombok.Getter;
import lombok.Setter;
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

@Entity
@Table(name = "artists")
@Getter
@Setter
@Accessors(chain = true)
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
    private List<Genre> genres;
    @ManyToMany(cascade = CascadeType.PERSIST)
    @JoinTable(joinColumns = @JoinColumn(name = "artist_id"),
            inverseJoinColumns = @JoinColumn(name = "song_id"))
    private List<Song> songs;
}
