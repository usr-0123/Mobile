package com.example.projo.models;

public class ReportReplyModel {

    private String replyId;
    private String reportId;
    private String userId;
    private String message;
    private long timestamp;

    // Default constructor required for calls to DataSnapshot.getValue(ReplyModel.class)
    public ReportReplyModel() {
    }

    public ReportReplyModel(String replyId, String reportId, String userId, String message, long timestamp) {
        this.replyId = replyId;
        this.reportId = reportId;
        this.userId = userId;
        this.message = message;
        this.timestamp = timestamp;
    }

    // Getters and setters
    public String getReplyId() {
        return replyId;
    }

    public void setReplyId(String replyId) {
        this.replyId = replyId;
    }

    public String getReportId() {
        return reportId;
    }

    public void setReportId(String reportId) {
        this.reportId = reportId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
