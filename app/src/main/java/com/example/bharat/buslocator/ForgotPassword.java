package com.example.bharat.buslocator;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;


/**
 * Created by bharat on 28/3/17.
 */

public class ForgotPassword extends AppCompatActivity {

    Button send;
    @Override
    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forgot_password_layout);

       // final String email=getIntent().getStringExtra("LoginEmail");
        send =(Button)findViewById(R.id.send);
        send.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent s = new Intent(ForgotPassword.this, RenewPass.class);
                //s.putExtra("LoginEmail",email);
                startActivity(s);
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
