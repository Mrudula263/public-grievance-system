package com.example.demo.service;

import com.example.demo.dto.DistrictDTO;
import com.example.demo.dto.TalukDTO;
import com.example.demo.entity.District;
import com.example.demo.repository.DistrictRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DistrictService {

    private final DistrictRepository repo;

    public DistrictService(DistrictRepository repo) {
        this.repo = repo;
    }

    public List<DistrictDTO> getAllDistrictDTOs() {
        return repo.findAll().stream()
                .map(d -> new DistrictDTO(d.getId(), d.getName()))
                .collect(Collectors.toList());
    }

    public List<TalukDTO> getTaluksByDistrictId(Long districtId) {
        District district = getById(districtId);
        return district.getTaluks().stream()
                .map(t -> new TalukDTO(t.getId(), t.getName()))
                .collect(Collectors.toList());
    }

    public District getById(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new RuntimeException("District not found with ID: " + id));
    }

    public District save(District district) {
        validateName(district.getName());
        try {
            return repo.save(district);
        } catch (DataIntegrityViolationException e) {
            throw new RuntimeException("District with name '" + district.getName() + "' already exists.");
        }
    }

    public District update(Long id, District updated) {
        District existing = getById(id);
        if (updated.getName() != null && !updated.getName().trim().isEmpty()) {
            existing.setName(updated.getName());
        }
        return save(existing);
    }

    public void delete(Long id) {
        if (!repo.existsById(id)) {
            throw new RuntimeException("District with ID " + id + " does not exist.");
        }
        repo.deleteById(id);
    }

    private void validateName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("District name must not be null or empty.");
        }
    }
}
