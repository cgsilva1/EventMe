package com.example.eventme.ui.explore;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eventme.Details;
import com.example.eventme.Event;
import com.example.eventme.MainActivity;
import com.example.eventme.R;
import com.example.eventme.databinding.FragmentExploreBinding;
import com.example.eventme.ui.login.LoginActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import com.firebase.ui.database.FirebaseRecyclerOptions;


import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;



import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.concurrent.Semaphore;

public class ExploreFragment extends Fragment {
    DatabaseReference ref;
    ArrayList<Event> data;
    RecyclerView recyclerView;
    TextView details;
    TextView name;
    SearchView searchView;
    ExploreAdapter adapter;
    private FragmentExploreBinding binding;
    private RadioGroup radioGroup;
    private RadioButton radioButton;
    private Button btnDisplay;

    FirebaseDatabase firebaseDatabase;
    //DatabaseReference dbRef = firebaseDatabase.getReference("Event");
    ArrayList<Event> shown_events;
    ArrayList<Event> events;
    FirebaseFirestore db;
    String[] nums = new String[]{ "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19"};

    String eventID;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        ExploreViewModel exploreViewModel = new ViewModelProvider(this).get(ExploreViewModel.class);

        binding = FragmentExploreBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        recyclerView = root.findViewById(R.id.rv);
        searchView = root.findViewById(R.id.searchView);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        shown_events = new ArrayList<>();
        events = new ArrayList<>();

        db = FirebaseFirestore.getInstance();

        adapter = new ExploreAdapter(getContext(), shown_events);

        radioGroup = root.findViewById(R.id.radioGroup);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener(){
            public void onCheckedChanged(RadioGroup group, int checkedId) {
//                radioButton = (RadioButton) root.findViewById(checkedId);
//                Toast.makeText(getContext(), radioButton.getText(), Toast.LENGTH_SHORT).show();
                switch(checkedId){
                    case R.id.costRBtn:
                        // do operations specific to this selection
                        break;
                    case R.id.distRBtn:
                        // do operations specific to this selection
                        break;
                    case R.id.dateRBtn:
                        // do operations specific to this selection
                        break;
                    case R.id.alphRBtn:
                        // do operations specific to this selection
                        break;
                }
            }
        });

        Spinner spinnerCats = root.findViewById(R.id.spinner_categories);
        //need to create adapter to display categories in strings.xml

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() { //taking in what user is entering in search bar
                @Override
                public boolean onQueryTextSubmit(String s) {
                    resetList();
                    if(s.equals("")){
                        return true;
                    }
                    search(s);
                    sortBy("distance");
                    adapter.notifyDataSetChanged();
                    searchView.clearFocus();
                    return true;
                }

                @Override
                public boolean onQueryTextChange(String s) {
                    if(s.equals("")){
                        resetList();
                        sortBy("distance");
                        adapter.notifyDataSetChanged();
                        searchView.clearFocus();
                    }
                    return true;
                }
            });



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
                                shown_events.add(event);
                                events.add(event);

                            }
                        }
                        sortBy("distance");
                        adapter.notifyDataSetChanged();
                    }
                });

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);

        return root;
    }

    private void search(String str){
        if(shown_events != null) {
            for (Iterator<Event> iterator = shown_events.iterator(); iterator.hasNext(); ) {
                Event event = iterator.next();
                if (!(event.getName().toLowerCase().contains(str.toLowerCase())  || event.getCategory().toLowerCase().contains(str.toLowerCase()) || event.getSponsor().toLowerCase().contains(str.toLowerCase()) || event.getLocation().toLowerCase().contains(str.toLowerCase()))){ //description matches what was entered
                    iterator.remove();
                }
            }
        }

    }

    private void resetList(){
        for (Iterator<Event> iterator = events.iterator(); iterator.hasNext(); ) {
            Event event = iterator.next();
            if (!shown_events.contains(event)){ //description matches what was entered
                shown_events.add(event);
            }
        }

    }

    private void sortBy(String sorter){

        if(sorter.equals("cost")){
            Collections.sort(shown_events, (o1, o2) -> {
                double num =  (o2.getCost() - o1.getCost());
                if(num<0){
                    num = 1;
                }
                else if(num>0){
                    num = -1;
                }
                else{
                    num =0;
                }
                return (int)num;
            });
        }
        else if(sorter.equals("distance")){
            Collections.sort(shown_events, (E1, E2) -> {
                double currLat = 34.02241412645936;
                double currLong = -118.28525647720889;
                double E1Lat = E1.getLatitude();
                double E1Long = E1.getLongitude();
                double E2Lat = E2.getLatitude();
                double E2Long = E1.getLongitude();
                double distanceE1 = Math.sqrt((currLat - E1Lat) * (currLat - E1Lat) + (currLong - E1Long) * (currLong - E1Long));
                double distanceE2 = Math.sqrt((currLat - E2Lat) * (currLat - E2Lat) + (currLong - E2Long) * (currLong - E2Long));

                double num =  (distanceE2 - distanceE1);

                if(num<0){
                    num = 1;
                }
                else if(num>0){
                    num = -1;
                }
                else{
                    num =0;
                }
                return (int)num;
            });


        }
        else if(sorter.equals("dates")){




        }
        else if(sorter.equals("alpha")){
            Collections.sort(shown_events, Comparator.comparing(Event::getName));
        }


    }





}


