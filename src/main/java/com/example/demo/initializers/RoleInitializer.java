package com.example.demo.initializers;

import com.example.demo.model.db.entity.Role;
import com.example.demo.model.db.repository.RoleRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class RoleInitializer implements CommandLineRunner {

    @Autowired
    private RoleRepo roleRepo;

    @Override
    public void run(String... args) throws Exception {
        if (!roleRepo.existsByName("ROLE_USER")) {
            Role roleUser = new Role();
            roleUser.setName("ROLE_USER");
            roleRepo.save(roleUser);
        }

        if (!roleRepo.existsByName("ROLE_ADMIN")) {
            Role roleAdmin = new Role();
            roleAdmin.setName("ROLE_ADMIN");
            roleRepo.save(roleAdmin);
        }
    }
}
