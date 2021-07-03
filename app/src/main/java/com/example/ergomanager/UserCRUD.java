package com.example.ergomanager;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import Models.Nordic;
import Models.NordicHelper;
import Models.User;
import Models.UserHelper;

public class UserCRUD extends AppCompatActivity {

    EditText tName, tEmail,tBirthDt, tHeight, tWeight, tPIN;
    RadioButton radioButtonM, radioButtonF;
    Button btnNewUser;
    DatePickerDialog picker;
    TextView valActive, lblPin;
    SeekBar barActive;
    FloatingActionButton fabSave, fabEdit;
    Switch swSmoker;

    User u;

    public static final int MAXPIN = 6;
    public static final String USERPINSP = "USERPIN_SP";
    public static final String USER = "USER";
    public static final String USERTOKEN = "TOKENCONTA";
    public static final String CONTENTORPREFS = "dicionarioUtilizador";

    private SharedPreferences userSP;

    static boolean insertMode = true;
    static boolean editPIN = false;
    public void setActionBarLook(){
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setIcon(R.mipmap.ic_be_launcher_round);
        actionBar.setTitle("");
    }
    public void  pointers(){
        tName = (EditText) findViewById(R.id.txtName);
        tEmail = (EditText) findViewById(R.id.txtEmail);
        tPIN = (EditText) findViewById(R.id.txtPIN);
        lblPin = (TextView) findViewById(R.id.lblPIN);
        tBirthDt = (EditText) findViewById(R.id.txtBirthDt);
        tHeight = (EditText) findViewById(R.id.txtHeight);
        tWeight = (EditText) findViewById(R.id.txtWeight);
        radioButtonF = (RadioButton) findViewById(R.id.rbFem);
        radioButtonM = (RadioButton) findViewById(R.id.rbMasc);
        btnNewUser = (Button) findViewById(R.id.btnCreate);
        swSmoker = (Switch) findViewById(R.id.swSmoker);
        valActive = (TextView) findViewById(R.id.valActive);
        barActive = (SeekBar) findViewById(R.id.barActive);
        fabSave = (FloatingActionButton) findViewById(R.id.floatingActionButtonSave);
        fabEdit = (FloatingActionButton) findViewById(R.id.floatingActionButtonEdit);
    }

