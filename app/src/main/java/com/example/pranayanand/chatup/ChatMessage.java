package com.example.pranayanand.chatup;

import java.util.Date;

/**
 * Created by Pranay Anand on 13-08-2017.
 */

public class ChatMessage {


    String username;
    String message;
    long time;

    public ChatMessage(String username, String message) {
        this.username = username;
        this.message = message;
        time = new Date().getTime();
    }

    public ChatMessage() {

    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }
}

