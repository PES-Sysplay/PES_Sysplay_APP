package com.example.workoutapp;

import java.util.List;

public class Chat {
    List<Message> messages;
    String username;
    int activity_id;
    String activity_name;
    String organization;
    int id;
    Boolean is_new;
    Message last_message;
    String organization_photo;

    public Chat(List<Message> messageList, String username, int activity_id, String activity_name, String organization, int id, Boolean is_new) {
        this.messages = messageList;
        this.username = username;
        this.activity_id = activity_id;
        this.activity_name = activity_name;
        this.organization = organization;
        this.id = id;
        this.is_new = is_new;
    }

    public List<Message> getMessageList() {
        return messages;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getActivity_id() {
        return activity_id;
    }

    public String getActivity_name() {
        return activity_name;
    }

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOrganization_photo() {
        return organization_photo;
    }

    public Message getLast_message() {
        return last_message;
    }
}
