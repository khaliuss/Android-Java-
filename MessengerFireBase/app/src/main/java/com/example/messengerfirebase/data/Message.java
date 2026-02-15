package com.example.messengerfirebase.data;

public class Message {
    private String senderId;
    private String receiverId;

    private String message;

    public Message(String senderId, String receiverId, String message) {
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.message = message;
    }

    public Message() {
    }

    public String getSenderId() {
        return senderId;
    }

    public String getReceiverId() {
        return receiverId;
    }

    public String getMessage() {
        return message;
    }
}