    public void getBirthdayDate(){
        //codificação do Picker da Data
        tBirthDt.setInputType(InputType.TYPE_NULL);

        //o que fazer ao clicar no campo da data
        tBirthDt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //criar o calendário
                Calendar cal = Calendar.getInstance();
                int day = cal.get(Calendar.DAY_OF_MONTH);
                int month = cal.get(Calendar.MONTH);
                int year = cal.get(Calendar.YEAR);
                // instanciar a caixa de diálogo
                picker = new DatePickerDialog(UserCRUD.this,
                        new DatePickerDialog.OnDateSetListener() {
                            //o que fazer quando escolher a data
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                tBirthDt.setText(year + "/" + (monthOfYear + 1) + "/" + dayOfMonth);
                            }
                        }, year, month, day);
                picker.getDatePicker().setMaxDate(System.currentTimeMillis() - 1000);
                picker.show();
            }
        });
    }
    public void activeSeekBarSet(){
        barActive.setMax(7);
        barActive.setProgress(0);
        valActive.setText(String.valueOf(barActive.getProgress()) + " x/semana");
        barActive.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int progressVal = 0;
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                progressVal=progress;
                valActive.setText(progressVal + " x/semana");

            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                //n.setActive(progressVal);
            }
        });
        swSmoker.setEnabled(false);
        barActive.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_crud);
        setActionBarLook();
        userSP = getSharedPreferences(CONTENTORPREFS, MODE_PRIVATE);
        Intent mode = getIntent();
        String option = mode.getStringExtra(MainActivity.OP_NEWUSER);
        u = (User) mode.getSerializableExtra(MainActivity.OP_UPDATEUSER);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        pointers();
        getBirthdayDate();
        activeSeekBarSet();

        if (option != null && option.equals("0")){
            enableEditMode();
            enableEditPIN();
            insertMode= true;
        }
        if (u != null){
            showDataInForm();
            insertMode= false;
        }
    }

    public void backToMain(View view) {

        this.finish();
    }
    public void showDataInForm(){
        btnNewUser.setText(R.string.btn_update);
        tName.setText(u.getName());
        tPIN.setText(u.getPIN());
        tEmail.setText(u.getEmail());
        tBirthDt.setText(u.getBirthDate());
        tHeight.setText(String.valueOf(u.getHeight()));
        tWeight.setText(String.valueOf(u.getWeight()));
        if ((u.getGenre().equals(radioButtonM.getText().toString()))) {
            radioButtonM.setChecked(true);
        } else {
            radioButtonF.setChecked(true);
        }
        if ((u.isSmoker().equals("Fumador"))) {
            swSmoker.setChecked(true);
        } else {
            swSmoker.setChecked(false);
        }
        barActive.setProgress(u.getActive());
    }
    public void enableEditPIN(){
    tPIN.setEnabled(true);
    tPIN.setText("");
    btnNewUser.setEnabled(true);
        fabEdit.setVisibility(View.GONE);
        fabSave.setVisibility(View.VISIBLE);
        tPIN.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }

            @Override
            public void afterTextChanged(Editable s) {
                if (!(tPIN.getText().toString().trim().length() == 6)){
                    tPIN.setError("O PIN deve ter 6 dígitos");
                }
            }
        });
}
    public void enableEditMode(){
        fabEdit.setVisibility(View.GONE);
        fabSave.setVisibility(View.VISIBLE);
        tName.setEnabled(true);
        tEmail.setEnabled(true);
        tBirthDt.setEnabled(true);
        tBirthDt.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                tBirthDt.setInputType(InputType.TYPE_NULL);
            }
        });
        tWeight.setEnabled(true);
        tHeight.setEnabled(true);
        radioButtonF.setEnabled(true);
        radioButtonM.setEnabled(true);
        btnNewUser.setEnabled(true);
        swSmoker.setEnabled(true);
        barActive.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return false;
            }
        });
        tEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!tEmail.getText().toString().contains("@") || !tEmail.getText().toString().contains(".")){
                    tEmail.setError("exemplo@exemplo.com");
                }
            }
        });

    }

    public void manageRecord(View view) {
        boolean result = false;
        SharedPreferences.Editor editSP = userSP.edit();
        //editSP.clear();

        String sName = tName.getText().toString();
        String sEmail = tEmail.getText().toString();
        String sPIN = tPIN.getText().toString();

        if (sPIN.length()>MAXPIN) sPIN=sPIN.substring(0, MAXPIN);
        String sBirthDate = tBirthDt.getText().toString();
        String sHeight = tHeight.getText().toString();
        String sWeight = tWeight.getText().toString();
        if (radioButtonM.isChecked()) radioButtonF.setChecked(false);
        if (radioButtonF.isChecked()) radioButtonM.setChecked(false);
        String sGenre = (radioButtonM.isChecked()) ? radioButtonM.getText().toString() : radioButtonF.getText().toString();
        String iSmoker = (swSmoker.isChecked()) ? "Fumador":"Não fumador";
        int iActive = barActive.getProgress();
        if (sName.length()>0 && sEmail.length()>0 && sPIN.length()>0 && sBirthDate.length()>0 && sHeight.length()>0 && sWeight.length()>0 && sGenre.length()>0){
            UserHelper userHelper = new UserHelper(this);
            if (insertMode){
                u = new User();
                u.setName(sName);
                if (!(u.isValidEmail(sEmail))){
                    tEmail.setError("Email inválido");
                    return;
                }else{
                    u.setEmail(sEmail);
                }
                if (sPIN.length()!=6){
                    Toast.makeText(this, "O PIN deve ter 6 dígitos", Toast.LENGTH_SHORT).show();
                    return;
                }else {
                    u.setPIN(SHA256(sPIN));
                }
                u.setBirthDate(sBirthDate);
                u.setHeight(Double.parseDouble(sHeight));
                u.setWeight(Double.parseDouble(sWeight));
                u.setIMC(u.getHeight(), u.getWeight());
                u.setGenre(sGenre);
                u.setAge(sBirthDate);
                u.setSmoker(iSmoker);
                u.setActive(iActive);

                result = userHelper.add(u);

                if (result){
                    Toast.makeText(this, "Utilizador criado", Toast.LENGTH_SHORT).show();
                    editSP.putString(USERPINSP, u.getPIN());
                    editSP.putString(USER, u.getName());
                    editSP.putString(USERTOKEN, UUID.randomUUID().toString());
                    editSP.apply();
                }
            }else{
                u.setName(sName);
                if (u.isValidEmail(sEmail)){
                    u.setEmail(sEmail);
                }else{
                    tEmail.setError("Email inválido");
                }
                if (sPIN.length()!=6){
                    Toast.makeText(this, "O PIN deve ter 6 dígitos", Toast.LENGTH_SHORT).show();
                    return;
                }else if (editPIN){
                    u.setPIN(SHA256(sPIN));
                    editSP.putString(USERPINSP, u.getPIN());
                    editSP.apply();
                }else {
                    u.setPIN(sPIN);
                }
                u.setBirthDate(sBirthDate);
                u.setHeight(Double.parseDouble(sHeight));
                u.setWeight(Double.parseDouble(sWeight));
                u.setIMC(u.getHeight(), u.getWeight());
                u.setGenre(sGenre);
                u.setAge(sBirthDate);
                u.setSmoker(iSmoker);
                u.setActive(iActive);

                result = userHelper.updateUser(u);
                if (result){
                    Toast.makeText(this, "Utilizador atualizado", Toast.LENGTH_SHORT).show();
                }
            }
            this.finish();
        }else{
            Toast.makeText(this, "Todos os campos devem ser preenchidos  ", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public boolean onCreateOptionsMenu (Menu menu){
        //carregar o menu com o conteúdo
        getMenuInflater().inflate(R.menu.menucrud, menu);
        //colocar o icon branco
        for(int i = 0; i < menu.size(); i++){
            Drawable drawable = menu.getItem(i).getIcon();
            if(drawable != null) {
                drawable.mutate();
                drawable.setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);
            }
        }
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        invalidateOptionsMenu();
        menu.findItem(R.id.it_edit).setVisible(false);
        menu.findItem(R.id.it_delete).setVisible(false);
        menu.findItem(R.id.it_editPIN).setEnabled(!insertMode);

        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();
        switch (id){
            /*case R.id.it_edit:
                enableEditMode();
                return true;

             */

            case R.id.it_editPIN:
                enableEditPIN();
                editPIN=true;
                return true;

            default:
                //obrigatorio
                return super.onOptionsItemSelected(item);
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

    public void enableUserEditMode(View view) {
        enableEditMode();
    }
}