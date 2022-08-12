package com.example.alarmtimer;

import static android.content.Context.ACTIVITY_SERVICE;

import android.app.ActivityManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class AlarmReceiver extends BroadcastReceiver {

    PendingIntent pendingIntent;

    @Override
    public void onReceive(Context context, Intent intent){
        boolean isRunning = false;
        String string = intent.getExtras().getString("extra");

        ActivityManager manager = (ActivityManager) context.getSystemService(ACTIVITY_SERVICE);
        for(ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)){
            if(AlarmRingtone.class.getName().equals(service.service.getClassName())){
                isRunning = true;
            }
        }
        Intent mIntent = new Intent(context, AlarmRingtone.class);
        if (string.equals("on") && !isRunning){
            context.startService(mIntent);
            MainActivity.activeAlarm = intent.getExtras().getString("active");
        } else if (string.equals("off")){
            context.stopService(mIntent);
            MainActivity.activeAlarm = "";
        }
        Intent resultIntent = new Intent(context, MainActivity.class);
        pendingIntent = PendingIntent.getActivity(context, 1, resultIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, AlarmAdd.CHANNEL_ID)

                .setSmallIcon(R.drawable.ic_notification)
                .setContentTitle("Reminder")
                .setContentText("Alarm")
                .setAutoCancel(true)
                .setPriority(NotificationCompat.PRIORITY_HIGH);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        notificationManager.notify(200,builder.build());
    }
}


