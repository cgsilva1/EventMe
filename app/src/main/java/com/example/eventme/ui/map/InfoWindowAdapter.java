package com.example.eventme.ui.map;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.example.eventme.Event;
import com.example.eventme.R;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

public class InfoWindowAdapter implements GoogleMap.InfoWindowAdapter{
    private final Event event;
//
//    @Override
//    public View getInfoContents(Marker marker) {
//        return null;
//    }

    private Context context;

    public InfoWindowAdapter(Context context, Event event) {
        this.context = context.getApplicationContext();
        this.event = event;
    }

    @Override
    public View getInfoWindow(Marker marker) {
        return null;
    }

    @Override
    public View getInfoContents(Marker marker) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v =  inflater.inflate(R.layout.info_window, null);

        //View view = context.getLayoutInflater().inflate(R.layout.info_window, null);

        TextView name = (TextView) v.findViewById(R.id.eventName);
        TextView loc = (TextView) v.findViewById(R.id.address);
        TextView date = (TextView) v.findViewById(R.id.date);
        TextView time = (TextView) v.findViewById(R.id.time);
        TextView cat = (TextView) v.findViewById(R.id.category);
        TextView cost = (TextView) v.findViewById(R.id.cost);

        name.setText(event.getName());
        loc.setText(event.getLocation());
        date.setText(event.getDate());
        time.setText(event.getTime());
        cat.setText(event.getCategory());
        cost.setText("$" + event.getCost());
        return v;
    }
}
