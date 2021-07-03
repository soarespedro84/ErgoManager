package com.example.ergomanager;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

public class RGPDConsent extends AppCompatActivity {
    public static final String PRIVACYPROTECTION = "PRIVACYPROTECTION";
    public static final String NOTIFICATIONS = "NOTIFICATIONS";
    private SharedPreferences userSP;
    TextView txtRGPDTitle;
    TextView txtRGPDMain;
    CheckBox chkPrivacy;
    CheckBox chkNotifications;
    Button btnSaveRGPD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rgpdconsent);
        ActionBar ab = getSupportActionBar();
        ab.hide();
        userSP = getApplicationContext().getSharedPreferences(UserCRUD.CONTENTORPREFS, MODE_PRIVATE);
        txtRGPDTitle = (TextView) findViewById(R.id.txtRGPDTitle);
        txtRGPDMain = (TextView) findViewById(R.id.txtRGPDMain);
        chkPrivacy = (CheckBox) findViewById(R.id.checkBoxPrivacy);
        chkNotifications = (CheckBox) findViewById(R.id.checkBoxNotifications);
        btnSaveRGPD = (Button) findViewById(R.id.btnSaveRGPD);
        enableSaveButton();

    }

    public void enableSaveButton(){

        chkPrivacy.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                switch (buttonView.getId()){
                    case R.id.checkBoxPrivacy:
                        if(isChecked) btnSaveRGPD.setEnabled(true);
                        return;
                }
            }
        });
    }

    public String acceptPrivacy(){
        return (chkPrivacy.isChecked()) ? "1" : "0";
    }

    private String acceptNotifications() {
        return (chkNotifications.isChecked()) ? "1" : "0";
    }

    public void saveRGPD(View view) {
        SharedPreferences.Editor editSP = userSP.edit();
        editSP.clear();
        if (chkPrivacy.isChecked() ){
            editSP.putString(PRIVACYPROTECTION, acceptPrivacy());
        }else {
            chkPrivacy.setError("Caso não consinta com a nossa Política de Privacidade e Proteção de Dados, por favor desinstale a aplicação ");
        }
        editSP.putString(NOTIFICATIONS, acceptNotifications());
        editSP.apply();
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(RGPDConsent.this, MainActivity.class));
                finish();
            }
        }, 1000);
    }
}