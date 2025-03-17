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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
@Slf4j
public class RegistrationController {

    @Autowired
    private UserRepo userRepo;
    @Autowired
    private RoleRepo roleRepo;
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AlumniRepo alumniRepo;

    @Autowired
    public RegistrationController(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("alumni", new Alumni());
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute Alumni alumni) {
        log.info("Starting user registration for {}", alumni.getEmail());
        alumni.setPassword(passwordEncoder.encode(alumni.getPassword()));
        Role role = roleRepo.findByName("ROLE_USER").
                orElseThrow(() -> new RuntimeException("Role not found"));
        alumni.getRoles().add(role);
        alumniRepo.save(alumni);
        log.info("Finished user registration for {}", alumni.getEmail());
        return "redirect:/login";
        }
}
