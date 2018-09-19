package com.codesndata.androidapps.zalego;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {


    //Declare data fields
    private EditText firstname_txt;
    private EditText lastname_txt;
    private EditText username_txt;
    private EditText password_txt;

    String fname, lname, uname, pword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //Connect Java fields to XML fields
        firstname_txt= findViewById(R.id.firstname);
        lastname_txt = findViewById(R.id.lastname);
        username_txt = findViewById(R.id.username);
        password_txt = findViewById(R.id.password);


        Button quitButton = findViewById(R.id.toLogin_button);
        quitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent quit = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(quit);
            }
        });

    }

        public void attemptReg() {

            // Store values at the time of the registration attempt.
            fname = firstname_txt.getText().toString();
            lname = lastname_txt.getText().toString();
            uname = username_txt.getText().toString();
            pword = password_txt.getText().toString();

            String method = "register";

            BgSync bgTask = new BgSync();
            bgTask.execute(method, fname, lname, uname, pword);
            finish();

        }


        public void popUp (View view){
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setMessage("Submit data to Zalego?");
            alertDialogBuilder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface arg0, int arg1) {

                    try {

                        try {
                            Toast.makeText(RegisterActivity.this, "Submitting data for registration...", Toast.LENGTH_LONG).show();
                            attemptReg();
                        } catch (Exception x) {
                            finish();
                        }
                        supportFinishAfterTransition();
                    } catch (Exception e) {
                        finish();
                    }

                }
            });
            alertDialogBuilder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Toast.makeText(RegisterActivity.this, "Data NOT Submitted!", Toast.LENGTH_LONG).show();
                    finish();
                }
            });

            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();

        }
}
