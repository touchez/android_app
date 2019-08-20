package com.example.a13162.activitytest.utils;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import com.example.a13162.activitytest.MainActivity;
import com.example.a13162.activitytest.R;

import static android.content.Context.NOTIFICATION_SERVICE;

public class NotificationUtil {

    private final String CHANNEL_ID = "";
    private Context context;

    public NotificationUtil(Context context) {
        this.context = context;
    }

    public void showNotification(String tagInfo, String textTitle, String textContent) {
        Log.i("touchez", "enter showNotification");
//        String textTitle = "检测到nfc标签";
//        String textContent = "要前往touchez小程序？";

        createNotificationChannel();

        // Create an explicit intent for an Activity in your app
        Intent intent = new Intent(context, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.putExtra("tagInfo", tagInfo);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);


        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_directions_bike_black_24dp)
                .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.drawable.bike))
                .setContentTitle(textTitle)
                .setContentText(textContent)
                .setWhen(System.currentTimeMillis())
                // Set the intent that will fire when the user taps the notification
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);

        int notificationId = 1;
        Log.i("touchez", "show notify");
        NotificationManager manager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);

        // notificationId is a unique int for each notification that you must define
        manager.notify(notificationId, builder.build());
    }

    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = context.getString(R.string.channel_name);
            String description = context.getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager manager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
            manager.createNotificationChannel(channel);
        }
    }
}
