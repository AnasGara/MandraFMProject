package com.Anas.onlineradio;

import android.app.Notification;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.session.MediaSession;
import android.os.Build;
import android.support.v4.media.session.MediaSessionCompat;
import android.util.Log;

import androidx.core.app.NotificationManagerCompat;
import androidx.media.app.NotificationCompat;

public class CreateNotification {
    public static  final  String CHANNEL_ID="channel";
    public static  final  String CHANNEL_PLAY="actionplay";


    public static Notification notification;
    public  static void createNotification(Context context,int playButton){
    if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O)
    {
        NotificationManagerCompat notificationManagerCompat=NotificationManagerCompat.from(context);
        MediaSessionCompat mediaSessionCompat = new MediaSessionCompat(context, "tag");

        Bitmap icon = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_baseline_pause_24);


        //Create Notification

        Log.d("Radio", "creating notif");


        notification= new Notification.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_music_note)
                .setContentTitle("MandraFM LIVE NOW")
                .setContentText("MandraTeam")
                .setLargeIcon(icon)
                .setOnlyAlertOnce(true)
                .setShowWhen(false)
                .build();
        notificationManagerCompat.notify(1,notification);

    }

    };
}
