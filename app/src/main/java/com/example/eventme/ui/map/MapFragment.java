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

import com.example.eventme.databinding.FragmentMapBinding;
import com.example.eventme.ui.explore.ExploreAdapter;
import com.example.eventme.ui.login.LoginActivity;
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

import java.util.ArrayList;

public class MapFragment extends Fragment {

    private FragmentMapBinding binding;
    //Location currentLocation;
    //FusedLocationProviderClient fusedLocationProviderClient;
    ArrayList<Event> events;
    ExploreAdapter adapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_map, container, false);

        // Initialize map fragment
        SupportMapFragment mapFragment = (SupportMapFragment)
                getChildFragmentManager().findFragmentById(R.id.mapAPI);
        // Async map
        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {

                LatLng usc = new LatLng(34.02241412645936, -118.28525647720889);
                googleMap.addMarker(new MarkerOptions()
                        .position(usc)
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
                        .title("Current Location"));

                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(usc, 10));
                //googleMap.setOnInfoWindowClickListener(this);
                FirebaseFirestore db = FirebaseFirestore.getInstance();
                events = new ArrayList<Event>();
                adapter = new ExploreAdapter(getContext(), events);

                db.collection("Events").orderBy("cost", Query.Direction.ASCENDING)
                        .addSnapshotListener(new EventListener<QuerySnapshot>() {
                            @Override
                            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                                if (error != null) {
                                    Log.e("Firestore error ", error.getMessage());
                                }
                                for (DocumentChange dc : value.getDocumentChanges()) {
                                    if (dc.getType() == DocumentChange.Type.ADDED) {
                                        Event event = dc.getDocument().toObject(Event.class);

                                        LatLng eventLoc = new LatLng(event.getLatitude(), event.getLongitude());
                                        googleMap.addMarker(new MarkerOptions()
                                                .position(eventLoc)
                                                .title(event.getName()));
                                        // Setting a custom info window adapter for the google map
                                        InfoWindowAdapter markerInfoWindowAdapter = new InfoWindowAdapter(getContext(), event);
                                        googleMap.setInfoWindowAdapter(markerInfoWindowAdapter);
                                    }
                                }
                                adapter.notifyDataSetChanged();
                            }
                        });
            }


        });


        // Return view
        return view;
        //MapViewModel mapViewModel =
        //        new ViewModelProvider(this).get(MapViewModel.class);

        //binding = FragmentMapBinding.inflate(inflater, container, false);
        //View root = binding.getRoot();

        //Intent intent = new Intent(getActivity(), MapActivity.class);
        //startActivity(intent);

        //final TextView textView = binding.textMap;
        //mapViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}