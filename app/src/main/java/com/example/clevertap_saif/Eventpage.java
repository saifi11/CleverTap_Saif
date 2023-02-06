package com.example.clevertap_saif;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;

import com.clevertap.android.sdk.CleverTapAPI;

public class Eventpage extends AppCompatActivity {

    EditText Event;
    ImageView raiseevent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CleverTapAPI clevertapDefaultInstance = CleverTapAPI.getDefaultInstance(getApplicationContext());

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_eventpage);

        Event= findViewById(R.id.EventR);
        raiseevent = findViewById(R.id.raise_event);
        raiseevent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Event_ = Event.getText().toString().trim();
                clevertapDefaultInstance.pushEvent(Event_);
            }
        });
    }
}