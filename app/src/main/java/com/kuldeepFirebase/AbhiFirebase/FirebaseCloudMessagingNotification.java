package com.kuldeepFirebase.AbhiFirebase;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.RemoteAction;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class FirebaseCloudMessagingNotification extends FirebaseMessagingService {

    NotificationManager notificationManager;
    Notification notification;

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        if (remoteMessage.getNotification() != null)
        {
            String title = remoteMessage.getNotification().getTitle();
            String body = remoteMessage.getNotification().getBody();

            generatenotification(title,body);

            Log.d("notification", title +" "+ body);
        }

//        if (remoteMessage.getData().size() > 0)
//        {
//            String statusValue = remoteMessage.getData().get("STATUS");
//            Log.d("NOTIFICATION ",statusValue );
//        }

    }

    private void generatenotification(String noteTitle, String noteMsg)
    {
        Intent intent = new Intent(FirebaseCloudMessagingNotification.this,UserNotes.class);

        PendingIntent pendingIntent = PendingIntent.getActivity(this,0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        Uri alarmforNotification =RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);


        if (Build.VERSION.SDK_INT >= 26)
        {
            // create channel for the notification manager

            String channelId = "com.kuldeepFirebase.AbhiFirebase";
            String channelName = "FCM DEMO";

            NotificationChannel notificationChannel = new NotificationChannel(channelId,channelName,NotificationManager.IMPORTANCE_DEFAULT);
            notificationChannel.setLightColor(Color.BLUE);
            notificationChannel.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);

            assert notificationManager != null;
            notificationManager.createNotificationChannel(notificationChannel);

            NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(FirebaseCloudMessagingNotification.this,channelId);
            notificationBuilder.setOngoing(true)
                    .setSmallIcon(R.drawable.ic_launcher_foreground)
                    .setColor(Color.YELLOW)
                    .setAutoCancel(true)
                    .setTicker(noteMsg)
                    .setContentTitle(noteTitle)
                    .setContentText(noteMsg)
                    .setSound(alarmforNotification)
                    .setDefaults(Notification.DEFAULT_VIBRATE| Notification.DEFAULT_LIGHTS)
                    .setContentIntent(pendingIntent);

        }
        else
        {
                NotificationCompat.Builder nb = new NotificationCompat.Builder(FirebaseCloudMessagingNotification.this);
                nb.setContentIntent(pendingIntent);
                nb.setContentText(noteTitle);
                nb.setContentText(noteMsg);
                nb.setSound(alarmforNotification);
                nb.setSmallIcon(R.drawable.ic_launcher_foreground);
                nb.build();

                notification = nb.getNotification();
                notification.flags =Notification.FLAG_AUTO_CANCEL;


        }

        if (Build.VERSION.SDK_INT>=26)
        {
            startForeground(0,notification);
        }
        else {
            notificationManager.notify(0,notification);
        }
    }
}
