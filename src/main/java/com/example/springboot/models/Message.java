package com.example.springboot.models;

import org.springframework.stereotype.Component;

@Component
public class Message {
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    private String message;

}
