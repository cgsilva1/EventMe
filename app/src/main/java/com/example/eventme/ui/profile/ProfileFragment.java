package com.example.eventme.ui.profile;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import com.example.eventme.Event;
import com.example.eventme.MainActivity;
import com.example.eventme.R;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eventme.User;
import com.example.eventme.databinding.FragmentExploreBinding;
import com.example.eventme.ui.explore.ExploreAdapter;
import com.example.eventme.ui.explore.ExploreFragment;
import com.example.eventme.ui.register.Register;
import com.example.eventme.databinding.FragmentProfileBinding;
import com.example.eventme.ui.login.LoginActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Objects;

public class ProfileFragment extends Fragment {
    View promptA;
    View promptB;
    Button loginButton;
    Button createAccountButton;

    String userID; //id in database
    TextView name_tv;
    ImageView profileImage;
    TextView dob_tv;
    Button uploadPhotoButton;
    Button signOut;
    ImageView profilePic;
    ImageView logo;
    Button regBtn;
    String dbName;
    String dbDob;
    String uname;
    String udob;
    String userId;

    //for recycler view of registered events events
    RecyclerView recyclerView;
    ProfileAdapter adapter;
    ArrayList<Event> data;
    ArrayList<Event> events;

    private FragmentProfileBinding binding;
    FirebaseAuth mAuth;
    FirebaseFirestore fStore;
    StorageReference storageReference;
    boolean userLoggedIn = false; //user not signed in


//    TO DO:
//        IF(SIGNED IN) -> SHOW PROFILE INFORMATION

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ProfileViewModel profileViewModel =
                new ViewModelProvider(this).get(ProfileViewModel.class);

        binding = FragmentProfileBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


        //display info for profile WHEN LOGGED IN
        profileImage = root.findViewById(R.id.profile_image);
        name_tv = root.findViewById(R.id.name_profile);
        dob_tv = root.findViewById(R.id.dob_profile);
        //upload profile picture button
        uploadPhotoButton = root.findViewById(R.id.uploadPhotoBtn);
        //sign out button
        signOut = root.findViewById(R.id.signOutBtn);
        profilePic = root.findViewById(R.id.profile_image);

        //info when NOT LOGGED IN
        //promts
        promptA = root.findViewById(R.id.prompt1);
        promptB = root.findViewById(R.id.prompt2);
        logo = root.findViewById(R.id.logo);
        //login button
        loginButton = root.findViewById(R.id.loginButton);
        //create account button
        createAccountButton = root.findViewById(R.id.createAccountButton);

        mAuth = FirebaseAuth.getInstance();

        //RECYCLER VIEW OF REGISTERED EVENTS
        recyclerView = root.findViewById(R.id.rvProfile);
        regBtn = root.findViewById(R.id.eventRegisterBtn);
//        regBtn.setText("unregister");


        if(loggedIn()){ //if user is logged in show profile infomration & log out button

            promptA.setVisibility(View.GONE);
            promptB.setVisibility(View.GONE);
            logo.setVisibility(View.GONE);
            loginButton.setVisibility(View.GONE);
            createAccountButton.setVisibility(View.GONE);
            mAuth = FirebaseAuth.getInstance();
            fStore = FirebaseFirestore.getInstance();
            storageReference = FirebaseStorage.getInstance().getReference();


            FirebaseFirestore db = FirebaseFirestore.getInstance();
            String id =mAuth.getCurrentUser().getUid();

            db.collection("User").document(mAuth.getCurrentUser().getUid()).addSnapshotListener(new EventListener<DocumentSnapshot>() {
                @Override
                public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                    if(error!=null){
                        Log.e("Firestore error ", error.getMessage());
                    }
                    String name = value.getData().get("name").toString();
                    name_tv.setText(name);
                    String dob = value.getData().get("birthday").toString();
                    dob_tv.setText(dob);
                }
            });
            Activity activity = getActivity();
            LinearLayoutManager lm = new LinearLayoutManager(activity);
            recyclerView.setLayoutManager(lm);
            events = new ArrayList<Event>();
            adapter = new ProfileAdapter(getContext(), events);


            db.collection("Events").orderBy("cost", Query.Direction.ASCENDING)
                    .addSnapshotListener(new EventListener<QuerySnapshot>() {
                        @Override
                        public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                            if(error!=null){
                                Log.e("Firestore error ", error.getMessage());
                            }
                            for(DocumentChange dc: value.getDocumentChanges()){
                                if(dc.getType()==DocumentChange.Type.ADDED){

                                    DocumentReference dRef = FirebaseFirestore.getInstance().collection("User")
                                            .document(mAuth.getCurrentUser().getUid());
                                    Task<DocumentSnapshot> task = dRef.get();
                                    task.addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                            String data = task.getResult().getData().toString();
                                             ArrayList<String> reservations = (ArrayList<String>) task.getResult().getData().get("Reservations");
                                             Event event = dc.getDocument().toObject(Event.class);
                                             for(String s: reservations) {
                                                 String eventName = event.getName();
                                                 if(s.equals(eventName)) {
                                                     events.add(event);
                                                 }
                                             }

                                             //always sort by cost
                                            Collections.sort(events, Comparator.comparing(Event::getDate));

                                            adapter.notifyDataSetChanged();
                                        }
                                    });



                                }
                            }

                        }
                    });

            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
            recyclerView.setLayoutManager(linearLayoutManager);
            recyclerView.setAdapter(adapter);



//            final StorageReference profileReference = storageReference.child("Users/"+ Objects.requireNonNull(mAuth.getCurrentUser()).getUid()+"/profile.jpg");
//            profileReference.getDownloadUrl().addOnSuccessListener(uri -> Picasso.get().load(uri).into(profileImage));


//            TextView tv = (TextView) root.findViewById(R.id.name_profile);
//            tv.setText(mAuth.getInstance().getCurrentUser().getUid());


            //userID = Objects.requireNonNull(mAuth.getCurrentUser()).getUid();
            //DocumentReference documentReference = fStore.collection("Users").document(userID);
//            documentReference.get().addOnSuccessListener(value -> {
//                uname = value.getString("name");
//                name.setText(uname);
//                udob = value.getString("dob");
//                dob.setText(udob);
//                userId=userID;
//            });


            //UPLOAD PHOTO ONCLICK
            uploadPhotoButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    sendUploadPicture();
                }
            });

            //SIGN OUT ONCLICK
            signOut.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    sendSignOut();
                }
            });
        }
        else{ //User is not logged in
            profilePic.setVisibility(View.GONE);
            name_tv.setVisibility(View.GONE);
            dob_tv.setVisibility(View.GONE);
            uploadPhotoButton.setVisibility(View.GONE);
            signOut.setVisibility(View.GONE);
            recyclerView.setVisibility(View.GONE);
            root.findViewById(R.id.upcoming).setVisibility(View.GONE);

            //LOGIN BUTTON ONCLICK
            loginButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    sendLogin();
                }
            });

            //REGISTER BUTTON ONCLICK
            createAccountButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    sendRegister();
                }
            });
        }

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

    public boolean loggedIn(){
        if(mAuth.getInstance().getCurrentUser() != null){ //user is signed in
            userLoggedIn = true;
        }
        return userLoggedIn;
    }

    public void sendUploadPicture(){
        // open gallery on phone
        // this intent returns the image that the user has clicked on to select
        Intent openGalleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
         startActivityForResult(openGalleryIntent, 1000);

    }

    public void sendSignOut(){
        mAuth.signOut();
        userLoggedIn = true;
        Intent intent = new Intent(getActivity(), MainActivity.class); //send back to explore page
        startActivity(intent);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}