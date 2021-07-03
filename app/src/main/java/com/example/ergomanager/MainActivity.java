package com.example.ergomanager;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.text.InputType;
import android.text.method.PasswordTransformationMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LegendEntry;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GridLabelRenderer;
import com.jjoe64.graphview.helper.StaticLabelsFormatter;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.jjoe64.graphview.series.Series;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Array;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import Models.Nordic;
import Models.NordicHelper;
import Models.User;
import Models.UserHelper;
import Models.dbWrapperSetup;

public class MainActivity extends AppCompatActivity {
    private SharedPreferences userSP;

    public static List<User> _listaUser;
    public static List<Nordic> _listaNordic;
    LineGraphSeries<DataPoint> series;
    BarChart barChart;
    BarData barData;
    BarDataSet barDataSet;
    ArrayList barEntriesArrayList;
    User uu;
    Nordic nn;
    UserHelper uh;
    ListView lvUser;
    ListView lvNordic;
    Button btnNewUser;
    Button btnStartNow;
    CardView cardShowPlan;
    TextView txtShowPlan;
    TextView txtWelcome;
    TextView txtAge;
    TextView txtIMC;
    TextView txtRisk;
    TextView txtErgo;
    GraphView graphView;
    CardView cardView;
    CardView cardRegion;
    CardView cardAge;
    CardView cardIMC;
    CardView cardRiskFactors;
    CardView cardErgoRisk;
    ImageView imgProfile;
    FloatingActionButton fabNewNordic;
    public final static String OP_NEWUSER = "INSERTUSER";
    public final static String OP_UPDATEUSER = "ATUALIZARCONTACTO";
    public final static String OP_NORDIC_LIST = "LISTNORDIC";
    public final static String OP_NEWNORDIC = "INSERTNORDIC";
    public final static String OP_USERNORDIC = "USERNORDIC";

    public final static String OP_PLAN_LIST = "LISTPLAN";
    public final static String OP_ABOUT = "ABOUT";
    public final String tableNordic = dbWrapperSetup.TBL_NORDIC;
    public final String columnNordic = dbWrapperSetup.COL_NORDIC_AUTONUMBER;
    public final boolean sortNordic = false;
    public final String tabelaUser = dbWrapperSetup.TBL_USER;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        userSP = getSharedPreferences(UserCRUD.CONTENTORPREFS, MODE_PRIVATE);
        setActionBarLook();
        uu = new User();
        nn = new Nordic();
        _listaUser = new ArrayList<User>();
        _listaNordic = new ArrayList<Nordic>();
        //getUserDB();

