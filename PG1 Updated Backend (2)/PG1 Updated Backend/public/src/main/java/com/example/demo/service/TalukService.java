package com.example.demo.service;

import com.example.demo.entity.Taluk;
import com.example.demo.repository.TalukRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TalukService {

    private final TalukRepository repo;

    public TalukService(TalukRepository repo) {
        this.repo = repo;
    }

    
    public List<Taluk> getAll() {
        return repo.findAll();
    }

    
    public Taluk getById(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Taluk not found with ID: " + id));
    }

    
    public Taluk save(Taluk taluk) {
        validateTaluk(taluk);

        try {
            return repo.save(taluk);
        } catch (DataIntegrityViolationException e) {
            throw new RuntimeException("Taluk with name '" + taluk.getName() + "' already exists for the given district.");
        }
    }

    
    public Taluk update(Long id, Taluk updated) {
        Taluk existing = getById(id);

        if (updated.getName() != null && !updated.getName().trim().isEmpty()) {
            existing.setName(updated.getName());
        }

        if (updated.getDistrict() != null && updated.getDistrict().getId() != null) {
            existing.setDistrict(updated.getDistrict());
        }

        return save(existing); 
    }

   
    public void delete(Long id) {
        if (!repo.existsById(id)) {
            throw new RuntimeException("Taluk with ID " + id + " does not exist.");
        }

        repo.deleteById(id);
    }

    
    private void validateTaluk(Taluk taluk) {
        if (taluk.getName() == null || taluk.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("Taluk name must not be null or empty.");
        }

        if (taluk.getDistrict() == null || taluk.getDistrict().getId() == null) {
            throw new IllegalArgumentException("Taluk must be associated with a valid District.");
        }
    }
}
