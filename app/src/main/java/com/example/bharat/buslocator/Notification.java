package com.example.bharat.buslocator;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

/**
 * Created by bharat on 7/5/17.
 */

public class Notification extends AppCompatActivity {

    Button set;
    EditText edtbusnum, edtyourloca;
    CheckBox settime;
    DataHandler helper;
    MapVIew mp;
    @Override
    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notification);

        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        edtbusnum=(EditText)findViewById(R.id.editTextBusNum );
        edtyourloca=(EditText)findViewById(R.id.editTextYourLocation);
        settime=(CheckBox)findViewById(R.id.checkBoxSetTime);

        set =(Button)findViewById(R.id.set);
        set.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                long when= System.currentTimeMillis();
                //Intent intent = new Intent();
               // PendingIntent pIntent = PendingIntent.getActivity(Notification.this, 0, intent, 0);
                // PendingIntent.getActivity(Notification.this, (int) System.currentTimeMillis(), resultIntent, 0);
                Intent resultIntent=new Intent(Notification.this, MapVIew.class);

               PendingIntent resultPendingIntent =PendingIntent.getActivity(Notification.this,0,resultIntent,PendingIntent.FLAG_UPDATE_CURRENT);


                AlarmManager am =(AlarmManager)getSystemService(Activity.ALARM_SERVICE);
                am.setRepeating(AlarmManager.ELAPSED_REALTIME, SystemClock.elapsedRealtime(),
                        1*60*60,resultPendingIntent);

                android.app.Notification notification = new android.app.Notification.Builder(Notification.this)
                        .setTicker("TickerTitle")
                        .setContentTitle("Bus Location")
                        .setContentText("your bus is at the loction please check in map")
                        .setSmallIcon(R.drawable.buss)
                        .setContentIntent(resultPendingIntent).getNotification();

                notification.flags = android.app.Notification.FLAG_AUTO_CANCEL;
                NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                nm.notify((int)when, notification);
            }
        });

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                //Write your logic here
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
