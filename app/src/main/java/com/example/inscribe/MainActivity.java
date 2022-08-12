package com.example.inscribe;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    public CardView todo , notes , water , medicine , timer , alarm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        todo = (CardView) findViewById(R.id.todo);
        notes = (CardView) findViewById(R.id.notes);
        water = (CardView) findViewById(R.id.water);
        medicine = (CardView) findViewById(R.id.medicine);
        timer = (CardView) findViewById(R.id.timer);
        alarm = (CardView) findViewById(R.id.alarm);

        todo.setOnClickListener(this);
        notes.setOnClickListener(this);
        water.setOnClickListener(this);
        medicine.setOnClickListener(this);
        timer.setOnClickListener(this);
        alarm.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent i;


        switch (v.getId()) {
            case R.id.todo:
                i = new Intent(this,com.example.mytodoapplication.MainActivity.class);
                startActivity(i);
                break;

            case R.id.notes:
                i = new Intent(this,com.example.notes.MainActivity.class);
                startActivity(i);
                break;

            case R.id.water:
                i = new Intent(this,com.codingwithsara.notificationapp.MainActivity.class);
                startActivity(i);
                break;

            case R.id.medicine:
                i = new Intent(this,com.dataflair.reminderapp.MainActivity.class);
                startActivity(i);
                break;

            case R.id.timer:
                i = new Intent(this,com.example.timer.MainActivity.class);
                startActivity(i);
                break;

            case R.id.alarm:
                i = new Intent(this,com.example.alarmtimer.MainActivity.class);
                startActivity(i);
                break;



        }

    }
}