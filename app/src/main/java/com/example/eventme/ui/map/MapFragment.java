package com.example.eventme.ui.map;

import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.eventme.Event;
import com.example.eventme.R;

import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eventme.databinding.FragmentMapBinding;
import com.example.eventme.databinding.FragmentProfileBinding;
import com.example.eventme.ui.explore.ExploreAdapter;
import com.example.eventme.ui.login.LoginActivity;
import com.example.eventme.ui.profile.ProfileAdapter;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import org.checkerframework.checker.units.qual.C;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;

public class MapFragment extends Fragment {

    private FragmentMapBinding binding;
    //Location currentLocation;
    //FusedLocationProviderClient fusedLocationProviderClient;
    ArrayList<Event> events;
    ArrayList<Event> shown_events;
    ExploreAdapter adapter;

    //for recycler view of registered events events
    RecyclerView recyclerView;
    ArrayList<Event> data;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        //View view=inflater.inflate(R.layout.fragment_map, container, false);
        binding = FragmentMapBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        //RECYCLER VIEW OF REGISTERED EVENTS
        recyclerView = root.findViewById(R.id.rvMap);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        // Initialize map fragment
        SupportMapFragment mapFragment =(SupportMapFragment)
                getChildFragmentManager().findFragmentById(R.id.mapAPI);
        // Async map
        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                //Toast.makeText(MapFragment.this, "marker in sydney", Toast.LENGTH_SHORT).show();

                LatLng usc = new LatLng(34.02241412645936, -118.28525647720889);
                googleMap.addMarker(new MarkerOptions()
                        .position(usc)
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
                        .title("Current Location"));
                //googleMap.moveCamera(CameraUpdateFactory.newLatLng(usc));
                //googleMap.animateCamera( CameraUpdateFactory.zoomTo( 11.0f ) );
                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(usc,10));

                FirebaseFirestore db = FirebaseFirestore.getInstance();
                events = new ArrayList<Event>();
                shown_events = new ArrayList<>();
                adapter = new ExploreAdapter(getContext(), shown_events);

                db.collection("Events").orderBy("cost", Query.Direction.ASCENDING)
                        .addSnapshotListener(new EventListener<QuerySnapshot>() {
                            @Override
                            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                                if(error!=null){
                                    Log.e("Firestore error ", error.getMessage());
                                }
                                for(DocumentChange dc: value.getDocumentChanges()){
                                    if(dc.getType()==DocumentChange.Type.ADDED){
                                        Event event = dc.getDocument().toObject(Event.class);

                                        LatLng eventLoc = new LatLng(event.getLatitude(), event.getLongitude());
                                        MarkerOptions marker =new MarkerOptions()
                                                .position(eventLoc)
                                                .title(event.getName());

                                        googleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                                            @Override
                                            public boolean onMarkerClick(Marker marker) {
                                                resetList();
                                                clickedEvent(marker.getTitle());
                                                adapter.notifyDataSetChanged();
                                                return false;
                                            }
                                        });
                                        googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                                            @Override
                                            public void onMapClick(LatLng latLng) {
                                                resetList();
                                                adapter.notifyDataSetChanged();
                                            }
                                        });

                                        googleMap.addMarker(marker);

                                        shown_events.add(event);
                                        events.add(event);

                                    }
                                }
                                Collections.sort(events, (E1, E2) -> {
                                    double currLat = 34.02241412645936;
                                    double currLong = -118.28525647720889;
                                    double E1Lat = E1.getLatitude();
                                    double E1Long = E1.getLongitude();
                                    double E2Lat = E2.getLatitude();
                                    double E2Long = E2.getLongitude();
                                    double distanceE1 = Math.sqrt((currLat - E1Lat) * (currLat - E1Lat) + (currLong - E1Long) * (currLong - E1Long));
                                    double distanceE2 = Math.sqrt((currLat - E2Lat) * (currLat - E2Lat) + (currLong - E2Long) * (currLong - E2Long));

                                    double num = (distanceE2 - distanceE1);

                                    if (num < 0) {
                                        num = 1;
                                    } else if (num > 0) {
                                        num = -1;
                                    } else {
                                        num = 0;
                                    }
                                    return (int) num;
                                });
                                InfoWindowAdapter markerInfoWindowAdapter = new InfoWindowAdapter(getContext(), events);
                                googleMap.setInfoWindowAdapter(markerInfoWindowAdapter);
                                adapter.notifyDataSetChanged();

                            }
                        });

                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
                recyclerView.setLayoutManager(linearLayoutManager);
                recyclerView.setAdapter(adapter);




                // When map is loaded
                //googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                //@Override
                //public void onMapClick(LatLng latLng) {
                // When clicked on map
                // Initialize marker options
                //MarkerOptions markerOptions=new MarkerOptions();
                // Set position of marker
                //markerOptions.position(latLng);
                // Set title of marker
                //markerOptions.title(latLng.latitude+" : "+latLng.longitude);
                // Remove all marker
                //googleMap.clear();
                // Animating to zoom the marker
                //googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,10));
                // Add marker on map
                //googleMap.addMarker(markerOptions);
            }
        });



        // Return view
        return root;
        //MapViewModel mapViewModel =
        //        new ViewModelProvider(this).get(MapViewModel.class);

        //binding = FragmentMapBinding.inflate(inflater, container, false);
        //View root = binding.getRoot();

        //Intent intent = new Intent(getActivity(), MapActivity.class);
        //startActivity(intent);

        //final TextView textView = binding.textMap;
        //mapViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
    }

    private void clickedEvent(String str) {
        if (shown_events != null) {
            for (Iterator<Event> iterator = shown_events.iterator(); iterator.hasNext(); ) {
                Event event = iterator.next();
                if (!event.getName().equals(str)) { //description matches what was entered
                    iterator.remove();
                }
            }
        }

    }

    private void resetList() {
        for (Iterator<Event> iterator = events.iterator(); iterator.hasNext(); ) {
            Event event = iterator.next();
            if (!shown_events.contains(event)) { //description matches what was entered
                shown_events.add(event);
            }
        }
        Collections.sort(shown_events, (E1, E2) -> {
            double currLat = 34.02241412645936;
            double currLong = -118.28525647720889;
            double E1Lat = E1.getLatitude();
            double E1Long = E1.getLongitude();
            double E2Lat = E2.getLatitude();
            double E2Long = E2.getLongitude();
            double distanceE1 = Math.sqrt((currLat - E1Lat) * (currLat - E1Lat) + (currLong - E1Long) * (currLong - E1Long));
            double distanceE2 = Math.sqrt((currLat - E2Lat) * (currLat - E2Lat) + (currLong - E2Long) * (currLong - E2Long));

            double num = (distanceE2 - distanceE1);

            if (num < 0) {
                num = 1;
            } else if (num > 0) {
                num = -1;
            } else {
                num = 0;
            }
            return (int) num;
        });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}