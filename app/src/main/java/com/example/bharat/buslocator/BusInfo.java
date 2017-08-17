package com.example.bharat.buslocator;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by bharat on 21/4/17.
 */

public class BusInfo extends AppCompatActivity {

    EditText busnumb,gpsid,busdrivers,roots,timings;
    Button savebusinfo, deletbus;
    Context context=this;
    DataHandler helper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bus_details);


        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        busnumb=(EditText)findViewById(R.id.enterbusnumber);
        gpsid=(EditText)findViewById(R.id.entergpsid);
        busdrivers=(EditText)findViewById(R.id.enterbusdriver);
        roots=(EditText)findViewById(R.id.busroot);
        timings=(EditText)findViewById(R.id.bustiming);

        helper=new DataHandler(getBaseContext());
        savebusinfo=(Button)findViewById(R.id.savebus);
        savebusinfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String busno=busnumb.getText().toString().trim();
                String gpsiid=gpsid.getText().toString().trim();
                String driver=busdrivers.getText().toString().trim();
                String root=roots.getText().toString().trim();
                String timing=timings.getText().toString().trim();
                helper.insertBusInfo(busno,gpsiid,driver,root,timing);
                Toast.makeText(getApplicationContext(),"Bus Information Added",Toast.LENGTH_LONG).show();

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
