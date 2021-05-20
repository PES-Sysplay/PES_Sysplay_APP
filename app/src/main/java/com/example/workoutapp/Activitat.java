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
    float number_participants;
    String status;
    String location;
    boolean only_member;
    String organization;
    String created;
    Long timestamp; //in seconds
    boolean joined;
    boolean favorite;
    float clients_joined;
    boolean checked_in;
    boolean reported;
    boolean reviewed;
    String token;
    String date_time_finish;
  
    //empty constructor
    public Activitat() {
    }

    //constructor with all parameters
    public Activitat(String name, String description, String photo_url, String activity_type_id, String date_time, float duration, float normal_price, float member_price, float number_participants, String status, String location, boolean only_member, String organization, Long timestamp, String date_time_finish) {
        this.name = name;
        this.description = description;
        this.photo_url = photo_url;
        this.activity_type_id = activity_type_id;
        this.date_time = date_time;
        this.duration = duration;
        this.normal_price = normal_price;
        this.member_price = member_price;
        this.number_participants = number_participants;
        this.status = status;
        this.location = location;
        this.only_member = only_member;
        this.organization = organization;
        this.timestamp = timestamp;
        this.date_time_finish = date_time_finish;
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

    public int getNumberParticipants() {
        return Math.round(number_participants);
    }
    public int getClientJoined(){
        return Math.round(clients_joined);
    }

    public String getStatus() {
        return status;
    }

    public String getLocation() {
        return location;
    }

    public String getToken() { return token; }

    public boolean isOnlySoci() {
        return only_member;
    }

    public String getOrganizerName() {
        return organization;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public String getDateTimeString(){
        //DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM,FormatStyle.SHORT);
        //return dateTime.format(formatter);
        return date_time;
    }
    public String getDateTimeFinish(){
        return date_time_finish;
    }

    public boolean isJoined() { return joined; }

    public boolean isChecked_in() {return checked_in; }

    public void toggleJoined() { this.joined = !this.joined; }

    public boolean isFavorite() { return favorite; }

    public void toggleFavorite() { this.favorite = !this.favorite; }

    public void toggleReported() { this.reported = !this.reported; }

    public boolean isReported() { return reported; }

    public void toggleReviewed() { this.reviewed = !this.reviewed; }

    public boolean isReviewed() { return reviewed; }

    public String getActivity_type_id() {
        return activity_type_id;
    }

    public boolean isOld() { return (timestamp+(60*duration))*1000L<System.currentTimeMillis(); }
}