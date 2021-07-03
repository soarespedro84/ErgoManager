package com.example.ergomanager;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import Models.User;
import Models.UserHelper;
import Models.dbWrapperSetup;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ActionBar ab = getSupportActionBar();
        ab.hide();
        UserHelper uh = new UserHelper(this);
        User u = uh.getUser(dbWrapperSetup.TBL_USER);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (u.getName().length()>0){
                    startActivity(new Intent(SplashActivity.this, LoginPin.class));
                }else {
                    startActivity(new Intent(SplashActivity.this, RGPDConsent.class));
                }
                finish();
            }
        }, 2000);
    }
}