package com.example.bharat.buslocator;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.regex.Pattern;


/**
 * Created by bharat on 24/3/17.
 */

public class Register extends AppCompatActivity
{
    EditText etName, etEmail, etPassword, etCpassword, etPhone;
    String name, email, pass, cpass, phone;
    Button submit;
    DataHandler db;
    public final Pattern EMAIL_ADDRESS_PATTERN = Pattern.compile(
            "[a-zA-Z0-9+._%-]" +
                    "@" +
                    "[a-zA-Z0-9][a-zA-Z0-9-]{0,64}" +
                    "(" +
                    "." +
                    "[a-zA-Z0-9][a-zA-Z0-9-]{0,25}" +
                    ")+"
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);


        db=new DataHandler(getBaseContext());

        etName = (EditText) findViewById(R.id.edtName);
        etEmail = (EditText) findViewById(R.id.edtEmail);
        etPassword = (EditText) findViewById(R.id.edtPass);
        etCpassword = (EditText) findViewById(R.id.edtCpass);
        etPhone = (EditText) findViewById(R.id.edtPhone);
        submit = (Button) findViewById(R.id.submit);
        Insert();

    }

    private void Insert() {

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RegisterValidate();
                AddData();
            }
        });

    }

    private void AddData() {
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               String name=etName.getText().toString().trim();
                String mail=etEmail.getText().toString().trim();
                String password=etPassword.getText().toString().trim();
                String confirmP=etCpassword.getText().toString().trim();
                String mobile=etPhone.getText().toString().trim();
                db.InsertDataRe(name,mail,mobile,password,confirmP);
                Toast.makeText(getApplicationContext(),"Registred Successfully",Toast.LENGTH_LONG).show();
                etName.getText().clear();
                etEmail.getText().clear();
                etPassword.getText().clear();
                etCpassword.getText().clear();
                etPhone.getText().clear();
                Intent intent = new Intent(Register.this,Login.class);
                startActivity(intent);

            }
        });
    }

    public void RegisterValidate() {
        initialize();
        if (!validate()) {
            Toast.makeText(this, "Sign Up failed", Toast.LENGTH_SHORT).show();
        }
    }
    public void initialize() {
        name = etName.getText().toString().trim();
        email = etEmail.getText().toString().trim();
        pass = etPassword.getText().toString().trim();
        cpass = etCpassword.getText().toString().trim();
        phone = etPhone.getText().toString().trim();
    }

    public boolean validate() {
        boolean valid = true;
        if (name.isEmpty() || name.length() > 32) {
            etName.setError("Invalid Name");
            valid = false;
        }
        if (email.isEmpty() || checkEmail(email)) {
            etEmail.setError("Invalid email address");
            valid = false;
        }
        if (pass.isEmpty() || pass.length() < 4 || pass.length() > 32) {
            etPassword.setError("password length should more than four charecter");
            valid = false;
        }
        if (!(pass.equals(cpass))) {
            etCpassword.setError("Confirmation password not matched");
            etPassword.setText("");
            etCpassword.setText("");
            valid = false;
        }
        if (phone.isEmpty() && phone.length() > 10 && phone.length() < 10) {
            etPhone.setError("Invalid Phone number");
            valid = false;
        }
        return valid;

    }

    private boolean checkEmail(String email) {
        return EMAIL_ADDRESS_PATTERN.matcher(email).matches();
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
