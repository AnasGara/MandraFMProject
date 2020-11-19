package com.Anas.onlineradio;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.session.MediaSession;
import android.os.Build;
import android.support.v4.media.session.MediaSessionCompat;
import android.util.Log;

import androidx.core.app.NotificationManagerCompat;
import androidx.media.app.NotificationCompat;

import com.Anas.onlineradio.Services.NotificationActionService;

public class CreateNotification {
    public static  final  String CHANNEL_ID="channel";
    public static  final  String ACTION_PLAY="actionplay";


    public static Notification notification;
    public  static void createNotification(Context context,int playButton){
    if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O)
    {
        NotificationManagerCompat notificationManagerCompat=NotificationManagerCompat.from(context);
        MediaSessionCompat mediaSessionCompat = new MediaSessionCompat(context, "tag");

        Bitmap icon = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_baseline_pause_24);

        Intent intentPlay = new Intent(context, NotificationActionService.class).setAction(ACTION_PLAY);
        PendingIntent pendingIntentPlay = PendingIntent.getBroadcast(context,0, intentPlay,PendingIntent.FLAG_UPDATE_CURRENT);

        //Create Notification

        Log.d("Radio", "creating notif");


        notification= new Notification.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_music_note)
                .setContentTitle("MandraFM LIVE NOW")
                .setContentText("MandraTeam")
                .setLargeIcon(icon)
                .setOnlyAlertOnce(true)
                .setShowWhen(false)
                .addAction(R.drawable.ic_baseline_play_arrow_24,"Play", pendingIntentPlay)
//                .setStyle(new androidx.media.app.NotificationCompat.MediaStyle()
//                        .setShowActionsInCompactView(0,1,2).setMediaSession(MediaSessionCompat.getSessionToken()))

               // .setOngoing(true)
                .build();
        notificationManagerCompat.notify(1,notification);

    }

    };
}
