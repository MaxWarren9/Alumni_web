package com.example.demo.service;

import com.example.demo.Utils.PaginationUtil;
import com.example.demo.exceptions.CustomException;
import com.example.demo.model.db.entity.Alumni;
import com.example.demo.model.db.entity.Playlist;
import com.example.demo.model.db.repository.PlaylistRepo;
import com.example.demo.model.dto.request.PlaylistInfoRequest;
import com.example.demo.model.dto.request.PlaylistToAlumniRequest;
import com.example.demo.model.dto.response.PlaylistInfoResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class PlaylistService {

    private final ObjectMapper mapper;
    private final PlaylistRepo playlistRepo;
    private AlumniService alumniService;


    public PlaylistInfoResponse createPlaylist(PlaylistInfoRequest playlistInfoRequest) {
        Playlist playlist = mapper.convertValue(playlistInfoRequest, Playlist.class);
        Playlist save = playlistRepo.save(playlist);
        return mapper.convertValue(save, PlaylistInfoResponse.class);
    }

    public PlaylistInfoResponse getPlaylist(long id) {
        Playlist playlist = playlistRepo.findById(id).orElseThrow(() -> new CustomException("Playlist not found", HttpStatus.NOT_FOUND));
        return mapper.convertValue(playlist, PlaylistInfoResponse.class);
    }

    public Playlist getPlaylistFromDB(long id) {
        return playlistRepo.findById(id).orElseThrow(() -> new CustomException("Playlist not found", HttpStatus.NOT_FOUND));
    }

    public Page<PlaylistInfoResponse> getAllPlaylists(Integer page, Integer perPage, String sort, Sort.Direction order, String filter) {
        Pageable playlistPageRequest = PaginationUtil.getPageRequest(page, perPage, sort, order);
        Page<Playlist> playlistPage;
        if (filter != null && !filter.isEmpty()) {
            playlistPage = playlistRepo.findByName(filter, playlistPageRequest);
        } else {
            playlistPage = playlistRepo.findAll(playlistPageRequest);
        }

        List<PlaylistInfoResponse> content = playlistPage.getContent().stream()
                .map(p -> mapper.convertValue(p, PlaylistInfoResponse.class))
                .collect(Collectors.toList());

        return new PageImpl<>(content, playlistPageRequest, playlistPage.getTotalElements());
    }

    public PlaylistInfoResponse updatePlaylist(Long id, PlaylistInfoRequest playlistInfoRequest) {
        Playlist playlist = getPlaylistFromDB(id);
        playlist.setPlaylistName(playlistInfoRequest.getPlaylistName() == null ? playlist.getPlaylistName() : playlistInfoRequest.getPlaylistName());
        playlist.setPlaylistOwner(playlistInfoRequest.getPlaylistOwner() == null ? playlist.getPlaylistOwner() : playlistInfoRequest.getPlaylistOwner());
        Playlist save = playlistRepo.save(playlist);
        return mapper.convertValue(save, PlaylistInfoResponse.class);
    }

    public void deletePlaylist(long id) {
        playlistRepo.deleteById(id);
    }

    public void addPlaylistToAlumni(PlaylistToAlumniRequest request) {
        Playlist playlist = playlistRepo.findById(request.getPlaylistId()).orElseThrow(() ->
                new CustomException("Playlist not found", HttpStatus.NOT_FOUND));

        Alumni alumniFromDB = alumniService.getAlumniFromDB(request.getAlumniId());

        if (alumniFromDB == null) {
            throw new CustomException("Alumni not found", HttpStatus.NOT_FOUND);
        }

        playlist.setAlumni(alumniFromDB);
        alumniFromDB.getPlaylists().add(playlist);
        playlistRepo.save(playlist);
    }
}
