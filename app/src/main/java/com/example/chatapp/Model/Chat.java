package com.example.chatapp.Model;

public class Chat {
    String sender;
    String receiver;
    String message;
    boolean isseen;

    public Chat(String sender, String receiver, String message, boolean isseen) {
        this.sender = sender;
       this.receiver = receiver;
        this.message = message;
        this.isseen=isseen;
    }

    public Chat() {
    }

    public boolean isIsseen() {
        return isseen;
    }

    public void setIsseen(boolean isseen) {
        this.isseen = isseen;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
