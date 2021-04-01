package com.example.apk_loader;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void testFunction(){
        Intent intent = getPackageManager().getLaunchIntentForPackage("com.example.apkloader");
        startActivity(intent);
    }
}