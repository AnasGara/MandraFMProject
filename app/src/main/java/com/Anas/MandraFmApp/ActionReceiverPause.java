package com.Anas.MandraFmApp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class ActionReceiverPause extends BroadcastReceiver {


    @Override
    public void onReceive(Context context, Intent intent) {
        boolean playing = true;
        Toast.makeText(context,"recieved", Toast.LENGTH_SHORT).show();
        Log.d("radio","Clciked pausr");


        MainActivity.mMediaPlayer.pause();

    }

    public void stopPlayer(){
        Log.d("radio"," stopPlayer()");
    }

    public void startPlayer(){
        Log.d("radio","startPlayer()");
    }

}