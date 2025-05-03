package com.example.demo.controller;


import com.example.demo.model.dto.request.TrackInfoRequest;
import com.example.demo.model.dto.request.TrackToAlumniRequest;
import com.example.demo.model.dto.request.TrackToPlaylistRequest;
import com.example.demo.model.dto.response.TrackInfoResponse;
import com.example.demo.service.TrackService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Треки")
@RestController
@RequestMapping("/api/tracks")
@RequiredArgsConstructor

public class TrackController {

    private final TrackService trackService;

    @PostMapping
    @Operation(summary = "Создать трек")
    public TrackInfoResponse createTrack(@RequestBody TrackInfoRequest request) {
        return trackService.createTrack(request);
    }

    @GetMapping("/all")
    @Operation(summary = "Получить список треков")
    public ResponseEntity<List<TrackInfoResponse>> getAllTracks(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer perPage,
            @RequestParam(defaultValue = "trackName") String sort,
            @RequestParam(defaultValue = "ASC") Sort.Direction order,
            @RequestParam(required = false) String filter,
            Model model) {
        Page<TrackInfoResponse> tracks = trackService.getAllTracks(page, perPage, sort, order, filter);
        model.addAttribute("tracks", tracks.getContent());
        return ResponseEntity.ok(tracks.getContent());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Получить трек по id")
    public TrackInfoResponse getTrackById(@PathVariable("id") long id) {
        return trackService.getTrack(id);
    }

    @PostMapping("/{id}")
    @Operation(summary = "Обновить трек по id")
    public TrackInfoResponse updateTrackById(@PathVariable("id") long id, @RequestBody TrackInfoRequest request) {
        return trackService.updateTrack(id, request);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Удалить трек по id")
    public void deleteTrackById(@PathVariable("id") long id) {
        trackService.deleteTrack(id);
    }

    @PostMapping("/trackToPlaylist")
    @Operation(summary = "Добавить трек в плейлист")
    public void trackToPlaylist(@RequestBody TrackToPlaylistRequest request) {
        trackService.addTrackToPlaylist(request);
    }

    @PostMapping("/trackToAlumni")
    @Operation(summary = "Добавить трек выпускнику")
    public void trackToAlumni(@RequestBody TrackToAlumniRequest request) {
        trackService.addTrackToAlumni(request);
    }

}
