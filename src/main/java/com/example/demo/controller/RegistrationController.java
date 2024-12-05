package com.example.demo.controller;

import com.example.demo.model.db.entity.Alumni;
import com.example.demo.model.db.entity.Role;
import com.example.demo.model.db.repository.AlumniRepo;
import com.example.demo.model.db.repository.RoleRepo;
import com.example.demo.model.db.repository.UserRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Optional;

@Controller
@ControllerAdvice
@RequiredArgsConstructor
@Slf4j
public class RegistrationController {
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private RoleRepo roleRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AlumniRepo alumniRepo;

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("alumni", new Alumni());
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute Alumni alumni) {
        log.info("Starting user registration for {}", alumni.getEmail());

        alumni.setPassword(passwordEncoder.encode(alumni.getPassword()));

        Optional<Role> roleOptional = roleRepo.findByName("ROLE_USER");
        if (roleOptional.isEmpty()) {
            log.error("Role 'ROLE_USER' not found in the database");
            throw new RuntimeException("Role 'ROLE_USER' not found");
        }

        Role userRole = roleOptional.get();
        log.info("Role 'ROLE_USER' found: {}", userRole.getName());
        log.info("Role: {}", roleOptional.get());
        log.info("Alumni: {}", alumni);
        alumni.getRoles().add(userRole);
        alumniRepo.save(alumni);
        log.info("User registration successful for {}", alumni.getEmail());

        return "redirect:/login";
    }
}
//    @PostMapping("/register")
//    public String registerUser(@ModelAttribute Alumni alumni) {
//        alumni.setPassword(passwordEncoder.encode(alumni.getPassword()));
//        Role alumniRole = roleRepo.findByName("ROLE_USER")
//                .orElseThrow(() -> new RuntimeException("Default role not found"));
//        alumni.getRoles().add(alumniRole);
//        alumniRepo.save(alumni);
//        return "redirect:/login";
//    }

//    @PostMapping("/register")
//    public String registerUser(@ModelAttribute User user) {
//        user.setPassword(passwordEncoder.encode(user.getPassword()));
//        Role userRole = roleRepo.findByName("ROLE_USER")
//                .orElseThrow(() -> new RuntimeException("Default role not found"));
//        user.getRoles().add(userRole);
//        userRepo.save(user);
//        return "redirect:/login";
//    }
