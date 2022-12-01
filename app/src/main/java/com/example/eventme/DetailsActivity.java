package com.example.eventme;

import android.app.Activity;
import android.content.Intent;
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

            Intent intent = getIntent();
            String Name = intent.getStringExtra("Name");
            String Location = intent.getStringExtra("Location");
            String Date = intent.getStringExtra("Date");
            String Time = intent.getStringExtra("Time");
            String Category = intent.getStringExtra("Category");
            String Cost = intent.getStringExtra("Cost");
            String PeopleRegistered = intent.getStringExtra("People Registered");
            String Description = intent.getStringExtra("Description");
            String Sponsor = intent.getStringExtra("Sponsor");

            TextView n = findViewById(R.id.eventName);
            n.setText(Name);
            TextView s = findViewById(R.id.sponsor);
            s.setText(Sponsor);
            TextView l = findViewById(R.id.addy);
            l.setText(Location);
            TextView d = findViewById(R.id.date);
            d.setText(Date);
            TextView t = findViewById(R.id.time);
            t.setText(Time);
            TextView c = findViewById(R.id.cat);
            c.setText(Category);
            TextView co = findViewById(R.id.cost);
            co.setText(Cost);
            TextView ppl = findViewById(R.id.peopleReg);
            ppl.setText(PeopleRegistered);
            TextView de = findViewById(R.id.desc);
            de.setText(Description);

        }


}
