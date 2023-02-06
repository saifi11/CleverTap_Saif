package com.example.clevertap_saif;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.clevertap.android.pushtemplates.TemplateRenderer;
import com.clevertap.android.sdk.CTInboxListener;
import com.clevertap.android.sdk.CleverTapAPI;
import com.clevertap.android.sdk.InboxMessageButtonListener;
import com.clevertap.android.sdk.InboxMessageListener;
import com.clevertap.android.sdk.inbox.CTInboxMessage;
import com.clevertap.android.sdk.interfaces.NotificationHandler;
import com.clevertap.android.pushtemplates.PushTemplateNotificationHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;
import java.util.HashMap;

//

public class Summarypage extends AppCompatActivity implements CTInboxListener  , InboxMessageListener{

    TextView Name , Email , Identity , Phone ;
    ImageView Event , profilepush , send_push , inAPP , inBox;
    EditText CTID_;

    String CTID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CleverTapAPI clevertapDefaultInstance = CleverTapAPI.getDefaultInstance(getApplicationContext());


        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_summarypage);

        CleverTapAPI.setNotificationHandler((NotificationHandler)new PushTemplateNotificationHandler());
        TemplateRenderer.setDebugLevel(3);




        // App INBOX
        //Set the Notification Inbox Listener
       // clevertapDefaultInstance.setCTInboxMessageListener(this);
        clevertapDefaultInstance.setCTNotificationInboxListener(this);


        //Initialize the inbox and wait for callbacks on overridden methods
        clevertapDefaultInstance.initializeInbox();

        Name = findViewById(R.id.namesu);
        Email = findViewById(R.id.emailsu);
        Identity = findViewById(R.id.identitysu);
        Phone = findViewById(R.id.phonenosu);
        Event = findViewById(R.id.Event);
        profilepush = findViewById(R.id.profilepush);
        send_push = findViewById(R.id.notification_push);
        inAPP = findViewById(R.id.in_app);
        inBox = findViewById(R.id.in_box);

        CTID_ = findViewById(R.id.CTID);

        // Clevertap CT Id
        CTID = clevertapDefaultInstance.getCleverTapID();

        Name.setText(getIntent().getStringExtra("name"));
        Email.setText(getIntent().getStringExtra("Email"));
        Identity.setText(getIntent().getStringExtra("Identity"));
        Phone.setText(getIntent().getStringExtra("Number"));

        CTID_.setText(CTID);

        //Event page
        Event.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Eventpage.class);
                startActivity(intent);
                Toast.makeText(Summarypage.this, "Event Page", Toast.LENGTH_SHORT).show();
            }
        });

        //profile push btn
        profilepush.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                String Name_ = Name.getText().toString();
//                String Email_ = Email.getText().toString();
//                String Identity_ = Identity.getText().toString();
//                String Phone_ = Phone.getText().toString();


                HashMap<String, Object> profileUpdate = new HashMap<String, Object>();
                profileUpdate.put("Name", "Saif");                  // String
                profileUpdate.put("Identity", "330180");                    // String or number
                profileUpdate.put("Email", "Saifullah@gmail.com");               // Email address of the user
                profileUpdate.put("Phone", "0987654321");                 // Phone (with the country code, starting with +
                profileUpdate.put("DOB", new Date());                    // Date of Birth. Set the Date object to the appropriate value first
                clevertapDefaultInstance.pushProfile(profileUpdate);

            }
        });

        // Push Notification

        send_push.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clevertapDefaultInstance.pushEvent("Product Viewed");
                Toast.makeText(getApplicationContext(), "PUSH", Toast.LENGTH_SHORT).show();
            }
        });

        // IN App
        inAPP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                CleverTapAPI.setDebugLevel(CleverTapAPI.LogLevel.DEBUG);
                // IN APP
                clevertapDefaultInstance.getDefaultInstance(getApplicationContext()).suspendInAppNotifications();
                Toast.makeText(getApplicationContext(), "In App", Toast.LENGTH_SHORT).show();
                clevertapDefaultInstance.pushEvent("InApp");
            }
        });

        // inbox

        inBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clevertapDefaultInstance.pushEvent("InBOX");
                Toast.makeText(Summarypage.this, "IN BOX", Toast.LENGTH_SHORT).show();
                clevertapDefaultInstance.showAppInbox();
            }
        });


    }
//    @Override
//    public  void onInboxButtonClick( HashMap<String, String> payload) {
//        /*for (int i = 0 ; i < payload.size();i++){
//            payload.get(payload.keySet()[i])
//        }*/
//        Log.d("Saif ---- ", payload.keySet().toString());
//    }



    @Override
    public void inboxDidInitialize() {

    }

    @Override
    public void inboxMessagesDidUpdate() {

    }


    @Override
    public void onInboxItemClicked(CTInboxMessage message) {
        try {
        JSONArray array = new JSONArray();
            array = message.getData().getJSONObject("msg").getJSONArray("content");
            String url = ((JSONObject)array.get(0)).getJSONObject("action").getJSONObject("url").getJSONObject("android").get("text").toString();
            Log.d("Saif -----", url);

            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(url));
            startActivity(i);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.d("saif---","Deeplink"+message.getData());
    }

}