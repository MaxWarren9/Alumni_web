package com.example.demo.service;

import com.example.demo.exceptions.CustomException;
import com.example.demo.model.db.entity.Alumni;
import com.example.demo.model.db.entity.Playlist;
import com.example.demo.model.db.repository.PlaylistRepo;
import com.example.demo.model.dto.request.PlaylistInfoRequest;
import com.example.demo.model.dto.request.PlaylistToAlumniRequest;
import com.example.demo.model.dto.response.PlaylistInfoResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.*;

import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class PlaylistServiceTest {

    @InjectMocks
    private PlaylistService playlistService;


    @Mock
    private ObjectMapper objectMapper;

    @Mock
    private PlaylistRepo playlistRepo;

    @Mock
    private AlumniService alumniService;


    @Test
    public void createPlaylist(){
        PlaylistInfoRequest playlistInfoRequest = new PlaylistInfoRequest();
        playlistInfoRequest.setPlaylistName("test");

        Playlist playlist = new Playlist();
        playlist.setPlaylistName("test");
        playlist.setId(1L);

        PlaylistInfoResponse playlistInfoResponse = new PlaylistInfoResponse();
        playlistInfoResponse.setPlaylistName("test");

        when(objectMapper.convertValue(any(PlaylistInfoRequest.class), eq(Playlist.class))).thenReturn(playlist);
        when(playlistRepo.save(any(Playlist.class))).thenReturn(playlist);
        when(objectMapper.convertValue(any(Playlist.class), eq(PlaylistInfoResponse.class))).thenReturn(playlistInfoResponse);

        PlaylistInfoResponse response = playlistService.createPlaylist(playlistInfoRequest);

        assertEquals("test", response.getPlaylistName());
    }

    @Test
    public void getPlaylist() {
        Playlist playlist = new Playlist();
        playlist.setId(1L);
        playlist.setPlaylistName("test");

        PlaylistInfoResponse playlistInfoResponse = new PlaylistInfoResponse();
        playlistInfoResponse.setPlaylistName("test");

        when(playlistRepo.findById(1L)).thenReturn(Optional.of(playlist));
        when(objectMapper.convertValue(playlist, PlaylistInfoResponse.class)).thenReturn(playlistInfoResponse);

        PlaylistInfoResponse result = playlistService.getPlaylist(1L);
        assertEquals(result.getPlaylistName(), playlist.getPlaylistName());
    }

    @Test
    public void getPlaylistFromDB() {
        Playlist playlist = new Playlist();
        playlist.setId(1L);
        playlist.setPlaylistName("My Test Playlist");

        PlaylistInfoResponse response = new PlaylistInfoResponse();
        response.setPlaylistName("My Test Playlist");

        when(playlistRepo.findById(playlist.getId())).thenReturn(Optional.of(playlist));
        when(objectMapper.convertValue(any(Playlist.class), eq(PlaylistInfoResponse.class)))
                .thenReturn(response);

        PlaylistInfoResponse result = playlistService.getPlaylist(playlist.getId());

        assertEquals("My Test Playlist", result.getPlaylistName());
    }

    @Test
    public void getAllPlaylists_withoutFilter_shouldReturnPage() {
        Playlist playlist = new Playlist();
        playlist.setId(1L);
        playlist.setPlaylistName("test");

        Playlist playlist2 = new Playlist();
        playlist2.setId(2L);
        playlist2.setPlaylistName("test2");

        List<Playlist> playlists = new ArrayList<>();
        playlists.add(playlist);
        playlists.add(playlist2);
        Page<Playlist> playlistPage = new PageImpl<>(playlists);

        Pageable pageable = PageRequest.of(0, 10, Sort.Direction.ASC, "playlistName");
        when(playlistRepo.findAll(pageable)).thenReturn(playlistPage);

        PlaylistInfoResponse response1 = new PlaylistInfoResponse();
        response1.setPlaylistName("test");
        PlaylistInfoResponse response2 = new PlaylistInfoResponse();
        response2.setPlaylistName("test2");

        when(objectMapper.convertValue(playlist, PlaylistInfoResponse.class)).thenReturn(response1);
        when(objectMapper.convertValue(playlist2, PlaylistInfoResponse.class)).thenReturn(response2);

        Page<PlaylistInfoResponse> result = playlistService.getAllPlaylists(0, 10, "playlistName", Sort.Direction.ASC, null);

        assertEquals(result.getTotalElements(), 2);
        assertEquals(result.getContent().get(0).getPlaylistName(), playlist.getPlaylistName());
        assertEquals(result.getContent().get(1).getPlaylistName(), playlist2.getPlaylistName());
        verify(playlistRepo, times(1)).findAll(pageable);

    }

    @Test
    public void testGetAllPlaylists_WithFilter_ReturnsPage() {
        // Arrange
        int page = 0;
        int perPage = 10;
        String sort = "id";
        Sort.Direction order = Sort.Direction.ASC;
        String filter = "Test";

        Playlist playlist = new Playlist();
        playlist.setId(2L);
        playlist.setPlaylistName("Test Playlist");

        PlaylistInfoResponse response = new PlaylistInfoResponse();
        response.setId(2L);
        response.setPlaylistName("Test Playlist");

        Pageable pageable = PageRequest.of(page, perPage, order, sort);
        Page<Playlist> playlistPage = new PageImpl<>(Collections.singletonList(playlist), pageable, 1);

        when(playlistRepo.findByName(filter, pageable)).thenReturn(playlistPage);
        when(objectMapper.convertValue(playlist, PlaylistInfoResponse.class)).thenReturn(response);

        // Act
        Page<PlaylistInfoResponse> result = playlistService.getAllPlaylists(page, perPage, sort, order, filter);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.getContent().size());
        assertEquals("Test Playlist", result.getContent().get(0).getPlaylistName());

        verify(playlistRepo, times(1)).findByName(filter, pageable);
        verify(playlistRepo, never()).findAll(pageable);
        verify(objectMapper, times(1)).convertValue(playlist, PlaylistInfoResponse.class);
    }


    @Test
    public void updatePlaylist() {
        PlaylistInfoRequest playlistInfoRequest = new PlaylistInfoRequest();
        playlistInfoRequest.setPlaylistName("test");
        playlistInfoRequest.setPlaylistOwner("Max");
        playlistInfoRequest.setCreateDate(LocalDate.now());

        Playlist playlist = new Playlist();
        playlist.setId(1L);
        playlist.setPlaylistName("test");
        playlist.setPlaylistOwner("Max");
        playlist.setCreateDate(LocalDate.now());

        when(playlistRepo.findById(playlist.getId())).thenReturn(Optional.of(playlist));

        playlistService.updatePlaylist(playlist.getId(), playlistInfoRequest);

        assertEquals(playlist.getPlaylistName(), playlist.getPlaylistName());
        assertEquals(playlist.getPlaylistOwner(), playlist.getPlaylistOwner());
        assertEquals(playlist.getCreateDate(), playlist.getCreateDate());
        verify(playlistRepo, times(1)).findById(playlist.getId());

    }

    @Test
    public void updatePlaylist_withoutChanges() {
        PlaylistInfoRequest playlistInfoRequest = new PlaylistInfoRequest();
        playlistInfoRequest.setPlaylistName(null);
        playlistInfoRequest.setPlaylistOwner(null);
        playlistInfoRequest.setCreateDate(null);

        Playlist playlist = new Playlist();
        playlist.setId(1L);
        playlist.setPlaylistName("test");
        playlist.setPlaylistOwner("Max");
        playlist.setCreateDate(LocalDate.now());
        when(playlistRepo.findById(playlist.getId())).thenReturn(Optional.of(playlist));

        playlistService.updatePlaylist(playlist.getId(), playlistInfoRequest);

        assertEquals("test", playlist.getPlaylistName());
        assertEquals("Max", playlist.getPlaylistOwner());
        assertEquals(LocalDate.now(), playlist.getCreateDate());
        verify(playlistRepo, times(1)).findById(playlist.getId());
    }

    @Test
    public void deletePlaylist() {
       long playlistId = 1L;
       playlistService.deletePlaylist(playlistId);
       verify(playlistRepo, times(1)).deleteById(playlistId);
    }

    @Test
    public void addPlaylistToAlumni() {
        Playlist playlist = new Playlist();
        playlist.setId(1L);

        when(playlistRepo.findById(playlist.getId())).thenReturn(Optional.of(playlist));

        Alumni alumni = new Alumni();
        alumni.setId(1L);
        alumni.setTracks(new HashSet<>());

        when(alumniService.getAlumniFromDB(alumni.getId())).thenReturn(alumni);

        PlaylistToAlumniRequest playlistToAlumniRequest = PlaylistToAlumniRequest.builder()
                .playlistId(playlist.getId())
                .alumniId(alumni.getId())
                .build();
        playlistService.addPlaylistToAlumni(playlistToAlumniRequest);
        verify(alumniService, times(1)).getAlumniFromDB(alumni.getId());
    }

    @Test(expected = CustomException.class)
    public void addPlaylistToAlumni_should_throw_exception() {
        Playlist playlist = new Playlist();
        playlist.setId(1L);
        when(playlistRepo.findById(playlist.getId())).thenReturn(Optional.empty());
        when(alumniService.getAlumniFromDB(1L)).thenReturn(null);

        PlaylistToAlumniRequest request = PlaylistToAlumniRequest.builder()
                .playlistId(playlist.getId())
                .alumniId(1L)
                .build();

        playlistService.addPlaylistToAlumni(request);
    }

    @Test(expected = CustomException.class)
    public void addPlaylistToAlumni_should_throw_exception2() {
        Playlist playlist = new Playlist();
        playlist.setId(1L);
        when(playlistRepo.findById(playlist.getId())).thenReturn(Optional.of(playlist));
        when(alumniService.getAlumniFromDB(1L)).thenReturn(null);

        PlaylistToAlumniRequest request = PlaylistToAlumniRequest.builder()
                .playlistId(playlist.getId())
                .alumniId(1L)
                .build();

        playlistService.addPlaylistToAlumni(request);
    }

}