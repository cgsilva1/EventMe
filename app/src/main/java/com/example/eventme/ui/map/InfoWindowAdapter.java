package com.example.eventme.ui.map;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.example.eventme.Event;
import com.example.eventme.R;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

import java.util.ArrayList;

public class InfoWindowAdapter implements GoogleMap.InfoWindowAdapter{
    private ArrayList<Event> events;
//
//    @Override
//    public View getInfoContents(Marker marker) {
//        return null;
//    }

    private Context context;

    public InfoWindowAdapter(Context context, ArrayList<Event> events) {
        this.context = context.getApplicationContext();
        this.events = events;
    }

    @Override
    public View getInfoWindow(Marker marker) {
        return null;
    }

    @Override
    public View getInfoContents(Marker marker) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v =  inflater.inflate(R.layout.info_window, null);

        if(marker.getTitle().equals("Current Location")) return null;

        //View view = context.getLayoutInflater().inflate(R.layout.info_window, null);
        Event currEvent = null;

        for(Event event: events) {
           if(marker.getTitle().equals(event.getName())){
               currEvent = event;
           }
        }


        TextView name = (TextView) v.findViewById(R.id.eventName);
        TextView loc = (TextView) v.findViewById(R.id.address);
        TextView date = (TextView) v.findViewById(R.id.date);
        TextView time = (TextView) v.findViewById(R.id.time);
        TextView cat = (TextView) v.findViewById(R.id.category);
        TextView cost = (TextView) v.findViewById(R.id.cost);
    if(currEvent!=null) {
        name.setText(currEvent.getName());
        loc.setText(currEvent.getLocation());
        date.setText(currEvent.getDate());
        time.setText(currEvent.getTime());
        cat.setText(currEvent.getCategory());
        cost.setText("$" + currEvent.getCost() + "0");
    }

        return v;
    }
}
