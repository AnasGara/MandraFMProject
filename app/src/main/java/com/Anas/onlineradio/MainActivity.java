package com.Anas.onlineradio
;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationManagerCompat;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.Anas.onlineradio.Services.OnClearFromRecentService;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.io.IOException;
import java.nio.channels.Channel;
import java.util.List;

public class MainActivity extends AppCompatActivity implements Playable{

    Button mPlayButton ;
    MediaPlayer mMediaPlayer;
    boolean prepared=false;
    boolean started=false;
    String stream = "https://radio.mosaiquefm.net/mosalive";
   // https://freeuk29.listen2myradio.com/live.mp3?typeportmount=s1_33203_stream_984183506
//https://freeuk29.listen2myradio.com/live.mp3?typeportmount=s1_33203_stream_984183506
    NotificationManager mNotificationManager;
    boolean isPlaying=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mPlayButton = (Button) findViewById(R.id.playButton);
        mPlayButton.setEnabled(false);
        mPlayButton.setText("LOADING");

        mMediaPlayer = new MediaPlayer();
        mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);

        new PLayerTask().execute(stream);

        mPlayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (started) {

                    Log.d("Radio", "Start?  ");
                    started = false;
                    mMediaPlayer.pause();
                    mPlayButton.setText("PLAY");

                } else {
                    Log.d("Radio", "pause?  ");
                    started = true;
                    mMediaPlayer.start();
                    mPlayButton.setText("PAUSE");

                }
                CreateNotification.createNotification(MainActivity.this, R.drawable.ic_baseline_pause_24 );
            if(isPlaying){
                onTrackPause();
            }
            else {onTrackPLay();}
            }


        });

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createChannel();
            registerReceiver(mBroadcastReceiver, new IntentFilter("TRACKS_TRACKS"));
            startService(new Intent(getBaseContext(), OnClearFromRecentService.class));


        }
    }

        private void createChannel() {

            if (Build.VERSION.SDK_INT >=Build.VERSION_CODES.O){
                NotificationChannel channel= new NotificationChannel(CreateNotification.CHANNEL_ID, "KKKK", NotificationManager.IMPORTANCE_HIGH);
                mNotificationManager = getSystemService(NotificationManager.class);
                if(mNotificationManager != null)
                {
                    mNotificationManager.createNotificationChannel(channel );

                }
            }

        }

        BroadcastReceiver mBroadcastReceiver= new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String action = intent.getExtras().getString("actionname");
                switch(action)
                {
                    case  CreateNotification.ACTION_PLAY:
                        if (isPlaying)
                        {onTrackPause();}
                        else {
                            onTrackPLay();
                        }
                    break;



                }
            }
        };
    @Override
    public void onTrackPLay() {
        CreateNotification.createNotification(MainActivity.this, R.drawable.ic_baseline_pause_24);
        //play.setImageRessources(R.drawable.ic_pause..)
        //title.setText(
        isPlaying=true;
    }

    @Override
    public void onTrackPause() {
CreateNotification.createNotification(MainActivity.this, R.drawable.ic_baseline_play_arrow_24);
        //play.setImageRessources(R.drawable.ic_pause..)
        //title.setText(
        isPlaying=false;
    }


    class PLayerTask extends AsyncTask<String, Void, Boolean> {

        @Override
        protected Boolean doInBackground(String... strings) {

            try {
                mMediaPlayer.setDataSource(strings[0]);
                mMediaPlayer.prepare();
                prepared=true;
                Log.d("Radio","Prepared = " + prepared);
            } catch (IOException e) {
                e.printStackTrace();
            }

            return prepared;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            Log.d("Radio","Started  ");
            mPlayButton.setEnabled(true);
            mPlayButton.setText("PLAY");


        }
    }

    @Override
    protected void onPause() {
        super.onPause();
//        if(started){
//            mMediaPlayer.pause();
//        }
        Log.d("Radio", "OnPause called");
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(started){
            mMediaPlayer.start();

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(prepared){
            mMediaPlayer.release();
        }
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
            mNotificationManager.cancelAll();
        }
        unregisterReceiver(mBroadcastReceiver);

    }

    //Notif work starts here



}