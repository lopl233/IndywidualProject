package com.example.mikolaj.pi;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.wearable.activity.WearableActivity;
import android.widget.Button;
import android.widget.TextView;
import android.provider.Settings.Secure;
import android.widget.Toast;

import java.util.UUID;


public class ShowDeviceId extends WearableActivity {

    private TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_device_id);

        mTextView = (TextView) findViewById(R.id.textView);
        String device_id;
        device_id = Secure.getString(getApplicationContext().getContentResolver(), Secure.ANDROID_ID);
        mTextView.setText("Device_Id " + device_id);

        setAmbientEnabled();
    }

}
