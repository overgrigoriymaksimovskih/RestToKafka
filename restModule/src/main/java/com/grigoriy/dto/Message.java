package com.grigoriy.dto;


public class Message {
    private Integer id;
    private String text;
    private Integer userId;

    public Message( String text, Integer userId) {
        this.text = text;
        this.userId = userId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}
