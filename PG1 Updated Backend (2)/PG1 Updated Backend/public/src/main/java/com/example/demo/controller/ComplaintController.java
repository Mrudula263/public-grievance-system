package com.example.demo.controller;

import com.example.demo.dto.ComplaintRequest;
import com.example.demo.dto.ComplaintReportDTO;
import com.example.demo.entity.*;
import com.example.demo.service.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/complaints")
@CrossOrigin("*")
public class ComplaintController {

    private final ComplaintService complaintService;
    private final UserService userService;
    private final DistrictService districtService;
    private final TalukService talukService;
    private final ObjectMapper objectMapper;

    private static final String UPLOAD_DIR = "uploads/";

    public ComplaintController(
            ComplaintService complaintService,
            UserService userService,
            DistrictService districtService,
            TalukService talukService,
            ObjectMapper objectMapper) {
        this.complaintService = complaintService;
        this.userService = userService;
        this.districtService = districtService;
        this.talukService = talukService;
        this.objectMapper = objectMapper;
    }

    @PostMapping(consumes = { "multipart/form-data" })
    public ResponseEntity<?> createComplaint(
            @RequestPart("complaint") String complaintJson,
            @RequestPart(value = "file", required = false) MultipartFile file) {

        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            String userEmail = auth.getName();

            ComplaintRequest request = objectMapper.readValue(complaintJson, ComplaintRequest.class);

            District district = districtService.getById(request.getDistrictId());
            Taluk taluk = talukService.getById(request.getTalukId());
            User user = userService.getUserByEmail(userEmail);

            if (user == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not found");
            }

            String filePath = null;
            if (file != null && !file.isEmpty()) {
                Path uploadPath = Paths.get(UPLOAD_DIR);
                if (!Files.exists(uploadPath)) {
                    Files.createDirectories(uploadPath);
                }
                String filename = System.currentTimeMillis() + "_" + file.getOriginalFilename();
                Path fullPath = uploadPath.resolve(filename);
                Files.write(fullPath, file.getBytes());
                filePath = fullPath.toString();
            }

            Complaint complaint = new Complaint();
            complaint.setName(request.getName());
            complaint.setEmail(request.getEmail());
            complaint.setPhone(request.getPhone());
            complaint.setPincode(request.getPincode());
            complaint.setAddress(request.getAddress());
            complaint.setLocation(request.getLocation());
            complaint.setRemarks(request.getRemarks());
            complaint.setFilePath(filePath);
            complaint.setDistrict(district);
            complaint.setTaluk(taluk);
            complaint.setUser(user);
            complaint.setStatus(ComplaintStatus.PENDING);
            complaint.setDepartment(null);

            Complaint saved = complaintService.save(complaint);
            return ResponseEntity.status(HttpStatus.CREATED).body(saved);

        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("File processing error: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Complaint creation failed: " + e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<Complaint>> getAll() {
        return ResponseEntity.ok(complaintService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        try {
            Complaint complaint = complaintService.getById(id);
            return ResponseEntity.ok(complaint);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Complaint not found");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody Complaint complaint) {
        try {
            complaint.setDepartment(null);
            Complaint updated = complaintService.update(id, complaint);
            return ResponseEntity.ok(updated);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Update failed: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        try {
            complaintService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Complaint not found");
        }
    }

    @GetMapping("/report")
    public ResponseEntity<List<ComplaintReportDTO>> getComplaintReport() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = auth.getName();
        User user = userService.getUserByEmail(userEmail);

        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        List<ComplaintReportDTO> report = complaintService.getComplaintReportByUserId(user.getId());
        return ResponseEntity.ok(report);
    }
}
