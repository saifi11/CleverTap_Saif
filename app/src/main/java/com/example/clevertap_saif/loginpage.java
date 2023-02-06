package com.example.clevertap_saif;

import static com.clevertap.android.sdk.CleverTapAPI.createNotificationChannel;

import androidx.appcompat.app.AppCompatActivity;

import android.app.NotificationManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;

import com.clevertap.android.sdk.CleverTapAPI;

import java.util.Date;
import java.util.HashMap;

public class loginpage extends AppCompatActivity {


    // variables

    ImageView Login;
    EditText name , Identity , Number , Email ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //Channel Class
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createNotificationChannel(getApplicationContext(),"CleverTap","Saif","XYZ", NotificationManager.IMPORTANCE_MAX,true,"sound1.mp3");
        }
        //clevertap logs enable
        CleverTapAPI.setDebugLevel(3);

        CleverTapAPI clevertapDefaultInstance = CleverTapAPI.getDefaultInstance(getApplicationContext());
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loginpage);

        //variable define

        Login = findViewById(R.id.Loginbtn);
        name = findViewById(R.id.name);
        Identity = findViewById(R.id.identity);
        Number = findViewById(R.id.Phone_number);
        Email = findViewById(R.id.Email_id);

        //login but click

        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Name_ = name.getText().toString().trim();
                String Identity_ = Identity.getText().toString().trim();
                String Number_ = Number.getText().toString().trim();
                String Email_ = Email.getText().toString().trim();

                HashMap<String, Object> profileUpdate = new HashMap<String, Object>();
                profileUpdate.put("Name", Name_);    // String
                profileUpdate.put("Identity", Identity_);      // String or number
                profileUpdate.put("Email",Email_); // Email address of the user
                profileUpdate.put("Phone","+91"+Number_);   // Phone (with the country code, starting with +)
                profileUpdate.put("Gender", "M");             // Can be either M or F
                profileUpdate.put("DOB", new Date());         // Date of Birth. Set the Date object to the appropriate value first
                clevertapDefaultInstance.onUserLogin(profileUpdate);

                Intent intent = new Intent(getApplicationContext(),Summarypage.class);
                intent.putExtra("name",Name_);
                intent.putExtra("Identity",Identity_);
                intent.putExtra("Number",Number_);
                intent.putExtra("Email",Email_);
                startActivity(intent);

            }
        });






    }
}