//
//        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
//        adapter = new ExploreAdapter(getContext(), data);
//        recyclerView.setAdapter(adapter);
//        data = new ArrayList<>();
//        ref = FirebaseDatabase.getInstance().getReference("Event");
//        ref.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                for(DataSnapshot ds : snapshot.getChildren()){
//                    Event events = ds.getValue(Event.class);
//                    data.add(events);
//                }
//                adapter.setCard(data);
//                adapter.notifyDataSetChanged();
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//                Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_SHORT).show();
//            }
//        });


//        recyclerView = root.findViewById(R.id.rv);
//       // manager.setOrientation(LinearLayoutManager.VERTICAL);
//        recyclerView.setHasFixedSize(true);
//        recyclerView.setItemAnimator(new DefaultItemAnimator());
//        searchView = root.findViewById(R.id.searchView);
//
//        //final androidx.appcompat.widget.SearchView searchView = binding.searchView;
//
//       // exploreViewModel.getText().observe(getViewLifecycleOwner(), searchView::setText);
//        return root;

//
//    @Override
//    public void onStart() {
//        super.onStart();
//        if(ref != null){
//            ref.addValueEventListener(new ValueEventListener() {
//                @Override
//                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                    if(dataSnapshot.exists()){
//                        for(DataSnapshot ds : dataSnapshot.getChildren()){
//                            Event events = ds.getValue(Event.class); //adding data to array list from firebase
//                            data.add(events);
//                        }
//
//                        adapter.setCard(data);
//                        //adapterClass = new ExploreAdapter(context, data);
//                        Toast.makeText(getActivity(), "Data Retrieved!", Toast.LENGTH_SHORT).show();
//                    }
//                }
//
//                @Override
//                public void onCancelled(@NonNull DatabaseError databaseError) {
//                   // Toast.makeText(ExploreFragment.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
//                    Toast.makeText(getActivity(), databaseError.getMessage(), Toast.LENGTH_SHORT).show();
//                }
//            });
//        }
//        if(searchView != null){
//            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() { //taking in what user is entering in search bar
//                @Override
//                public boolean onQueryTextSubmit(String s) {
//                    return false;
//                }
//
//                @Override
//                public boolean onQueryTextChange(String s) {
//                    search(s);
//                    return true;
//                }
//            });
//        }
//
//    }
//
//    private void search(String str){
//        ArrayList<Event> results = new ArrayList<>();
//        if(data != null) {
//            for (Event object : data) { //DATA IS NULL SO NEED TO MAKE SURE ACCESEING RIGHT SPOT IN DATABASE
//                if (object.getDescription().toLowerCase().contains(str.toLowerCase())) { //description matches what was entered
//                    results.add(object);
//                }
//            }
//        }
////        ExploreAdapter exploreAdapter = new ExploreAdapter(results);
////        recyclerView.setAdapter(exploreAdapter);
//    }
//
//    @Override
//    public void onDestroyView() {
//        super.onDestroyView();
//        binding = null;
//    }
//}