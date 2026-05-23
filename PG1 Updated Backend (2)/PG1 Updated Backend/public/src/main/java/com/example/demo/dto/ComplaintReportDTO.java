package com.example.demo.dto;

import java.time.LocalDate;

public class ComplaintReportDTO {

    // private Long slNo;
    private Long complaintId;
    private String name;
    private String ticketNumber;
    private String remarks;
    private LocalDate complaintDate;
    private String status;
    private String actions;

    private String district;
    private String taluk;
    private String department;
    private String location;
    private String pincode;
    private String filePath;
    private String deptSolutionFilePath;

    public ComplaintReportDTO(Long complaintId, String name, String ticketNumber,
            String remarks, LocalDate complaintDate, String status,
            String district, String taluk, String department, String location, String pincode, String filePath,
            String deptSolutionFilePath) {

        // this.slNo = slNo;
        this.complaintId = complaintId;
        this.name = name;
        this.ticketNumber = ticketNumber;
        this.remarks = remarks;
        this.complaintDate = complaintDate;
        this.status = status;
        this.district = district;
        this.taluk = taluk;
        this.department = department;
        this.location = location;
        this.pincode = pincode;
        this.filePath = filePath;
        this.deptSolutionFilePath = deptSolutionFilePath;
        this.actions = "/api/admin/reports/" + complaintId;
    }

    // public Long getSlNo() {
    // return slNo;
    // }

    public Long getComplaintId() {
        return complaintId;
    }

    public String getName() {
        return name;
    }

    public String getTicketNumber() {
        return ticketNumber;
    }

    public String getRemarks() {
        return remarks;
    }

    public LocalDate getComplaintDate() {
        return complaintDate;
    }

    public String getStatus() {
        return status;
    }

    public String getActions() {
        return actions;
    }

    public String getDistrict() {
        return district;
    }

    public String getTaluk() {
        return taluk;
    }

    public String getDepartment() {
        return department;
    }

    public String getLocation() {
        return location;

    }

    public String getPincode() {
        return pincode;
    }

    public String getFilePath() {
        return filePath;
    }

    public String getDeptSolutionFilePath() {
        return deptSolutionFilePath;
    }

}
