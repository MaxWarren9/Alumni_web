package com.example.demo.service;


import com.example.demo.Utils.PaginationUtil;
import com.example.demo.model.db.entity.Alumni;
import com.example.demo.model.db.entity.Specialization;
import com.example.demo.model.db.repository.SpecializationRepo;
import com.example.demo.model.dto.request.SpecializationInfoRequest;
import com.example.demo.model.dto.request.SpecializationToAlumniRequest;
import com.example.demo.model.dto.response.SpecializationInfoResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
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
        return mapper.convertValue(specialization,SpecializationInfoResponse.class);
    }

    public Specialization getSpecializationFromDB(long id) {
        return specializationRepo.findById(id).orElse(new Specialization());
    }

    public SpecializationInfoResponse updateSpecialization(long id, SpecializationInfoRequest request) {
        return SpecializationInfoResponse.builder()
                .name(request.getName())
                .build();
    }

    public SpecializationInfoResponse deleteSpecialization(long id) {
        return null;
    }

    public Page<SpecializationInfoResponse> getAllSpecialization(Integer page, Integer perPage, String sort, Sort.Direction order, String filter) {

        Pageable pageRequest = PaginationUtil.getPageRequest(page, perPage, sort, order);

        Page<Specialization> specializationPage;
        if (filter == null || filter.isEmpty()) {
            specializationPage = specializationRepo.findAll(pageRequest);
        } else {
            specializationPage = specializationRepo.findAllByName(filter, pageRequest);
        }
        List<SpecializationInfoResponse> content = specializationPage.getContent().stream()
                .map(specialization -> mapper.convertValue(specialization, SpecializationInfoResponse.class))
                .collect(Collectors.toList());

        return new PageImpl<>(content, pageRequest, specializationPage.getTotalElements());
    }

    public void addSpecializationToAlumni(SpecializationToAlumniRequest request) {
        Specialization specialization = specializationRepo.findById(request.getSpecializationId()).orElse(null);
        if(specialization == null) {
            return;
        }
        Alumni alumniFromDB = alumniService.getAlumniFromDB(request.getAlumniId());

        if(alumniFromDB == null){
            return;
        }
        alumniFromDB.getSpecializations().add(specialization);

        alumniService.updateAlumniData(alumniFromDB);

        specialization.setAlumni(alumniFromDB);
        specializationRepo.save(specialization);
    }
}