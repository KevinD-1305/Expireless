package com.example.foodwasteapp;

import android.os.Bundle;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;

public class CallPciture extends AppCompatActivity {
    ImageView imageView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picture);

        imageView = findViewById(R.id.ItemImage);
        String url = "https://static.openfoodfacts.org/images/products/506/033/563/5228/front_de.29.400.jpg";

        Picasso.with(this).load(url).into(imageView);

    }
}
