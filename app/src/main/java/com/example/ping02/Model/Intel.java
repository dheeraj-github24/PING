package com.example.ping02.Model;

public class Intel {
    private String Sender;
    private String Receiver;
    private String Message;
    private long timestamp;

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

    public long getTimestamp() {
        return timestamp;
    }

    public String setTimestamp(long timestamp) {
        this.timestamp = timestamp;
        return null;
    }
}
