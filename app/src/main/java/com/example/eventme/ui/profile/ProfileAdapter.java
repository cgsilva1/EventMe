package com.example.eventme.ui.profile;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import java.util.Date;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
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

public class ProfileAdapter extends RecyclerView.Adapter<ProfileAdapter.MyViewHolder> {
    private Context context;
    SearchView searchView;
    User user;
    ArrayList<Event> data;
    RecyclerView recyclerView;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    ProfileAdapter THIS = this;

    String date;
    //Constructor
    public ProfileAdapter(Context context, ArrayList<Event> data){
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public ProfileAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View big_view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_holder, viewGroup, false);
        Button btn = big_view.findViewById(R.id.eventRegisterBtn);
        btn.setText("Unregister");
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

                        dRef.update("Reservations", FieldValue.arrayRemove(eventName.getText()));
                        delete(getEvent((String)eventName.getText()));
                        THIS.notifyDataSetChanged();


                    }
                }
            }
        });

        //view = inflater.inflate(R.layout.card_holder, viewGroup, false);
        return new ProfileAdapter.MyViewHolder(big_view);
    }
//    public ExploreAdapter(Context context){
//        this.context = context;
//    }

    @Override
    public void onBindViewHolder(@NonNull ProfileAdapter.MyViewHolder holder, int position) {
        Event event = data.get(position);
        holder.name.setText(event.getName());
        holder.cat.setText(event.getCategory());
        holder.loc.setText(event.getLocation());
        holder.cost.setText("$"+event.getCost() + "0");
        holder.date.setText(event.getDate().toString());
        holder.time.setText(event.getTime());

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        TextView name;
        TextView cat;
        TextView loc;
        TextView cost;
        TextView date;
        TextView time;
        Button reg;

        //CardView cardView;
        //need to add other parameters of event

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.eventName);
            cat = itemView.findViewById(R.id.category);
            loc = itemView.findViewById(R.id.address);
            cost = itemView.findViewById(R.id.cost);
            date = itemView.findViewById(R.id.date);
            time = itemView.findViewById(R.id.time);
            reg = itemView.findViewById(R.id.eventRegisterBtn);
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
            if (e.getName().equals(name)) return e;
        }
        return null;
    }
}