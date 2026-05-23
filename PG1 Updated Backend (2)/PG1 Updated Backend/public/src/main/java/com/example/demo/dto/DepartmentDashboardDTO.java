package com.example.demo.dto;

import java.util.List;

public class DepartmentDashboardDTO {
    private long totalAssigned;
    // private long pendingComplaints;
    private long resolvedComplaints;
    private long rejectedComplaints;
    private List<ComplaintReportDTO> recentComplaints;

    // --- Getters and Setters for all fields ---
    // (Aap inhein apni IDE se automatically generate kar sakte hain)

    public long getTotalAssigned() {
        return totalAssigned;
    }

    public void setTotalAssigned(long totalAssigned) {
        this.totalAssigned = totalAssigned;
    }

    // public long getPendingComplaints() {
    // return pendingComplaints;
    // }

    // public void setPendingComplaints(long pendingComplaints) {
    // this.pendingComplaints = pendingComplaints;
    // }

    public long getResolvedComplaints() {
        return resolvedComplaints;
    }

    public void setResolvedComplaints(long resolvedComplaints) {
        this.resolvedComplaints = resolvedComplaints;
    }

    // public long getRejectedComplaints() {
    // return rejectedComplaints;
    // }

    // public void setRejectedComplaints(long rejectedComplaints) {
    // this.rejectedComplaints = rejectedComplaints;
    // }

    public List<ComplaintReportDTO> getRecentComplaints() {
        return recentComplaints;
    }

    public void setRecentComplaints(List<ComplaintReportDTO> recentComplaints) {
        this.recentComplaints = recentComplaints;
    }
}