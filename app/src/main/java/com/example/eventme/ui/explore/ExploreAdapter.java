package com.example.eventme.ui.explore;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eventme.DetailsActivity;
import com.example.eventme.Event;
import com.example.eventme.R;
import com.example.eventme.User;
import com.example.eventme.ui.login.LoginActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;


public class ExploreAdapter extends RecyclerView.Adapter<ExploreAdapter.MyViewHolder> {
    private Context context;
    private Context cont;
    SearchView searchView;
    User user;
    ArrayList<Event> data;
    RecyclerView recyclerView;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    Event checkEvent;
    DocumentReference dRef;



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
        TextView eventName = big_view.findViewById(R.id.eventName);

        //when clicking on event name sends to details page
        eventName.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

                Intent intent = new Intent(context, DetailsActivity.class);
                for(int i = 0; i<data.size(); i++){
                    if(data.get(i).getName()== eventName.getText()){
                        intent.putExtra("Name",data.get(i).getName());
                        intent.putExtra("Location", data.get(i).getLocation());
                        intent.putExtra("Date", data.get(i).getDate());
                        intent.putExtra("Time", data.get(i).getTime());
                        intent.putExtra("Category", data.get(i).getCategory());
                        String cost = ""+data.get(i).getCost();
                        intent.putExtra("Cost", cost);
                        String registered = ""+data.get(i).getPeopleRegistered();
                        intent.putExtra("People Registered", registered);
                        intent.putExtra("Description", data.get(i).getDescription());
                        intent.putExtra("Sponsor", data.get(i).getSponsor());
                       // Toast.makeText(context, data.get(i).getCost()+" "+registered, Toast.LENGTH_SHORT).show();
                        Toast.makeText(context, "Sending to details ", Toast.LENGTH_SHORT).show();
                    }
                }

                //i.putExtra("Event",);
                context.startActivity(intent);
            }
        });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(mAuth.getCurrentUser()==null){
                    context.startActivity(new Intent(context, LoginActivity.class));
                    return;
                }
                dRef = FirebaseFirestore.getInstance().collection("User")
                        .document(mAuth.getCurrentUser().getUid());

//                TextView eventName = big_view.findViewById(R.id.eventName);


                for(int i = 0; i<data.size(); i++){
                    if(data.get(i).getName()== eventName.getText()){
                        //register the user for this event

                        checkEvent = data.get(i);
                        //TODO: check times that you can't register for conflicting events
                        //if reservation doesn't conflict
                        if(true) {
                            dRef.update("Reservations", FieldValue.arrayUnion(eventName.getText()));
                            Toast.makeText(context, "Successfully Registered for "+eventName.getText(), Toast.LENGTH_SHORT).show();
                        }
                        else{

                        }

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
        holder.cost.setText("$"+event.getCost() + "0");
//        holder.date.setText(event.getWhen().toString());
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

    public void popUpMessage(Event registered, Event attempt){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        alertDialogBuilder.setTitle("Warning ");
        alertDialogBuilder.setMessage("The event you are trying to register for, "+attempt.getName()+", conflicts with, "+registered.getName());
        alertDialogBuilder.setPositiveButton("Register Anyway",new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dRef.update("Reservations", FieldValue.arrayUnion(attempt.getName()));
            }
        });
        alertDialogBuilder.setNegativeButton("Don't Register", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                return;
            }
        });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
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