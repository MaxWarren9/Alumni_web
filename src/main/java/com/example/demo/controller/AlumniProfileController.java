package com.example.demo.controller;

import com.example.demo.model.db.entity.Alumni;
import com.example.demo.model.dto.request.AlumniInfoRequest;
import com.example.demo.model.dto.response.AlumniInfoResponse;
import com.example.demo.service.AlumniService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/profile")
@RequiredArgsConstructor
public class AlumniProfileController {

    private final AlumniService alumniService;

//    @GetMapping
//    public AlumniInfoResponse getProfile(@AuthenticationPrincipal Alumni alumni) {
//        if (alumni == null) {
//            throw new IllegalArgumentException("User is not authenticated, Max!");
//        }
//        return alumniService.getAlumni(alumni.getId());
//    }

    @GetMapping
    public AlumniInfoResponse getProfile(@AuthenticationPrincipal(expression = "alumni") Alumni alumni) {
        if (alumni == null) {
            throw new IllegalStateException("User is not authenticated");
        }
        return alumniService.getAlumni(alumni.getId());
    }

    @PutMapping("/edit")
    public AlumniInfoResponse updateProfile(
            @AuthenticationPrincipal Alumni alumni,
            @RequestBody AlumniInfoRequest request) {
        if (alumni == null) {
            throw new IllegalArgumentException("User is not authenticated");
        }
        return alumniService.updateAlumni(alumni.getId(), request);
    }
}