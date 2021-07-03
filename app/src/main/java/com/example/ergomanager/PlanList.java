package com.example.ergomanager;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import Models.Nordic;
import Models.User;

public class PlanList extends AppCompatActivity {
    public static final int LOWPAIN = 1;
    public static final int MEDIUMPAIN = 4;
    public static final int HIGHPAIN = 7;
    Nordic nn;
    TextView lblNeck, lblShoulder, lblUpBack, lblElbowHand, lblLowBack, lblHip, lblKneeFeet;
    ImageView imgNeck1, imgNeck2, imgNeck3, imgShoulder1, imgShoulder2, imgShoulder3, imgUpBack1, imgUpBack2, imgUpBack3;
    ImageView imgElbowHand1, imgElbowHand2, imgElbowHand3, imgLowBack1, imgLowBack2, imgLowBack3;
    ImageView imgHip1, imgHip2, imgHip3, imgKneeFeet1, imgKneeFeet2, imgKneeFeet3;

public void setPointers(){
    lblNeck = (TextView) findViewById(R.id.lblNeck);
    lblShoulder = (TextView) findViewById(R.id.lblShoulder);
    lblUpBack = (TextView) findViewById(R.id.lblUpBack);
    lblElbowHand = (TextView) findViewById(R.id.lblWristHand);
    lblLowBack = (TextView) findViewById(R.id.lblLowBack);
    lblHip = (TextView) findViewById(R.id.lblHip);
    lblKneeFeet = (TextView) findViewById(R.id.lblAnkFeet);

    imgNeck1 = (ImageView) findViewById(R.id.neck1);
    imgNeck2 = (ImageView) findViewById(R.id.neck2);
    imgNeck3 = (ImageView) findViewById(R.id.neck3);
    imgShoulder1 = (ImageView) findViewById(R.id.shoulder1);
    imgShoulder2 = (ImageView) findViewById(R.id.shoulder2);
    imgShoulder3 = (ImageView) findViewById(R.id.shoulder3);
    imgUpBack1 = (ImageView) findViewById(R.id.upback1);
    imgUpBack2 = (ImageView) findViewById(R.id.upback2);
    imgUpBack3 = (ImageView) findViewById(R.id.upback3);
    imgElbowHand1 = (ImageView) findViewById(R.id.ms1);
    imgElbowHand2 = (ImageView) findViewById(R.id.ms2);
    imgElbowHand3 = (ImageView) findViewById(R.id.ms3);
    imgLowBack1 = (ImageView) findViewById(R.id.lowback1);
    imgLowBack2 = (ImageView) findViewById(R.id.lowback2);
    imgLowBack3 = (ImageView) findViewById(R.id.lowback3);
    imgHip1 = (ImageView) findViewById(R.id.hip1);
    imgHip2 = (ImageView) findViewById(R.id.hip2);
    imgHip3 = (ImageView) findViewById(R.id.hip3);
    imgKneeFeet1 = (ImageView) findViewById(R.id.mi1);
    imgKneeFeet2 = (ImageView) findViewById(R.id.mi2);
    imgKneeFeet3 = (ImageView) findViewById(R.id.mi3);
}

public void setPlan(Nordic n){
    if (n.getNeck()>LOWPAIN) {
        lblNeck.setVisibility(View.VISIBLE);
        imgNeck1.setVisibility(View.VISIBLE);
    }
    if (n.getNeck()>MEDIUMPAIN) imgNeck2.setVisibility(View.VISIBLE);
    if (n.getNeck()>HIGHPAIN) imgNeck3.setVisibility(View.VISIBLE);
    if (n.getShoulder()>LOWPAIN) {
        lblShoulder.setVisibility(View.VISIBLE);
        imgShoulder1.setVisibility(View.VISIBLE);
    }
    if (n.getShoulder()>MEDIUMPAIN) imgShoulder2.setVisibility(View.VISIBLE);
    if (n.getShoulder()>HIGHPAIN) imgShoulder3.setVisibility(View.VISIBLE);
    if (n.getUpBack()>LOWPAIN){
        lblUpBack.setVisibility(View.VISIBLE);
        imgUpBack1.setVisibility(View.VISIBLE);
    }
    if (n.getUpBack()>MEDIUMPAIN) imgUpBack2.setVisibility(View.VISIBLE);
    if (n.getUpBack()>HIGHPAIN) imgUpBack3.setVisibility(View.VISIBLE);
    if (n.getElbow()>LOWPAIN || n.getWrstHand()>LOWPAIN){
        lblElbowHand.setVisibility(View.VISIBLE);
        imgElbowHand1.setVisibility(View.VISIBLE);
    }
    if (n.getElbow()>MEDIUMPAIN || n.getWrstHand()>MEDIUMPAIN) imgElbowHand2.setVisibility(View.VISIBLE);
    if (n.getElbow()>HIGHPAIN || n.getWrstHand()>MEDIUMPAIN) imgElbowHand3.setVisibility(View.VISIBLE);
    if (n.getLowBack()>LOWPAIN){
        lblLowBack.setVisibility(View.VISIBLE);
        imgLowBack1.setVisibility(View.VISIBLE);
    }
    if (n.getLowBack()>MEDIUMPAIN) imgLowBack2.setVisibility(View.VISIBLE);
    if (n.getLowBack()>HIGHPAIN) imgLowBack3.setVisibility(View.VISIBLE);
    if (n.getHip()>LOWPAIN){
        imgHip1.setVisibility(View.VISIBLE);
        lblHip.setVisibility(View.VISIBLE);
    }
    if (n.getHip()>MEDIUMPAIN) imgHip2.setVisibility(View.VISIBLE);
    if (n.getHip()>HIGHPAIN) imgHip3.setVisibility(View.VISIBLE);
    if (n.getKnee()>LOWPAIN || n.getAnkFeet()>LOWPAIN){
        lblKneeFeet.setVisibility(View.VISIBLE);
        imgKneeFeet1.setVisibility(View.VISIBLE);
    }
    if (n.getKnee()>MEDIUMPAIN || n.getAnkFeet()>MEDIUMPAIN) imgKneeFeet2.setVisibility(View.VISIBLE);
    if (n.getKnee()>HIGHPAIN || n.getAnkFeet()>MEDIUMPAIN) imgKneeFeet3.setVisibility(View.VISIBLE);
}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan_list);
        setActionBarLook();
        setPointers();

        Intent iNordic = getIntent();
        nn = (Nordic) iNordic.getSerializableExtra(MainActivity.OP_PLAN_LIST);
        if (nn != null){
            setPlan(nn);
        }
    }

    public void setActionBarLook(){
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setIcon(R.mipmap.ic_be_launcher_round);
        actionBar.setTitle("");
    }
    public void backToMain(View view) {
        this.finish();
    }

}