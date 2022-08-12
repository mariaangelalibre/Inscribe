package com.example.alarmtimer;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TimePicker;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

public class AlarmAdd extends AppCompatActivity {
    private TimePicker timePicker;
    private EditText editText;
    private ImageButton buttonSave, buttonCancel;
    private AlarmTime alarm;
    private boolean needRefresh;
    public static final String CHANNEL_ID = "channel_id";
    public static final String CHANNEL_NAME = "channel name";
    public static final String CHANNEL_Desc = "channel desc";

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_alarm);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.E6BA95)));
        notificationChannel();
        timePicker = findViewById(R.id.timePicker);
        editText = findViewById(R.id.alarmName);
        buttonSave = findViewById(R.id.btncheck);
        buttonCancel = findViewById(R.id.btnx);

        buttonSave.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                int hour = timePicker.getCurrentHour();
                int minute = timePicker.getCurrentMinute();
                String name = editText.getText().toString();

                AlarmDatabaseHelper db = new AlarmDatabaseHelper(getApplicationContext());

                alarm = new AlarmTime(hour, minute, true, name);
                db.addAlarm(alarm);

                needRefresh = true;
                onBackPressed();
            }
        });


        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }


    private void notificationChannel() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID,CHANNEL_NAME,
                    NotificationManager.IMPORTANCE_HIGH);
            channel.setDescription(CHANNEL_Desc);
            NotificationManager managerCompat = getSystemService(NotificationManager.class);
            managerCompat.createNotificationChannel(channel);
        }
    }

    @Override
    public void finish() {
        Intent data = new Intent();
        data.putExtra("needRefresh", needRefresh);
        this.setResult(RESULT_OK, data);
        super.finish();
    }
}

