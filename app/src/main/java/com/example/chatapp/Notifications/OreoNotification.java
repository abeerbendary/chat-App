package com.example.chatapp.Notifications;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.ContextWrapper;
import android.net.Uri;
import android.os.Build;

public class OreoNotification extends ContextWrapper {

    static final String Channel_id="com.example.chatapp";
    static final String Channel_name="chatapp";
    public  NotificationManager notificationManager;
    public OreoNotification(Context base) {
        super(base);

        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.O){
            CreatChannel();
        }

    }
@TargetApi(Build.VERSION_CODES.O)
    private void CreatChannel() {
    NotificationChannel notificationChannel=new NotificationChannel(Channel_id,Channel_name, NotificationManager.IMPORTANCE_DEFAULT);
    notificationChannel.enableLights(false);
    notificationChannel.enableVibration(true);
    notificationChannel.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);

    }

    public NotificationManager getManger(){
        if(notificationManager==null){
            notificationManager=(NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        }
        return  notificationManager;
    }

    @TargetApi(Build.VERSION_CODES.O)
    public Notification.Builder getOreoNotification(String title, String body ,
                                                    PendingIntent pendingIntent,Uri soundUri,String icon){

        return new Notification.Builder(getApplicationContext(),Channel_id)
                .setContentTitle(title)
                .setContentText(body)
                .setContentIntent(pendingIntent)
                .setSmallIcon(Integer.parseInt(icon))
                .setSound(soundUri)
                .setAutoCancel(true);
    }
}
