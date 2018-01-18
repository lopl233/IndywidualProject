package com.example.mikolaj.pi;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.wearable.activity.WearableActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Timer;

public class MainActivity extends WearableActivity {

    private TextView mTextView;
    private LocationSchedulder locationSchedulder;
    private Timer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTextView = (TextView) findViewById(R.id.text);

        setAmbientEnabled();

        Button btn1 = (Button) findViewById(R.id.button2);
        btn1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Button btn1 = (Button) findViewById(R.id.button2);
                if(btn1.getText().equals("STOP")){
                    btn1.setText("START");
                    timer.cancel();
                    timer.purge();
                }
                else {btn1.setText("STOP");
                    timer = new Timer();
                    timer.schedule(new LocationSchedulder(getApplicationContext()),30000);

                }
            }
        });

        Button btn = (Button)findViewById(R.id.button3);

       btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast toast = Toast.makeText(MainActivity.this, Settings.Secure.getString(getApplicationContext().getContentResolver(), Settings.Secure.ANDROID_ID), Toast.LENGTH_LONG);
                ViewGroup group = (ViewGroup) toast.getView();
                TextView messageTextView = (TextView) group.getChildAt(0);
                messageTextView.setTextSize(18);
                toast.show();
                //startActivity(new Intent(MainActivity.this, ShowDeviceId.class));
            }
        });

        requestPermission(Manifest.permission.ACCESS_FINE_LOCATION,1);
        requestPermission(Manifest.permission.INTERNET,2);
        locationSchedulder = new LocationSchedulder(getApplicationContext());
        timer = new Timer();



    }
    public void requestPermission(String permission, int requestCode) {
        if (ContextCompat.checkSelfPermission(this,
                permission)
                != PackageManager.PERMISSION_GRANTED) {


            ActivityCompat.requestPermissions(this,
                    new String[]{permission},
                    requestCode);
        }
    }

}
