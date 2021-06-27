package com.example.ping02.Notifications;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.ContextWrapper;
import android.net.Uri;
import android.os.Build;

import com.example.ping02.R;

public class OreoNotification extends ContextWrapper {
    private static final String ID="some_id";
    private static final String NAME="PING";

    private NotificationManager notificationManager;

    public OreoNotification(Context base) {
        super(base);
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
            createChannel();
        }
    }
    @TargetApi(Build.VERSION_CODES.O)
    private void createChannel() {
        NotificationChannel nc=new NotificationChannel(ID,NAME,NotificationManager.IMPORTANCE_DEFAULT);
        nc.enableLights(true);
        nc.enableVibration(true);
        nc.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);
        getManager().createNotificationChannel(nc);
    }
    public NotificationManager getManager(){
        if(notificationManager==null){
            notificationManager=(NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        }
        return notificationManager;
    }

    @TargetApi(Build.VERSION_CODES.O)
    public Notification.Builder getONotification(String title, String body, PendingIntent pIntent, Uri sounduri,String icon){
        return new Notification.Builder(getApplicationContext(),ID)
                .setContentIntent(pIntent)
                .setContentTitle(title)
                .setContentText(body)
                .setSound(sounduri)
                .setAutoCancel(true)
                .setSmallIcon(R.mipmap.ic_launcher_round);
    }

}