        pointers();
        launchMain();

    }

    public void onResume(){
        super.onResume();
        graphView.removeAllSeries();
        launchMain();
    }

    private void launchMain(){
        getUserDB();
        getNordicDB();

        setMainVisibleActions();

        bindUser();
    }

    private void bindUser() {


        int ergoRisk = nn.getErgoRisk();
        int riskFactors = Integer.valueOf((nn.getErgoRisk()/2)+uu.getHealthRisk());

        txtWelcome.setText(" "+uu.getName());
        txtAge.setText(String.valueOf(uu.getAge()));
        double imc = uu.getIMC();
        txtIMC.setText(String.valueOf(imc));
        if (imc<uu.IMC_UNDER) txtIMC.setTextColor(getResources().getColor(android.R.color.holo_orange_light));
        if (imc>=uu.IMC_UNDER &&imc<=uu.IMC_OK) txtIMC.setTextColor(getResources().getColor(android.R.color.holo_green_dark));
        if (imc>uu.IMC_OK &&imc<=uu.IMC_OVER1) txtIMC.setTextColor(getResources().getColor(android.R.color.holo_orange_light));
        if (imc>uu.IMC_OVER1 &&imc<=uu.IMC_OVER2){
            txtIMC.setTextColor(getResources().getColor(android.R.color.holo_orange_dark));
        }
        if (imc>uu.IMC_OVER2 ) {
            txtIMC.setTextColor(getResources().getColor(R.color.design_default_color_error));
        }

        txtRisk.setText(String.valueOf(riskFactors));
        if (riskFactors>=8) txtRisk.setTextColor(getResources().getColor(R.color.red));
        if (riskFactors>=4 && riskFactors<8) txtRisk.setTextColor(getResources().getColor(android.R.color.holo_orange_dark));
        if (riskFactors<5 && riskFactors>1) txtRisk.setTextColor(getResources().getColor(android.R.color.holo_orange_light));
        if (riskFactors<2 ) txtRisk.setTextColor(getResources().getColor(android.R.color.holo_green_dark));

        txtErgo.setText(String.valueOf(ergoRisk));
        if (ergoRisk>=7) txtErgo.setTextColor(getResources().getColor(R.color.red));
        if (ergoRisk>=4 && ergoRisk<7) txtErgo.setTextColor(getResources().getColor(android.R.color.holo_orange_dark));
        if (ergoRisk<4 && ergoRisk>0) txtErgo.setTextColor(getResources().getColor(android.R.color.holo_orange_light));
        if (ergoRisk==0 ) txtErgo.setTextColor(getResources().getColor(android.R.color.holo_green_dark));

        ArrayAdapter<User> aaUser = new ArrayAdapter<User>(this, android.R.layout.simple_list_item_1, _listaUser);
        lvUser.setAdapter(aaUser);
        ArrayAdapter<Nordic> aaNordic = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, _listaNordic);
        lvNordic.setAdapter(aaNordic);

        if (_listaNordic.size()>0){
            getErgoRiskGraph();
            getNordicGraph();
        }
        imgProfile.setColorFilter(R.color.teal_700);
    }

    private User getUserDB(){
        uh = new UserHelper(this);
        _listaUser = uh.getUserList(tabelaUser);
        uu = uh.getUser(tabelaUser);
        return uu;
    }

    private Nordic getNordicDB(){
        _listaNordic.clear();
        NordicHelper nh = new NordicHelper(this);
        _listaNordic = nh.getList(tableNordic, columnNordic, sortNordic);
        if (!_listaNordic.isEmpty()) nn = _listaNordic.get(0);
        return nn;
    }

    public void setMainVisibleActions(){
        if(!(_listaUser.isEmpty())){
            btnNewUser.setVisibility(View.GONE);
            imgProfile.setVisibility(View.VISIBLE);
            txtAge.setVisibility(View.VISIBLE);
            txtIMC.setVisibility(View.VISIBLE);
            txtRisk.setVisibility(View.VISIBLE);
            txtErgo.setVisibility(View.VISIBLE);
            cardAge.setVisibility(View.VISIBLE);
            cardIMC.setVisibility(View.VISIBLE);
            cardErgoRisk.setVisibility(View.VISIBLE);
            cardRiskFactors.setVisibility(View.VISIBLE);
            cardView.setVisibility(View.VISIBLE);
            cardRegion.setVisibility(View.VISIBLE);
            cardShowPlan.setVisibility(View.VISIBLE);
            btnStartNow.setVisibility(View.GONE);
            fabNewNordic.setVisibility(View.VISIBLE);
            if (_listaNordic.isEmpty()){
                cardView.setVisibility(View.GONE);
                cardRegion.setVisibility(View.GONE);
                cardShowPlan.setVisibility(View.GONE);
                btnStartNow.setVisibility(View.VISIBLE);
                fabNewNordic.setVisibility(View.GONE);

            }
        }else {
            btnNewUser.setVisibility(View.VISIBLE);
            btnStartNow.setVisibility(View.GONE);
            cardShowPlan.setVisibility(View.GONE);
            imgProfile.setVisibility(View.GONE);
            txtAge.setVisibility(View.GONE);
            txtIMC.setVisibility(View.GONE);
            txtRisk.setVisibility(View.GONE);
            txtErgo.setVisibility(View.GONE);
            cardView.setVisibility(View.GONE);
            cardAge.setVisibility(View.GONE);
            cardIMC.setVisibility(View.GONE);
            cardRiskFactors.setVisibility(View.GONE);
            cardErgoRisk.setVisibility(View.GONE);
            cardRegion.setVisibility(View.GONE);
            fabNewNordic.setVisibility(View.GONE);

        }
    }

    public void setActionBarLook(){
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setIcon(R.mipmap.ic_be_launcher_round);
        actionBar.setTitle("");
    }

    private void pointers(){
        graphView = findViewById(R.id.idGraphView);
        barChart = findViewById(R.id.idGraphViewRegion);
        cardView = (CardView) findViewById(R.id.cardViewNordic);
        cardRegion = (CardView) findViewById(R.id.cardViewRegion);
        cardAge = (CardView) findViewById(R.id.cardAge);
        cardIMC = (CardView) findViewById(R.id.cardIMC);
        cardRiskFactors = (CardView) findViewById(R.id.cardRF);
        cardErgoRisk = (CardView) findViewById(R.id.cardErgoRisk);
        lvUser = (ListView) findViewById(R.id.lstUser);
        lvNordic = (ListView) findViewById(R.id.lstNordicsMain);
        btnNewUser = (Button) findViewById(R.id.btnNewUser);
        btnStartNow = (Button) findViewById(R.id.btnStartNow);
        cardShowPlan = (CardView) findViewById(R.id.cardShowPlan);
        txtShowPlan = (TextView) findViewById(R.id.txtCardPlan);
        txtWelcome = (TextView) findViewById(R.id.txtWelcome);
        txtAge = (TextView) findViewById(R.id.cardAgeValue);
        txtIMC = (TextView) findViewById(R.id.cardIMCValue);
        txtRisk = (TextView) findViewById(R.id.cardRFValue);
        txtErgo = (TextView) findViewById(R.id.cardErgoValue);
        imgProfile = (ImageView) findViewById(R.id.imgProfile);
        fabNewNordic = (FloatingActionButton) findViewById(R.id.fabNewNordic);
    }

    public void getErgoRiskGraph() {
        series = new LineGraphSeries<DataPoint>();
        int cont = _listaNordic.size();
        if (cont > 6) cont=6;
        int i = 0;
        while (cont > 0) {
            cont--;
            Nordic nnnn = _listaNordic.get(cont);
            series.appendData(new DataPoint(i, nnnn.getErgoRisk()), true, 6);
            i++;
        }
        graphView.addSeries(series);
        setErgoGraphAppearance();
    }

    public void setErgoGraphAppearance(){
        series.setDrawBackground(true);
        series.setDrawDataPoints(true);
        series.setDataPointsRadius(5);
        graphView.setTitle("Risco Ergonómico");
        graphView.setTitleTextSize(25);
        graphView.getViewport().setMinX(0);
        graphView.getViewport().setMaxX(5);
        graphView.getViewport().setMinY(0);
        graphView.getViewport().setMaxY(10);
        graphView.getViewport().setXAxisBoundsManual(true);
        graphView.getViewport().setYAxisBoundsManual(true);
        graphView.getGridLabelRenderer().setGridStyle(GridLabelRenderer.GridStyle.NONE);
        graphView.getGridLabelRenderer().setHighlightZeroLines(false);
        graphView.getGridLabelRenderer().setHorizontalLabelsVisible(false);
        graphView.setBackgroundColor(getResources().getColor(R.color.lightgrey));
    }

    public void getNordicGraph() {
        getBarEntries();
        barDataSet = new BarDataSet(barEntriesArrayList, "Nível de Dor");
        barDataSet.setColors(new int[] {Color.RED, Color.GREEN, Color.GRAY, getResources().getColor(R.color.teal_200), Color.BLUE, Color.MAGENTA, Color.YELLOW, Color.CYAN, getResources().getColor(R.color.purple_500)});
        barData = new BarData(barDataSet);
        barChart.setData(barData);
        setNordicGraphAppearance();
    }

    private void setNordicGraphAppearance(){
        LegendEntry legNeck = new LegendEntry();
        legNeck.label="Pescoço";
        LegendEntry legShoulder = new LegendEntry();
        legShoulder.label="Ombro";
        LegendEntry legUpBack = new LegendEntry();
        legUpBack.label=" Tórax";
        LegendEntry legElbow = new LegendEntry();
        legElbow.label=" Cotovelo";
        LegendEntry legHand = new LegendEntry();
        legHand.label="Punho";
        LegendEntry legLowBack = new LegendEntry();
        legLowBack.label="Lombar";
        LegendEntry legHip = new LegendEntry();
        legHip.label="Anca";
        LegendEntry legKnee = new LegendEntry();
        legKnee.label="Joelho";
        LegendEntry legFeet = new LegendEntry();
        legFeet.label=" "+" "+" "+"Pé";
        List<LegendEntry> labels = new ArrayList<>();
        labels.add(legNeck);
        labels.add(legShoulder);
        labels.add(legUpBack);
        labels.add(legElbow);
        labels.add(legHand);
        labels.add(legLowBack);
        labels.add(legHip);
        labels.add(legKnee);
        labels.add(legFeet);
        barChart.getDescription().setText("Última avaliação de dor");
        barChart.getDescription().setTextSize(12f);
        barChart.getDescription().setPosition(450f, 25f);
        barChart.animateY(3000);
        barChart.setDrawBorders(false);
        barChart.getXAxis().setDrawGridLines(false);
        barChart.getAxisRight().setEnabled(false);
        barChart.getAxisLeft().setAxisMaximum(11);
        barChart.getAxisLeft().setAxisMinimum(0);
        barChart.getXAxis().setDrawAxisLine(false);
        barChart.getXAxis().setDrawLabels(false);
        Legend legend = barChart.getLegend();
        legend.setCustom(labels);
        legend.setTextSize(5);
        legend.setXEntrySpace(0f);
        legend.setXOffset(-9f);
        barChart.setBackgroundColor(getResources().getColor(R.color.lightgrey));
    }

    private void getBarEntries() {
        barEntriesArrayList = new ArrayList<>();

        barEntriesArrayList.add(new BarEntry(1f, nn.getNeck()));
        barEntriesArrayList.add(new BarEntry(2f, nn.getShoulder()));
        barEntriesArrayList.add(new BarEntry(3f, nn.getUpBack()));
        barEntriesArrayList.add(new BarEntry(4f, nn.getElbow()));
        barEntriesArrayList.add(new BarEntry(5f, nn.getWrstHand()));
        barEntriesArrayList.add(new BarEntry(6f, nn.getLowBack()));
        barEntriesArrayList.add(new BarEntry(7f, nn.getHip()));
        barEntriesArrayList.add(new BarEntry(8f, nn.getKnee()));
        barEntriesArrayList.add(new BarEntry(9f, nn.getAnkFeet()));
    }

    public void newUserCreateButton(View view) {
        Intent user = new Intent(MainActivity.this, UserCRUD.class);
        user.putExtra(OP_NEWUSER, "0");
        startActivity(user);
    }

    //Codificar menu preferencias
    @Override
    public boolean onCreateOptionsMenu (Menu menu){
        //carregar o menu com o conteúdo
        if (!_listaUser.isEmpty()){
            getMenuInflater().inflate(R.menu.optionsmenumain, menu);
            //colocar o icon branco
            for(int i = 0; i < menu.size(); i++){
                Drawable drawable = menu.getItem(i).getIcon();
                if(drawable != null) {
                    drawable.mutate();
                    drawable.setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);
                }
            }
        }
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        invalidateOptionsMenu();
        menu.findItem(R.id.it_profile);
        menu.findItem(R.id.it_nordic);
        menu.findItem(R.id.it_plan);

        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        //id do item clicado
        int id = item.getItemId();
        switch (id){
            case R.id.it_profile:
                viewProfile();
                return true;

            case R.id.it_nordic:
                listNordic();

                return true;
            case R.id.it_plan:
                listPlan();
                return true;
            case R.id.it_share:
                confirmShareDialog();
                return true;
            case R.id.it_about:
                goToAbout();
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    private void createJSONObject() {
        JSONObject jsonUserDataToShare = new JSONObject();
        try {
            jsonUserDataToShare.put("id", userSP.getString(UserCRUD.USERTOKEN, "!"));
            jsonUserDataToShare.put("imc", String.valueOf(uu.getIMC()));
            jsonUserDataToShare.put("fatores de risco", String.valueOf(uu.getHealthRisk()));
            jsonUserDataToShare.put("data",nn.getFillDate());
            jsonUserDataToShare.put("risco ergonomico", String.valueOf(nn.getErgoRisk()));
            jsonUserDataToShare.put("dor pescoço", String.valueOf(nn.getNeck()));
            jsonUserDataToShare.put("dor ombro", String.valueOf(nn.getShoulder()));
            jsonUserDataToShare.put("dor torax", String.valueOf(nn.getUpBack()));
            jsonUserDataToShare.put("dor cotovelo", String.valueOf(nn.getElbow()));
            jsonUserDataToShare.put("dor punho/mão", String.valueOf(nn.getWrstHand()));
            jsonUserDataToShare.put("dor lombar", String.valueOf(nn.getLowBack()));
            jsonUserDataToShare.put("dor anca", String.valueOf(nn.getHip()));
            jsonUserDataToShare.put("dor joelho", String.valueOf(nn.getKnee()));
            jsonUserDataToShare.put("dor tornozelo/pé", String.valueOf(nn.getAnkFeet()));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JSONArray jsonArrayUserDataToShare = new JSONArray();
        jsonArrayUserDataToShare.put(jsonUserDataToShare);
        if (jsonArrayUserDataToShare.length()>0){
            Toast.makeText(this, "Dados partilhados com o seu médico", Toast.LENGTH_SHORT).show();
        }
    }

    public void confirmShareDialog(){
        android.app.AlertDialog.Builder boxConfirmShare = new android.app.AlertDialog.Builder(this);
        boxConfirmShare.setMessage("Irá enviar os seus dados de saúde para o seu médico?\nPretende continuar?");
        boxConfirmShare.setCancelable(true);
        boxConfirmShare.setPositiveButton("Enviar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                createJSONObject();
            }
        });
        boxConfirmShare.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });
        AlertDialog alerta = boxConfirmShare.create();
        alerta.show();
    }

    public void backToMain(View view) {
        this.finish();
    }

    public void listNordic(View view) {
        listNordic();
    }

    public void viewProfile(View view) {
        viewProfile();
    }

    public void listPlan(View view) {
        listPlan();
    }

    public void newNordic(View view) {
        newNordic();
    }

    public void newNordic(){
        Intent newNordic = new Intent(this, NordicCRUD.class);
        newNordic.putExtra(OP_NEWNORDIC, "0");
        newNordic.putExtra(OP_USERNORDIC, uu);
        startActivity(newNordic);
    }

    public void viewProfile() {
        Intent user = new Intent(MainActivity.this, UserCRUD.class);
        User u = (User) lvUser.getAdapter().getItem(0);
        user.putExtra(OP_UPDATEUSER, u);
        startActivity(user);
    }

    public void listNordic() {
        Intent nordicI = new Intent(MainActivity.this, NordicList.class);
        User u = (User) lvUser.getAdapter().getItem(0);
        nordicI.putExtra(OP_NORDIC_LIST, u);
        startActivity(nordicI);
    }

    public void goToAbout() {
        Intent about = new Intent(MainActivity.this, About.class);
        about.putExtra(OP_ABOUT, "0");
        startActivity(about);
    }

    public void listPlan() {
        Intent planI = new Intent(MainActivity.this, PlanList.class);
        planI.putExtra(OP_PLAN_LIST, nn);
        startActivity(planI);
    }
}
