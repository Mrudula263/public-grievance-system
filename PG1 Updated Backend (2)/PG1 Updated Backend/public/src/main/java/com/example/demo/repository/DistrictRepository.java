package com.example.demo.repository;

import com.example.demo.entity.District;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DistrictRepository extends JpaRepository<District, Long> {
    Optional<District> findByName(String name); // Find district by name
}
