package com.example.alarmtimer;

import static android.content.Context.ALARM_SERVICE;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.RequiresApi;

import java.util.List;

public class AlarmCustomAdapter extends BaseAdapter {
    private Context context;
    private List<AlarmTime> alarmList;
    private LayoutInflater layoutInflater;
    ImageButton deletebtn;

    public AlarmCustomAdapter(Context c, List<AlarmTime> alarmList){
        this.context = c;
        this.alarmList = alarmList;
        layoutInflater = (LayoutInflater.from(context));
    }

    @Override
    public int getCount(){
        return alarmList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = layoutInflater.inflate(R.layout.alarm_items, null);
        final AlarmTime selectedAlarm = alarmList.get(position);
        final TextView nameTV = convertView.findViewById(R.id.nameTextView);
        final TextView alarmTV = convertView.findViewById(R.id.timeTextView);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(ALARM_SERVICE);

        nameTV.setText(selectedAlarm.getName());
        alarmTV.setText(selectedAlarm.toString());

        final Intent serviceIntent = new Intent(context, AlarmReceiver.class);

        final Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, selectedAlarm.getHour());
        calendar.set(Calendar.MINUTE, selectedAlarm.getMinute());
        calendar.set(Calendar.SECOND, 0);
        if (calendar.getTimeInMillis() < System.currentTimeMillis()){
            calendar.add(Calendar.DATE,1);
        }

        deletebtn = convertView.findViewById(R.id.deletebtn);
        deletebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlarmDatabaseHelper db = new AlarmDatabaseHelper(context);
                db.deleteAlarm(selectedAlarm);
                alarmList.remove(selectedAlarm);
                notifyDataSetChanged();
            }
        });

        Switch aSwitch = convertView.findViewById(R.id.onoff);
        aSwitch.setChecked(selectedAlarm.getStatus());
        AlarmManager finalAlarmManager = alarmManager;
        aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                selectedAlarm.setStatus(isChecked);
                AlarmDatabaseHelper db = new AlarmDatabaseHelper(context);
                db.updateAlarm(selectedAlarm);

                MainActivity.alarmList.clear();
                List<AlarmTime> list = db.getAllAlarms();
                MainActivity.alarmList.addAll(list);
                notifyDataSetChanged();

                if (!isChecked && selectedAlarm.toString().equals(MainActivity.activeAlarm)){
                    serviceIntent.putExtra("extra","off");
                    PendingIntent pendingIntent = PendingIntent.getBroadcast(context, position, serviceIntent, PendingIntent.FLAG_UPDATE_CURRENT);
                    finalAlarmManager.cancel(pendingIntent);
                    context.sendBroadcast(serviceIntent);
                }
            }
        });

        if (selectedAlarm.getStatus()){
            serviceIntent.putExtra("extra","on");
            serviceIntent.putExtra("active",selectedAlarm.toString());
            PendingIntent pendingIntent = PendingIntent.getBroadcast(context, position, serviceIntent, PendingIntent.FLAG_UPDATE_CURRENT);
            alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
            alarmManager = (AlarmManager) context.getSystemService(ALARM_SERVICE);
        }
        return convertView;
    }
}
