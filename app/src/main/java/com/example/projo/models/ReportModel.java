package com.example.projo.models;

import java.util.List;

public class ReportModel {
    private String reportId;
    private long datetime;
    private String location;
    private String message;
    private String reportTitle;
    private String userId;
    private String firstName;
    private String lastName;
    private List<String> attachmentURLs;

    public ReportModel() {
        // No-argument constructor required for Firebase
    }

    public ReportModel(long datetime, String reportId, String firstName, String lastName, String location, String message, String reportTitle, String userId, List<String> mediaUrls) {
        this.reportId = reportId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.datetime = datetime;
        this.location = location;
        this.message = message;
        this.reportTitle = reportTitle;
        this.userId = userId;
        this.attachmentURLs = attachmentURLs;
    }

    // Getters and Setters
    public String getReportId() {return reportId;};

    public void setReportId(String reportId) {this.reportId = reportId;}

    public String getFirstName() {return firstName;}

    public void setFirstName(String firstName) {this.firstName = firstName;}

    public String getLastName() {return lastName;}

    public void setLastName(String lastName) {this.firstName = lastName;}

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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public List<String> getMediaUrls() {
        return attachmentURLs;
    }

    public void setMediaUrls(List<String> mediaUrls) {
        this.attachmentURLs = mediaUrls;
    }
}