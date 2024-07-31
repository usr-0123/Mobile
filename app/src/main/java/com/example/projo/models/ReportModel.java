package com.example.projo.models;

import java.util.List;

public class ReportModel {
    private long datetime;
    private String location;
    private String message;
    private String reportTitle;
    private String userEmail;
    private List<String> attachmentURLs;

    public ReportModel() {
        // No-argument constructor required for Firebase
    }

    public ReportModel(long datetime, String location, String message, String reportTitle, String userEmail, List<String> mediaUrls) {
        this.datetime = datetime;
        this.location = location;
        this.message = message;
        this.reportTitle = reportTitle;
        this.userEmail = userEmail;
        this.attachmentURLs = attachmentURLs;
    }

    // Getters and Setters
    public long getDatetime() {
        return datetime;
    }

    public void setDatetime(long datetime) {
        this.datetime = datetime;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getReportTitle() {
        return reportTitle;
    }

    public void setReportTitle(String reportTitle) {
        this.reportTitle = reportTitle;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public List<String> getMediaUrls() {
        return attachmentURLs;
    }

    public void setMediaUrls(List<String> mediaUrls) {
        this.attachmentURLs = mediaUrls;
    }
}