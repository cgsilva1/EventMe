package com.example.eventme;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.example.eventme.ui.login.LoginActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.ArrayList;

public class DetailsActivity extends Activity {
    TextView eventname;
    Button regBtn;
    Button backBtn;
    FirebaseFirestore d;
    DocumentReference dRef;
    ArrayList<Event> data;
    Event checkEvent;
    private Context context;

        public void onCreate(Bundle savedInstanceState) {

            d = FirebaseFirestore.getInstance();

            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_details2);
            eventname = findViewById(R.id.eventName);
            regBtn = findViewById(R.id.regButton);
            backBtn = findViewById(R.id.backButton);
            FirebaseAuth mAuth = FirebaseAuth.getInstance();

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


            //register button
            regBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if(mAuth.getCurrentUser()==null){
                        context.startActivity(new Intent(context, LoginActivity.class));
                        return;
                    }
                    dRef = FirebaseFirestore.getInstance().collection("User")
                            .document(mAuth.getCurrentUser().getUid());

//                TextView eventName = big_view.findViewById(R.id.eventName);



                    dRef.update("Reservations", FieldValue.arrayUnion(eventname.getText()));
                    //Toast.makeText(context, "Successfully Registered", Toast.LENGTH_SHORT).show();
                    //sendEmail(mAuth, dRef, Name, Date, Time);
//
//                    Intent intent = new Intent(Intent.ACTION_SENDTO); // it's not ACTION_SEND
//                    intent.putExtra(Intent.EXTRA_SUBJECT, "Subject of email");
//                    intent.putExtra(Intent.EXTRA_TEXT, "Body of email");
//                    intent.setData(Uri.parse("mailto:")); // or just "mailto:" for blank
//                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK); // this will make such that when user returns to your app, your app is displayed, instead of the email app.
//                    startActivity(intent);

                }
            });
            //back button
            backBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    startActivity(new Intent(DetailsActivity.this, MainActivity.class));

                }
            });

        }

    //@TODO specify item for efficiency purposes
    public void add(Event rs) {
        if (this.data.contains(rs)) return;
        this.data.add(rs);
    }

    protected void sendEmail(FirebaseAuth mAuth, DocumentReference dRef, String eventName, String eventDate, String eventTime) {

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("User").document(mAuth.getCurrentUser().getUid()).addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                if(error!=null){
                    Log.e("Firestore error ", error.getMessage());
                }
                String userName = value.getData().get("name").toString();
                String email = value.getData().get("email").toString();
                String sub="EventMe Registration" ;
                Mail m = new Mail("eventmesuppor1@gmail.com", "ReturnofTheSal123");
                String msg = "You've successfully registered for " + eventName + " at " + eventTime + " on " + eventDate;
                String[] toArr = {email};
                m.setFrom("EventMeSupport@gmail.com");

                m.setTo(toArr);
                m.setSubject(sub);
                m.setBody(msg);

                try{

                    if(m.send()) {
                        //successful
                    } else {
                        //failure
                    }
                } catch(Exception e) {

                    Log.e("MailApp", "Could not send email", e);
                }
            }
        });


    }


}
