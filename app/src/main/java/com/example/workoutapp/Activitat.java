package com.example.workoutapp;

public class Activitat {
    String name;
    String description;
    String photo_url;
    String activity_type_id;
    String date_time;
    int duration;
    int preu;
    int preuSoci;
    int capacity;
    String status;
    String location;
    boolean onlySoci;
    String organizerName;

    //empty constructor
    public Activitat() {
    }

    //constructor with all parameters
    public Activitat(String name, String description, String photo_url, String activity_type_id, String date_time, int duration, int preu, int preuSoci, int capacity, String status, String location, boolean onlySoci, String organizerName) {
        this.name = name;
        this.description = description;
        this.photo_url = photo_url;
        this.activity_type_id = activity_type_id;
        this.date_time = date_time;
        this.duration = duration;
        this.preu = preu;
        this.preuSoci = preuSoci;
        this.capacity = capacity;
        this.status = status;
        this.location = location;
        this.onlySoci = onlySoci;
        this.organizerName = organizerName;
    }

    //constructor with the paramters we will most likely use
    public Activitat(String name, String description, String photo_url, String date_time, int duration, int preu, int preuSoci, String organizerName) {
        this.name = name;
        this.description = description;
        this.photo_url = photo_url;
        this.date_time = date_time;
        this.duration = duration;
        this.preu = preu;
        this.preuSoci = preuSoci;
        this.organizerName = organizerName;
    }

    public Activitat(String name, String description, String photo_url, String date_time, int duration, int preu, int preuSoci, String organizerName, String activity_type_id) {
        this.name = name;
        this.description = description;
        this.photo_url = photo_url;
        this.date_time = date_time;
        this.duration = duration;
        this.preu = preu;
        this.preuSoci = preuSoci;
        this.organizerName = organizerName;
        this.activity_type_id = activity_type_id;
    }

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

    public int getDuration() {
        return duration;
    }

    public int getPreu() {
        return preu;
    }

    public int getPreuSoci() {
        return preuSoci;
    }

    public int getCapacity() {
        return capacity;
    }

    public String getStatus() {
        return status;
    }

    public String getLocation() {
        return location;
    }

    public boolean isOnlySoci() {
        return onlySoci;
    }

    public String getOrganizerName() {
        return organizerName;
    }

    public String getDateTimeString(){
        //DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM,FormatStyle.SHORT);
        //return dateTime.format(formatter);
        return date_time;
    }

    public String getActivity_type_id() {
        return activity_type_id;
    }
}
