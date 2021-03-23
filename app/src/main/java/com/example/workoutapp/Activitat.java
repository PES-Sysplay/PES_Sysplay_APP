package com.example.workoutapp;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

public class Activitat {
    String name;
    String description;
    String photo;
    LocalDateTime dateTime;
    int duration;
    int preu;
    int preuSoci;
    int capacity;
    int status;
    String location;
    boolean onlySoci;
    String organizerName;

    //empty constructor
    public Activitat() {
    }

    //constructor with all parameters
    public Activitat(String name, String description, String photo, LocalDateTime dateTime, int duration, int preu, int preuSoci, int capacity, int status, String location, boolean onlySoci, String organizerName) {
        this.name = name;
        this.description = description;
        this.photo = photo;
        this.dateTime = dateTime;
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
    public Activitat(String name, String description, String photo, LocalDateTime dateTime, int duration, int preu, int preuSoci, String organizerName) {
        this.name = name;
        this.description = description;
        this.photo = photo;
        this.dateTime = dateTime;
        this.duration = duration;
        this.preu = preu;
        this.preuSoci = preuSoci;
        this.organizerName = organizerName;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getPhoto() {
        return photo;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
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

    public int getStatus() {
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
        DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM,FormatStyle.SHORT);
        return dateTime.format(formatter);
    }
}
