package com.example.foodwasteapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;


import java.util.List;

public class ReadData extends AppCompatActivity {

    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_data);
        mRecyclerView = findViewById(R.id.recyclerview_items);
        new FirebaseDatabaseHelper().readItems(new FirebaseDatabaseHelper.DataStatus() {
            @Override
            public void DataIsLoaded(List<Item> items, List<String> keys) {
                new RecyclerView_Config().setConfig(mRecyclerView, ReadData.this,
                        items,keys);
            }

            @Override
            public void DataIsInserted() {

            }

            @Override
            public void DatIsUpdated() {

            }

            @Override
            public void DataIsDeleted() {

            }
        });
    }
}
