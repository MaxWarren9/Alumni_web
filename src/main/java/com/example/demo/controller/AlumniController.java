package com.example.demo.controller;
import com.example.demo.model.dto.request.AlumniInfoRequest;
import com.example.demo.model.dto.response.AlumniInfoResponse;
import com.example.demo.service.AlumniService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Выпускники")
@RestController
@RequiredArgsConstructor

    @RequestMapping("/api/alumni")

    public class AlumniController {

        private final AlumniService alumniService;

        @PostMapping
        @Operation(summary = "Создать выпускника")
        public AlumniInfoResponse createAlumni(@RequestBody AlumniInfoRequest request) {
            return alumniService.createAlumni(request);
        }

        // Получить выпускника по ID
        @GetMapping("/{id}")
        @Operation(summary = "Получить выпускника по id")
        public AlumniInfoResponse getAlumni(@PathVariable long id) {
            return alumniService.getAlumni(id);
        }

        // Обновить информацию о выпускнике
        @PutMapping("/{id}")
        @Operation(summary = "Обновить выпускника по id")
        public AlumniInfoResponse updateAlumni(@PathVariable long id, @RequestBody AlumniInfoRequest request) {
            return alumniService.updateAlumni(id, request);
        }

        // Удалить выпускника
        @DeleteMapping("/{id}")
        @Operation(summary = "Удалить выпускника по id")
        public void deleteAlumni(@PathVariable long id){
            alumniService.deleteAlumni(id);
        }

        // Получить всех выпускников
        @GetMapping("/all")
        @Operation(summary = "Получить список пользователей")
        public List<AlumniInfoResponse> getAllAlumni() {
            return alumniService.getAllAlumni();
        }
    }