package com.example.demo.controller;

import com.example.demo.entity.Taluk;
import com.example.demo.service.TalukService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/taluks")
public class TalukController {

    private final TalukService service;

    public TalukController(TalukService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<Taluk>> getAllTaluks() {
        List<Taluk> taluks = service.getAll();
        return ResponseEntity.ok(taluks);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getTalukById(@PathVariable Long id) {
        try {
            Taluk taluk = service.getById(id);
            return ResponseEntity.ok(taluk);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<?> createTaluk(@RequestBody Taluk taluk) {
        try {
            Taluk saved = service.save(taluk);
            return ResponseEntity.status(HttpStatus.CREATED).body(saved);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateTaluk(@PathVariable Long id, @RequestBody Taluk taluk) {
        try {
            Taluk updated = service.update(id, taluk);
            return ResponseEntity.ok(updated);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTaluk(@PathVariable Long id) {
        try {
            service.delete(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
