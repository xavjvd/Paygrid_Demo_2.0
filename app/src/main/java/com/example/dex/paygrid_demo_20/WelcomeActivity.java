package com.example.dex.paygrid_demo_20;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class WelcomeActivity extends AppCompatActivity {

    MediaPlayer mysong;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        mysong = MediaPlayer.create(WelcomeActivity.this,R.raw.alien);
        mysong.start();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i=new Intent(WelcomeActivity.this,LoginActivity.class);
                startActivity(i);
                mysong.release();
                finish();

            }
        },3000);   //here 3000 is for 3 seconds which is the delay between activities

    }
}
