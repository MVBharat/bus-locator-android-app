package com.example.bharat.buslocator;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

/**
 * Created by bharat on 18/4/17.
 */

public class AdminViews extends AppCompatActivity {

    DataHandler helper;
    EditText busnumb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_views);

        ImageView busdetail = (ImageView) findViewById(R.id.busdtl);
        busdetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent bd = new Intent(AdminViews.this, BusInfo.class);
                startActivity(bd);


            }
        });

        final ImageView numbus = (ImageView) findViewById(R.id.userdtl);

        helper = new DataHandler(getBaseContext());

        numbus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminViews.this, UserDetails.class);
                startActivity(intent);
            }
        });

        final ImageView updatebus=(ImageView)findViewById(R.id.updatebus);
       updatebus.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent intent=new Intent(AdminViews.this,UpdateBus.class);
               startActivity(intent);
           }
       });

        ImageView readComplaint = (ImageView) findViewById(R.id.viewcomplst);
        readComplaint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminViews.this, VIewComplaints.class);
                startActivity(intent);
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.adminmenu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.logout:
                Intent intent = new Intent(AdminViews.this, Login.class);
                startActivity(intent);
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
