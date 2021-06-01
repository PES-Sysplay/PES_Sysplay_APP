package com.example.workoutapp;

public class Organizer {

    String name;
    String photo;
    Float rank;
    boolean superhost;

    public Organizer(String name, String photo, Float rank, boolean superhost) {
        this.name = name;
        this.photo = photo;
        this.rank = rank;
        this.superhost = superhost;
    }

    public Organizer() {
    }

    public String getName() {
        return name;
    }

    public String getPhoto() {
        return photo;
    }

    public Float getRank() {
        return rank;
    }

    public boolean isSuperhost() {
        return superhost;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSuperhost(boolean superhost) {
        this.superhost = superhost;
    }
}
