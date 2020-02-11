package com.example.foodwasteapp;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class FirebaseDatabaseHelper {
    private FirebaseDatabase mDatabase;
    private DatabaseReference mReferenceItems;
    private List<Item> items =  new ArrayList<>();

    public interface DataStatus{
        void DataIsLoaded(List<Item> items, List<String> keys);
        void DataIsInserted();
        void DatIsUpdated();
        void DataIsDeleted();
    }
    public  FirebaseDatabaseHelper() {
        mDatabase = FirebaseDatabase.getInstance();
        mReferenceItems = mDatabase.getReference("items");
        Log.d("Finding database", "database found");
    }

    public void readItems(final DataStatus dataStatus) {
        mReferenceItems.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                items.clear();
                List<String> itemKeys = new ArrayList<>();
                for (DataSnapshot keyNode : dataSnapshot.getChildren()) {
                    itemKeys.add(keyNode.getKey());
                    //keys.add(keyNode.getKey());
                    Item item = keyNode.getValue(Item.class);
                    items.add(item);
                }
                dataStatus.DataIsLoaded(items, itemKeys);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
