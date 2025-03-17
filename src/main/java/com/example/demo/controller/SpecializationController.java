package com.example.demo.controller;

import com.example.demo.model.dto.request.SpecializationInfoRequest;
import com.example.demo.model.dto.request.SpecializationToAlumniRequest;
import com.example.demo.model.dto.response.SpecializationInfoResponse;
import com.example.demo.service.SpecializationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Специализации")
@RestController
@RequestMapping("/api/specializations")
@RequiredArgsConstructor

    public class SpecializationController {

        private final SpecializationService specializationService;

        // Создать новую специализацию
        @PostMapping
        @Operation(summary = "Создать специализацию")
        public SpecializationInfoResponse createSpecialization(@RequestBody SpecializationInfoRequest request) {
            return specializationService.createSpecialization(request);
        }

        // Получить специализацию по ID
        @GetMapping("/{id}")
        @Operation(summary = "Получить специализацию по id")
        public SpecializationInfoResponse getSpecialization(@PathVariable long id) {
            return specializationService.getSpecialization(id);
        }

        // Обновить информацию о специализации
        @PutMapping("/{id}")
        @Operation(summary = "Обновить специализацию по id")
        public SpecializationInfoResponse updateSpecialization(@PathVariable long id, @RequestBody SpecializationInfoRequest request) {
            return specializationService.updateSpecialization(id, request);
        }

        // Удалить специализацию
        @DeleteMapping("/{id}")
        @Operation(summary = "Удалить специализацию по id")
        public void deleteSpecialization(@PathVariable long id){
            specializationService.deleteSpecialization(id);
        }

        // Получить всех выпускников
        @GetMapping
        @Operation(summary = "Получить список специализаций")
        public Page<SpecializationInfoResponse> getAllSpecializations(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer perPage,
            @RequestParam(defaultValue = "name") String sort,
            @RequestParam(defaultValue = "ASC") Sort.Direction order,
            @RequestParam(required = false) String filter
        )
        {
            return specializationService.getAllSpecializations(page,perPage,sort,order,filter);
        }


    // Создать новую специализацию
        @PostMapping("/specializationToAlumni")
        @Operation(summary = "Добавить специализацию выпускнику")
        public void addSpecializationToAlumni(@RequestBody SpecializationToAlumniRequest request) {
            specializationService.addSpecializationToAlumni(request);
        }
    }