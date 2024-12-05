package com.example.demo.service;


import com.example.demo.enums.UserStatus;
import com.example.demo.model.db.entity.Alumni;
import com.example.demo.model.db.repository.AlumniRepo;
import com.example.demo.model.dto.request.AlumniInfoRequest;
import com.example.demo.model.dto.response.AlumniInfoResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AlumniService implements UserDetailsService {

    private final ObjectMapper mapper;
    @Autowired
    private final AlumniRepo alumniRepo;

    public AlumniInfoResponse createAlumni(AlumniInfoRequest request) {
        if(!EmailValidator.getInstance().isValid(request.getEmail())) {
            return null;
        }
        Alumni alumni = mapper.convertValue(request, Alumni.class);
        alumni.setStatus(UserStatus.CREATED);
        Alumni save = alumniRepo.save(alumni);
        return mapper.convertValue(save, AlumniInfoResponse.class);
    }

    public AlumniInfoResponse getAlumni(long id) {
        Alumni alumni = alumniRepo.findById(id).orElse(new Alumni());
        return mapper.convertValue(alumni,AlumniInfoResponse.class);
    }

    public Alumni getAlumniFromDB(Long id) {
        return alumniRepo.findById(id).orElse(null);
    }

    public AlumniInfoResponse updateAlumni(long id, AlumniInfoRequest request) {
        if(!EmailValidator.getInstance().isValid(request.getEmail())) {
            return null;
        }
        Alumni alumni = getAlumniFromDB(id);
        alumni.setEmail(request.getEmail());
        alumni.setGender(request.getGender() == null ? alumni.getGender() : request.getGender());
        alumni.setFirstName(request.getFirstName() == null ? alumni.getFirstName() : request.getFirstName());
        alumni.setLastName(request.getLastName() == null ? alumni.getLastName() : request.getLastName());
        alumni.setDateOfBirth(request.getDateOfBirth() == null ? alumni.getDateOfBirth() : request.getDateOfBirth());
        alumni.setGraduationYear(request.getGraduationYear() == null ? alumni.getGraduationYear() : request.getGraduationYear());
        alumni.setPassword(request.getPassword() == null ? alumni.getPassword() : request.getPassword());
        alumni.setStatus(UserStatus.UPDATED);
        Alumni save = alumniRepo.save(alumni);
        return mapper.convertValue(save, AlumniInfoResponse.class);
    }

    public void deleteAlumni(long id){
        alumniRepo.deleteById(id);
    }

    public List<AlumniInfoResponse> getAllAlumni() {
        return alumniRepo.findAll().stream()
                .map(alumni -> mapper.convertValue(alumni, AlumniInfoResponse.class))
                .collect(Collectors.toList());
    }

    public Alumni updateAlumniData(Alumni alumni) {
        return alumniRepo.save(alumni);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Alumni alumni = alumniRepo.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
        return alumni;
    }
}