package com.example.gitaapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;

import android.Manifest;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gitaapplication.Data.Users;

public class SettingsActivity extends AppCompatActivity {

    TextView mobSMSTV,emailSENDTV;
    Button sendEmailButton, sendSMSButton;
    EditText smsTextET;
    String smsText,smsNum;
    

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        mobSMSTV = findViewById(R.id.smsNumTV);
        emailSENDTV = findViewById(R.id.emailSendingTV);

        sendEmailButton = findViewById(R.id.sendEmailButton);
        sendSMSButton = findViewById(R.id.smsSendButton);
        smsTextET = findViewById(R.id.smsTextTV);
        
        Users usersData = (Users) getIntent().getSerializableExtra("users");
        getInfo(usersData);

        
        
        sendEmailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent email = new Intent(Intent.ACTION_SEND);
                email.putExtra(Intent.EXTRA_EMAIL, emailSENDTV.getText());
                email.setType("message/rfc822");
                startActivity(Intent.createChooser(email, "Choose an Email client :"));
            }
        });

        ActivityCompat.requestPermissions(SettingsActivity.this,
                new String[]{Manifest.permission.SEND_SMS},
                1);

        sendSMSButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                smsText = smsTextET.getText().toString();
                smsNum = mobSMSTV.getText().toString();
                SmsManager sm = SmsManager.getDefault();

                sm.sendTextMessage(smsNum,null,smsText,null, null);

                Toast.makeText(SettingsActivity.this, "SMS Successfully Sent! ", Toast.LENGTH_SHORT).show();

                NotificationManager notificationManager = (NotificationManager)
                        SettingsActivity.this.getSystemService(Context.NOTIFICATION_SERVICE);

                int notificationId = 1;
                String channelId = "channel-01";
                String channelName = "Channel Name";
                int importance = NotificationManager.IMPORTANCE_HIGH;

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

                    NotificationChannel mChannel = new NotificationChannel(
                            channelId, channelName, importance);

                    notificationManager.createNotificationChannel(mChannel);
                }

                NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(SettingsActivity.this, channelId)
                        .setSmallIcon(R.drawable.ic_smile)
                        .setContentTitle("SMS")
                        .setContentText("Your SMS Sent Successfully.! ");

                Intent intent = new Intent(SettingsActivity.this, SettingsActivity.class);

                PendingIntent pendingIntent = PendingIntent.getActivity(SettingsActivity.this,
                        0, intent, PendingIntent.FLAG_MUTABLE);

                mBuilder.setContentIntent(pendingIntent);

                notificationManager.notify(notificationId, mBuilder.build());




            }
        });
    }
    
    public void getInfo(Users users){
            emailSENDTV.setText(users.getEmail());
            mobSMSTV.setText(users.getMobNum());
            
        }
    }
