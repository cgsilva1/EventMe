package com.example.eventme.ui.login;

        import android.content.Intent;
        import android.os.Bundle;
        import android.text.TextUtils;
        import android.widget.Button;
        import android.widget.EditText;
        import android.widget.Toast;
        import androidx.appcompat.app.AppCompatActivity;

        import com.example.eventme.MainActivity;
        import com.google.firebase.auth.FirebaseAuth;
        import java.util.Objects;
        import java.util.concurrent.atomic.AtomicBoolean;
        import com.example.eventme.R;


public class LoginActivity extends AppCompatActivity {

    EditText uEmail, uPassword;
    FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        uEmail = findViewById(R.id.username);
        uPassword = findViewById(R.id.password);
        fAuth = FirebaseAuth.getInstance();
        Button logIn = findViewById(R.id.login);

        logIn.setOnClickListener(view -> {
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
            int valid=checkFields(email,password);
            if(valid!=0){
                if(valid==1){
                    uEmail.setError("Please enter a valid email");
                }
                if(valid==2){
                    uPassword.setError("Please enter a valid password");
                }
            }


            //authenticate user
            boolean success=authenticate(email, password);

        });

    }
    protected boolean authenticate(String email, String password){
        AtomicBoolean success= new AtomicBoolean(false);
        fAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
            if(task.isSuccessful())
            {
                success.set(true);
                Toast.makeText(LoginActivity.this, "Log in was successful, Welcome!", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(LoginActivity.this, MainActivity.class));

            }

            else
            {
                Toast.makeText(LoginActivity.this, "Error logging in!" + Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
        return success.get();
    }
    public static int checkFields(String email, String password){
        // validate the data in email and password - check for empty fields and such
        if (TextUtils.isEmpty(email)) {
            return 1;
        }

        if (TextUtils.isEmpty(password)) {
            return 2;
        }

        if (password.length() < 6) {
            return 2;
        }
        if(!email.contains("@")){

            return 1;
        }
        return 0;
    }
}