package com.example.demo.repository;

import com.example.demo.entity.Complaint;
import com.example.demo.entity.ComplaintStatus;
import com.example.demo.dto.ComplaintReportDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ComplaintRepository extends JpaRepository<Complaint, Long> {

    List<Complaint> findByStatus(ComplaintStatus status);

    List<Complaint> findByDepartmentIdAndStatus(Long departmentId, ComplaintStatus status);

    List<Complaint> findByDepartmentIsNullAndStatus(ComplaintStatus status);

    @Query("SELECT c FROM Complaint c ORDER BY c.complaintDate ASC")
    List<Complaint> findAllForReport();

    @Query("SELECT new com.example.demo.dto.ComplaintReportDTO(" +
            "c.id, c.ticketNumber, c.name, c.complaintDate, c.status) " +
            "FROM Complaint c WHERE c.id = :id")
    ComplaintReportDTO findReportById(@Param("id") Long id);

    @Query("SELECT c FROM Complaint c WHERE c.user.id = :userId ORDER BY c.complaintDate ASC")
    List<Complaint> findByUserId(@Param("userId") Long userId);

    @Query(value = """
                SELECT
                    ROW_NUMBER() OVER (ORDER BY c.complaint_date) AS sl_no,
                    c.id AS complaint_id,
                    c.name,
                    c.ticket_number,
                    c.remarks,
                    c.complaint_date,
                    c.status
                FROM complaints c
            """, nativeQuery = true)
    List<Object[]> fetchComplaintReportRaw();

    @Query("SELECT c FROM Complaint c " +
            "LEFT JOIN FETCH c.district " +
            "LEFT JOIN FETCH c.taluk " +
            "LEFT JOIN FETCH c.department " +
            "LEFT JOIN FETCH c.user")
    List<Complaint> fetchAllWithDetails();
}
