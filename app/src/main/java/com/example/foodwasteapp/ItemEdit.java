package com.example.foodwasteapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;

import java.util.Calendar;
import java.util.List;

public class ItemEdit extends AppCompatActivity {

    private static final String TAG = "Expiration activity";
    private TextView mDisplayDate;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    private EditText editItemName; //Will Change with barcode scanner
    private Button buttonUpdate,buttonDelete, buttonBack;
    private Spinner spinnerQuantity, spinnerStorage;
    private String key, name, quantity, storage, expiryDate;
    public static ImageView itemImage;
    public static String imageUrl;
    public static Uri itemImageUri;
    private Toolbar toolbar;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_edit);

        mAuth = FirebaseAuth.getInstance();
        key = getIntent().getStringExtra("key");
        name = getIntent().getStringExtra("name");
        quantity = getIntent().getStringExtra("quantity");
        storage = getIntent().getStringExtra("storage");
        expiryDate = getIntent().getStringExtra("expiryDate");
        imageUrl = getIntent().getStringExtra("image");

        mDisplayDate = findViewById(R.id.expiration_date);
        editItemName = findViewById(R.id.editItemName);
        spinnerQuantity = findViewById(R.id.SpinnerQuantity);
        spinnerStorage = findViewById(R.id.SpinnerStorage);
        itemImage = findViewById(R.id.ItemImage);

        buttonUpdate = findViewById(R.id.Update);
        buttonDelete = findViewById(R.id.Delete);
        buttonBack = findViewById(R.id.Back);

        mDisplayDate.setText(expiryDate);
        mDisplayDate.setGravity(Gravity.CENTER);
        editItemName.setText(name);
        spinnerQuantity.setSelection(getIndex_SpinnerItem(spinnerQuantity, quantity));
        spinnerStorage.setSelection(getIndex_SpinnerItem(spinnerStorage, storage));
        Picasso.with(this).load(imageUrl).into(itemImage);

        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(name);
        setSupportActionBar(toolbar);

        mDisplayDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(ItemEdit.this,android.R.style.Theme_Holo_Light_Dialog_MinWidth, mDateSetListener, year,month,day);
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
                mDisplayDate.setGravity(Gravity.CENTER);

            }
        };
        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Item item = new Item();
                item.setName(editItemName.getText().toString());
                item.setQuantity(spinnerQuantity.getSelectedItem().toString());
                item.setStorage(spinnerStorage.getSelectedItem().toString());
                item.setExpiryDate(mDisplayDate.getText().toString());
                item.setImage(imageUrl);
                new FirebaseDatabaseHelper().updateItem(key, item, new FirebaseDatabaseHelper.DataStatus() {
                    @Override
                    public void DataIsLoaded(List<Item> items, List<String> keys) {

                    }

                    @Override
                    public void DataIsInserted() {

                    }

                    @Override
                    public void DataIsUpdated() {
                        Toast.makeText(ItemEdit.this, "Item has successfully been Updated.", Toast.LENGTH_LONG).show();
                        finish(); return;
                    }

                    @Override
                    public void DataIsDeleted() {

                    }
                });
            }
        });

        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new FirebaseDatabaseHelper().deleteItem(key, new FirebaseDatabaseHelper.DataStatus() {
                    @Override
                    public void DataIsLoaded(List<Item> items, List<String> keys) {

                    }

                    @Override
                    public void DataIsInserted() {

                    }

                    @Override
                    public void DataIsUpdated() {

                    }

                    @Override
                    public void DataIsDeleted() {
                        Toast.makeText(ItemEdit.this, "Item has successfully been deleted.", Toast.LENGTH_LONG).show();
                        finish(); return;
                    }
                });
            }
        });

        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); return;
            }
        });
    }

    private int getIndex_SpinnerItem(Spinner spinner, String item) {
        int index = 0;
        for(int i = 0; i<spinner.getCount(); i++){
            if(spinner.getItemAtPosition(i).equals(item)) {
                index = i;
                break;
            }
        }
        return index;
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        FirebaseUser user = mAuth.getCurrentUser();
        getMenuInflater().inflate(R.menu.manual, menu);
        if(user != null) {
            menu.getItem(0).setVisible(true);
            menu.getItem(1).setVisible(true);
        } else {
            menu.getItem(0).setVisible(false);
            menu.getItem(1).setVisible(false);
        }
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.new_item:
                startActivity(new Intent (this, Expiration.class));
                return  true;
            case R.id.Logout:
                mAuth.signOut();
                invalidateOptionsMenu();
                RecyclerView_Config.Logout();
                startActivity(new Intent (this, SignIn.class));
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
