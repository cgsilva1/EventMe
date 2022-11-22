package com.example.eventme;

import static org.junit.Assert.*;

import android.app.Activity;
import android.content.Context;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.test.platform.app.InstrumentationRegistry;

import com.example.eventme.ui.explore.ExploreAdapter;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

public class ExploreAdapterTest {
    private String date;
    private ExploreAdapter eAdapter;
    private User user;
    private Event event;
    private ArrayList<Event> events;
    Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
    @Before
    public void setUp() {
        event = new Event("name", "cat", "11/09/2001", "location", "7:00 PM", 10.00, "sponsor", "desc", 100, 120.0, 120.0);
        events = new ArrayList<>();
        eAdapter = new ExploreAdapter(appContext, events);

    }
    @Test
    public void add() {
        eAdapter.add(event);
        assertEquals(1, eAdapter.getItemCount());
        assertEquals(event,eAdapter.getEvent(event.getName()));
    }

    @Test
    public void addDuplicates() {
        eAdapter.add(event);
        eAdapter.add(event);
        assertEquals(1, eAdapter.getItemCount());
        assertEquals(event,eAdapter.getEvent(event.getName()));
    }

    @Test
    public void delete() {
        eAdapter.add(event);
        eAdapter.add(event);
        eAdapter.delete(event);
        assertEquals(0,eAdapter.getItemCount());
        assertNull(eAdapter.getEvent(event.getName()));
    }

    @Test
    public void refreshDelete() {
        eAdapter.add(event);
        eAdapter.refreshDelete();
        assertEquals(0, eAdapter.getItemCount());
    }

}