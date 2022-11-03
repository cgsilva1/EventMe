package com.example.eventme.ui.login;

import android.app.Activity;

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
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.eventme.R;
import com.example.eventme.ui.explore.ExploreAdapter;
import com.example.eventme.ui.login.LoginViewModel;
import com.example.eventme.ui.login.LoginViewModelFactory;
import com.example.eventme.databinding.ActivityLoginBinding;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;

public class LoginActivity extends AppCompatActivity {

    private LoginViewModel loginViewModel;
    private ActivityLoginBinding binding;

    EditText uEmail, uPassword;
    FirebaseAuth mAuth;

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

        loginButton.setOnClickListener(view -> {
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

            //authenticate user
            boolean success = authenticate(email, password);

        });
    }

    protected boolean authenticate(String email, String password){
        AtomicBoolean success= new AtomicBoolean(false);
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
            if(task.isSuccessful())
            {
                success.set(true);
                Toast.makeText(LoginActivity.this, "Log in was successful, Welcome!", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(LoginActivity.this, ExploreAdapter.class));
            }
            else
            {
                Toast.makeText(LoginActivity.this, "Error logging in!" + Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        return success.get();
    }

}