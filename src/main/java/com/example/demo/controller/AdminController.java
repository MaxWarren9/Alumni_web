package com.example.demo.controller;

import com.example.demo.service.AlumniService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final AlumniService alumniService;
    public AdminController(AlumniService alumniService) {
        this.alumniService = alumniService;
    }

    @GetMapping
    public String userList(Model model) {
        model.addAttribute("allUsers", alumniService.getAllAlumni());
        return "admin";
    }

    @PostMapping
    public String  deleteUser(@RequestParam Long userId,
                              @RequestParam String action) {
        if (action.equals("delete")){
            alumniService.deleteAlumni(userId);
        }
        return "redirect:/admin";
    }

    @GetMapping("/get/{userId}")
    public String getUser(@PathVariable Long userId, Model model) {
        model.addAttribute("user", alumniService.getAlumni(userId));
        return "admin";
    }
}