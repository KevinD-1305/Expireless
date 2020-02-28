package com.example.foodwasteapp;

import android.provider.ContactsContract;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
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
        void DataIsUpdated();
        void DataIsDeleted();
    }
    public  FirebaseDatabaseHelper() {
        mDatabase = FirebaseDatabase.getInstance();
        mReferenceItems = mDatabase.getReference().child("items");
    }
    public void readFridgeItems(final DataStatus dataStatus) {
        Query query = FirebaseDatabase.getInstance().getReference("items")
                .orderByChild("storage")
                .equalTo("Fridge");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                items.clear();
                List<String> keys = new ArrayList<>();
                for (DataSnapshot keyNode : dataSnapshot.getChildren()) {
                    keys.add((keyNode.getKey()));
                    Item item = keyNode.getValue(Item.class);
                    items.add(item);

                }
                dataStatus.DataIsLoaded(items, keys);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void readFreezerItems(final DataStatus dataStatus) {
        Query query2 = FirebaseDatabase.getInstance().getReference("items")
                .orderByChild("storage")
                .equalTo("Freezer");
        query2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                items.clear();
                List<String> keys = new ArrayList<>();
                for (DataSnapshot keyNode : dataSnapshot.getChildren()) {
                    keys.add((keyNode.getKey()));
                    Item item = keyNode.getValue(Item.class);
                    items.add(item);

                }
                dataStatus.DataIsLoaded(items, keys);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void readPantryItems(final DataStatus dataStatus) {
        Query query3 = FirebaseDatabase.getInstance().getReference("items")
                .orderByChild("storage")
                .equalTo("Pantry");
        query3.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                items.clear();
                List<String> keys = new ArrayList<>();
                for (DataSnapshot keyNode : dataSnapshot.getChildren()) {
                    keys.add((keyNode.getKey()));
                    Item item = keyNode.getValue(Item.class);
                    items.add(item);

                }
                dataStatus.DataIsLoaded(items, keys);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void addItem(Item item, final DataStatus dataStatus) {
        String key = mReferenceItems.push().getKey();
        mReferenceItems.child(key).setValue(item)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        dataStatus.DataIsInserted();
                    }
                });
    }

    public void updateItem(String key, Item item, final DataStatus dataStatus) {
        mReferenceItems.child(key).setValue(item)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        dataStatus.DataIsUpdated();
                    }
                });
    }

    public void deleteItem(String key, final DataStatus dataStatus) {
        mReferenceItems.child(key).setValue(null)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        dataStatus.DataIsDeleted();
                    }
                });
    }
}
