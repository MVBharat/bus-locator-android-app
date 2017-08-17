package com.example.bharat.buslocator;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by bharat on 28/3/17.
 */

public class RenewPass extends AppCompatActivity {

    EditText oldpass,newpass,confirmnewpass;
    Button btnClick;
    DataHandler helper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.renew_password);

        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);


        oldpass=(EditText)findViewById(R.id.oldpass);
        newpass=(EditText)findViewById(R.id.newpass);
        confirmnewpass=(EditText)findViewById(R.id.reenternewpass);
        btnClick=(Button)findViewById(R.id.btnChangePass);


        helper=new DataHandler(getBaseContext());
        final  String email=getIntent().getStringExtra("LoginEmail");

oldpass.setOnFocusChangeListener(new View.OnFocusChangeListener() {
    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        String enteredPassword=oldpass.getText().toString();

        String storedPassword=helper.getLoginCredits(email);
        if(!enteredPassword.equals(storedPassword))
        {
            oldpass.setError("Old password does not matched");
            oldpass.getText().clear();
        }

    }
});
        btnClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newP=newpass.getText().toString();
                String confirmP=confirmnewpass.getText().toString();
                if(oldpass.getText().toString().trim().length()==0)
                {
                    oldpass.setError("Please Enter Old Password");
                }
                else if(newP.equals(""))
                {
                    newpass.setError("Please enter new Password");
                }
                else if(confirmP.equals(""))
                {
                    confirmnewpass.setError("Please Confirm Password");
                }
                else if(!newP.equals(confirmP))
                {
                    Toast.makeText(getApplicationContext(),"New Password and Confirmed passwords were not matched",Toast.LENGTH_LONG).show();
                    newpass.getText().clear();
                    confirmnewpass.getText().clear();
                }
                else
                {
                    helper.renewPassUpdate(email,newP);
                    Toast.makeText(getApplicationContext(),"Password Has beeen Changed Successfylly",Toast.LENGTH_LONG).show();
                    oldpass.getText().clear();
                    newpass.getText().clear();
                    confirmnewpass.getText().clear();
                }
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
