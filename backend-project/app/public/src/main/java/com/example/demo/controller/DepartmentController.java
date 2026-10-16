// package com.example.demo.controller;

// import com.example.demo.entity.Complaint;
// import com.example.demo.entity.Department;
// import com.example.demo.service.DepartmentService;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.http.ResponseEntity;
// import org.springframework.web.bind.annotation.*;

// import java.util.List;

// @RestController
// @RequestMapping("/api/departments")
// @CrossOrigin("*")
// public class DepartmentController {

//     private final DepartmentService service;

//     @Autowired
//     public DepartmentController(DepartmentService service) {
//         this.service = service;
//     }

//     @GetMapping
//     public ResponseEntity<List<Department>> getDepartments() {
//         return ResponseEntity.ok(service.getAllDepartments());
//     }

//     @PostMapping
//     public ResponseEntity<Department> addDepartment(@RequestBody Department department) {
//         Department saved = service.addDepartment(department);
//         return ResponseEntity.ok(saved);
//     }

//     @GetMapping("/{departmentId}/assigned-complaints")
//     public ResponseEntity<List<Complaint>> getAssignedComplaints(@PathVariable Long departmentId) {
//         return ResponseEntity.ok(service.getAssignedComplaints(departmentId));
//     }

//     @PutMapping("/resolve/{complaintId}")
//     public ResponseEntity<Complaint> resolveComplaint(
//             @PathVariable Long complaintId,
//             @RequestParam String photoUrl) {
//         return ResponseEntity.ok(service.resolveComplaint(complaintId, photoUrl));
//     }

//     @PutMapping("/reject/{complaintId}")
//     public ResponseEntity<Complaint> rejectComplaint(@PathVariable Long complaintId) {
//         return ResponseEntity.ok(service.rejectComplaint(complaintId));
//     }
// }

package com.example.demo.controller;

import com.example.demo.dto.ComplaintReportDTO;
import com.example.demo.dto.DashboardStatsDTO;
import com.example.demo.entity.Complaint;
import com.example.demo.entity.ComplaintStatus;
import com.example.demo.entity.Department;
import com.example.demo.entity.User;
import com.example.demo.service.ComplaintService;
import com.example.demo.service.DepartmentService;
import com.example.demo.service.UserService;
import com.example.demo.dto.DepartmentDashboardDTO;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@RestController
@RequestMapping("/api/department")
@PreAuthorize("hasRole('DEPARTMENT')")
public class DepartmentController {

    private final ComplaintService complaintService;
    private final UserService userService;
    private final DepartmentService service;

    public DepartmentController(ComplaintService complaintService, UserService userService, DepartmentService service) {
        this.complaintService = complaintService;
        this.userService = userService;
        this.service = service;
    }

    @GetMapping("/dashboard/stats")
    public ResponseEntity<DashboardStatsDTO> getDepartmentDashboardStats() {
        List<Complaint> allComplaints = complaintService.getAll();

        DashboardStatsDTO stats = new DashboardStatsDTO();
        stats.setTotalComplaints(allComplaints.size());
        stats.setResolvedComplaints(
                allComplaints.stream().filter(c -> c.getStatus() == ComplaintStatus.RESOLVED).count());
        // stats.setPendingComplaints(
        // allComplaints.stream().filter(c -> c.getStatus() ==
        // ComplaintStatus.PENDING).count());
        // stats.setRejectedComplaints(
        // allComplaints.stream().filter(c -> c.getStatus() ==
        // ComplaintStatus.REJECT).count());
        // stats.setAdminVerifyComplaints(
        // allComplaints.stream().filter(c -> c.getStatus() ==
        // ComplaintStatus.ADMIN_VERIFY).count());
        stats.setAssignedComplaints(
                allComplaints.stream().filter(c -> c.getStatus() == ComplaintStatus.ASSIGNED).count());

        List<ComplaintReportDTO> reports = allComplaints.stream()
                // First, filter the list to only include 'ASSIGNED' complaints
                .filter(c -> c.getStatus() == ComplaintStatus.ASSIGNED)

                // Next, sort the filtered list by date (newest first)
                .sorted(Comparator.comparing(Complaint::getComplaintDate).reversed())

                // Then, take only the top 5 from the sorted list
                .limit(5)

                // Finally, map the result to your DTO
                .map(c -> {
                    User user = c.getUser();
                    String name = user != null ? (user.getFirstName() + " " + user.getLastName()) : "Unknown User";

                    return new ComplaintReportDTO(
                            c.getId(), // Use the real ID, not a generated slNo
                            c.getName(),
                            c.getTicketNumber(),
                            c.getRemarks(),
                            c.getComplaintDate(),
                            c.getStatus().toString(),
                            c.getDistrict() != null ? c.getDistrict().getName() : "N/A",
                            c.getTaluk() != null ? c.getTaluk().getName() : "N/A",
                            c.getDepartment() != null ? c.getDepartment().getName() : "Not Assigned",
                            c.getLocation(),
                            c.getPincode(),
                            c.getFilePath(),
                            c.getDeptSolutionFilePath());
                })
                .collect(Collectors.toList());

        // Now, 'reports' contains a list of up to 5 DTOs that are sorted and filtered.
        // You can set this list in your stats object or return it directly.
        stats.setRecentComplaints(reports);
        return ResponseEntity.ok(stats);
    }

