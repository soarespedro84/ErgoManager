package com.example.ergomanager;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.text.InputType;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import Models.User;
import Models.UserHelper;
import Models.dbWrapperSetup;

public class LoginPin extends AppCompatActivity {
    //private static final String USERPINSP = "USERPIN_SP";
    //private static final String CONTENTORPREFS = "PREFS";
    //public static final String USER = "USER";
    TextView txtWelcome;
    ProgressBar loadingBar;
    TextView lblLogin;
    TextView lblHelp;
    Button btnLogin;
    User u;
    private SharedPreferences userSP;
    EditText myPIN;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_pin);
        ActionBar ab = getSupportActionBar();
        ab.hide();

        txtWelcome = (TextView) findViewById(R.id.txtWelcome);
        lblLogin = (TextView) findViewById(R.id.lblLogin);
        lblHelp = (TextView) findViewById(R.id.lblHelp);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        loadingBar = (ProgressBar) findViewById(R.id.loadingBar);
        myPIN = (EditText) findViewById(R.id.editTextNumberPassword);
        myPIN.setInputType(InputType.TYPE_CLASS_NUMBER);
        myPIN.setTransformationMethod(PasswordTransformationMethod.getInstance());
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);

        userSP = getApplicationContext().getSharedPreferences(UserCRUD.CONTENTORPREFS, MODE_PRIVATE);
        UserHelper uh = new UserHelper(this);
        u = uh.getUser(dbWrapperSetup.TBL_USER);
        txtWelcome.setText("Olá "+u.getName() + "!");

    }

    public void loginToMain(View view) {
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        String pinSP = userSP.getString(UserCRUD.USERPINSP, "!");
        String pinInserted = myPIN.getText().toString();
        String pinCode = SHA256(pinInserted);
        if (!pinCode.equals(pinSP)){
            myPIN.setError("PIN ERRADO");
            Toast.makeText(LoginPin.this, "PIN ERRADO" , Toast.LENGTH_SHORT).show();
            return;
        }else{
            loadingBar.setVisibility(View.VISIBLE);
            btnLogin.setVisibility(View.GONE);
            lblHelp.setVisibility(View.GONE);
            Toast.makeText(LoginPin.this, "PIN Correto" , Toast.LENGTH_SHORT).show();
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    startActivity(new Intent(LoginPin.this, MainActivity.class));
                    finish();
                }
            }, 1500);
        }
    }


    public String SHA256(String stringNormal) {
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("SHA256");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        try {
            md.update(stringNormal.getBytes("utf-8"), 0, stringNormal.length());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        byte[] hashBytesSha256 = md.digest();
        return converterParaHex(hashBytesSha256);
    }

    private String converterParaHex(byte[] data) {
        StringBuilder sb = new StringBuilder();
        for (byte b : data) {
            int byteCentral = (b >>> 4) & 0x0F;
            int metades = 0;
            do {
                sb.append((0 <= byteCentral) && (byteCentral <= 9) ? (char) ('0' + byteCentral) : (char) ('a' + (byteCentral - 10)));
                byteCentral = b & 0x0F;
            } while (metades++ < 1);
        }
        return sb.toString();
    }


  /*  public void resetPin(View view) {
        String newPIN = "654321";
        Intent i = new Intent(Intent.ACTION_SEND);
        i.setType("text/plain");
        i.putExtra(Intent.EXTRA_EMAIL , new String[]{u.getEmail()});
        i.putExtra(Intent.EXTRA_SUBJECT, "PIN Reset");
        i.putExtra(Intent.EXTRA_TEXT , newPIN);
        u.setPIN(newPIN);
        try {
            startActivity(Intent.createChooser(i, "Send mail..."));
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(LoginPin.this, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
        }
    }*/
}