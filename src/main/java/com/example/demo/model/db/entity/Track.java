package com.example.demo.model.db.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.Duration;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "tracks")
@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
public class Track{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "track_name")
    private String trackName;
    @Column(name = "track_artist")
    private String trackArtist;
    @Column(name = "track_album")
    private String trackAlbum;
    @Column(name = "track_genre")
    private String trackGenre;
    @Column(name = "track_duration")
    private Duration trackDuration;

    @ManyToMany(mappedBy = "tracks")
    @JsonIgnore
    private Set<Alumni> alumni = new HashSet<>();

    @ManyToMany(mappedBy = "tracks")
    @JsonIgnore
    private Set<Playlist> playlists = new HashSet<>();
}
