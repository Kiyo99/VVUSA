package com.example.vvusa;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class myAdapter extends RecyclerView.Adapter<myAdapter.MyViewHolder> {

    Context context;

    ArrayList <User> list;

    public myAdapter(Context context, ArrayList<User> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @NotNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull MyViewHolder holder, int position) {

        User user = list.get(position);
        holder.applicant.setText("Applicant: " + user.getApplicant());
        holder.ID.setText("ID: "+ user.getID());
        holder.position.setText("Position: "+ user.getPosition());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView applicant, ID, position;

        public MyViewHolder (@NonNull View itemView){
        super(itemView);

        applicant = itemView.findViewById(R.id.applicant);
        ID = itemView.findViewById(R.id.id);
        position = itemView.findViewById(R.id.Position);
        }
    }
}
