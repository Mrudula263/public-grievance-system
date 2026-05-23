package com.example.demo.service;

import com.example.demo.dto.ComplaintReportDTO;
import com.example.demo.entity.*;
import com.example.demo.repository.ComplaintRepository;
import com.example.demo.repository.DepartmentRepository;
import com.example.demo.repository.DistrictRepository;
import com.example.demo.repository.TalukRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ComplaintService {

    private final ComplaintRepository complaintRepo;
    private final DistrictRepository districtRepo;
    private final TalukRepository talukRepo;
    private final DepartmentRepository departmentRepo;

    public ComplaintService(
            ComplaintRepository complaintRepo,
            DistrictRepository districtRepo,
            TalukRepository talukRepo,
            DepartmentRepository departmentRepo) {
        this.complaintRepo = complaintRepo;
        this.districtRepo = districtRepo;
        this.talukRepo = talukRepo;
        this.departmentRepo = departmentRepo;
    }

    public Complaint save(Complaint complaint) {
        attachValidDistrictAndTaluk(complaint);
        complaint.setDepartment(null);
        complaint.setStatus(ComplaintStatus.PENDING);

        Complaint savedComplaint = complaintRepo.save(complaint);
        savedComplaint.generateTicketNumberFromSequence(savedComplaint.getId());

        return complaintRepo.save(savedComplaint);
    }

    public List<Complaint> getAll() {
        return complaintRepo.findAll();
    }

    public Complaint getById(Long id) {
        return complaintRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Complaint not found with ID: " + id));
    }

    public Complaint update(Long id, Complaint newData) {
        return complaintRepo.findById(id).map(existing -> {
            existing.setName(newData.getName());
            existing.setEmail(newData.getEmail());
            existing.setPhone(newData.getPhone());
            existing.setPincode(newData.getPincode());
            existing.setAddress(newData.getAddress());
            existing.setLocation(newData.getLocation());
            existing.setRemarks(newData.getRemarks());
            existing.setFilePath(newData.getFilePath());
            existing.setDistrict(newData.getDistrict());
            existing.setTaluk(newData.getTaluk());
            attachValidDistrictAndTaluk(existing);
            return complaintRepo.save(existing);
        }).orElseThrow(() -> new RuntimeException("Complaint not found with ID: " + id));
    }

    public void delete(Long id) {
        if (!complaintRepo.existsById(id)) {
            throw new RuntimeException("Complaint with ID " + id + " does not exist.");
        }
        complaintRepo.deleteById(id);
    }

    public Complaint updateStatus(Long id, ComplaintStatus status) {
        Complaint complaint = complaintRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Complaint not found with ID: " + id));
        complaint.setStatus(status);
        return complaintRepo.save(complaint);
    }

    public Complaint assignDepartment(Long complaintId, Long departmentId) {
        Complaint complaint = complaintRepo.findById(complaintId)
                .orElseThrow(() -> new RuntimeException("Complaint not found with ID: " + complaintId));
        Department department = departmentRepo.findById(departmentId)
                .orElseThrow(() -> new RuntimeException("Department not found with ID: " + departmentId));
        complaint.setDepartment(department);
        complaint.setStatus(ComplaintStatus.ASSIGNED);
        return complaintRepo.save(complaint);
    }

    public Complaint updatePhoto(Long id, String photoPath) {
        Complaint complaint = complaintRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Complaint not found with ID: " + id));
        complaint.setFilePath(photoPath);
        return complaintRepo.save(complaint);
    }

    private void attachValidDistrictAndTaluk(Complaint complaint) {
        Long districtId = complaint.getDistrict() != null ? complaint.getDistrict().getId() : null;
        Long talukId = complaint.getTaluk() != null ? complaint.getTaluk().getId() : null;
        if (districtId == null || talukId == null) {
            throw new IllegalArgumentException("District and Taluk IDs are required.");
        }
        District district = districtRepo.findById(districtId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid District ID: " + districtId));
        Taluk taluk = talukRepo.findById(talukId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid Taluk ID: " + talukId));
        complaint.setDistrict(district);
        complaint.setTaluk(taluk);
    }

    public List<ComplaintReportDTO> getComplaintReport() {
        List<Complaint> complaints = complaintRepo.fetchAllWithDetails();
        return convertToReportDTOs(complaints);
    }

    public List<ComplaintReportDTO> getComplaintReportByUserId(Long userId) {
        List<Complaint> complaints = complaintRepo.findByUserId(userId);
        return convertToReportDTOs(complaints);
    }

    private List<ComplaintReportDTO> convertToReportDTOs(List<Complaint> complaints) {
        List<ComplaintReportDTO> reportList = new ArrayList<>();
        // int slNo = 1;
        for (Complaint c : complaints) {
            String fullName = c.getUser().getFirstName() + " " + c.getUser().getLastName();
            String district = c.getDistrict() != null ? c.getDistrict().getName() : "N/A";
            String taluk = c.getTaluk() != null ? c.getTaluk().getName() : "N/A";
            String department = c.getDepartment() != null ? c.getDepartment().getName() : "Not Assigned";
            String location = c.getLocation() != null ? c.getLocation() : "Not Provided";

            ComplaintReportDTO dto = new ComplaintReportDTO(
                    // (long) slNo++,
                    c.getId(),
                    fullName,
                    c.getTicketNumber(),
                    c.getRemarks(),
                    c.getComplaintDate(),
                    c.getStatus().toString(),
                    c.getDistrict().getName(),
                    c.getTaluk().getName(),
                    c.getDepartment() != null ? c.getDepartment().getName() : "Not Assigned",
                    c.getLocation(),
                    c.getPincode(),
                    c.getFilePath(),
                    c.getDeptSolutionFilePath());
            reportList.add(dto);
        }
        return reportList;
    }

    public void saveDepartmentSolutionFilePath(Long complaintId, String filePath) {
        Complaint complaint = complaintRepo.findById(complaintId)
                .orElseThrow(() -> new RuntimeException("Complaint solution not found"));

        complaint.setDeptSolutionFilePath(filePath);

        complaintRepo.save(complaint);
    }
}
