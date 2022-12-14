package com.example.eventme;

import android.text.TextUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class User {
    private String name;
    private String email;
    private String birthday;
    private String passwordHash;
    private List<Event> reservations;

    public static HashMap<String, String> keys = new HashMap<>();

    public User(){

    }

    public User(String name, String email, String birthday, String passwordHash) {
        this.name = name;
        this.email = email;
        this.birthday = birthday;
        this.passwordHash = passwordHash;
       // this.reservations = reservations;
//        this.location = location;
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

//    public Location getLocation() {
//        return location;
//    }
//
//    //not in design doc
//    public void setLocation(Location location) {
//        this.location = location;
//    }
//
    public List<Event> getReservations() {
        return reservations;
    }

    public void makeReservation(Event event){
        reservations.add(event);
    }

    public void removeReservation(Event event){
        reservations.remove(event);
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                '}';
    }

}
