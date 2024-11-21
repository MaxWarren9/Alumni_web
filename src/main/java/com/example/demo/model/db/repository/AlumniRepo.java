package com.example.demo.model.db.repository;

import com.example.demo.model.db.entity.Alumni;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AlumniRepo extends JpaRepository<Alumni, Long> {
    List<Alumni> findBySpecializationsAndGraduationYear(String specialization, int graduationYear);
    Optional<Alumni> findByUsername(String username);
}