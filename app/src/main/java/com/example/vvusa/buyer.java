package com.example.vvusa;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class buyer extends AppCompatActivity {
    Button stationaryBtn, shoeBtn, bagsBtn, clothBtn, gadgetBtn, cosmeticBtn;

    Button repairBtn, hairDressingBtn, barberingBtn, cateringBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buyer);

        // Buttons for products
        stationaryBtn = findViewById(R.id.stationaryBtn);
        shoeBtn = findViewById(R.id.shoeBtn);
        bagsBtn = findViewById(R.id.bagsBtn);
        clothBtn = findViewById(R.id.clothBtn);
        gadgetBtn = findViewById(R.id.gadgetsBtn);
        cosmeticBtn = findViewById(R.id.cosmeticsBtn);

        // Buttons for services
        repairBtn = findViewById(R.id.gadgetRepairBtn);
        hairDressingBtn = findViewById(R.id.hairDressingBtn);
        barberingBtn = findViewById(R.id.babberingBtn);
        cateringBtn = findViewById(R.id.catheringBtn);

//         Onclick listener for products
        stationaryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent kio = new Intent(buyer.this, Stationary.class);
                startActivity(kio);
            }
        });

        shoeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent kio = new Intent(buyer.this, shoesActivity.class);
                startActivity(kio);
            }
        });

        bagsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent kio = new Intent(buyer.this, BagsActivity.class);
                startActivity(kio);
            }
        });


        clothBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(buyer.this, ClothesActivity.class);
                startActivity(intent);
            }
        });

        cosmeticBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(buyer.this, CosmeticActivity.class);
                startActivity(intent);
            }
        });

        gadgetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(buyer.this, GadgetsActivity.class);
                startActivity(intent);
            }
        });

        //Onclick listeners for services
        barberingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(buyer.this, BarberingActivity.class);
                startActivity(intent);
            }
        });

        repairBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(buyer.this, RepairActivity.class);
                startActivity(intent);
            }
        });

        hairDressingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(buyer.this, HairDressingActivity.class);
                startActivity(intent);
            }
        });

        cateringBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(buyer.this, CateringActivity.class);
                startActivity(intent);
            }
        });
    }
}