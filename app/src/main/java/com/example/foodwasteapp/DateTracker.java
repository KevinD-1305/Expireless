package com.example.foodwasteapp;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DateTracker extends AppCompatActivity {

    private static final String TAG = "Expiration activity";
    public TextView mDisplayDate;
    private DatePickerDialog.OnDateSetListener mDateSetListener;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barcode_scanner);

        mDisplayDate = findViewById(R.id.expiration_date);

        mDisplayDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(DateTracker.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth, mDateSetListener, year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable((Color.TRANSPARENT)));
                dialog.show();
            }
        });
        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                Log.d(TAG, "onDateSet: dd/mm/yy: " + dayOfMonth + "/" + month + "/" + year);
                String formattedMonth = "" + month;
                String formattedDayOfMonth = "" + dayOfMonth;

                if(month < 10){

                     formattedMonth = "0" + (month + 1);
                }
                if(dayOfMonth < 10){

                    formattedDayOfMonth = "0" + dayOfMonth;
                }
                String expiryDate = formattedDayOfMonth + "/" + formattedMonth + "/" + year;
                mDisplayDate.setText(expiryDate); //For Datepicker


                String currentDate = new SimpleDateFormat("dd/MM/yyyy").format(new Date());

                try {
                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
                    Date date1 = sdf.parse(expiryDate);
                    Date date2 = sdf.parse(currentDate);
                    TextView date = findViewById(R.id.date);
                    if (date1.before(date2)) {
                        date.setText("This item will expire soon.");
                    } else if (date1.equals(date2)) {
                        date.setText("This item expires today!");
                    } else if (date1.after(date2)) {
                        date.setText("This item has expired.");
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
    }
}