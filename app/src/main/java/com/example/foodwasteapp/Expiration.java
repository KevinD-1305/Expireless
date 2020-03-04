package com.example.foodwasteapp;


import android.app.DatePickerDialog;
import android.content.ContentResolver;
import android.content.Intent;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationManagerCompat;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.Calendar;
import java.util.List;


public class Expiration extends AppCompatActivity
{
    private NotificationManagerCompat notificationManager;
    private static final String TAG = "Expiration activity";
    public TextView mDisplayDate;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    private EditText editItemName;
    private Button buttonAdd, buttonBack;
    private Spinner spinnerQuantity, spinnerStorage;
    public static ImageView itemImage;
    private StorageReference mStorageRef;
    private DatabaseReference mDatabaseRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expiration);

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
                                , Scanner.class));
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
        mDisplayDate = findViewById(R.id.expiration_date);
        editItemName = findViewById(R.id.editItemName);
        editItemName.setText(Scanner.productName);
        buttonAdd = findViewById(R.id.Add);
        buttonBack = findViewById(R.id.Back);
        spinnerQuantity = findViewById(R.id.SpinnerQuantity);
        spinnerStorage = findViewById(R.id.SpinnerStorage);
        itemImage = findViewById(R.id.ItemImage);
        //itemImage.setImageBitmap(Scanner.item);
        notificationManager = NotificationManagerCompat.from(this);

        mStorageRef = FirebaseStorage.getInstance().getReference("items");
        mDatabaseRef = FirebaseDatabase.getInstance().getReference().child("items");

        mDisplayDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(Expiration.this,android.R.style.Theme_Holo_Light_Dialog_MinWidth, mDateSetListener, year,month,day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable((Color.TRANSPARENT)));
                dialog.show();
            }
        });
        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                Log.d(TAG, "onDateSet: dd/mm/yy: " + dayOfMonth + "/" + month + "/" + year);

                String date = dayOfMonth + "/" + (month + 1) + "/" + year;
                mDisplayDate.setText(date);
            }
        };
        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Item item = new Item();
                item.setName(editItemName.getText().toString());
                item.setQuantity(spinnerQuantity.getSelectedItem().toString());
                item.setStorage(spinnerStorage.getSelectedItem().toString());
                item.setExpiryDate(mDisplayDate.getText().toString());
                item.setImage(itemImage);
                new FirebaseDatabaseHelper().addItem(item, new FirebaseDatabaseHelper.DataStatus() {
                    @Override
                    public void DataIsLoaded(List<Item> items, List<String> keys) {

                    }

                    @Override
                    public void DataIsInserted() {
                        Toast.makeText(Expiration.this, "Item added Successfully", Toast.LENGTH_LONG).show();
                        startActivity(new Intent(getApplicationContext()
                                , Fridge.class));
                    }
                    @Override
                    public void DataIsUpdated() {

                    }

                    @Override
                    public void DataIsDeleted() {

                    }
                });
            }
        });
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                return;
            }
        });
    }
/*
    private void uploadFile() {
        if (itemImage != null) {
            StorageReference fileReference = mStorageRef.child(System.currentTimeMillis()
            + ".");
            fileReference.putFile(itemImage)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Toast.makeText(Expiration.this, "Upload Successful", Toast.LENGTH_LONG).show();
                            Upload upload = new Upload(editItemName.getText().toString());
                            String uploadId = mDatabaseRef.push().getKey(); //New Entry with new id
                            mDatabaseRef.child(uploadId).setValue(upload); // Take unique ID and set Data to upload file
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(Expiration.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        } else {
            Toast.makeText(this, "No file", Toast.LENGTH_SHORT).show();
        }

    }

 */
}
