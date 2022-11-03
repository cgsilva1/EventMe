package com.example.eventme.ui.explore;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eventme.Event;
import com.example.eventme.R;
import com.example.eventme.User;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class ExploreAdapter extends RecyclerView.Adapter<ExploreAdapter.MyViewHolder> {
    private Context context;
    User user;
    ArrayList<Event> data;


    String date;
    //Constructor
    public ExploreAdapter(ArrayList<Event> data){
        this.data = data;
    }
//    public void setCard(ArrayList<Event> data) {
//        this.data = data;
//        notifyDataSetChanged();
//    }

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

    @NonNull
    @Override
    public ExploreAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_holder,viewGroup, false);
        return new MyViewHolder(view, viewType);
    }

    @Override
    public void onBindViewHolder(@NonNull ExploreAdapter.MyViewHolder holder, int position) {
        MyViewHolder vh = (MyViewHolder) holder;
        Event event = data.get(position);

        vh.name.setText(data.get(position).getName());
        vh.desc.setText(data.get(position).getDescription());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        TextView name;
        TextView desc;
        //need to add other parameters of event

        public MyViewHolder(@NonNull View itemView, int viewType) {
            super(itemView);
            name = itemView.findViewById(R.id.eventName);
            desc = itemView.findViewById(R.id.description);
        }
    }
}
