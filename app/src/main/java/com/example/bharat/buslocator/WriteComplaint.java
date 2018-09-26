package com.example.bharat.buslocator;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by bharat on 18/4/17.
 */

public class WriteComplaint extends AppCompatActivity {

    EditText email,sub,det;
    DataHandler helper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.write_complaint);

        email=(EditText)findViewById(R.id.edtem) ;
        sub=(EditText)findViewById(R.id.sub);
        det=(EditText)findViewById(R.id.compdetails);

        Button complaints=(Button)findViewById(R.id.wrtcompl);
        helper=new DataHandler(getBaseContext());

        complaints.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String emailadd=email.getText().toString().trim();
                String subject=sub.getText().toString().trim();
                String compdetail=det.getText().toString().trim();
                helper.editComplaints(emailadd,subject,compdetail);
                Toast.makeText(getApplicationContext(),"Complainted succesfull",Toast.LENGTH_LONG).show();

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
