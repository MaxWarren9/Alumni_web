package com.example.demo.service;

import com.example.demo.enums.Gender;
import com.example.demo.exceptions.CustomException;
import com.example.demo.model.db.entity.Alumni;
import com.example.demo.model.db.repository.AlumniRepo;
import com.example.demo.model.dto.request.AlumniInfoRequest;
import com.example.demo.model.dto.response.AlumniInfoResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@RunWith(MockitoJUnitRunner.class)
public class AlumniServiceTest {

    @InjectMocks
    private AlumniService alumniService;

    @Mock
    private AlumniRepo alumniRepo;

    @Spy
    private ObjectMapper objectMapper;

    @Test
    public void createAlumni() {
        AlumniInfoRequest alumniInfoRequest = new AlumniInfoRequest();
        alumniInfoRequest.setEmail("test@test.com");

        Alumni alumni = new Alumni();
        alumni.setId(1L);

        when(alumniRepo.save(any(Alumni.class))).thenReturn(alumni);

        AlumniInfoResponse result = alumniService.createAlumni(alumniInfoRequest);
        assertEquals(alumni.getId(), result.getId());
    }

    @Test(expected = CustomException.class)
    public void createAlumni_badEmail() {
        AlumniInfoRequest alumniInfoRequest = new AlumniInfoRequest();
        alumniInfoRequest.setEmail("test@.test.com");

        alumniService.createAlumni(alumniInfoRequest);
    }


    @Test
    public void getAlumni() {
        Alumni alumni = new Alumni();
        alumni.setId(1L);

        when(alumniRepo.findById(alumni.getId())).thenReturn(Optional.of(alumni));
        AlumniInfoResponse result = alumniService.getAlumni(alumni.getId());
        assertEquals(alumni.getId(), result.getId());
    }

    @Test
    public void getAlumniFromDB() {
        Alumni alumni = new Alumni();
        alumni.setId(1L);
        when(alumniRepo.findById(alumni.getId())).thenReturn(Optional.of(alumni));
        AlumniInfoResponse result = alumniService.getAlumni(alumni.getId());
        assertEquals(alumni.getId(), result.getId());
    }

    @Test(expected = CustomException.class)
    public void getAlumniFromDB_userNotFound() {
        when(alumniRepo.findById(anyLong())).thenReturn(Optional.empty());
        alumniService.getAlumniFromDB(1L);
    }

    @Test
    public void updateAlumni() {
        AlumniInfoRequest alumniInfoRequest = new AlumniInfoRequest();
        alumniInfoRequest.setEmail("test@newtest.com");
        alumniInfoRequest.setGender(Gender.MALE);
        alumniInfoRequest.setPassword("123456");
        alumniInfoRequest.setFirstName("John");
        alumniInfoRequest.setLastName("Doe");
        alumniInfoRequest.setGraduationYear(2020);

        Alumni alumni = new Alumni();
        alumni.setId(1L);
        when(alumniRepo.findById(alumni.getId())).thenReturn(Optional.of(alumni));
        alumniService.updateAlumni(alumni.getId(), alumniInfoRequest);
        verify(alumniRepo, times(1)).save(any(Alumni.class));
    }


    @Test(expected = CustomException.class)
    public void updateAlumni_badEmail() {
        AlumniInfoRequest alumniInfoRequest = new AlumniInfoRequest();
        alumniInfoRequest.setEmail("test@.newtest.com");
        alumniInfoRequest.setGender(Gender.MALE);
        alumniInfoRequest.setPassword("123456");
        alumniInfoRequest.setFirstName("John");
        alumniInfoRequest.setLastName("Doe");
        alumniInfoRequest.setGraduationYear(2020);

        Alumni alumni = new Alumni();
        alumni.setId(1L);

        alumniService.updateAlumni(alumni.getId(), alumniInfoRequest);
    }

    @Before
    public void setUp() {
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }
    @Test
    public void updateAlumni_nullRequest() {
        AlumniInfoRequest alumniInfoRequest = new AlumniInfoRequest();
        alumniInfoRequest.setEmail("test@newtest.com");
        alumniInfoRequest.setGender(null);
        alumniInfoRequest.setPassword(null);
        alumniInfoRequest.setFirstName(null);
        alumniInfoRequest.setLastName(null);
        alumniInfoRequest.setGraduationYear(null);
        alumniInfoRequest.setDateOfBirth(LocalDate.of(2020, 1, 1));

        Alumni alumni = new Alumni();
        alumni.setId(1L);
        alumni.setEmail("test@test.com");
        alumni.setGender(Gender.MALE);
        alumni.setPassword("123456");
        alumni.setFirstName("John");
        alumni.setLastName("Doe");
        alumni.setGraduationYear(2020);
        alumni.setDateOfBirth(LocalDate.of(2020, 1, 1));

        when(alumniRepo.findById(1L)).thenReturn(Optional.of(alumni));
        when(alumniRepo.save(any(Alumni.class))).thenAnswer(invocation -> invocation.getArgument(0));

        AlumniInfoResponse response = alumniService.updateAlumni(1L, alumniInfoRequest);


        assertEquals("test@newtest.com", response.getEmail());
        assertEquals(Gender.MALE, response.getGender());
        assertEquals("123456", response.getPassword());
        assertEquals("John", response.getFirstName());
        assertEquals("Doe", response.getLastName());
        assertEquals((Integer) 2020, response.getGraduationYear());
        assertEquals(LocalDate.of(2020,1,1), response.getDateOfBirth());
    }

    @Test
    public void deleteAlumni() {

        Long id = 1L;

        alumniService.deleteAlumni(id);

        verify(alumniRepo, times(1)).deleteById(id);
    }

    @Test
    public void getAllAlumni_shouldReturnList() {
        Alumni alumni1 = new Alumni();
        alumni1.setId(1L);
        alumni1.setEmail("a@test.com");

        Alumni alumni2 = new Alumni();
        alumni2.setId(2L);
        alumni2.setEmail("b@test.com");

        List<Alumni> alumniList = Arrays.asList(alumni1, alumni2);

        when(alumniRepo.findAll()).thenReturn(alumniList);

        List<AlumniInfoResponse> response = alumniService.getAllAlumni();

        assertEquals(2, response.size());
        assertEquals("a@test.com", response.get(0).getEmail());
        assertEquals("b@test.com", response.get(1).getEmail());
    }

    @Test
    public void updateAlumniData_shouldSaveAndReturnAlumni() {
        Alumni alumni = new Alumni();
        alumni.setId(1L);
        alumni.setEmail("updated@test.com");

        when(alumniRepo.save(any(Alumni.class))).thenReturn(alumni);

        Alumni result = alumniService.updateAlumniData(alumni);

        assertEquals("updated@test.com", result.getEmail());
        verify(alumniRepo, times(1)).save(alumni);
    }

    @Test
    public void loadUserByUsername_shouldReturnUser() {
        Alumni alumni = new Alumni();
        alumni.setEmail("user@test.com");
        alumni.setUsername("user");

        when(alumniRepo.findByUsername("user")).thenReturn(Optional.of(alumni));

        UserDetails userDetails = alumniService.loadUserByUsername("user");

        assertEquals("user", userDetails.getUsername());
    }
}