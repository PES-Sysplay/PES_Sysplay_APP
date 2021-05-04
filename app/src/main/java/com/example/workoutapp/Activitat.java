package com.example.workoutapp;

public class Activitat {
    Integer id;
    String name;
    String description;
    String photo_url;
    String activity_type_id;
    String date_time;
    float duration;
    float normal_price;
    float member_price;
    float capacity;
    String status;
    String location;
    boolean only_member;
    String organization;
    boolean joined;
    boolean favorite;

    //empty constructor
    public Activitat() {
    }

    //constructor with all parameters
    public Activitat(String name, String description, String photo_url, String activity_type_id, String date_time, float duration, float normal_price, float member_price, float capacity, String status, String location, boolean only_member, String organization) {
        this.name = name;
        this.description = description;
        this.photo_url = photo_url;
        this.activity_type_id = activity_type_id;
        this.date_time = date_time;
        this.duration = duration;
        this.normal_price = normal_price;
        this.member_price = member_price;
        this.capacity = capacity;
        this.status = status;
        this.location = location;
        this.only_member = only_member;
        this.organization = organization;
    }

    //constructor with the paramters we will most likely use
    public Activitat(String name, String description, String photo_url, String date_time, float duration, float normal_price, float member_price, String organization) {
        this.name = name;
        this.description = description;
        this.photo_url = photo_url;
        this.date_time = date_time;
        this.duration = duration;
        this.normal_price = normal_price;
        this.member_price = member_price;
        this.organization = organization;
    }

    public Integer getId() { return id; }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getPhoto_url() {
        return photo_url;
    }

    public String getDate_time() {
        return date_time;
    }

    public float getDuration() {
        return duration;
    }

    public String getPreu() {
        return String.valueOf(normal_price);
    }

    public String getPreuSoci() {
        return String.valueOf(member_price);
    }

    public float getCapacity() {
        return capacity;
    }

    public String getStatus() {
        return status;
    }

    public String getLocation() {
        return location;
    }

    public boolean isOnlySoci() {
        return only_member;
    }

    public String getOrganizerName() {
        return organization;
    }

    public String getDateTimeString(){
        //DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM,FormatStyle.SHORT);
        //return dateTime.format(formatter);
        return date_time;
    }

    public boolean isJoined() { return joined; }

    public void toggleJoined() { this.joined = !this.joined; }

    public boolean isFavorite() { return favorite; }

    public void toggleFavorite() { this.favorite = !this.favorite; }

    public String getActivity_type_id() {
        return activity_type_id;
    }
}