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
import lombok.AllArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class TrackService {
    private final ObjectMapper mapper;
    private final TrackRepo trackRepo;
    private final PlaylistService playlistService;
    private AlumniService alumniService;

    public TrackInfoResponse createTrack(TrackInfoRequest trackInfoRequest) {
        Track track = mapper.convertValue(trackInfoRequest, Track.class);
        Track save = trackRepo.save(track);
        return mapper.convertValue(save, TrackInfoResponse.class);
    }

    public TrackInfoResponse getTrack(long trackId) {
        Track track = trackRepo.findById(trackId).orElseThrow(()-> new CustomException("Track Not Found", HttpStatus.NOT_FOUND));
        return mapper.convertValue(track, TrackInfoResponse.class);
    }
    public Track getTrackFromDb(long trackId) {
        return trackRepo.findById(trackId).orElseThrow(() -> new CustomException("Track not found",
                HttpStatus.NOT_FOUND));
    }

//    public Page<TrackInfoResponse> getAllTracks(Integer page, Integer perPage, String sort, Sort.Direction order, String filter) {
//        Pageable pageRequest = PaginationUtil.getPageRequest(page, perPage, sort, order);
//        Page<Track> trackPage;
//        if (filter == null || filter.isEmpty()) {
//            trackPage = trackRepo.findAll(pageRequest);
//        } else {
//            trackPage = trackRepo.findAllByTrackName(filter, pageRequest);
//        }
//
//        List<TrackInfoResponse> content = trackPage.getContent().stream()
//                .map(p -> mapper.convertValue(p, TrackInfoResponse.class))
//                .collect(Collectors.toList());
//
//        return new PageImpl<>(content, pageRequest, trackPage.getTotalElements());
//    }

    public Page<TrackInfoResponse> getAllTracks(Integer page, Integer perPage, String sort, Sort.Direction order, String filter) {

        Pageable pageRequest = PageRequest.of(page, perPage, Sort.by(order, sort));
        Page<Track> trackPage;

        if (filter == null || filter.isEmpty()) {
            trackPage = trackRepo.findAll(pageRequest);
        } else {
            trackPage = trackRepo.findAllByTrackNameJPQL("%" + filter + "%", pageRequest);
        }

        List<TrackInfoResponse> content = trackPage.getContent().stream()
                .map(p -> mapper.convertValue(p, TrackInfoResponse.class))
                .collect(Collectors.toList());

        return new PageImpl<>(content, pageRequest, trackPage.getTotalElements());
    }

    public TrackInfoResponse updateTrack(long trackId, TrackInfoRequest trackInfoRequest) {
        Track track = getTrackFromDb(trackId);
        track.setTrackName(trackInfoRequest.getTrackName());
        track.setTrackArtist(trackInfoRequest.getTrackArtist());
        track.setTrackAlbum(trackInfoRequest.getTrackAlbum() == null ? track.getTrackAlbum() : trackInfoRequest.getTrackAlbum());
        track.setTrackGenre(trackInfoRequest.getTrackGenre()==null ? track.getTrackGenre() : trackInfoRequest.getTrackGenre());
        track.setTrackDuration(trackInfoRequest.getTrackDuration() == null ? track.getTrackDuration() : trackInfoRequest.getTrackDuration());
        trackRepo.save(track);
        return mapper.convertValue(track, TrackInfoResponse.class);
    }

    public void deleteTrack(long trackId) {
        trackRepo.deleteById(trackId);
    }

    public void addTrackToAlumni(TrackToAlumniRequest request) {
        Track track = trackRepo.findById(request.getTrackId()).orElseThrow(() -> new CustomException("Track not found", HttpStatus.NOT_FOUND));

        Alumni alumniFromDB = alumniService.getAlumniFromDB(request.getAlumniId());

        if (alumniFromDB == null) {
            throw new CustomException("Alumni not found", HttpStatus.NOT_FOUND);
        }
        alumniFromDB.getTracks().add(track);
        track.getAlumni().add(alumniFromDB);
        trackRepo.save(track);
    }

    public void addTrackToPlaylist(TrackToPlaylistRequest request) {
        Track track = trackRepo.findById(request.getTrackId()).orElseThrow(() -> new CustomException("Track not found", HttpStatus.NOT_FOUND));
        Playlist playlist = playlistService.getPlaylistFromDB(request.getPlaylistId());

        if (playlist == null) {
            throw new CustomException("Playlist not found", HttpStatus.NOT_FOUND);
        }

        playlist.getTracks().add(track);
        track.getPlaylists().add(playlist);
        trackRepo.save(track);
    }

}
