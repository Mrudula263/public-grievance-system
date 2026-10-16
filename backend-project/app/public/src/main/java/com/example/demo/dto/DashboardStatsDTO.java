package com.example.demo.dto;

import java.util.List;

public class DashboardStatsDTO {
    private long totalComplaints;
    private long resolvedComplaints;
    private long pendingComplaints;
    private long rejectedComplaints;
    // private long adminVerifyComplaints;
    private long assignedComplaints;
    private List<ComplaintReportDTO> recentComplaints; // A few recent ones for the table

    // --- Getters and Setters for all fields ---

    public long getTotalComplaints() {
        return totalComplaints;
    }

    public void setTotalComplaints(long totalComplaints) {
        this.totalComplaints = totalComplaints;
    }

    public long getResolvedComplaints() {
        return resolvedComplaints;
    }

    public void setResolvedComplaints(long resolvedComplaints) {
        this.resolvedComplaints = resolvedComplaints;
    }

    public long getPendingComplaints() {
        return pendingComplaints;
    }

    public void setPendingComplaints(long pendingComplaints) {
        this.pendingComplaints = pendingComplaints;
    }

    public long getRejectedComplaints() {
        return rejectedComplaints;
    }

    public void setRejectedComplaints(long rejectedComplaints) {
        this.rejectedComplaints = rejectedComplaints;
    }

    // public long getAdminVerifyComplaints() {
    // return adminVerifyComplaints;
    // }

    // public void setAdminVerifyComplaints(long adminVerifyComplaints) {
    // this.adminVerifyComplaints = adminVerifyComplaints;
    // }
    public long getAssignedComplaints() {
        return assignedComplaints;
    }

    public void setAssignedComplaints(long adminVerifyComplaints) {
        this.assignedComplaints = adminVerifyComplaints;
    }

    public List<ComplaintReportDTO> getRecentComplaints() {
        return recentComplaints;
    }

    public void setRecentComplaints(List<ComplaintReportDTO> recentComplaints) {
        this.recentComplaints = recentComplaints;
    }
}