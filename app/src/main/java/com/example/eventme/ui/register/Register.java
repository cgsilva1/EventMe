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

import com.example.eventme.MainActivity;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Register extends AppCompatActivity {

    // Firebase Database.
    FirebaseDatabase firebaseDatabase;
    // Reference for Firebase.
    DatabaseReference databaseReference;
    DatabaseReference usersRef;
    // creating a variable for our object class
    User userInfo;
    String userID;

    FirebaseUser currentUser;

    //ProgressBar progressBar;
    EditText nameR, emailR, dobR, passwordR, confirmPasswordR;
    FirebaseAuth mAuth;
    FirebaseFirestore fStore;

    User user;
    Button submitRegister;
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

        submitRegister = (Button) findViewById(R.id.registerBtn);
        //txtalreadyamember = findViewById(R.id.txtAlreadyAMember);


        //get the instance of our Firebase database.
        firebaseDatabase = FirebaseDatabase.getInstance();
        //get reference for our database
        databaseReference = firebaseDatabase.getReference("User");
        usersRef = databaseReference.child("users");
        //databaseReference.setValue("TESTING");

        // initializing our object class variable
        userInfo = new User();

        mAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        //check if the user is logged in --> never logged out
        if (mAuth.getCurrentUser() != null) {
            startActivity(new Intent(Register.this, ExploreAdapter.class));
            finish();
        }

        //REGISTER BUTTON ONCLICK
        submitRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = nameR.getText().toString().trim();
                String email = emailR.getText().toString().trim();
                String dob = dobR.getText().toString().trim();
                String password = passwordR.getText().toString().trim();
                String confirmPassword = confirmPasswordR.getText().toString().trim();

                //check whether name is empty or not
                if (TextUtils.isEmpty(name)) {
                    nameR.setError("Name is required");
                    return;
                }

                //check whether email address is empty or not
                if (TextUtils.isEmpty(email)) {
                    emailR.setError("Email Address is required");
                    return;
                }

                //check email address pattern is correct or not
                if (!email.matches(emailPattern)) {
                    emailR.setError("Email Address is invalid");
                    return;
                }

                //check whether dob address is empty or not
                if (TextUtils.isEmpty(dob)) {
                    dobR.setError("Birthdate is required");
                    return;
                }

                //check whether password is less than 5 characters?
                if (password.length() < 5) {
                    passwordR.setError("Password must be >= 5 characters");
                    return;
                }

                //check whether password and confirm password are match or not
                if (!password.equals(confirmPassword)) {
                    confirmPasswordR.setError("Password does not match");
                    return;
                }

                //register user in firebase
                Toast.makeText(Register.this, "Creating on complete listener", Toast.LENGTH_SHORT).show();
                mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        Toast.makeText(Register.this, "in the listener", Toast.LENGTH_SHORT).show();
                        if(task.isSuccessful())
                        {
                            Toast.makeText(Register.this, "Account Created, Welcome!", Toast.LENGTH_SHORT).show();
                            userID = mAuth.getCurrentUser().getUid();
                            DocumentReference documentReference = fStore.collection("User").document(userID);
                            //store the data in the document --> hash map
                            Map<String, Object> user = new HashMap<String, Object>();
                            // insert data into map using put
                            ArrayList<String> Reservations = new ArrayList<>();
                            user.put("name", name);
                            user.put("email", email);
                            user.put("birthday", dob);
                            user.put("Reservations", Reservations);
                            FirebaseMessaging.getInstance().getToken().addOnCompleteListener(task1 -> {
                                Toast.makeText(Register.this, "ENTER!", Toast.LENGTH_SHORT).show();
                                if (!task1.isSuccessful()) {
                                    Log.d("TAG", "Failed to create user" + userID);
                                    return;
                                }
                                // Get new FCM registration token
                                String token= task1.getResult();
                                user.put("notificationToken",token);
                                // now insert into cloud database
                                documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>(){
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Log.d("TAG", "onSuccess: user Profile is created for" + userID);
                                    }
                                });
                                startActivity(new Intent(Register.this, MainActivity.class));
                            });
                        }

                        else
                        {
                            Toast.makeText(Register.this, "Error creating account!" + Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_SHORT).show();
                            String err ="Error creating account! " + Objects.requireNonNull(task.getException()).getMessage()+ userID;

                            startActivity(new Intent(Register.this, LoginActivity.class));
                        }
                    }
                });





            }



                //for testing fors up to here its redirect to login
//                Intent intent = new Intent(Register.this, LoginActivity.class);
//                startActivity(intent);

//                Toast.makeText(Register.this, "Account Created, Welcome!", Toast.LENGTH_SHORT).show();
//                databaseReference.push().setValue(new User(name, email, dob, password));
//                Intent intent = new Intent(Register.this, MainActivity.class);
//                startActivity(intent);

//                //Call function to empty All EditText
//                emptyInputEditText();


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

    public void onStart() {
        super.onStart();
        //check if user is signed in and update UI accordingly
        currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }


    private void updateUI(FirebaseUser currentUser) {
        if (user != null) {
            String uid = currentUser.getUid();
            DatabaseReference userRef = databaseReference.child("Events");//Create child node reference
            userRef.child("User").child(uid).setValue(user);//Insert value to child node
        }
    }

    public void enabled(View view) {
        Intent intent = new Intent(Register.this, ExploreAdapter.class);
        startActivity(intent);
        finish();
    }

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


//    private void addDataToFirebase(String name, String email, String dob, String password) {
//        // set data in our object class.
//        userInfo.setName(name);
//        userInfo.setEmail(email);
//        userInfo.setBirthday(dob);
//        userInfo.setPasswordHash(password);
//        databaseReference.push().setValue(new User(name, email, dob, password));
//
//        // we are use add value event listener method
//        // which is called with database reference.
//
////        ValueEventListener userListener = new ValueEventListener() {
////            @Override
////            public void onDataChange(@NonNull DataSnapshot snapshot) {
////                // inside the method of on Data change we are setting
////                // our object class to our database reference.
////                // database reference will sends data to firebase.
////
////                //TESTING READING
////                //String value = snapshot.getValue(String.class);
////                //Log.d("TAG", "Value is:" + value);
////
////                databaseReference.setValue(userInfo);
////                //databaseReference.child("users").child(userInfo.getEmail()).setValue(userInfo);
////                //usersRef.child(userInfo.getEmail()).setValue(userInfo);
////                //databaseReference.child("users").child(userInfo.getEmail()).setValue(userInfo);
////                //usersRef.child("gracehop").setValue(new User("December 9, 1906", "Grace Hopper"));
////                // after adding this data we are showing toast message.
////                Toast.makeText(Register.this, "data added", Toast.LENGTH_SHORT).show();
////            }
////
////            @Override
////            public void onCancelled(@NonNull DatabaseError error) {
////                // if the data is not added or it is cancelled then
////                // we are displaying a failure toast message.
////                Toast.makeText(Register.this, "Fail to add data " + error, Toast.LENGTH_SHORT).show();
////            }
////        };
////        databaseReference.addValueEventListener(userListener);
//
//
//    }