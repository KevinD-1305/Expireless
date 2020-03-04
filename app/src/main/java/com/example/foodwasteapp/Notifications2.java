package com.example.foodwasteapp;

import android.app.Notification;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationManagerCompat;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import static com.example.foodwasteapp.Notifications.CHANNEL_1_ID;

public class Notifications2 extends AppCompatActivity {
    TextView a;
    private FirebaseDatabase mDatabase;
    private DatabaseReference mReferenceItems;
    private NotificationManagerCompat notificationManager;
    long daysToExpiry;
    String expiryDate;
    private Context mContext;
    String currentDate;
    private TextView mExpiryDate;

    public void onCreate(Bundle savedInstanceState, View view, final Item item) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picture);
        currentDate = new SimpleDateFormat("dd/MM/yyyy").format(new Date());

        notificationManager = NotificationManagerCompat.from(this);

        //a = findViewById(R.id.date);


    }
    public void Tracker (View view){

        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
            Date currentDate1 = sdf.parse(currentDate);
            Date expiryDate1 = sdf.parse(expiryDate);
            daysToExpiry= expiryDate1.getTime() - currentDate1.getTime();
            daysToExpiry = TimeUnit.DAYS.convert(daysToExpiry, TimeUnit.MILLISECONDS);
            if (daysToExpiry > 0) {
                if (daysToExpiry <= 2){
                    System.out.println(expiryDate1 + " Between 3 days " + currentDate1);
                    ExpiresSoon(view);
                }
            }  else if (daysToExpiry == 0) {
                ExpiresToday(view);
            } else if (daysToExpiry < 0) {
                System.out.println(currentDate +"     " + expiryDate);
                Expired(view);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void ExpiresSoon(View v) {
        String title = "Item will expire in: "; //date.getText().toString();
        Notification notification = new Notification.Builder(this, CHANNEL_1_ID)
                .setSmallIcon(R.drawable.ic_star)
                .setContentTitle("Pringles")
                .setContentText(title + daysToExpiry + " day/s")
                .build();
        notificationManager.notify(1,notification);
    }
    public void ExpiresToday(View v) {
        String title = "Item expires today!"; //date.getText().toString();
        Notification notification = new Notification.Builder(this, CHANNEL_1_ID)
                .setSmallIcon(R.drawable.ic_star)
                .setContentTitle("Pringles")
                .setContentText(title)
                .build();
        notificationManager.notify(1,notification);
    }
    public void Expired(View v) {
        String title = "Item has expired."; //date.getText().toString();
        Notification notification = new Notification.Builder(this, CHANNEL_1_ID)
                .setSmallIcon(R.drawable.ic_star)
                .setContentTitle("Pringles")
                .setContentText(title)
                .build();
        notificationManager.notify(1,notification);
    }
}
