package com.example.foodwasteapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

public class Fridge extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fridge);

        //Toolbar
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Fridge");
        setSupportActionBar(toolbar);


        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        //Set Home Selected
        bottomNavigationView.setSelectedItemId(R.id.Fridge);

        //Perform ItemSelected
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId())
                {
                    case R.id.BarcodeScanner:
                        startActivity(new Intent(getApplicationContext()
                                ,Scanner.class));
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

        new FirebaseDatabaseHelper().readFridgeItems(new FirebaseDatabaseHelper.DataStatus() {
            @Override
            public void DataIsLoaded(List<Item> items, List<String> keys) {
                new RecyclerView_Config().setConfig(mRecyclerView, Fridge.this,
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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.manual, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.new_item:
                startActivity(new Intent (this, Expiration.class));
                return  true;
        }
        return super.onOptionsItemSelected(item);
    }
}
