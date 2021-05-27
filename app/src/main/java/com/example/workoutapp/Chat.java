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

    public void setMessageList(List<Message> messageList) {
        this.messages = messageList;
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

    public void setActivity_id(int activity_id) {
        this.activity_id = activity_id;
    }

    public String getActivity_name() {
        return activity_name;
    }

    public void setActivity_name(String activity_name) {
        this.activity_name = activity_name;
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

    public Boolean getIs_new() {
        return is_new;
    }

    public void setIs_new(Boolean is_new) {
        this.is_new = is_new;
    }

    public float getLastMessageTimestamp() {
        return messages.get(messages.size()-1).getDate_timestamp();
    }

    public String getLastMessageText() {
        return messages.get(messages.size()-1).getText();
    }

    public String getOrganization_photo() {
        return organization_photo;
    }

    public void setOrganization_photo(String organization_photo) {
        this.organization_photo = organization_photo;
    }
}
