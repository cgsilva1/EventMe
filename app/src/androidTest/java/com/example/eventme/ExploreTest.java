package com.example.eventme;

import com.example.eventme.ui.explore.ExploreAdapter;
import com.example.eventme.ui.explore.ExploreFragment;
import static org.junit.Assert.*;
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


import org.checkerframework.checker.units.qual.A;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;

public class ExploreTest {

    private ExploreFragment exploreFragment;
    private ArrayList<Event> events;
    private ArrayList<Event> locationevents;
    private ArrayList<Event> nameevents;
    private ArrayList<Event> categoryevents;

    private ArrayList<String> alphaEvents;
    private ArrayList<String> soonestEvents;
    private ArrayList<String> distanceEvents;
    private ArrayList<String> costEvents;


    @Before
    public void setUp() {
        exploreFragment = new ExploreFragment();
        events = new ArrayList<>();
        locationevents = new ArrayList<>();
        nameevents = new ArrayList<>();
        categoryevents = new ArrayList<>();
        distanceEvents = new ArrayList<>();

        Event e1 = new Event("Elton John Concert",
                "music",
                "11/16/2022",
                "Dodger Stadium",
                "8:00 PM",
                200,
                "Elton John",
                "Farewell Yellow Brick Road Tour",
                70000,
                34.07402871776655,
                -118.23991538699642);
        Event e2 = new Event("Rams vs Cardinals",
                "sports",
                "11/13/2022",
                "Sofi Stadium",
                "1:25 PM",
                70,
                "NFL",
                "A battle in the NFC West",
                40000,
                33.954123658809124,
                -118.33893231164234);
        locationevents.add(e2);
        locationevents.add(e1);
        Event e3 = new Event("Smile",
                "movie",
                "11/21/2022",
                "Regal Cinema",
                "8:00 PM",
                20,
                "Paramount",
                "Horror Film",
                50,
                34.046122445693626,
                -118.2673407311768);
        nameevents.add(e3);
        Event e4 = new Event("Lakers vs Spurs",
                "sports",
                "11/20/2022",
                "Cryto.com Arena",
                "6:30 PM",
                60,
                "NBA",
                "Lebron James.",
                20000,
                34.04318196670314,
                -118.2673790237983807);
        categoryevents.add(e4);
        categoryevents.add(e2);

        events.add(e3);
        events.add(e4);
        events.add(e2);
        events.add(e1);

        alphaEvents = new ArrayList<>();
        alphaEvents.add(e1.getName());
        alphaEvents.add(e4.getName());
        alphaEvents.add(e2.getName());
        alphaEvents.add(e3.getName());

        soonestEvents = new ArrayList<>();
        soonestEvents.add(e2.getName());
        soonestEvents.add(e1.getName());
        soonestEvents.add(e4.getName());
        soonestEvents.add(e3.getName());

        costEvents = new ArrayList<>();
        costEvents.add(e3.getName());
        costEvents.add(e4.getName());
        costEvents.add(e2.getName());
        costEvents.add(e1.getName());

        distanceEvents.add(e4.getName());
        distanceEvents.add(e3.getName());
        distanceEvents.add(e1.getName());
        distanceEvents.add(e2.getName());


        exploreFragment.buildTestEventList(events);

    }

    @Test
    public void searchLocation() {
        String searcher = "Stadium";
        searcher.toLowerCase();
        exploreFragment.search(searcher);
        exploreFragment.sortBy("cost");
        ArrayList<Event> shown_events = exploreFragment.getShown_events();
        assertNotNull(shown_events);
        assertArrayEquals(locationevents.toArray(), shown_events.toArray());
    }

    @Test
    public void searchName() {
        String searcher = "Smile";
        searcher.toLowerCase();
        exploreFragment.search(searcher);
        exploreFragment.sortBy("cost");
        ArrayList<Event> shown_events = exploreFragment.getShown_events();
        assertNotNull(shown_events);
        assertArrayEquals(nameevents.toArray(), shown_events.toArray());
    }

    @Test
    public void searchCategory() {
        String searcher = "sports";
        searcher.toLowerCase();
        exploreFragment.search(searcher);
        exploreFragment.sortBy("cost");
        ArrayList<Event> shown_events = exploreFragment.getShown_events();
        assertNotNull(shown_events);
        assertArrayEquals(categoryevents.toArray(), shown_events.toArray());
    }

    @Test
    public void sortAlpha(){
        exploreFragment.resetList();
        exploreFragment.sortBy("alpha");
        ArrayList<String> shown = new ArrayList<>();
        for(Event e: exploreFragment.getShown_events()){
            shown.add(e.getName());
        }
        assertArrayEquals(shown.toArray(), alphaEvents.toArray());
    }

    @Test
    public void sortCost(){
        exploreFragment.resetList();
        exploreFragment.sortBy("cost");
        ArrayList<String> shown = new ArrayList<>();
        for(Event e: exploreFragment.getShown_events()){
            shown.add(e.getName());
        }
        assertArrayEquals(shown.toArray(), costEvents.toArray());
    }

    @Test
    public void sortSoonest(){
        exploreFragment.resetList();
        exploreFragment.sortBy("dates");
        ArrayList<String> shown = new ArrayList<>();
        for(Event e: exploreFragment.getShown_events()){
            shown.add(e.getName());
        }
        assertArrayEquals(shown.toArray(), soonestEvents.toArray());
    }




}




