package com.Anas.onlineradio;

import androidx.appcompat.app.AppCompatActivity;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    Button mPlayButton ;
    MediaPlayer mMediaPlayer;
    boolean prepared=false;
    boolean started=false;
    String stream = "https://freeuk29.listen2myradio.com/live.mp3?typeportmount=s1_33203_stream_984183506";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mPlayButton = (Button) findViewById(R.id.playButton);
        mPlayButton.setEnabled(false);
        mPlayButton.setText("LOADING");

        mMediaPlayer= new MediaPlayer();
        mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);

        new PLayerTask().execute(stream);

        mPlayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(started){

                    Log.d("Radio","Start?  ");
                    started=false;
                    mMediaPlayer.pause();
                    mPlayButton.setText("PLAY");

                }else{
                    Log.d("Radio","pause?  ");
                    started=true;
                    mMediaPlayer.start();
                    mPlayButton.setText("PAUSE");

                }
            }
        });

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
    }
}