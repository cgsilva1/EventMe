package com.example.eventme;

public class Location {
    private String streetAddress;
    private int zipCode; //string in design doc
    private int row;
    private int col;

    public Location(String streetAddress, int zipCode) {
        this.streetAddress = streetAddress;
        this.zipCode = zipCode;
    }

    public String getStreetAddress() {
        return streetAddress;
    }

    public int getZipCode() {
        return zipCode;
    }

    public int getRow(){
        return row;
    }
    public int getCol(){
        return col;
    }
}
