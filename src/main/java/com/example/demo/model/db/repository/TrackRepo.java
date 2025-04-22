package com.example.demo.model.db.repository;

import com.example.demo.model.db.entity.Track;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TrackRepo extends JpaRepository<Track, Long> {
    Track findByTrackName(String trackName);

    @Query("select t from Track t where t.trackName is not null and t.trackName <> '' and upper(t.trackName) like upper(:filter)")
    Page<Track> findAllByTrackNameJPQL(@Param("filter") String filter, Pageable pageable);

    @Query(nativeQuery = true, value = "select * from tracks where id = 1")
    Track getTrack();
}
