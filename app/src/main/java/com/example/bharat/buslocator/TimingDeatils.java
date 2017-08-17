package com.example.bharat.buslocator;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by bharat on 25/3/17.
 */

public class TimingDeatils extends AppCompatActivity {

    EditText busnum;
    Button ok,viewallBus,viewinMap;
    TextView viewtime;
    ListView listbusnums;
    DataHandler helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.timing);

        helper=new DataHandler(getBaseContext());
        busnum=(EditText)findViewById(R.id.etBusNum);
        ok=(Button)findViewById(R.id.btnOkk);
        viewallBus=(Button)findViewById(R.id.busnumview);
        viewinMap=(Button)findViewById(R.id.mapviewButtonTiming);
        viewtime=(TextView)findViewById(R.id.txtTiming);
        listbusnums=(ListView)findViewById(R.id.busnuminfolistView);


        final ArrayList<String> theList=new ArrayList<>();


        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listbusnums.setVisibility(View.GONE);

                viewtime.setVisibility(View.VISIBLE);
                String busno=busnum.getText().toString().trim();
                Cursor data=helper.getBusDetails(busno);
                if(data.getCount()==0)
                {
                    Toast.makeText(getApplicationContext(),"No TIMING found for "+busno+"Bus number",Toast.LENGTH_LONG).show();
                    viewtime.setVisibility(View.GONE);
                }
                else
                {
                    while (data.moveToNext())
                    {
                        String driver=data.getString(data.getColumnIndex("BUS_TIMING"));
                        viewtime.setText(driver);
                    }
                }
            }
        });

        viewallBus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listbusnums.setVisibility(View.VISIBLE);
                Cursor data=helper.getBusNODetails();
                if(data.getCount()==0)
                {
                    Toast.makeText(getApplicationContext(),"No Users found in Database",Toast.LENGTH_LONG).show();
                }
                else
                {
                    while (data.moveToNext())
                    {
                        String first=data.getString(data.getColumnIndex("BUS_NUM"));
                        theList.add(first+"\n");
                        ListAdapter listAdapter=new ArrayAdapter<>(TimingDeatils.this,android.R.layout.simple_list_item_1,theList);
                        listbusnums.setAdapter(listAdapter);
                    }
                }


            }
        });

        viewinMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(TimingDeatils.this,MapVIew.class);
                startActivity(i);
            }
        });

        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
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
