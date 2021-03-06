package com.example.ping02.Model;

public class Intel {
    private String Sender;
    private String Receiver;
    private String Message;
    private long timestamp;
    private boolean isseen;

    public Intel(String sender, String receiver, String message, Long timestamp) {
        Sender = sender;
        Receiver = receiver;
        Message = message;
        timestamp=timestamp;
    }

    public Intel(String sender, String receiver, String message) {
        Sender = sender;
        Receiver = receiver;
        Message = message;
    }

    public Intel(String sender, String receiver, String message, boolean isseen) {
        Sender = sender;
        Receiver = receiver;
        Message = message;
        this.isseen = isseen;
    }

    public Intel() {
    }

    public String getSender() {
        return Sender;
    }

    public void setSender(String sender) {
        Sender = sender;
    }

    public String getReceiver() {
        return Receiver;
    }

    public void setReceiver(String receiver) {
        Receiver = receiver;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public Long getTimestamp(Long timestamp) {
        return timestamp;
    }

    public Long setTimestamp(long timestamp) {
        this.timestamp = timestamp;
        return timestamp;
    }

    public boolean isIsseen() {
        return isseen;
    }

    public void setIsseen(boolean isseen) {
        this.isseen = isseen;
    }
}
