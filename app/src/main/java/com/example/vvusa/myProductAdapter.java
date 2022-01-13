package com.example.vvusa;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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

import static androidx.core.content.ContextCompat.startActivity;

public class myProductAdapter extends RecyclerView.Adapter<myProductAdapter.MyViewHolder> {

    Context context;

    ArrayList <Products> productList;

    public myProductAdapter(Context context, ArrayList<Products> productList) {
        this.context = context;
        this.productList = productList;
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

        Products products = productList.get(position);

        holder.name.setText(products.getProductName());
        holder.condition.setText(products.getProductCondition());
        holder.price.setText(products.getProductPrice());

        Picasso.get().load(products.productImage).into(holder.image);

    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView name, condition, price;
        ImageView image;
        Button buy;

        public MyViewHolder (@NonNull View itemView){
            super(itemView);

            name = itemView.findViewById(R.id.name);
            condition = itemView.findViewById(R.id.condition);
            price = itemView.findViewById(R.id.price);
            image = itemView.findViewById(R.id.image);
            buy = itemView.findViewById(R.id.buy);

            buy.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    itemView.getContext().startActivity(new Intent(itemView.getContext(), ChatArea.class));
                }
            });

        }
    }
}
