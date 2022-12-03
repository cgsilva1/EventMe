package com.example.eventme;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;

import com.example.eventme.ui.login.LoginActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class DetailsActivity extends Activity {
    TextView eventname;
    Button regBtn;
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
                    sendEmail();
//
//                    Intent intent = new Intent(Intent.ACTION_SENDTO); // it's not ACTION_SEND
//                    intent.putExtra(Intent.EXTRA_SUBJECT, "Subject of email");
//                    intent.putExtra(Intent.EXTRA_TEXT, "Body of email");
//                    intent.setData(Uri.parse("mailto:")); // or just "mailto:" for blank
//                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK); // this will make such that when user returns to your app, your app is displayed, instead of the email app.
//                    startActivity(intent);

                }
            });

        }

    //@TODO specify item for efficiency purposes
    public void add(Event rs) {
        if (this.data.contains(rs)) return;
        this.data.add(rs);
    }

    protected void sendEmail() {
       // Log.i("Send email", "");
        String[] TO = {"cgsilva@usc.edu"};
        String[] CC = {""};
        Intent emailIntent = new Intent(Intent.ACTION_SENDTO);

        emailIntent.setData(Uri.parse("mailto:")); //to ensure only email apps
        emailIntent.setType("text/plain");
        emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
        //emailIntent.putExtra(Intent.EXTRA_CC, CC);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Your subject");
        emailIntent.putExtra(Intent.EXTRA_TEXT, "Email message goes here");

        if(emailIntent.resolveActivity(getPackageManager()) != null){
            startActivity(emailIntent);
        }

       //try {
//            startActivity(new Intent(emailIntent));
//            finish();
//            Log.i("Finished sending email...", "");
////        } catch (android.content.ActivityNotFoundException ex) {
//            Toast.makeText(DetailsActivity.this, "There is no email client installed.", Toast.LENGTH_SHORT).show();
//        }
    }


}
