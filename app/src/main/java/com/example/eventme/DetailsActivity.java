package com.example.eventme;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

public class DetailsActivity extends Activity {
    TextView eventname;
    TextView addy;
    TextView time;
    TextView cat;
    TextView sponsor;
    TextView cost;
    TextView date;
    TextView peopleReg;
    FirebaseFirestore d;

        public void onCreate(Bundle savedInstanceState) {

            d = FirebaseFirestore.getInstance();

            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_details2);
            eventname = findViewById(R.id.eventName);

            d.collection("Events").addSnapshotListener(new EventListener<QuerySnapshot>() {
                @Override
                public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                    if (error != null) {
                        Log.e("Firestore error", error.getMessage());
                    }
                    for(DocumentChange dc : value.getDocumentChanges()){
                        if(dc.getType() == DocumentChange.Type.ADDED){
                            Event event = dc.getDocument().toObject(Event.class);

                        }
                    }
                }
            });


        }


}
