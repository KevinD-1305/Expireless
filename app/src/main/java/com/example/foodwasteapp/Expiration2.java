package com.example.foodwasteapp;


import android.content.Intent;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;



public class Expiration2 extends AppCompatActivity
{
    private EditText editItemName;
    private Button buttonAdd;
    private Spinner spinnerQuantity;

    DatabaseReference databaseItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expiration2);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        //Set Home Selected
        bottomNavigationView.setSelectedItemId(R.id.Freezer);

        //Perform ItemSelected
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.BarcodeScanner:
                        startActivity(new Intent(getApplicationContext()
                                , BarcodeScanner.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.Fridge:
                        startActivity(new Intent(getApplicationContext()
                                , Fridge.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.Freezer:
                        startActivity(new Intent(getApplicationContext()
                                , Freezer.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.Pantry:
                        startActivity(new Intent(getApplicationContext()
                                , Pantry.class));
                        overridePendingTransition(0, 0);
                        return true;
                }
                return false;
            }
        });


        databaseItems = FirebaseDatabase.getInstance().getReference("items");

        editItemName = findViewById(R.id.editItemName);
        buttonAdd = findViewById(R.id.Add);
        spinnerQuantity = findViewById(R.id.SpinnerQuantity);

        buttonAdd.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addItem();
            }
        }));
    }

    private void addItem(){
        String name = editItemName.getText().toString().trim();
        String quantity = spinnerQuantity.getSelectedItem().toString();


        if(!TextUtils.isEmpty(name)){
                String id = databaseItems.push().getKey();

                    Item item = new Item(id, name, quantity);

                    databaseItems.child(id).setValue(item);

                    Toast.makeText(this,"item added", Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(this,"You should enter a name", Toast.LENGTH_LONG).show();
        }
    }
}
