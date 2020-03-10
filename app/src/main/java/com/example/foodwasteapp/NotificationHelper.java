package com.example.foodwasteapp;


import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;


import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;


import static com.example.foodwasteapp.Notifications.CHANNEL_1_ID;

public class NotificationHelper {

    public static void  displayNotification(Context context, String title, String body) {
        Intent intent = new Intent(context, SplashScreen.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 1, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(context, CHANNEL_1_ID)
                .setSmallIcon(R.drawable.expireless)
                .setColor(ContextCompat.getColor(context, R.color.Expire))
                .setContentText(body)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setVibrate(new long[100])
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);

        NotificationManagerCompat mNotification = NotificationManagerCompat.from(context);
        mNotification.notify(1,mBuilder.build());
    }
}
