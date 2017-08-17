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
 * Created by bharat on 24/3/17.
 */

public class Route extends AppCompatActivity {

    Button ok,viewbus,viewinMap;
    TextView textView;
    DataHandler helper;
    EditText busnum;
    ListView listbusnums;
    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.route);
        helper=new DataHandler(getBaseContext());

        textView=(TextView)findViewById(R.id.txtroute);
        listbusnums=(ListView)findViewById(R.id.listViewBusNum);
        ok=(Button)findViewById(R.id.buttonOk);
        viewbus=(Button)findViewById(R.id.viewAllBusButton);
        viewinMap=(Button)findViewById(R.id.viewMapButton);
        busnum=(EditText)findViewById(R.id.editText);

        final ArrayList<String> theList=new ArrayList<>();

        ok.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                textView.setVisibility(View.VISIBLE);
                listbusnums.setVisibility(View.GONE);
            String busno=busnum.getText().toString().trim();
            Cursor data=helper.getBusDetails(busno);
                if(data.getCount()==0)
                {
                    Toast.makeText(getApplicationContext(),"No Drivers found for "+busno+"Bus number",Toast.LENGTH_LONG).show();
                    textView.setVisibility(View.GONE);
                }
                else
                {
                    while (data.moveToNext())
                    {
                        String driver=data.getString(data.getColumnIndex("BUS_ROOT"));
                        textView.setText(driver);
                    }
                }
            }
        });

        viewbus.setOnClickListener(new View.OnClickListener() {
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
                        ListAdapter listAdapter=new ArrayAdapter<>(Route.this,android.R.layout.simple_list_item_1,theList);
                        listbusnums.setAdapter(listAdapter);
                    }
                }


            }
        });
        viewinMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(Route.this,MapVIew.class);
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
