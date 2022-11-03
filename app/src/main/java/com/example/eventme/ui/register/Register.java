package com.example.eventme.ui.register;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.eventme.MainActivity;
import com.example.eventme.R;
import com.example.eventme.User;
import com.example.eventme.databinding.ActivityRegisterBinding;
import com.example.eventme.ui.explore.ExploreAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Register<FirebaseAuth> extends AppCompatActivity {

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private FirebaseAuth mAuth;
    FirebaseUser currentUser;
    private User user;

    //ProgressBar progressBar;
    EditText nameR, emailR, dobR, passwordR, confirmPasswordR;
    Button submitRegister;
    TextView txtalreadyamember;
    Snackbar snackbar;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();

        //progressBar = findViewById(R.id.progressBar);
        nameR = findViewById(R.id.nameReg);
        emailR = findViewById(R.id.emailReg);
        dobR = findViewById(R.id.dobReg);
        passwordR = findViewById(R.id.passwordReg);
        confirmPasswordR = findViewById(R.id.confirmPasswordReg);
        //txtalreadyamember = findViewById(R.id.txtAlreadyAMember);


        //REGISTER BUTTON ONCLICK
        submitRegister = findViewById(R.id.register);
        submitRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitRegister();
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
                    emailR.setError("Email Address is required");
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

                //User Method call
                user = new User (name, email, dob, password);

                //create user by calling registerUser function
                registerUser(email,password);

                //Call function to empty All EditText
                emptyInputEditText();
            }
        });
    }

    private void registerUser(String email,String password) {
        //Register the user in firebase
        mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    FirebaseUser user = mAuth.getCurrentUser();
                    updateUI(user);

                    //display snackbar with green background
                    View view = findViewById(android.R.id.content);
                    String message = "Account Created Successfully";
                    int duration = Snackbar.LENGTH_LONG;

                    snackbar = Snackbar.make(view, message, duration)
                            .setAction("Action", null);
                    View sbView = snackbar.getView();
                    sbView.setBackgroundColor(Color.GREEN);
                    snackbar.show();

                    //Toast.makeText(Register.this,"Account Created",Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(), ExploreAdapter.class));

                }
                else{
                    //display snackbar with red background
                    View view = findViewById(android.R.id.content);
                    String message = "Email ID already exits";
                    int duration = Snackbar.LENGTH_LONG;

                    snackbar = Snackbar.make(view, message, duration)
                            .setAction("Action", null);
                    View sbView = snackbar.getView();
                    sbView.setBackgroundColor(Color.RED);
                    snackbar.show();

                    Toast.makeText(Register.this,"Error Occured" + task.getException(),Toast.LENGTH_SHORT).show();
                    updateUI(null);
                    //progressBar.setVisibility(View.INVISIBLE);
                }
            }
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

    //CLICK REGISTER BUTTON
    public void submitRegister(){
        //add data to data base
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference();//databasereference

        String uid = currentUser.getUid();
        DatabaseReference userRef = databaseReference.child("Events");//Create child node reference
        userRef.child(uid).setValue(user);//Insert value to child node
    }

//    public void signOut(){
//        FirebaseAuth.getInstance().signOut();
//    }
}