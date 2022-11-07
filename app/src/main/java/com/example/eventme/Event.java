package com.example.eventme;

import java.util.ArrayList;
import java.util.Date;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class Event {
    public String name;
    private String category;
    private Date when;
    private String date;
    private String location;
    private String time;
    private double cost;
    private String sponsor;
    private String description;
    private int peopleRegistered;
    private double latitude;
    private double longitude;

    public Event() {
    }

    public Event(String name, String category, String strdate, String location, String time, double cost, String sponsor, String description, int peopleRegistered, double latitude, double longitude) {
        this.name = name;
        this.category = category;
        this.date = strdate;

        String[] strTime = time.split(" ");
        //get the hour value
        Integer hour = Integer.parseInt(strTime[0].split(":")[0]);

        //if PM add to the military time
        if(strTime[1].equals("PM")){
            hour+=12;
            strTime[0] = hour +":"+ strTime[0].split(":")[1];
        }

        strdate+=(":"+strTime[0]);
        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy:H:mm", Locale.ENGLISH);
        Date _date = null;
        try {
            _date = formatter.parse(strdate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        this.when = _date;

        this.location = location;
        this.time = time;
        this.cost = cost;
        this.sponsor = sponsor;
        this.description = description;
        this.peopleRegistered = peopleRegistered;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getDate(){
        return date;
    }
    public Date getWhen(){
        return when;
    }

    public void setDate(String strdate){
        this.date = strdate;
        String[] strTime = time.split(" ");
        //get the hour value
        Integer hour = Integer.parseInt(strTime[0].split(":")[0]);

        //if PM add to the military time
        if(strTime[1].equals("PM")){
            hour+=12;
            strTime[0] = hour +":"+ strTime[0].split(":")[1];
        }
        strdate+=(":"+strTime[0]);
        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy:H:mm", Locale.ENGLISH);
        Date _date = null;
        try {
            _date = formatter.parse(strdate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        this.when = _date;
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


    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
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

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}
