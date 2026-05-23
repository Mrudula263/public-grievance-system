package com.example.demo.service;

import com.example.demo.entity.Complaint;
import com.example.demo.entity.Department;
import com.example.demo.entity.ComplaintStatus;
import com.example.demo.repository.ComplaintRepository;
import com.example.demo.repository.DepartmentRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Service
public class DepartmentService {

    private final DepartmentRepository departmentRepo;
    private final ComplaintRepository complaintRepo;

    public DepartmentService(DepartmentRepository departmentRepo, ComplaintRepository complaintRepo) {
        this.departmentRepo = departmentRepo;
        this.complaintRepo = complaintRepo;
    }

    // Get all departments
    public List<Department> getAllDepartments() {
        return departmentRepo.findAll();
    }

    // Add a new department
    public Department addDepartment(Department department) {
        return departmentRepo.save(department);
    }

    // Get a department by ID
    public Department getDepartmentById(Long id) {
        return departmentRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Department not found with ID: " + id));
    }

    // Update an existing department
    public Department updateDepartment(Long id, Department updatedDepartment) {
        return departmentRepo.findById(id)
                .map(existing -> {
                    existing.setName(updatedDepartment.getName());
                    return departmentRepo.save(existing);
                })
                .orElseThrow(() -> new RuntimeException("Department not found with ID: " + id));
    }

    // Delete a department by ID
    public void deleteDepartment(Long id) {
        if (!departmentRepo.existsById(id)) {
            throw new RuntimeException("Department not found with ID: " + id);
        }
        departmentRepo.deleteById(id);
    }

    // Get complaints assigned to a specific department with ASSIGNED status
    public List<Complaint> getAssignedComplaints(Long departmentId) {
        return complaintRepo.findByDepartmentIdAndStatus(departmentId, ComplaintStatus.ASSIGNED);
    }

    // Resolve a complaint by updating its status and adding a new file path (photo)
    public Complaint resolveComplaint(Long complaintId, String updatedPhotoPath) {
        Complaint complaint = complaintRepo.findById(complaintId)
                .orElseThrow(() -> new RuntimeException("Complaint not found"));

        complaint.setFilePath(updatedPhotoPath);
        complaint.setStatus(ComplaintStatus.RESOLVED);
        return complaintRepo.save(complaint);
    }

    // Reject a complaint
    public Complaint rejectComplaint(Long complaintId) {
        Complaint complaint = complaintRepo.findById(complaintId)
                .orElseThrow(() -> new RuntimeException("Complaint not found"));

        complaint.setStatus(ComplaintStatus.REJECT);
        return complaintRepo.save(complaint);
    }

    // new content added by inija
    public Complaint updateComplaintStatus(Long complaintId, ComplaintStatus newStatus) {
        Complaint complaint = complaintRepo.findById(complaintId)
                .orElseThrow(() -> new RuntimeException("Complaint not found with ID: " + complaintId));

        complaint.setStatus(newStatus);

        return complaintRepo.save(complaint);
    }

    public Complaint uploadSolutionDocument(Long complaintId, MultipartFile file) throws Exception {
        Complaint complaint = complaintRepo.findById(complaintId)
                .orElseThrow(() -> new RuntimeException("Complaint not found with ID: " + complaintId));

        Path uploadPath = Paths.get("dept-document");
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }
        String filename = System.currentTimeMillis() + "_" + file.getOriginalFilename();
        Path fullPath = uploadPath.resolve(filename);
        Files.write(fullPath, file.getBytes());

        complaint.setDeptSolutionFilePath(filename);

        return complaintRepo.save(complaint);
    }
}
