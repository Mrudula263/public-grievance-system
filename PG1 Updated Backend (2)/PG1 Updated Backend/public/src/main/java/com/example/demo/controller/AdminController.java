package com.example.demo.controller;

import com.example.demo.dto.ComplaintReportDTO;
import com.example.demo.dto.JwtResponse;
import com.example.demo.dto.LoginRequest;
import com.example.demo.entity.*;
import com.example.demo.repository.UserRepository;
import com.example.demo.security.JwtUtil;
import com.example.demo.service.ComplaintService;
import com.example.demo.service.DepartmentService;
import com.example.demo.service.UserService;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.example.demo.dto.DashboardStatsDTO;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.*;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@RestController
@RequestMapping("/api/admin")
@CrossOrigin("*")
public class AdminController {

    private final ComplaintService complaintService;
    private final UserService userService;
    private final DepartmentService departmentService;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;

    private static final String UPLOAD_DIR = "uploads/";

    public AdminController(
            ComplaintService complaintService,
            UserService userService,
            DepartmentService departmentService,
            AuthenticationManager authenticationManager,
            JwtUtil jwtUtil,
            UserRepository userRepository) {
        this.complaintService = complaintService;
        this.userService = userService;
        this.departmentService = departmentService;
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.userRepository = userRepository;
    }

    // NEW API to serve uploaded documents/photos
    // @GetMapping("/documents/{filename:.+}")
    // public ResponseEntity<Resource> getDocument(@PathVariable String filename) {
    // try {
    // Path filePath = Paths.get(UPLOAD_DIR).resolve(filename).normalize();
    // Resource resource = new UrlResource(filePath.toUri());

    // if (!resource.exists()) {
    // return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    // }

    // // Detect content type
    // String contentType;
    // try {
    // contentType = Files.probeContentType(filePath);
    // } catch (IOException e) {
    // contentType = "application/octet-stream";
    // }

    // return ResponseEntity.ok()
    // .contentType(
    // MediaType.parseMediaType(contentType != null ? contentType :
    // "application/octet-stream"))
    // .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" +
    // resource.getFilename() + "\"")
    // .body(resource);

    // } catch (MalformedURLException e) {
    // return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    // }
    // }

    @GetMapping("/dashboard/stats")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<DashboardStatsDTO> getDashboardStats() {
        List<Complaint> allComplaints = complaintService.getAll();

        DashboardStatsDTO stats = new DashboardStatsDTO();
        stats.setTotalComplaints(allComplaints.size());
        stats.setResolvedComplaints(
                allComplaints.stream().filter(c -> c.getStatus() == ComplaintStatus.RESOLVED).count());
        stats.setPendingComplaints(
                allComplaints.stream().filter(c -> c.getStatus() == ComplaintStatus.PENDING).count());
        stats.setRejectedComplaints(
                allComplaints.stream().filter(c -> c.getStatus() == ComplaintStatus.REJECT).count());
        // stats.setAdminVerifyComplaints(
        // allComplaints.stream().filter(c -> c.getStatus() ==
        // ComplaintStatus.ADMIN_VERIFY).count());
        stats.setAssignedComplaints(
                allComplaints.stream().filter(c -> c.getStatus() == ComplaintStatus.ASSIGNED).count());

        List<Complaint> recentComplaints = allComplaints.stream()
                .sorted(Comparator.comparing(Complaint::getComplaintDate).reversed())
                .limit(5)
                .collect(Collectors.toList());

        List<ComplaintReportDTO> recent = IntStream.range(0, recentComplaints.size())
                .mapToObj(i -> {
                    Complaint c = recentComplaints.get(i);
                    return new ComplaintReportDTO(
                            // (long) (i + 1),
                            c.getId(),
                            c.getName(),
                            c.getTicketNumber(),
                            c.getRemarks(),
                            c.getComplaintDate(),
                            c.getStatus() != null ? c.getStatus().toString() : "UNKNOWN",
                            c.getDistrict() != null ? c.getDistrict().getName() : "N/A",
                            c.getTaluk() != null ? c.getTaluk().getName() : "N/A",
                            c.getDepartment() != null ? c.getDepartment().getName() : "Not Assigned",
                            c.getLocation(),
                            c.getPincode(),
                            c.getFilePath(),
                            c.getDeptSolutionFilePath());
                })
                .collect(Collectors.toList());

        stats.setRecentComplaints(recent);

        return ResponseEntity.ok(stats);
    }

