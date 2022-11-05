package com.example.eventme.ui.login;

import android.app.Activity;

import androidx.annotation.NonNull;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.eventme.R;
import com.example.eventme.User;
import com.example.eventme.ui.explore.ExploreAdapter;
import com.example.eventme.ui.login.LoginViewModel;
import com.example.eventme.ui.login.LoginViewModelFactory;
import com.example.eventme.databinding.ActivityLoginBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;

public class LoginActivity extends AppCompatActivity {

    private LoginViewModel loginViewModel;
    private ActivityLoginBinding binding;

    EditText uEmail, uPassword;
    FirebaseAuth mAuth;

    // Firebase Database.
    FirebaseDatabase firebaseDatabase;
    // Reference for Firebase.
    DatabaseReference databaseReference;
    DatabaseReference usersRef;
    User userInfo;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        loginViewModel = new ViewModelProvider(this, new LoginViewModelFactory())
                .get(LoginViewModel.class);

        //uEmail = binding.username;
        uEmail = findViewById(R.id.username);
        //uPassword = binding.password;
        uPassword = findViewById(R.id.password);
        //Button loginButton = binding.login;
        Button loginButton = findViewById(R.id.login);
        mAuth = FirebaseAuth.getInstance();
        ProgressBar loadingProgressBar = binding.loading;

        //get the instance of our Firebase database.
        firebaseDatabase = FirebaseDatabase.getInstance();
        //get reference for our database
        databaseReference = firebaseDatabase.getInstance().getReference("User");
        usersRef = databaseReference.child("User");

        loginButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String email = uEmail.getText().toString().trim();
                String password = uPassword.getText().toString().trim();

                //check whether name is empty or not
                if (TextUtils.isEmpty(email)) {
                    uEmail.setError("Email is required");
                    return;
                }

                //check whether email address is empty or not
                if (TextUtils.isEmpty(password)) {
                    uPassword.setError("Password is required");
                    return;
                }


//                usersRef.addValueEventListener(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot snapshot) {
//                        String value = snapshot.getValue(String.class);
//                        Toast.makeText(LoginActivity.this, "Log in was successful, Welcome!", Toast.LENGTH_SHORT).show();
//                        Log.d(TAG, "Value is: " + value);
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError error) {
//                        Toast.makeText(LoginActivity.this, "Error logging in!" + Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_SHORT).show();
//                        Log.w(TAG, "Failed to read value", error.toException());
//                    }
//                });

                //authenticate user
                authenticate(email, password);
            }
        });
    }

    protected void authenticate(String email, String password){

                usersRef.child(email).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for(DataSnapshot singleSnapshot : snapshot.getChildren()){
                            userInfo = singleSnapshot.getValue(User.class);
                        }
//                        userInfo = snapshot.getValue(User.class);
                        Toast.makeText(LoginActivity.this, "Log in was successful, Welcome!", Toast.LENGTH_SHORT).show();
//                        Log.d(TAG, "Value is: " + value);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(LoginActivity.this, "Login Error!", Toast.LENGTH_SHORT).show();
                       // Toast.makeText(LoginActivity.this, "Error logging in!" + Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_SHORT).show();
                       // Log.w(TAG, "Failed to read value", error.toException());
                    }
                });

//        AtomicBoolean success= new AtomicBoolean(false);
//        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
//            if(task.isSuccessful())
//            {
//                success.set(true);
//                Toast.makeText(LoginActivity.this, "Log in was successful, Welcome!", Toast.LENGTH_SHORT).show();
//                startActivity(new Intent(LoginActivity.this, ExploreAdapter.class));
//            }
//            else
//            {
//                Toast.makeText(LoginActivity.this, "Error logging in!" + Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_SHORT).show();
//            }
//        });
//        return success.get();
    }

}