package com.example.eventme.ui.explore;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import androidx.appcompat.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eventme.Event;
import com.example.eventme.MainActivity;
import com.example.eventme.R;
import com.example.eventme.databinding.FragmentExploreBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Locale;

public class ExploreFragment extends Fragment {
    DatabaseReference ref;
    ArrayList<Event> data;
    RecyclerView recyclerView;
    SearchView searchView;
    ExploreAdapter adapterClass;
    private FragmentExploreBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ExploreViewModel exploreViewModel = new ViewModelProvider(this).get(ExploreViewModel.class);

        binding = FragmentExploreBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        ref = FirebaseDatabase.getInstance().getReference("Events/Events");
        recyclerView = root.findViewById(R.id.rv);
       // manager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        searchView = root.findViewById(R.id.searchView);

        final androidx.appcompat.widget.SearchView searchView = binding.searchView;
       // exploreViewModel.getText().observe(getViewLifecycleOwner(), searchView::setText);
        return root;
    }

    @Override
    public void onStart() {
        super.onStart();
        if(ref != null){
            ref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if(dataSnapshot.exists()){
                        data = new ArrayList<>();
                        for(DataSnapshot ds : dataSnapshot.getChildren()){
                            data.add(ds.getValue(Event.class)); //adding data to array list from firebase
                        }

                        adapterClass = new ExploreAdapter(data);
                        recyclerView.setAdapter(adapterClass);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                   // Toast.makeText(ExploreFragment.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                    Toast.makeText(getActivity(), databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
        if(searchView != null){
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() { //taking in what user is entering in search bar
                @Override
                public boolean onQueryTextSubmit(String s) {
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String s) {
                    search(s);
                    return true;
                }
            });
        }

    }

    private void search(String str){
        ArrayList<Event> results = new ArrayList<>();
        if(data != null) {
            for (Event object : data) { //DATA IS NULL SO NEED TO MAKE SURE ACCESEING RIGHT SPOT IN DATABASE
                if (object.getDescription().toLowerCase().contains(str.toLowerCase())) { //description matches what was entered
                    results.add(object);
                }
            }
        }
        ExploreAdapter exploreAdapter = new ExploreAdapter(results);
        recyclerView.setAdapter(exploreAdapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}