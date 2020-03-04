package com.example.foodwasteapp;

import android.provider.ContactsContract;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.List;

public class FirebaseDatabaseHelper {
    private FirebaseDatabase mDatabase;
    private DatabaseReference mReferenceItems;
    private StorageReference mStorageRef;
    private FirebaseStorage mStorage;
    private List<Item> items =  new ArrayList<>();

    public interface DataStatus{
        void DataIsLoaded(List<Item> items, List<String> keys);
        void DataIsInserted();
        void DataIsUpdated();
        void DataIsDeleted();
    }
    public  FirebaseDatabaseHelper() {
        mDatabase = FirebaseDatabase.getInstance();
        mStorage = FirebaseStorage.getInstance();
        mStorageRef = mStorage.getReference().child("items");
        mReferenceItems = mDatabase.getReference("items");
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

    public void addItem(final Item item, final DataStatus dataStatus) {
        StorageReference fileReference = mStorageRef.child(System.currentTimeMillis() + ".");
        /*fileReference.putFile(item.getImage())
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Upload upload = new Upload(item.getName());
                    }
                });

         */
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
