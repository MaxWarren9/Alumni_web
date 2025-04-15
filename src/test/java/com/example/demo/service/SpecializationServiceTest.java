package com.example.demo.service;

import com.example.demo.model.db.entity.Alumni;
import com.example.demo.model.db.entity.Specialization;
import com.example.demo.model.db.repository.SpecializationRepo;
import com.example.demo.model.dto.request.SpecializationToAlumniRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.HashSet;
import java.util.Optional;

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class SpecializationServiceTest {

    @InjectMocks
    private SpecializationService specializationService;

    @Spy
    private ObjectMapper mapper;

    @Mock
    private SpecializationRepo specializationRepo;

    @Mock
    private AlumniService alumniService;


    @Test
    public void createSpecialization() {
    }

    @Test
    public void getSpecialization() {
    }

    @Test
    public void getSpecializationFromDB() {
    }

    @Test
    public void updateSpecialization() {
    }

    @Test
    public void deleteSpecialization() {
    }

    @Test
    public void getAllSpecializations() {
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
}