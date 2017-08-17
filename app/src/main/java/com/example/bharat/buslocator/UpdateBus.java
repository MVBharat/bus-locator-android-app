package com.example.bharat.buslocator;

import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

/**
 * Created by bharat on 26/5/17.
 */

public class UpdateBus extends AppCompatActivity {

    DataHandler helper;
    EditText busnumber;
    Button deletbus;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update_bus_info);

        helper=new DataHandler(getBaseContext());
        busnumber=(EditText)findViewById(R.id.busnumberenter);

        deletbus=(Button) findViewById(R.id.deletonebus);
        deletbus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String busno=busnumber.getText().toString().trim();
                helper.deletbusinfo(busno);
                Toast.makeText(getApplicationContext(),"Bus deleted ",Toast.LENGTH_SHORT).show();
            }
        });
    }
}
