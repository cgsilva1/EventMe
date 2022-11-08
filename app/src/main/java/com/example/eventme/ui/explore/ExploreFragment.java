package com.example.eventme.ui.explore;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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

    Spinner dropdown;

    FirebaseDatabase firebaseDatabase;
    //DatabaseReference dbRef = firebaseDatabase.getReference("Event");
    ArrayList<Event> shown_events;
    ArrayList<Event> events;
    FirebaseFirestore db;
    String[] nums = new String[]{"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19"};
    String sort_by;
    String eventID;
    String category_result;

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
        sort_by = "cost";
        category_result ="None";

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                radioButton = (RadioButton) root.findViewById(checkedId);
                CharSequence text = radioButton.getText();
                if ("low to high".equals(text)) {// do operations specific to this selection
                    sort_by = "cost";
                } else if ("closest to farthest".equals(text)) {// do operations specific to this selection
                    sort_by = "distance";
                } else if ("soonest".equals(text)) {// do operations specific to this selection
                    sort_by = "dates";
                } else if ("a to z".equals(text)) {// do operations specific to this selection
                    sort_by = "alpha";
                }
                sortBy(sort_by);
                adapter.notifyDataSetChanged();
            }
        });

        dropdown = root.findViewById(R.id.spinner_categories);
        String[] items = new String[]{"None", "Sports", "Music", "Food", "Movie", "Health"};
        ArrayAdapter<String> adapterDropDown = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_item, items);
        dropdown.setAdapter(adapterDropDown);

        dropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.v("item", (String) parent.getItemAtPosition(position));
                String category = parent.getItemAtPosition(position).toString();
                category_result = category;
                resetList();
                if(!category_result.equals("None"))  search(category_result);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub

            }
        });
        //need to create adapter to display categories in strings.xml

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() { //taking in what user is entering in search bar
            @Override
            public boolean onQueryTextSubmit(String s) {
                resetList();
                if (s.equals("")) {
                    return true;
                }
                search(s);
                adapter.notifyDataSetChanged();
                searchView.clearFocus();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                if (s.equals("")) {
                    resetList();
                    if(!category_result.equals("None"))  search(category_result);
                    sortBy(sort_by);
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
                        if (error != null) {
                            Log.e("Firestore error ", error.getMessage());
                        }
                        for (DocumentChange dc : value.getDocumentChanges()) {
                            if (dc.getType() == DocumentChange.Type.ADDED) {
                                Event event = dc.getDocument().toObject(Event.class);
                                String date = event.getDate().toString();
                                shown_events.add(event);
                                events.add(event);

                            }
                        }
                        sortBy(sort_by);
                        adapter.notifyDataSetChanged();
                    }
                });

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);

        return root;
    }

    private void search(String str) {
        if (shown_events != null) {
            for (Iterator<Event> iterator = shown_events.iterator(); iterator.hasNext(); ) {
                Event event = iterator.next();
                if (!(event.getName().toLowerCase().contains(str.toLowerCase()) || event.getCategory().toLowerCase().contains(str.toLowerCase()) || event.getSponsor().toLowerCase().contains(str.toLowerCase()) || event.getLocation().toLowerCase().contains(str.toLowerCase()))) { //description matches what was entered
                    iterator.remove();
                }
            }
            sortBy(sort_by);
        }

    }

    private void resetList() {
        for (Iterator<Event> iterator = events.iterator(); iterator.hasNext(); ) {
            Event event = iterator.next();
            if (!shown_events.contains(event)) { //description matches what was entered
                shown_events.add(event);
            }
        }

    }

    private void sortBy(String sorter) {

        if (sorter.equals("cost")) {
            Collections.sort(shown_events, (o1, o2) -> {
                double num = (o2.getCost() - o1.getCost());
                if (num < 0) {
                    num = 1;
                } else if (num > 0) {
                    num = -1;
                } else {
                    num = 0;
                }
                return (int) num;
            });
        } else if (sorter.equals("distance")) {
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


        } else if (sorter.equals("dates")) {
            Collections.sort(shown_events, Comparator.comparing(Event::getWhen));

        } else if (sorter.equals("alpha")) {
            Collections.sort(shown_events, Comparator.comparing(Event::getName));
        }


    }


}