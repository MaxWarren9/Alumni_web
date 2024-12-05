package com.example.demo.model.db.repository;

import com.example.demo.model.db.entity.Worker;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkerRepo extends JpaRepository<Worker, Long> {
}
