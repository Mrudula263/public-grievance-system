package com.example.demo.repository;

import com.example.demo.entity.Taluk;
import com.example.demo.entity.District;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TalukRepository extends JpaRepository<Taluk, Long> {
    boolean existsByNameAndDistrict(String name, District district); 
}
