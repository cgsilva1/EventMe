package com.example.eventme;

import java.util.List;

public class Map {
    private List<Event> eventLocations;

    public Map(List<Event> eventLocations) {
        this.eventLocations = eventLocations;
    }

    public List<Event> displayEvents(User user)
    {
        return user.getReservations();
    }

    public List<Event> displayEvents()
    {
        return eventLocations;
    }
}