    @GetMapping("/assigned-complaints")
    public ResponseEntity<List<ComplaintReportDTO>> getAllComplaintsForDepartmentView() {
        List<Complaint> complaints = complaintService.getAll();

        List<ComplaintReportDTO> reports = complaints.stream()
                .filter(c -> c.getStatus() == ComplaintStatus.ASSIGNED || c.getStatus() == ComplaintStatus.RESOLVED)
                .map(c -> {
                    User user = c.getUser();
                    String name = user != null ? (user.getFirstName() + " " + user.getLastName()) : "Unknown User";
                    return new ComplaintReportDTO(
                            c.getId(),
                            c.getName(),
                            c.getTicketNumber(),
                            c.getRemarks(),
                            c.getComplaintDate(),
                            c.getStatus().toString(),
                            c.getDistrict() != null ? c.getDistrict().getName() : "N/A",
                            c.getTaluk() != null ? c.getTaluk().getName() : "N/A",
                            c.getDepartment() != null ? c.getDepartment().getName() : "Not Assigned",
                            c.getLocation(),
                            c.getPincode(),
                            c.getFilePath(),
                            c.getDeptSolutionFilePath());
                })
                .collect(Collectors.toList());
        return ResponseEntity.ok(reports);
    }

    @PutMapping("/complaints/{complaintId}/status")
    public ResponseEntity<?> updateComplaintStatus(
            @PathVariable Long complaintId,
            @RequestParam ComplaintStatus status) {
        try {
            Complaint updatedComplaint = service.updateComplaintStatus(complaintId, status);
            return ResponseEntity.ok(updatedComplaint);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    // @PostMapping("/complaints/{id}/document")
    // public ResponseEntity<?> uploadSolutionDocument(
    // @PathVariable Long id,
    // @RequestParam("file") MultipartFile file) {
    // try {
    // if (file.isEmpty()) {
    // return ResponseEntity.badRequest().body("File cannot be empty.");
    // }
    // Complaint updatedComplaint = service.uploadSolutionDocument(id, file);
    // return ResponseEntity.ok(updatedComplaint);
    // } catch (Exception e) {
    // return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
    // .body("File upload failed: " + e.getMessage());
    // }
    // }
    @PostMapping("/complaints/{id}/document")
    @PreAuthorize("hasRole('DEPARTMENT')")
    public ResponseEntity<?> uploadSolutionDocument(
            @PathVariable Long id,
            @RequestParam("file") MultipartFile file) {

        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body("File is empty");
        }

        try {
            Path uploadPath = Paths.get("dept-document");
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            String filename = System.currentTimeMillis() + "_" + file.getOriginalFilename();
            Path filePath = uploadPath.resolve(filename);
            Files.write(filePath, file.getBytes());

            complaintService.saveDepartmentSolutionFilePath(id, filePath.toString());

            return ResponseEntity.ok("File uploaded successfully");

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Could not upload file: " + e.getMessage());
        }
    }
}