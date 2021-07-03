package com.example.ergomanager;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

public class About extends AppCompatActivity {
    RatingBar ratingbar;
    Button btnSubmit;
    Button btnSend;
    TextView txtAskRating;
    TextView txtContact;
    EditText contactform;
    private SharedPreferences userSP;
    public static final String RATING = "RATING";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        setActionBarLook();
        Intent mode = getIntent();
        String option = mode.getStringExtra(MainActivity.OP_ABOUT);
        ratingbar=(RatingBar)findViewById(R.id.ratingBar);
        txtAskRating = (TextView) findViewById(R.id.txtRateTitle);
        txtContact= (TextView) findViewById(R.id.txtContact);
        contactform= (EditText) findViewById(R.id.contactform);
        userSP = getApplicationContext().getSharedPreferences(UserCRUD.CONTENTORPREFS, MODE_PRIVATE);
        //String lastRating = userSP.getString(RATING, "!");
        if (option != null && option.equals("0")){
            addListenerOnButtonClick();
        }

    }
    public void setActionBarLook(){
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setIcon(R.mipmap.ic_be_launcher_round);
        actionBar.setTitle("");
    }

    public void addListenerOnButtonClick(){
        btnSubmit =(Button)findViewById(R.id.btnSubmit);
        btnSend =(Button)findViewById(R.id.btnSend);
        SharedPreferences.Editor editSP = userSP.edit();
        //Performing action on Button Click
        btnSubmit.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View arg0) {
                //Getting the rating and displaying it on the toast
                String rating=String.valueOf(ratingbar.getRating());
                editSP.putString(RATING, rating);
                editSP.apply();
                String feedback = "Obrigado pela sua avaliação!";
                Toast.makeText(getApplicationContext(), feedback, Toast.LENGTH_LONG).show();
            }
        });
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String adress = "pedro.soares.735@my.istec.pt";
                String subject= "Contacto App Be-Ergo";
                composeEmail(adress, subject);
            }
        });
    }
    public void composeEmail(String address, String subject) {
        Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                "mailto",address, null));
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, subject);
        emailIntent.putExtra(Intent.EXTRA_TEXT, contactform.getText().toString());
        startActivity(Intent.createChooser(emailIntent, "Send email..."));
    }
}