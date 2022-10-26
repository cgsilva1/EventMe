package com.example.eventme;

import java.sql.Time;

public class Event {
    private String category;
    private String date;
    private Location location;
    private Time time;

    public Event(String category, String date, Location location, Time time) {
        this.category = category;
        this.date = date;
        this.location = location;
        this.time = time;
    }

    public String getCategory() {
        return category;
    }

    public String getDate() {
        return date;
    }

    public Location getLocation() {
        return location;
    }

    public Time getTime() {
        return time;
    }
}
