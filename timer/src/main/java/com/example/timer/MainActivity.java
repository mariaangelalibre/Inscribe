package com.example.timer;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.view.View;
import android.widget.Chronometer;
import android.widget.ImageButton;


public class MainActivity extends AppCompatActivity {
    Chronometer chronometer;
    ImageButton btnStart,btnRestart;
    private boolean isResume;
    Handler handler;
    long tMilliSec, tStart, tBuff,tUpdate = 0L;
    int min,sec,milliSec;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.E6BA95)));
        chronometer = findViewById(R.id.chronometer);
        btnStart = findViewById(R.id.btnStart);
        btnRestart = findViewById(R.id.btnRestart);

        handler = new Handler();
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!isResume){
                    tStart = SystemClock.uptimeMillis();
                    handler.postDelayed(runnable,0);
                    chronometer.start();
                    isResume = true;
                    btnRestart.setVisibility(View.GONE);
                    btnStart.setImageDrawable(getResources().getDrawable(
                            R.drawable.ic_pause
                    ));
                }else{
                    tBuff += tMilliSec;
                    handler.removeCallbacks(runnable);
                    chronometer.stop();
                    isResume = false;
                    btnRestart.setVisibility(view.VISIBLE);
                    btnStart.setImageDrawable(getResources().getDrawable(
                            R.drawable.ic_start
                    ));
                }
            }
        });

        btnRestart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isResume){
                    btnStart.setImageDrawable(getResources().getDrawable(
                            R.drawable.ic_start
                    ));
                    tMilliSec = 0L;
                    tStart = 0L;
                    tBuff = 0L;
                    tUpdate = 0L;
                    sec = 0;
                    min = 0;
                    milliSec = 0;
                    chronometer.setText("00:00:00");
                }
            }
        });
    }
    public Runnable runnable = new Runnable() {
        @Override
        public void run() {
            tMilliSec = SystemClock.uptimeMillis() - tStart;
            tUpdate = tBuff + tMilliSec;
            sec = (int) (tUpdate/1000);
            min = sec/60;
            sec = sec%60;
            milliSec = (int) (tUpdate%100);
            chronometer.setText(String.format("%02d",min)+":"+String.format("%02d",sec)+":"+String.format("%02d",milliSec));
            handler.postDelayed(this,60);
        }
    };
}