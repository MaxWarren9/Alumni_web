package com.example.demo.model.db.repository;

import com.example.demo.model.db.entity.Playlist;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PlaylistRepo extends JpaRepository<Playlist, Long> {


    @Query(nativeQuery = true, value = "SELECT * FROM playlists WHERE playlist_name IS NOT NULL AND playlist_name != '' AND playlist_name LIKE %:filter%")
    Page<Playlist> findByName(@Param("filter") String filter, Pageable pageable);

    @Query(nativeQuery = true, value = "select * from playlists where id = 1")
    Playlist findPlaylist();
}
