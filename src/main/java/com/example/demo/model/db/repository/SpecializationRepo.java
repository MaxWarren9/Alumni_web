package com.example.demo.model.db.repository;


import com.example.demo.model.db.entity.Specialization;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SpecializationRepo extends JpaRepository<Specialization, Long> {
    Specialization findByName(String name);

    @Query(nativeQuery = true, value = "select * from specializations where id = 1")
    Specialization getSpecialization();

    @Query(nativeQuery = true, value = "SELECT * from specializations WHERE name IS NOT NULL and name != '' and name LIKE %:filter%")
    Page<Specialization> findAllByName(@Param("filter") String filter, Pageable pageable);
}
