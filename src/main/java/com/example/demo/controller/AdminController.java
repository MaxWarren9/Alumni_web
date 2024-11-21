package com.example.demo.controller;

import com.example.demo.service.AlumniService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RestController
public class AdminController {
    @Autowired
    private AlumniService alumniService;

    @GetMapping("/admin")
    public String userList(Model model) {
        model.addAttribute("allUsers", alumniService.getAllAlumni());
        return "admin";
    }

    @PostMapping("/admin")
    public String  deleteUser(@RequestParam(required = true, defaultValue = "" ) Long userId,
                              @RequestParam(required = true, defaultValue = "" ) String action,
                              Model model) {
        if (action.equals("delete")){
            alumniService.deleteAlumni(userId);
        }
        return "redirect:/admin";
    }

    @GetMapping("/admin/get/{userId}")
    public String  gtUser(@PathVariable("userId") Long userId, Model model) {
        model.addAttribute("allUsers", alumniService.getAlumni(userId));
        return "admin";
    }
}