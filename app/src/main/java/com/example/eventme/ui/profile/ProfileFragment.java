package com.example.eventme.ui.profile;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import com.example.eventme.R;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.eventme.ui.register.Register;
import com.example.eventme.databinding.FragmentProfileBinding;
import com.example.eventme.ui.login.LoginActivity;

public class ProfileFragment extends Fragment {
    Button loginButton;
    Button createAccountButton;
    private FragmentProfileBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ProfileViewModel profileViewModel =
                new ViewModelProvider(this).get(ProfileViewModel.class);

        binding = FragmentProfileBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        //LOGIN BUTTON ONCLICK
        loginButton = root.findViewById(R.id.loginButton);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendLogin();
            }
        });

        //REGISTER BUTTON ONCLICK
        createAccountButton = root.findViewById(R.id.createAccountButton);
        createAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendRegister();
            }
        });

        final TextView textView = binding.textProfile;
        profileViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    public void sendLogin(){
        Intent intent = new Intent(getActivity(), LoginActivity.class);
        startActivity(intent);
    }

    public void sendRegister(){
        Intent intent = new Intent(getActivity(), Register.class);
        startActivity(intent);
    }

    public void loggedIn(){

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}