package com.example.eventme.ui.register;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.eventme.R;
import com.example.eventme.User;
import com.example.eventme.ui.explore.ExploreAdapter;
import com.example.eventme.ui.login.LoginActivity;
import com.example.eventme.ui.login.LoginViewModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.HashMap;
import java.util.Map;

public class Register extends AppCompatActivity {

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    FirebaseUser currentUser;

    //ProgressBar progressBar;
    EditText nameR, emailR, dobR, passwordR, confirmPasswordR;
    FirebaseAuth mAuth;
    FirebaseFirestore fStore;

    User user;
    //Button submitRegister;
    TextView txtalreadyamember;
    Snackbar snackbar;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //progressBar = findViewById(R.id.progressBar);
        nameR = findViewById(R.id.nameReg);
        emailR = findViewById(R.id.emailReg);
        dobR = findViewById(R.id.dobReg);
        passwordR = findViewById(R.id.passwordReg);
        confirmPasswordR = findViewById(R.id.confirmPasswordReg);

        Button submitRegister = (Button) findViewById(R.id.registerBtn);
        //txtalreadyamember = findViewById(R.id.txtAlreadyAMember);

        mAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        //check if the user is logged in --> never logged out
        if(mAuth.getCurrentUser() != null)
        {
            startActivity(new Intent(Register.this, ExploreAdapter.class));
            finish();
        }

        //REGISTER BUTTON ONCLICK
        submitRegister.setOnClickListener(view-> {
                String name = nameR.getText().toString().trim();
                String email = emailR.getText().toString().trim();
                String dob = dobR.getText().toString().trim();
                String password = passwordR.getText().toString().trim();
                String confirmPassword = confirmPasswordR.getText().toString().trim();

                //check whether name is empty or not
                if(TextUtils.isEmpty(name)){
                    nameR.setError("Name is required");
                    return;
                }

                //check whether email address is empty or not
                if(TextUtils.isEmpty(email)){
                    emailR.setError("Email Address is required");
                    return;
                }

                //check email address pattern is correct or not
                if (!email.matches(emailPattern)){
                    emailR.setError("Email Address is invalid");
                    return;
                }

                //check whether email address is empty or not
                if(TextUtils.isEmpty(dob)){
                    emailR.setError("Birthdate is required");
                    return;
                }

                //check whether password is less than 5 characters?
                if(password.length() < 5){
                    passwordR.setError("Password must be >= 5 characters");
                    return;
                }

                //check whether password and confirm password are match or not
                if(!password.equals(confirmPassword)){
                    confirmPasswordR.setError("Password does not match");
                    return;
                }

            mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {

                    //display snackbar with green background
                    View v = findViewById(android.R.id.content);
                    String message = "Account Created Successfully";
                    int duration = Snackbar.LENGTH_LONG;

                    snackbar = Snackbar.make(v, message, duration)
                            .setAction("Action", null);
                    View sbView = snackbar.getView();
                    sbView.setBackgroundColor(Color.GREEN);
                    snackbar.show();

                    DocumentReference documentReference = fStore.collection("Event/Users").document(String.valueOf(user));

                    //store the data in the document --> hash map
                    Map<String, Object> users = new HashMap<String, Object>();
                    users.put("rName", nameR);
                    users.put("rEmail", emailR);
                    users.put("rDob", dobR);
                    users.put("rPassword", passwordR);

                    FirebaseMessaging.getInstance().getToken().addOnCompleteListener(new OnCompleteListener<String>() {
                        @Override
                        public void onComplete(@NonNull Task<String> task) {
                            if (!task.isSuccessful()) {
                                return;
                            }
                            // Get new FCM registration token
                            String token= task.getResult();
                            users.put("notificationToken",token);
                            // now insert into cloud database
                            documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>(){
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Log.d("TAG", "onSuccess: user Profile is created for" + user);
                                }
                            });

                        }

                    });
                    Intent intent = new Intent(Register.this, ExploreAdapter.class);
                    startActivity(intent);
                    //User Method call create new user in user class
                    user = new User (name, email, dob, password);
                }
                else{
                    //display snackbar with red background
                    View vi = findViewById(android.R.id.content);
                    String message = "Email ID already exits";
                    int duration = Snackbar.LENGTH_LONG;

                    snackbar = Snackbar.make(vi, message, duration)
                            .setAction("Action", null);
                    View sbView = snackbar.getView();
                    sbView.setBackgroundColor(Color.RED);
                    snackbar.show();

                    Toast.makeText(Register.this,"Error Occured" + task.getException(),Toast.LENGTH_SHORT).show();
                    updateUI(null);
                    //progressBar.setVisibility(View.INVISIBLE);
                }



                //Call function to empty All EditText
                emptyInputEditText();

            });
        });
    }


    /**
     * This method is to empty all input edit text
     */
    private void emptyInputEditText() {
        nameR.setText(null);
        emailR.setText(null);
        dobR.setText(null);
        passwordR.setText(null);
        confirmPasswordR.setText(null);
    }

    public void onStart(){
        super.onStart();
        //check if user is signed in and update UI accordingly
        currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }


    private void updateUI(FirebaseUser currentUser) {
        if (user != null) {
            firebaseDatabase = FirebaseDatabase.getInstance();
            databaseReference = firebaseDatabase.getReference();//databasereference

            String uid = currentUser.getUid();
            DatabaseReference userRef = databaseReference.child("Events");//Create child node reference
            userRef.child(uid).setValue(user);//Insert value to child node
        }
    }

    public void enabled(View view) {
        Intent intent = new Intent(Register.this, ExploreAdapter.class);
        startActivity(intent);
        finish();
    }
//
//    //CLICK REGISTER BUTTON
//    public void submitRegister(){
//        //add data to data base
//        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
//        DatabaseReference databaseReference = firebaseDatabase.getReference();//databasereference
//
//        String uid = currentUser.getUid();
//        DatabaseReference userRef = databaseReference.child("Events");//Create child node reference
//        userRef.child(uid).setValue(user);//Insert value to child node
//    }

//    public void signOut(){
//        FirebaseAuth.getInstance().signOut();
//    }
}