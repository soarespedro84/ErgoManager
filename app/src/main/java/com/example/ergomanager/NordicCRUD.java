package com.example.ergomanager;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import Models.Nordic;
import Models.NordicHelper;
import Models.User;
import Models.UserHelper;
import Models.dbWrapperSetup;

public class NordicCRUD extends AppCompatActivity {

        private SeekBar barNeck, barShoudler, barUpBack, barElbow, barWristHand, barLowBack, barHip, barKnee, barAnkFeet;
        private TextView labelNeck, labelShoulder, labelUpBack, labelElbow, labelWristHand, labelLowBack, labelHip, labelKnee, labelAnkFeet, lblDateToday;
        private TextView valNeck, valShoudler, valUpBack, valElbow, valWristHand, valLowBack, valHip, valKnee, valAnkFeet;
        Nordic n;
        User u;
        NordicHelper nh = new NordicHelper(this);
        public static List<Nordic> _listToShow;

        static boolean insertMode = true;
        Button btnCreate;
        private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");

        public void setPointers(){
            btnCreate = (Button) findViewById(R.id.btnAddNordic);

            barNeck = (SeekBar) findViewById(R.id.barNeck);
            barShoudler = (SeekBar) findViewById(R.id.barShoulder);
            barUpBack = (SeekBar) findViewById(R.id.barUpBack);
            barElbow = (SeekBar) findViewById(R.id.barElbow);
            barWristHand = (SeekBar) findViewById(R.id.barWristHand);
            barLowBack = (SeekBar) findViewById(R.id.barLowBack);
            barHip = (SeekBar) findViewById(R.id.barHip);
            barKnee = (SeekBar) findViewById(R.id.barKnee);
            barAnkFeet = (SeekBar) findViewById(R.id.barAnkFeet);
            lblDateToday = (TextView) findViewById(R.id.lblDateToday);
            labelNeck = (TextView) findViewById(R.id.lblNeck);
            labelShoulder = (TextView) findViewById(R.id.lblShoulder);
            labelUpBack = (TextView) findViewById(R.id.lblUpBack);
            labelElbow = (TextView) findViewById(R.id.lblElbow);
            labelWristHand = (TextView) findViewById(R.id.lblWristHand);
            labelLowBack = (TextView) findViewById(R.id.lblLowBack);
            labelHip = (TextView) findViewById(R.id.lblHip);
            labelKnee = (TextView) findViewById(R.id.lblKnee);
            labelAnkFeet = (TextView) findViewById(R.id.lblAnkFeet);

            valNeck = (TextView) findViewById(R.id.valNeck);
            valShoudler = (TextView) findViewById(R.id.valShoulder);
            valUpBack = (TextView) findViewById(R.id.valUpBack);
            valElbow = (TextView) findViewById(R.id.valElbow);
            valWristHand = (TextView) findViewById(R.id.valWristHand);
            valLowBack = (TextView) findViewById(R.id.valLowBack);
            valHip = (TextView) findViewById(R.id.valHip);
            valKnee = (TextView) findViewById(R.id.valKnee);
            valAnkFeet = (TextView) findViewById(R.id.valAnkFeet);
        }

        public void setBarValues(){
            barNeck.setMax(10);
            barNeck.setProgress(0);
            barShoudler.setMax(10);
            barShoudler.setProgress(0);
            barUpBack.setMax(10);
            barUpBack.setProgress(0);
            barElbow.setMax(10);
            barElbow.setProgress(0);
            barWristHand.setMax(10);
            barWristHand.setProgress(0);
            barLowBack.setMax(10);
            barLowBack.setProgress(0);
            barHip.setMax(10);
            barHip.setProgress(0);
            barKnee.setMax(10);
            barKnee.setProgress(0);
            barAnkFeet.setMax(10);
            barAnkFeet.setProgress(0);
        }

        public void setBarText(){
            valNeck.setText(barNeck.getProgress() + " / " + barNeck.getMax());
            valShoudler.setText(barShoudler.getProgress() + " / " + barShoudler.getMax());
            valUpBack.setText(barUpBack.getProgress() + " / " + barUpBack.getMax());
            valElbow.setText(barElbow.getProgress() + " / " + barElbow.getMax());
            valWristHand.setText(barWristHand.getProgress() + " / " + barWristHand.getMax());
            valLowBack.setText(barLowBack.getProgress() + " / " + barLowBack.getMax());
            valHip.setText(barHip.getProgress() + " / " + barHip.getMax());
            valKnee.setText(barKnee.getProgress() + " / " + barKnee.getMax());
            valAnkFeet.setText(barAnkFeet.getProgress() + " / " + barAnkFeet.getMax());
        }

