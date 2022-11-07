package com.example.eventme.ui.explore;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eventme.Details;
import com.example.eventme.Event;
import com.example.eventme.MainActivity;
import com.example.eventme.R;
import com.example.eventme.User;
import com.example.eventme.ui.login.LoginActivity;
import com.example.eventme.ui.register.Register;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import org.w3c.dom.Text;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Objects;

import javax.xml.transform.Source;

public class ExploreAdapter extends RecyclerView.Adapter<ExploreAdapter.MyViewHolder> {
    private Context context;
    User user;
    ArrayList<Event> data;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();

    String date;
    //Constructor
    public ExploreAdapter(Context context, ArrayList<Event> data){
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public ExploreAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View big_view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_holder, viewGroup, false);
        Button btn = big_view.findViewById(R.id.eventRegisterBtn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(mAuth.getCurrentUser()==null){
                    context.startActivity(new Intent(context, LoginActivity.class));
                }

                TextView eventName = big_view.findViewById(R.id.eventName);
                for(int i = 0; i<data.size(); i++){
                    if(data.get(i).getName()== eventName.getText()){
                        //register the user for this event
                        DocumentReference dRef = FirebaseFirestore.getInstance().collection("User")
                                .document(mAuth.getCurrentUser().getUid());

                        //TODO: make sure you can't register for multiple of the same event
                        //TODO: check times to?
                        dRef.update("Reservations", FieldValue.arrayUnion(eventName.getText()));
                        Toast.makeText(context, "Successfully Registered for "+eventName.getText(), Toast.LENGTH_SHORT).show();

                    }
                }
            }
        });

        //view = inflater.inflate(R.layout.card_holder, viewGroup, false);
        return new ExploreAdapter.MyViewHolder(big_view);
    }
//    public ExploreAdapter(Context context){
//        this.context = context;
//    }

    @Override
    public void onBindViewHolder(@NonNull ExploreAdapter.MyViewHolder holder, int position) {
        Event event = data.get(position);
//        holder.name.setText(model.getName());
//        holder.desc.setText(model.getDescription());
        holder.name.setText(event.getName());
        holder.cat.setText(event.getCategory());
        holder.loc.setText(event.getLocation());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        TextView name;
        TextView cat;
        TextView loc;

        //CardView cardView;
        //need to add other parameters of event

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.eventName);
            cat = itemView.findViewById(R.id.category);
            loc = itemView.findViewById(R.id.address);
            //cardView = itemView.findViewById(R.id.card_view);

        }
    }

    public void setCard(ArrayList<Event> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    //@TODO specify item for efficiency purposes
    public void add(Event rs) {
        if (this.data.contains(rs)) return;
        this.data.add(rs);
    }

    public void delete(Event ev) {
        this.data.remove(ev);
    }

    public void refreshDelete(){
        data.clear();
        this.notifyDataSetChanged();
    }

    public Event getEvent(@NonNull String name){
        for (Event e: data){
            if (e.name.equals(name)) return e;
        }
        return null;
    }
}