package com.example.alarmtimer;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static String activeAlarm = "";
    private ListView listView;
    private static final int REQUEST_CODE = 1000;
    public static List<AlarmTime> alarmList = new ArrayList<>();
    public static final String CHANNEL_ID = "channel_id";
    public static final String CHANNEL_NAME = "channel name";
    public static final String CHANNEL_Desc = "channel desc";

    AlarmCustomAdapter customAdapter;
    AlarmDatabaseHelper db = new AlarmDatabaseHelper(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.E6BA95)));
        com.google.android.material.floatingactionbutton.FloatingActionButton button = findViewById(R.id.add);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AlarmAdd.class);
                startActivityForResult(intent, REQUEST_CODE);
            }
        });

        listView = findViewById(R.id.listView);
        List<AlarmTime> list = db.getAllAlarms();
        alarmList.addAll(list);
        customAdapter = new AlarmCustomAdapter (getApplicationContext(),alarmList);
        listView.setAdapter(customAdapter);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == Activity.RESULT_OK && requestCode == REQUEST_CODE) {
            boolean needRefresh = data.getExtras().getBoolean("needRefresh");
            if (needRefresh){
                alarmList.clear();
                List<AlarmTime> list = db.getAllAlarms();
                alarmList.addAll(list);
                customAdapter.notifyDataSetChanged();
            }
        }
    }
}