        public void setListeners(){

            barNeck.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                int progressVal = 0;
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    progressVal = progress;
                    valNeck.setText(progressVal + " / " + seekBar.getMax());
                    barNeck.setProgress(progressVal);
                }
                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {
                }
                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {
                    //n.setNeck(progressVal);


                }
            });
            barShoudler.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                int progressVal = 0;
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    progressVal = progress;
                    valShoudler.setText(progressVal + " / " + seekBar.getMax());
                }
                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {
                }
                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {
                    //n.setShoulder(progressVal);
                }
            });
            barUpBack.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                int progressVal = 0;
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    progressVal = progress;
                    valUpBack.setText(progressVal + " / " + seekBar.getMax());
                }
                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {
                }
                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {
                    //n.setUpBack(progressVal);
                }
            });
            barElbow.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                int progressVal = 0;
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    progressVal = progress;
                    valElbow.setText(progressVal + " / " + seekBar.getMax());
                }
                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {
                }
                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {
                    //n.setElbow(progressVal);
                }
            });
            barWristHand.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                int progressVal = 0;
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    progressVal = progress;
                    valWristHand.setText(progressVal + " / " + seekBar.getMax());
                }
                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {
                }
                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {
                    //n.setWrstHand(progressVal);
                }
            });
            barLowBack.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                int progressVal = 0;
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    progressVal = progress;
                    valLowBack.setText(progressVal + " / " + seekBar.getMax());
                }
                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {
                }
                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {
                    //n.setLowBack(progressVal);
                }
            });
            barHip.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                int progressVal = 0;
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    progressVal = progress;
                    valHip.setText(progressVal + " / " + seekBar.getMax());
                }
                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {
                }
                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {
                    //n.setHip(progressVal);
                }
            });
            barKnee.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                int progressVal = 0;
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    progressVal = progress;
                    valKnee.setText(progressVal + " / " + seekBar.getMax());
                }
                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {
                }
                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {
                   // n.setKnee(progressVal);
                }
            });
            barAnkFeet.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                int progressVal = 0;
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    progressVal = progress;
                    valAnkFeet.setText(progressVal + " / " + seekBar.getMax());
                    barAnkFeet.setProgress(progressVal);

                }
                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {
                }
                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {
                    //n.setAnkFeet(progressVal);
                }
            });
        }

        public void disableTouch(){

            barNeck.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    return true;
                }
            });
            barShoudler.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    return true;
                }
            });
            barUpBack.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    return true;
                }
            });
            barElbow.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    return true;
                }
            });
            barWristHand.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    return true;
                }
            });
            barLowBack.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    return true;
                }
            });
            barHip.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    return true;
                }
            });
            barKnee.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    return true;
                }
            });
            barAnkFeet.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    return true;
                }
            });

        }

        public void enableTouch(){

        barNeck.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return false;
            }
        });
        barShoudler.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return false;
            }
        });
        barUpBack.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return false;
            }
        });
        barElbow.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return false;
            }
        });
        barWristHand.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return false;
            }
        });
        barLowBack.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return false;
            }
        });
        barHip.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return false;
            }
        });
        barKnee.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return false;
            }
        });
        barAnkFeet.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return false;
            }
        });
    }

        public void enableEditMode(){
            btnCreate.setEnabled(true);
            enableTouch();
        }

        public void setActionBarLook(){
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setIcon(R.mipmap.ic_be_launcher_round);
        actionBar.setTitle("");
    }
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_nordic_crud);
            setActionBarLook();
            setPointers();
            Intent modo = getIntent();
            u = (User) modo.getSerializableExtra(NordicList.OP_USERNORDIC);
            String option = modo.getStringExtra(NordicList.OP_NEWNORDIC);
            n = (Nordic) modo.getSerializableExtra(NordicList.OP_VIEWNORDIC);

            lblDateToday.setText(sdf.format(Calendar.getInstance().getTime()));
            setBarValues();
            setBarText();
            setListeners();
            disableTouch();
            _listToShow = new ArrayList<Nordic>();
            _listToShow = nh.getList(dbWrapperSetup.TBL_NORDIC, dbWrapperSetup.COL_NORDIC_AUTONUMBER, false);

            if(option != null && option.equals("0")){
                if (_listToShow.isEmpty()){
                    enableEditMode();
                }else {
                    Nordic n = _listToShow.get(0);
                    barNeck.setProgress(n.getNeck());
                    barShoudler.setProgress(n.getShoulder());
                    barUpBack.setProgress(n.getUpBack());
                    barElbow.setProgress(n.getElbow());
                    barWristHand.setProgress(n.getWrstHand());
                    barLowBack.setProgress(n.getLowBack());
                    barHip.setProgress(n.getHip());
                    barKnee.setProgress(n.getKnee());
                    barAnkFeet.setProgress(n.getAnkFeet());
                    btnCreate.setText(R.string.btn_create);
                    enableEditMode();
                    Toast.makeText(this, "Utilizamos a sua última avaliação", Toast.LENGTH_LONG).show();
                }
                insertMode=true;
            }
            if (n!=null){
                lblDateToday.setText(n.getFillDate());
                barNeck.setProgress(n.getNeck());
                barShoudler.setProgress(n.getShoulder());
                barUpBack.setProgress(n.getUpBack());
                barElbow.setProgress(n.getElbow());
                barWristHand.setProgress(n.getWrstHand());
                barLowBack.setProgress(n.getLowBack());
                barHip.setProgress(n.getHip());
                barKnee.setProgress(n.getKnee());
                barAnkFeet.setProgress(n.getAnkFeet());
                btnCreate.setText(R.string.btn_update);
                insertMode=false;
            }
        }

        public void backToMain(View view) {
            this.finish();
        }

        public void manageRecord(View view) {
            boolean resultado = false;

            String iFillDate = lblDateToday.getText().toString();

            int iNeck=barNeck.getProgress();
            int iShoulder=barShoudler.getProgress();
            int iUpBack=barUpBack.getProgress();
            int iElbow=barElbow.getProgress();
            int iWristHand=barWristHand.getProgress();
            int iLowBack=barLowBack.getProgress();
            int iHip=barHip.getProgress();
            int iKnee=barHip.getProgress();
            int iAnkFeet=barAnkFeet.getProgress();

            if (insertMode){
                n = new Nordic();
                n.setFkIdUser(u.getID());
                n.setFillDate(iFillDate);
                n.setNeck(iNeck);
                n.setShoulder(iShoulder);
                n.setUpBack(iUpBack);
                n.setElbow(iElbow);
                n.setWrstHand(iWristHand);
                n.setLowBack(iLowBack);
                n.setHip(iHip);
                n.setKnee(iKnee);
                n.setAnkFeet(iAnkFeet);
                n.setErgoRisk();

                resultado = nh.add(n);
                if (resultado) Toast.makeText(this, "Nordic criado com sucesso " + n.toString(), Toast.LENGTH_LONG).show();

            }else {
                n.setFkIdUser(u.getID());
                n.setFillDate(iFillDate);
                n.setNeck(iNeck);
                n.setShoulder(iShoulder);
                n.setUpBack(iUpBack);
                n.setElbow(iElbow);
                n.setWrstHand(iWristHand);
                n.setLowBack(iLowBack);
                n.setHip(iHip);
                n.setKnee(iKnee);
                n.setAnkFeet(iAnkFeet);
                n.setErgoRisk();
                resultado = nh.updateNordic(n);
                if (resultado) Toast.makeText(this, "Nordic atualizado com sucesso " + n.toString(), Toast.LENGTH_SHORT).show();

            }
            this.finish();
        }

    @Override
    public boolean onCreateOptionsMenu (Menu menu){
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
        if (insertMode){
            menu.findItem(R.id.it_edit).setVisible(!insertMode);
            menu.findItem(R.id.it_delete).setVisible(!insertMode);
            menu.findItem(R.id.it_editPIN).setVisible(false);
        }else {
            menu.findItem(R.id.it_edit).setEnabled(!insertMode);
            menu.findItem(R.id.it_delete).setEnabled(!insertMode);
            menu.findItem(R.id.it_editPIN).setVisible(false);
        }


        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();
        switch (id){
            case R.id.it_edit:
                Toast.makeText(this, "Modo edição ativado", Toast.LENGTH_SHORT).show();
                enableEditMode();
                return true;

            case R.id.it_delete:
                createDeleteDialog();
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void createDeleteDialog(){
        AlertDialog.Builder boxDelete = new AlertDialog.Builder(this);
        boxDelete.setMessage("Pretende eliminar este questionário?");
        boxDelete.setCancelable(true);
        boxDelete.setPositiveButton("Eliminar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                boolean resultado = false;
                NordicHelper nordiHelper = new NordicHelper(NordicCRUD.this);
                resultado = nordiHelper.deleteNordic(n);
                if(resultado){
                    Toast.makeText(NordicCRUD.this, "Questionário eliminado", Toast.LENGTH_SHORT).show();
                }
                finish();
            }
        });
        boxDelete.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });
        AlertDialog alerta = boxDelete.create();
        alerta.show();
    }

}

