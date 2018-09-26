package com.example.bharat.buslocator;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by bharat on 18/4/17.
 */

public class AdminLogin extends Activity {

    String useremail, userpass;
  //  Context ctx = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.adminlogin);

        final EditText USEREMAIL, USERPASSWORD;
        USEREMAIL = (EditText) findViewById(R.id.adminnametxt);
        USERPASSWORD = (EditText) findViewById(R.id.adminpasstxt);

        Button login = (Button) findViewById(R.id.adminsubmitbtn);

        login.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                useremail = USEREMAIL.getText().toString();
                userpass = USERPASSWORD.getText().toString();

                    if (useremail.equals("admin") && (userpass.equals("admin"))) {
                        Toast.makeText(getBaseContext(), "login successfull...", Toast.LENGTH_LONG).show();
                        Intent nxt = new Intent(getApplicationContext(), AdminViews.class);
                        startActivity(nxt);
                    }
                 else {
                    Toast.makeText(getBaseContext(), "Adminname and Password not currect.", Toast.LENGTH_LONG).show();
                }


            }
        });

        TextView forgotpassword = (TextView) findViewById(R.id.forgot_password);
        forgotpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent forg = new Intent(AdminLogin.this, ForgotPassword.class);
                startActivity(forg);
            }
        });


    }
}
