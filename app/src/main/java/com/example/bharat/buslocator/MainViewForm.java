package com.example.bharat.buslocator;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

/**
 * Created by bharat on 17/3/17.
 */

public class MainViewForm extends AppCompatActivity {

    ImageView notifaciton, map, route, timing,wrtcomplaints;
 //   TextView tv_route, tv_map, tv_notification, tv_timing;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainviewform);
        String email=getIntent().getStringExtra("bharat");

        Toast.makeText(getApplicationContext(),email,Toast.LENGTH_LONG).show();
        notifaciton = (ImageView) findViewById(R.id.imageViewNotify);
         map = (ImageView) findViewById(R.id.imageViewMap);
         route = (ImageView) findViewById(R.id.imageViewRoute);
        timing = (ImageView) findViewById(R.id.imageViewTiming);
        wrtcomplaints=(ImageView)findViewById(R.id.wrtcompimg);

        notifaciton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainViewForm.this, com.example.bharat.buslocator.Notification.class);
                startActivity(intent);
            }
        });

        route.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainViewForm.this, Route.class);
                startActivity(intent);
            }
        });

        map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainViewForm.this, MapVIew.class);
                startActivity(intent);
            }
        });

        timing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainViewForm.this, TimingDeatils.class);
                startActivity(intent);
            }
        });

        wrtcomplaints.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainViewForm.this, WriteComplaint.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.usermenu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        String email=getIntent().getStringExtra("bharat");
        switch (item.getItemId()) {
           /*
            case R.id.username:

                break;
            */
            case R.id.changepasswordMenu:
                Intent i = new Intent(MainViewForm.this, RenewPass.class);
                i.putExtra("LoginEmail",email);
                startActivity(i);
                break;

            case R.id.logoutMenu:
                Intent intent = new Intent(MainViewForm.this, Login.class);
                startActivity(intent);
                finish();
                break;


        }
        return super.onOptionsItemSelected(item);
    }

}