    @PostMapping("/signin")
    public ResponseEntity<?> adminLogin(@RequestBody LoginRequest request) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(),
                            request.getPassword()));

            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            User user = userRepository.findByEmail(userDetails.getUsername())
                    .orElseThrow(() -> new RuntimeException("User not found"));

            if (!user.getRole().equals("ADMIN")) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body("Access denied: Not an admin");
            }

            String token = jwtUtil.generateToken(user);
            return ResponseEntity.ok(new JwtResponse(token, user.getEmail(), user.getRole()));

        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/reports")
    public ResponseEntity<List<ComplaintReportDTO>> getAllReports() {
        List<Complaint> complaints = complaintService.getAll();

        List<ComplaintReportDTO> reports = IntStream.range(0, complaints.size())
                .mapToObj(i -> {
                    Complaint c = complaints.get(i);

                    User user = c.getUser();
                    String name = user != null
                            ? (user.getFirstName() + " " + user.getLastName())
                            : "Unknown User";

                    return new ComplaintReportDTO(
                            // (long) (i + 1),
                            c.getId(),
                            name,
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

    @GetMapping("/reports/{id}")
    public ResponseEntity<?> viewComplaintDetails(@PathVariable Long id) {
        try {
            Complaint complaint = complaintService.getById(id);
            return ResponseEntity.ok(complaint);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Complaint not found");
        }
    }

    @PutMapping("/complaints/{id}/status")
    public ResponseEntity<?> updateStatus(@PathVariable Long id, @RequestParam ComplaintStatus status) {
        try {
            Complaint updated = complaintService.updateStatus(id, status);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Complaint not found");
        }
    }

    @PostMapping("/complaints/{id}/photo")
    public ResponseEntity<?> uploadUpdatedPhoto(@PathVariable Long id,
            @RequestParam("file") MultipartFile file) {
        try {
            if (file.isEmpty()) {
                return ResponseEntity.badRequest().body("File is empty");
            }

            Path uploadPath = Paths.get(UPLOAD_DIR);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            String filename = System.currentTimeMillis() + "_" + file.getOriginalFilename();
            Path fullPath = uploadPath.resolve(filename);
            Files.write(fullPath, file.getBytes());

            Complaint updated = complaintService.updatePhoto(id, filename);
            return ResponseEntity.ok(updated);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Photo upload failed: " + e.getMessage());
        }
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/departments")
    @PreAuthorize("hasRole('ADMIN') or hasRole('DEPARTMENT')")
    public ResponseEntity<List<Department>> getAllDepartments() {
        return ResponseEntity.ok(departmentService.getAllDepartments());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/departments")
    public ResponseEntity<?> addDepartment(@RequestBody Department department) {
        try {
            Department saved = departmentService.addDepartment(department);
            return ResponseEntity.status(HttpStatus.CREATED).body(saved);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Department creation failed: " + e.getMessage());
        }
    }

    @PutMapping("/departments/{id}")
    public ResponseEntity<?> updateDepartment(@PathVariable Long id, @RequestBody Department department) {
        try {
            return ResponseEntity.ok(departmentService.updateDepartment(id, department));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Department not found");
        }
    }

    @PutMapping("/complaints/{complaintId}/assign-department")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> assignDepartment(
            @PathVariable Long complaintId,
            @RequestBody Map<String, Long> payload) {
        try {
            Long departmentId = payload.get("departmentId");
            if (departmentId == null) {
                return ResponseEntity.badRequest().body("departmentId is required in the request body.");
            }
            Complaint updated = complaintService.assignDepartment(complaintId, departmentId);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Complaint or Department not found");
        }
    }

    @DeleteMapping("/departments/{id}")
    public ResponseEntity<?> deleteDepartment(@PathVariable Long id) {
        try {
            departmentService.deleteDepartment(id);
            return ResponseEntity.ok("Department deleted successfully");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Department not found");
        }
    }
}
