package com.example.demo.service;


import com.example.demo.Utils.PaginationUtil;
import com.example.demo.exceptions.CustomException;
import com.example.demo.model.db.entity.Alumni;
import com.example.demo.model.db.entity.Specialization;
import com.example.demo.model.db.repository.SpecializationRepo;
import com.example.demo.model.dto.request.SpecializationInfoRequest;
import com.example.demo.model.dto.request.SpecializationToAlumniRequest;
import com.example.demo.model.dto.response.SpecializationInfoResponse;
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

public class SpecializationService {

    private final ObjectMapper mapper;
    private final SpecializationRepo specializationRepo;
    private final AlumniService alumniService;


    public SpecializationInfoResponse createSpecialization(SpecializationInfoRequest request) {
        Specialization specialization = mapper.convertValue(request, Specialization.class);
        Specialization save = specializationRepo.save(specialization);
        return mapper.convertValue(save, SpecializationInfoResponse.class);
    }

    public SpecializationInfoResponse getSpecialization(long id) {
        Specialization specialization = specializationRepo.findById(id).orElse(new Specialization());
        return mapper.convertValue(specialization, SpecializationInfoResponse.class);
    }

    public Specialization getSpecializationFromDB(long id) {
        return specializationRepo.findById(id).orElseThrow(() -> new CustomException("Specialization not found", HttpStatus.NOT_FOUND));
    }

    public SpecializationInfoResponse updateSpecialization(long id, SpecializationInfoRequest request) {
        Specialization specialization = getSpecializationFromDB(id);
        specialization.setName(request.getName());
        Specialization save = specializationRepo.save(specialization);
        return mapper.convertValue(save, SpecializationInfoResponse.class);
    }

    public void deleteSpecialization(long id) {
        specializationRepo.deleteById(id);
    }

    public Page<SpecializationInfoResponse> getAllSpecializations(Integer page, Integer perPage, String sort,
                                                                  Sort.Direction order, String filter) {
        Pageable pageRequest = PaginationUtil.getPageRequest(page, perPage, sort, order);

        Page<Specialization> specializationPage;
        if (filter == null || filter.isEmpty()) {
            specializationPage = specializationRepo.findAll(pageRequest);
        } else {
            specializationPage = specializationRepo.findAllByName(filter, pageRequest);
        }
        List<SpecializationInfoResponse> content = specializationPage.getContent().stream()
                .map(specialization -> mapper.convertValue(specialization, SpecializationInfoResponse.
                        class))
                .collect(Collectors.toList());

        return new PageImpl<>(content, pageRequest, specializationPage.getTotalElements());
    }

    public void addSpecializationToAlumni(SpecializationToAlumniRequest request) {
        Specialization specialization = specializationRepo.findById(request.getSpecializationId()).orElseThrow(() ->
                new CustomException("Specialization not found", HttpStatus.NOT_FOUND));
        Alumni alumniFromDB = alumniService.getAlumniFromDB(request.getAlumniId());

        if (alumniFromDB == null) {
            throw new CustomException("Alumni not found", HttpStatus.NOT_FOUND);
        }
        alumniFromDB.getSpecializations().add(specialization);
        alumniService.updateAlumniData(alumniFromDB);

        specialization.getAlumni().add(alumniFromDB);
        specializationRepo.save(specialization);
    }


}