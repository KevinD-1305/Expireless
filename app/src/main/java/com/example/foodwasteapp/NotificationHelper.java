package com.example.foodwasteapp;

import android.app.Notification;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.google.firebase.database.core.Context;

import static com.example.foodwasteapp.Notifications.CHANNEL_1_ID;

public class NotificationHelper {

    public static void ExpiresSoon(Context context, String title) {
        String text = "Item will expire in: "; //date.getText().toString();
        NotificationCompat.Builder notification =
                new NotificationCompat.Builder(context, Notifications.CHANNEL_1_ID)
                    .setSmallIcon(R.drawable.ic_star)
                    .setContentTitle(title)
                    .setContentText(text + "5" + " day/s")
                    .setPriority(NotificationCompat.PRIORITY_HIGH);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        notificationManager.notify(1,notification.build());
    }
    public static void ExpiresToday(View v, String title) {
        String text = "Item expires today!"; //date.getText().toString();
        Notification notification = new Notification.Builder(this, CHANNEL_1_ID)
                .setSmallIcon(R.drawable.ic_star)
                .setContentTitle(title)
                .setContentText(text)
                .build();
        notificationManager.notify(1,notification);
    }
    public static void Expired(View v, String title) {
        String text = "Item has expired."; //date.getText().toString();
        Notification notification = new Notification.Builder(this, CHANNEL_1_ID)
                .setSmallIcon(R.drawable.ic_star)
                .setContentTitle(title)
                .setContentText(text)
                .build();
        notificationManager.notify(1,notification);
    }
}
