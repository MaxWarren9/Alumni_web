package com.example.demo.service;

import com.example.demo.exceptions.CustomException;
import com.example.demo.model.db.entity.Alumni;
import com.example.demo.model.db.entity.Specialization;
import com.example.demo.model.db.repository.SpecializationRepo;
import com.example.demo.model.dto.request.SpecializationInfoRequest;
import com.example.demo.model.dto.request.SpecializationToAlumniRequest;
import com.example.demo.model.dto.response.SpecializationInfoResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.*;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class SpecializationServiceTest {

    @InjectMocks
    private SpecializationService specializationService;

    @Spy
    private ObjectMapper mapper = new ObjectMapper();

    @Mock
    private SpecializationRepo specializationRepo;

    @Mock
    private AlumniService alumniService;


    @Test
    public void createSpecialization() {
        SpecializationInfoRequest specializationInfoRequest = new SpecializationInfoRequest();
        specializationInfoRequest.setName("test specialization");

        Specialization specialization = new Specialization();
        specialization.setId(1L);

        when(specializationRepo.save(any(Specialization.class))).thenReturn(specialization);

        SpecializationInfoResponse specializationInfoResponse = specializationService.createSpecialization(specializationInfoRequest);

        assertEquals(specialization.getName(), specializationInfoResponse.getName());
    }

    @Test
    public void getSpecialization() {
        Specialization specialization = new Specialization();
        specialization.setId(1L);

        when(specializationRepo.findById(1L)).thenReturn(Optional.of(specialization));
        SpecializationInfoResponse specializationInfoResponse = specializationService.getSpecialization(1L);
        assertEquals(specialization.getName(), specializationInfoResponse.getName());
    }

    @Test
    public void getSpecializationFromDB() {
        Specialization specialization = new Specialization();
        specialization.setId(1L);
        when(specializationRepo.findById(specialization.getId())).thenReturn(Optional.of(specialization));
        SpecializationInfoResponse result = specializationService.getSpecialization(specialization.getId());
        assertEquals(specialization.getId(), result.getId());
    }

    @Test(expected = CustomException.class)
    public void getSpecializationFromDB_userNotFound() {
        when(specializationRepo.findById(anyLong())).thenReturn(Optional.empty());
        specializationService.getSpecializationFromDB(1L);
    }
    @Test
    public void updateSpecialization() {
        SpecializationInfoRequest specializationInfoRequest = new SpecializationInfoRequest();
        specializationInfoRequest.setName("test specialization");

        Specialization specialization = new Specialization();
        specialization.setId(1L);
        when(specializationRepo.findById(specialization.getId())).thenReturn(Optional.of(specialization));
        specializationService.updateSpecialization(specialization.getId(), specializationInfoRequest);
        verify(specializationRepo, times(1)).save(any(Specialization.class));
    }

    @Test
    public void deleteSpecialization() {
        Long specializationId = 1L;
        specializationService.deleteSpecialization(specializationId);
        verify(specializationRepo, times(1)).deleteById(specializationId);
    }

    @Test
    public void getAllSpecializations_withoutFilter_shouldReturnPage() {
        Specialization specialization = new Specialization();
        specialization.setId(1L);
        specialization.setName("Math");

        Specialization specialization1 = new Specialization();
        specialization1.setId(2L);
        specialization1.setName("English");

        List<Specialization> specializations = List.of(specialization, specialization1);
        Page<Specialization> pagedSpecializations = new PageImpl<>(specializations);

        Pageable pageRequest = PageRequest.of(0, 10, Sort.Direction.ASC, specialization1.getName());
        when(specializationRepo.findAll(pageRequest)).thenReturn(pagedSpecializations);

        Page<SpecializationInfoResponse> result = specializationService
                .getAllSpecializations(0, 10, specialization1.getName(), Sort.Direction.ASC, null);

        assertEquals(pagedSpecializations.getTotalElements(), result.getTotalElements());
    }


    @Test
    public void getAllSpecializations_withFilter_shouldCallFindAllByName() {
        Specialization specialization = new Specialization();
        specialization.setId(1L);
        specialization.setName("Physics");

        Specialization specialization2 = new Specialization();
        specialization2.setId(2L);
        specialization2.setName("English");

        List<Specialization> specializations = List.of(specialization, specialization2);
        Page<Specialization> pagedSpecializations = new PageImpl<>(specializations);

        Pageable pageable = PageRequest.of(0, 10, Sort.Direction.ASC, "Physics");

        when(specializationRepo.findAllByName("Physics",pageable)).thenReturn(pagedSpecializations);
        Page<SpecializationInfoResponse> result = specializationService.
                getAllSpecializations(0, 10, specialization.getName(), Sort.Direction.ASC, "Physics");

        assertEquals(pagedSpecializations.getTotalElements(), result.getTotalElements());
    }

    @Test
    public void addSpecializationToAlumni() {

        Specialization specialization = new Specialization();
        specialization.setId(1L);

        when(specializationRepo.findById(specialization.getId())).thenReturn(Optional.of(specialization));

        Alumni alumni = new Alumni();
        alumni.setId(1L);
        alumni.setSpecializations(new HashSet<>());

        when(alumniService.getAlumniFromDB(alumni.getId())).thenReturn(alumni);

        SpecializationToAlumniRequest specializationToAlumniRequest = SpecializationToAlumniRequest.builder()
                .specializationId(specialization.getId())
                .alumniId(alumni.getId())
                .build();
        specializationService.addSpecializationToAlumni(specializationToAlumniRequest);
    }

    @Test(expected = CustomException.class)
    public void addSpecializationToAlumni_shouldThrowIfAlumniNotFound() {
        Specialization specialization = new Specialization();
        specialization.setId(1L);

        when(specializationRepo.findById(specialization.getId())).thenReturn(Optional.of(specialization));
        when(alumniService.getAlumniFromDB(1L)).thenReturn(null); // важный момент

        SpecializationToAlumniRequest request = SpecializationToAlumniRequest.builder()
                .specializationId(specialization.getId())
                .alumniId(1L)
                .build();

        specializationService.addSpecializationToAlumni(request);
    }

    @Test(expected = CustomException.class)
    public void addSpecializationToAlumni_shouldThrowIfSpecializationNotFound() {
        Long specializationId = 1L;
        Long alumniId = 2L;

        when(specializationRepo.findById(specializationId)).thenReturn(Optional.empty());

        SpecializationToAlumniRequest request = SpecializationToAlumniRequest.builder()
                .specializationId(specializationId)
                .alumniId(alumniId)
                .build();

        specializationService.addSpecializationToAlumni(request);
    }


}