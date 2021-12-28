package com.example.vvusa;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.net.URI;
import java.util.ArrayList;

import retrofit2.http.Url;

public class myServiceAdapter extends RecyclerView.Adapter<myServiceAdapter.MyViewHolder> {

    Context context;

    ArrayList <Service> serviceList;

    public myServiceAdapter(Context context, ArrayList<Service> serviceList) {
        this.context = context;
        this.serviceList = serviceList;
    }

    @NonNull
    @NotNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_product_service, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull MyViewHolder holder, int position) {

        Service service = serviceList.get(position);

        holder.name.setText(service.getServiceName());
        holder.condition.setText(service.getServiceCondition());
        holder.price.setText(service.getServicePrice());

        Picasso.get().load(service.serviceImage).into(holder.image);

    }

    @Override
    public int getItemCount() {
        return serviceList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView name, condition, price;
        ImageView image;

        public MyViewHolder (@NonNull View itemView){
            super(itemView);

            name = itemView.findViewById(R.id.name);
            condition = itemView.findViewById(R.id.condition);
            price = itemView.findViewById(R.id.price);
            image = itemView.findViewById(R.id.image);

        }
    }
}
