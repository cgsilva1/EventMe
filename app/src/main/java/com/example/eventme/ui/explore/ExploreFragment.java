package com.example.eventme.ui.explore;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

public class ExploreFragment extends Fragment {
    DatabaseReference ref;
    ArrayList<Event> data;
    RecyclerView recyclerView;
    TextView details;
    TextView name;
    SearchView searchView;
    ExploreAdapter adapter;
    private FragmentExploreBinding binding;
    FirebaseDatabase firebaseDatabase;
    //DatabaseReference dbRef = firebaseDatabase.getReference("Event");
    ArrayList<Event> events;
    String[] nums = new String[]{ "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19"};

    String eventID;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        ExploreViewModel exploreViewModel = new ViewModelProvider(this).get(ExploreViewModel.class);

        binding = FragmentExploreBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        recyclerView = root.findViewById(R.id.rv);



        

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));



        //for loop through events in firebase add it to data array list and then display


        FirebaseFirestore db = FirebaseFirestore.getInstance();
        events = new ArrayList<Event>();
        adapter = new ExploreAdapter(getContext(), events);

        db.collection("Events").orderBy("cost", Query.Direction.ASCENDING)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if(error!=null){
                            Log.e("Firestore error ", error.getMessage());
                        }
                        for(DocumentChange dc: value.getDocumentChanges()){
                            if(dc.getType()==DocumentChange.Type.ADDED){
                                events.add(dc.getDocument().toObject(Event.class));

                            }
                        }
                        adapter.notifyDataSetChanged();
                    }
                });

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);

        return root;
    }

//    @Override public void onStart(){
//        super.onStart();
//        adapter.startListening();
//    }
//
//    @Override public void onStop(){
//        super.onStop();
//        adapter.startListening();
//    }
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