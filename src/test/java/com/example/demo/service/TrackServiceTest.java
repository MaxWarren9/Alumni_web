package com.example.demo.service;

import com.example.demo.exceptions.CustomException;
import com.example.demo.model.db.entity.Alumni;
import com.example.demo.model.db.entity.Playlist;
import com.example.demo.model.db.entity.Track;
import com.example.demo.model.db.repository.TrackRepo;
import com.example.demo.model.dto.request.TrackInfoRequest;
import com.example.demo.model.dto.request.TrackToAlumniRequest;
import com.example.demo.model.dto.request.TrackToPlaylistRequest;
import com.example.demo.model.dto.response.TrackInfoResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.*;

import java.time.Duration;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class TrackServiceTest {

    @InjectMocks
    private TrackService trackService;

    @Spy
    private ObjectMapper objectMapper = new ObjectMapper();

    @Mock
    private TrackRepo trackRepo;

    @Mock
    private AlumniService alumniService;

    @Mock
    private PlaylistService playlistService;


    @Test
    public void createTrack() {
        TrackInfoRequest trackInfoRequest = new TrackInfoRequest();
        trackInfoRequest.setTrackName("test");

        Track track = new Track();
        track.setId(1L);
        track.setTrackName("test");

        when(trackRepo.save(any(Track.class))).thenReturn(track);
        TrackInfoResponse trackInfoResponse = trackService.createTrack(trackInfoRequest);
        assertEquals(trackInfoResponse.getTrackName(), track.getTrackName());
    }

    @Test
    public void getTrack() {
        Track track = new Track();
        track.setId(1L);
        track.setTrackName("test");

        when(trackRepo.findById(1L)).thenReturn(Optional.of(track));
        TrackInfoResponse trackInfoResponse = trackService.getTrack(1L);
        assertEquals(trackInfoResponse.getTrackName(), track.getTrackName());
    }

    @Test
    public void getTrackFromDb() {
        Track track = new Track();
        track.setId(1L);
        when(trackRepo.findById(track.getId())).thenReturn(Optional.of(track));
        TrackInfoResponse result = trackService.getTrack(track.getId());
        assertEquals(track.getTrackName(), result.getTrackName());
    }

    @Test
    public void updateTrack_withNonNullFields_shouldUpdateAllFields() {

        TrackInfoRequest trackInfoRequest = new TrackInfoRequest();
        trackInfoRequest.setTrackAlbum("New Album");
        trackInfoRequest.setTrackGenre("New Genre");
        trackInfoRequest.setTrackDuration(Duration.ofSeconds(120));

        Track track = new Track();
        track.setId(1L);
        track.setTrackAlbum("Old Album");
        track.setTrackGenre("Old Genre");
        track.setTrackDuration(Duration.ofSeconds(60));

        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        when(trackRepo.findById(track.getId())).thenReturn(Optional.of(track));

        trackService.updateTrack(track.getId(), trackInfoRequest);

        assertEquals("New Album", track.getTrackAlbum());
        assertEquals("New Genre", track.getTrackGenre());
        assertEquals(Duration.ofSeconds(120), track.getTrackDuration());
        verify(trackRepo, times(1)).save(track);
    }

    @Test
    public void updateTrack_withNullFields_shouldNotUpdateNullFields() {

        TrackInfoRequest trackInfoRequest = new TrackInfoRequest();
        trackInfoRequest.setTrackAlbum(null);
        trackInfoRequest.setTrackGenre("New Genre");
        trackInfoRequest.setTrackDuration(null);

        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        Track track = new Track();
        track.setId(1L);
        track.setTrackAlbum("Old Album");
        track.setTrackGenre("Old Genre");
        track.setTrackDuration(Duration.ofSeconds(60));

        when(trackRepo.findById(track.getId())).thenReturn(Optional.of(track));

        trackService.updateTrack(track.getId(), trackInfoRequest);

        assertEquals("Old Album", track.getTrackAlbum());
        assertEquals("New Genre", track.getTrackGenre());
        assertEquals(Duration.ofSeconds(60), track.getTrackDuration());
        verify(trackRepo, times(1)).save(track);
    }

    @Test
    public void getAllTracks_withoutFilter_shouldReturnPage() {

        Track track1 = new Track();
        track1.setId(1L);
        track1.setTrackName("Track A");

        Track track2 = new Track();
        track2.setId(2L);
        track2.setTrackName("Track B");

        List<Track> tracks = List.of(track1, track2);
        Page<Track> trackPage = new PageImpl<>(tracks);

        Pageable pageable = PageRequest.of(0, 10, Sort.Direction.ASC, "trackName");

        when(trackRepo.findAll(pageable)).thenReturn(trackPage);

        TrackInfoResponse response1 = new TrackInfoResponse();
        response1.setId(1L);
        response1.setTrackName("Track A");

        TrackInfoResponse response2 = new TrackInfoResponse();
        response2.setId(2L);
        response2.setTrackName("Track B");

        when(objectMapper.convertValue(track1, TrackInfoResponse.class)).thenReturn(response1);
        when(objectMapper.convertValue(track2, TrackInfoResponse.class)).thenReturn(response2);

        Page<TrackInfoResponse> result = trackService.getAllTracks(0, 10, "trackName", Sort.Direction.ASC, null);


        assertEquals(2, result.getTotalElements());
        assertEquals("Track A", result.getContent().get(0).getTrackName());
        assertEquals("Track B", result.getContent().get(1).getTrackName());
        verify(trackRepo, times(1)).findAll(pageable);
    }

    @Test
    public void getAllTracks_withFilter_shouldReturnFilteredPage() {

        Track track1 = new Track();
        track1.setId(1L);
        track1.setTrackName("Track A");

        Track track2 = new Track();
        track2.setId(2L);
        track2.setTrackName("Track B");

        List<Track> tracks = List.of(track1);
        Page<Track> trackPage = new PageImpl<>(tracks);

        Pageable pageable = PageRequest.of(0, 10, Sort.Direction.ASC, "trackName");

        when(trackRepo.findAllByTrackNameJPQL("Track", pageable)).thenReturn(trackPage);

        TrackInfoResponse response1 = new TrackInfoResponse();
        response1.setId(1L);
        response1.setTrackName("Track A");

        when(objectMapper.convertValue(track1, TrackInfoResponse.class)).thenReturn(response1);

        Page<TrackInfoResponse> result = trackService.getAllTracks(0, 10, "trackName", Sort.Direction.ASC, "Track");

        assertEquals(1, result.getTotalElements());
        assertEquals("Track A", result.getContent().get(0).getTrackName());
        verify(trackRepo, times(1)).findAllByTrackNameJPQL("Track", pageable);  // Проверяем, что был вызван findAllByTrackNameJPQL
    }
    @Test
    public void updateTrack() {

        TrackInfoRequest trackInfoRequest = new TrackInfoRequest();
        trackInfoRequest.setTrackName("test");
        trackInfoRequest.setTrackArtist("test artist");
        trackInfoRequest.setTrackAlbum("test album");
        trackInfoRequest.setTrackDuration(Duration.ofSeconds(10));
        trackInfoRequest.setTrackGenre("test genre");

        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        Track track = new Track();
        track.setId(1L);
        when(trackRepo.findById(track.getId())).thenReturn(Optional.of(track));
        trackService.updateTrack(track.getId(), trackInfoRequest);
        verify(trackRepo, times(1)).save(any(Track.class));
    }

    @Test
    public void deleteTrack() {
        long trackId = 1L;
        trackService.deleteTrack(trackId);
        verify(trackRepo, times(1)).deleteById(trackId);
    }

    @Test
    public void addTrackToAlumni() {
        Track track = new Track();
        track.setId(1L);

        when(trackRepo.findById(track.getId())).thenReturn(Optional.of(track));

        Alumni alumni = new Alumni();
        alumni.setId(1L);
        alumni.setTracks(new HashSet<>());

        when(alumniService.getAlumniFromDB(alumni.getId())).thenReturn(alumni);

        TrackToAlumniRequest trackToAlumniRequest = TrackToAlumniRequest.builder()
                .trackId(track.getId())
                .alumniId(alumni.getId())
                .build();
        trackService.addTrackToAlumni(trackToAlumniRequest);

        verify(alumniService, times(1)).getAlumniFromDB(1L);
    }

    @Test
    public void addTrackToPlaylist() {
        Track track = new Track();
        track.setId(1L);

        when(trackRepo.findById(track.getId())).thenReturn(Optional.of(track));

        Playlist playlist = new Playlist();
        playlist.setId(1L);
        playlist.setTracks(new HashSet<>());

        when(playlistService.getPlaylistFromDB(playlist.getId())).thenReturn(playlist);
        TrackToPlaylistRequest trackToPlaylistRequest = TrackToPlaylistRequest.builder()
                .trackId(track.getId())
                .playlistId(playlist.getId())
                .build();
        trackService.addTrackToPlaylist(trackToPlaylistRequest);
        verify(playlistService, times(1)).getPlaylistFromDB(1L);
    }

    @Test(expected = CustomException.class)
    public void addTrackToAlumni_shouldThrowIfAlumniNotFound() {
        Track track = new Track();
        track.setId(1L);

        when(trackRepo.findById(track.getId())).thenReturn(Optional.of(track));
        when(alumniService.getAlumniFromDB(1L)).thenReturn(null);

        TrackToAlumniRequest request = TrackToAlumniRequest.builder()
                .trackId(track.getId())
                .alumniId(1L)
                .build();

        trackService.addTrackToAlumni(request);
    }


    @Test(expected = CustomException.class)
    public void addTrackToPlaylist_shouldThrowIfPlaylistNotFound() {
        Track track = new Track();
        track.setId(1L);
        when(trackRepo.findById(track.getId())).thenReturn(Optional.of(track));
        when(playlistService.getPlaylistFromDB(1L)).thenReturn(null);

        TrackToPlaylistRequest request = TrackToPlaylistRequest.builder()
                .trackId(track.getId())
                .playlistId(1L)
                .build();

        trackService.addTrackToPlaylist(request);
    }
}