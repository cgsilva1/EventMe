package com.example.eventme;

import java.sql.Time;

public class Event {
    private String name;
    private String category;
    private String date;
    private Location location;
    private Time time;
    private double cost;
    private String sponsor;
    private String description;
    private int peopleRegistered;

    public Event() {
    }

    public Event(String name, String category, String date, Location location, Time time, double cost, String sponsor, String description, int peopleRegistered) {
        this.name = name;
        this.category = category;
        this.date = date;
        this.location = location;
        this.time = time;
        this.cost = cost;
        this.sponsor = sponsor;
        this.description = description;
        this.peopleRegistered = peopleRegistered;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Time getTime() {
        return time;
    }

    public void setTime(Time time) {
        this.time = time;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public String getSponsor() {
        return sponsor;
    }

    public void setSponsor(String sponsor) {
        this.sponsor = sponsor;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPeopleRegistered() {
        return peopleRegistered;
    }

    public void setPeopleRegistered(int peopleRegistered) {
        this.peopleRegistered = peopleRegistered;
    }




}
