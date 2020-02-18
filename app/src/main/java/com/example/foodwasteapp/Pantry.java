package com.example.foodwasteapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

public class Pantry extends AppCompatActivity {

    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantry);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        //Set Home Selected
        bottomNavigationView.setSelectedItemId(R.id.Pantry);

        //Perform ItemSelected
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId())
                {
                    case R.id.BarcodeScanner:
                        startActivity(new Intent(getApplicationContext()
                                ,BarcodeScanner.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.Fridge:
                        startActivity(new Intent(getApplicationContext()
                                ,Fridge.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.Freezer:
                        startActivity(new Intent(getApplicationContext()
                                ,Freezer.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.Pantry:
                        startActivity(new Intent(getApplicationContext()
                                ,Pantry.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });
        mRecyclerView = findViewById(R.id.recyclerview_items);

        new FirebaseDatabaseHelper().readPantryItems(new FirebaseDatabaseHelper.DataStatus() {
            @Override
            public void DataIsLoaded(List<Item> items, List<String> keys) {
                new RecyclerView_Config().setConfig(mRecyclerView, Pantry.this,
                        items,keys);
            }

            @Override
            public void DataIsInserted() {

            }

            @Override
            public void DataIsUpdated() {

            }

            @Override
            public void DataIsDeleted() {

            }
        });
    }
}
