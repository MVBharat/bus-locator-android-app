package com.example.bharat.buslocator;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Login extends Activity {
    String useremail, userpass;
    Context ctx = this;
DataHandler helper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_main);

        TextView aboutclick = (TextView) findViewById(R.id.aboutapp);
        String abtstring = new String("About App");
        SpannableString contentabt = new SpannableString(abtstring);
        contentabt.setSpan(new UnderlineSpan(), 0, abtstring.length(), 0);
        aboutclick.setText(contentabt);
        aboutclick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent abt = new Intent(Login.this, AboutApp.class);
                startActivity(abt);
            }
        });

        TextView adminclick = (TextView) findViewById(R.id.admin);
        String mystring = new String("Login as Admin");
        SpannableString content = new SpannableString(mystring);
        content.setSpan(new UnderlineSpan(), 0, mystring.length(), 0);
        adminclick.setText(content);
        adminclick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent adm = new Intent(Login.this, AdminLogin.class);
                startActivity(adm);
            }
        });

        TextView register = (TextView) findViewById(R.id.reg);
        TextView forgotpassword = (TextView) findViewById(R.id.forgot_password);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent regi = new Intent(Login.this, Register.class);
                startActivity(regi);
            }
        });

        forgotpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent forg = new Intent(Login.this, ForgotPassword.class);
                startActivity(forg);
            }
        });


        final EditText USEREMAIL, USERPASSWORD;
        USEREMAIL = (EditText) findViewById(R.id.usernametxt);
        USERPASSWORD = (EditText) findViewById(R.id.passtxt);

        Button login = (Button) findViewById(R.id.submitbtn);

        login.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                useremail = USEREMAIL.getText().toString();
                userpass = USERPASSWORD.getText().toString();

            helper=new DataHandler(getBaseContext());

                String storedPassword=helper.getLoginCredits(useremail);
                        if(userpass.equals(storedPassword))
                        {
                            Toast.makeText(getApplicationContext(),"Login Successful",Toast.LENGTH_LONG).show();
                            Intent intent=new Intent(Login.this,MainViewForm.class);
                            intent.putExtra("bharat",useremail);
                            startActivity(intent);
                        }
                        else
                        {
                            Toast.makeText(getApplicationContext(),"Login UnSuccessful",Toast.LENGTH_LONG).show();
                        }



            }
        });

        Button exit=(Button)findViewById(R.id.exit);

        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"Doublle Tap to Exit", Toast.LENGTH_LONG).show();
                finish();
                System.exit(0);
            }
        });
    }
}