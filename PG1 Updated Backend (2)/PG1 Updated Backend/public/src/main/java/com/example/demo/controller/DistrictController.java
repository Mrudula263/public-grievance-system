package com.example.demo.controller;

import com.example.demo.dto.DistrictDTO;
import com.example.demo.dto.TalukDTO;
import com.example.demo.entity.District;
import com.example.demo.service.DistrictService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/districts")
public class DistrictController {

    private final DistrictService service;

    public DistrictController(DistrictService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<DistrictDTO>> getAllDistricts() {
        List<DistrictDTO> districts = service.getAllDistrictDTOs();
        return ResponseEntity.ok(districts);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getDistrictById(@PathVariable Long id) {
        try {
            District district = service.getById(id);
            return ResponseEntity.ok(district);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/{id}/taluks")
    public ResponseEntity<?> getTaluksByDistrictId(@PathVariable Long id) {
        try {
            List<TalukDTO> taluks = service.getTaluksByDistrictId(id);
            return ResponseEntity.ok(taluks);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<?> createDistrict(@RequestBody District district) {
        try {
            District created = service.save(district);
            return ResponseEntity.status(HttpStatus.CREATED).body(created);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateDistrict(@PathVariable Long id, @RequestBody District district) {
        try {
            District updated = service.update(id, district);
            return ResponseEntity.ok(updated);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteDistrict(@PathVariable Long id) {
        try {
            service.delete(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
