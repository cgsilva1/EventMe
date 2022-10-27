package com.example.eventme;

import java.util.List;

public class User {
    private String name;
    private String birthday;
    private String email;
    private String passwordHash;
    private List<Event> reservations;
    private Location location;

    public User(String name, String birthday, String email, String passwordHash, Location location) {
        this.name = name;
        this.birthday = birthday;
        this.email = email;
        this.passwordHash = passwordHash;
        this.location = location;
    }

    public String getName() {
        return name;
    }

    //not in design doc
    public void setName(String name) {
        this.name = name;
    }

    public String getBirthday() {
        return birthday;
    }

    //not in design doc
    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    //not in design doc
    public String getEmail() {
        return email;
    }

    //not in design doc
    public void setEmail(String email) {
        this.email = email;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    //not in design doc
    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public Location getLocation() {
        return location;
    }

    //not in design doc
    public void setLocation(Location location) {
        this.location = location;
    }

    public List<Event> getReservations() {
        return reservations;
    }


    public void makeReservation(Event event){
        reservations.add(event);
    }

    public void removeReservation(Event event){
        reservations.remove(event);
    }

}
