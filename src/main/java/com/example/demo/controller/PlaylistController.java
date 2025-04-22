package com.example.demo.controller;

import com.example.demo.model.dto.request.PlaylistInfoRequest;
import com.example.demo.model.dto.request.PlaylistToAlumniRequest;
import com.example.demo.model.dto.response.PlaylistInfoResponse;
import com.example.demo.service.PlaylistService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Плейлисты")
@RestController
@RequestMapping("/api/playlists")
@RequiredArgsConstructor
public class PlaylistController {

    private final PlaylistService playlistService;

    @PostMapping
    @Operation(summary = "Создать плейлист")
    public PlaylistInfoResponse createPlaylist(@RequestBody PlaylistInfoRequest playlistInfoRequest) {
        return playlistService.createPlaylist(playlistInfoRequest);
    }

    @GetMapping("/all")
    @Operation(summary = "Получить список плейлистов")
    public ResponseEntity<List<PlaylistInfoResponse>> getAllPlaylists(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer perPage,
            @RequestParam(defaultValue = "name") String sort,
            @RequestParam(defaultValue = "ASC") Sort.Direction order,
            @RequestParam(required = false) String filter,
            Model model) {
        Page<PlaylistInfoResponse> playlists = playlistService.getAllPlaylists(page, perPage, sort, order, filter);
        model.addAttribute("playlists", playlists.getContent());
        return ResponseEntity.ok(playlists.getContent());
    }


    @GetMapping("/{id}")
    @Operation(summary = "Получить плейлист по id")
    public PlaylistInfoResponse getPlaylist(@PathVariable long id) {
        return playlistService.getPlaylist(id);
    }

    @PostMapping("/{id}")
    @Operation(summary = "Обновить плейлист")
    public PlaylistInfoResponse updatePlaylist(@PathVariable long id, @RequestBody PlaylistInfoRequest playlistInfoRequest) {
        return playlistService.updatePlaylist(id, playlistInfoRequest);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Удалить плейлист")
    public void deletePlaylist(@PathVariable long id) {
        playlistService.deletePlaylist(id);
    }

    @PostMapping("/playlistToAlumni")
    @Operation(summary = "Добавить плейлист пользователю")
    public void addPlaylistToAlumni(@RequestBody PlaylistToAlumniRequest playlistInfoRequest) {
        playlistService.addPlaylistToAlumni(playlistInfoRequest);
    }
}
