package com.example.bharat.buslocator;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by bharat on 29/4/17.
 */

public class VIewComplaints extends AppCompatActivity {

    DataHandler helper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_complaints);
        ListView listView=(ListView)findViewById(R.id.listviewComplaint);
        TextView cnt=(TextView)findViewById(R.id.txtCount);
        helper=new DataHandler(getBaseContext());
        ArrayList<String> theList=new ArrayList<>();
        Cursor data=helper.getDetails();
        int h=data.getCount();
        cnt.setText(String.valueOf(h));
        if(data.getCount()==0)
        {
            Toast.makeText(getApplicationContext(),"No Users found in Database",Toast.LENGTH_LONG).show();
        }
        else
        {
            while (data.moveToNext())
            {
                String first=data.getString(data.getColumnIndex("EMAILS"));
                String second=data.getString(data.getColumnIndex("SUB"));
                String third=data.getString(data.getColumnIndex("COMPLAINT"));
                theList.add(first+"\n"+ second+"\n"+ third);
                ListAdapter listAdapter=new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,theList);
                listView.setAdapter(listAdapter);
            }
        }

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



