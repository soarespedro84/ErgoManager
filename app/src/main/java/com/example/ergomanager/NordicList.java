package com.example.ergomanager;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import Models.Nordic;
import Models.NordicHelper;
import Models.User;
import Models.dbWrapperSetup;

public class NordicList extends AppCompatActivity {

    public static List<Nordic> _listToShow;
    User u;
    ListView lvNordics;
    public final static String OP_NEWNORDIC = "INSERTNORDIC";
    public final static String OP_VIEWNORDIC = "VIEWNORDIC";
    public final static String OP_USERNORDIC = "USERNORDIC";
    public final static String OP_PLAN_LIST = "LISTPLAN";

    boolean sort = false;
    public final String table = dbWrapperSetup.TBL_NORDIC;
    public final String column = dbWrapperSetup.COL_NORDIC_AUTONUMBER;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nordic_list);
        setActionBarLook();
        Intent iNordic = getIntent();
        u = (User) iNordic.getSerializableExtra(MainActivity.OP_NORDIC_LIST);
        _listToShow = new ArrayList<Nordic>();
        lvNordics = (ListView) findViewById(R.id.lstNordics);
        if(u != null){
            startList();
        }
    }
    public void onResume(){
        super.onResume();
        startList();
    }
    public void setActionBarLook(){
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setIcon(R.mipmap.ic_be_launcher_round);
        actionBar.setTitle("");
    }
    private void startList() {
        NordicHelper nh = new NordicHelper(this);
        _listToShow.clear();
        _listToShow = nh.getList(table, column, sort);

        bindNordics();
        TextView tv = (TextView) findViewById(R.id.lblNrNordics);
        tv.setText("Nr. Questionários: " + nh.getNrContactos());
    }

    private void bindNordics() {

        ArrayAdapter<Nordic> aaNordics = new ArrayAdapter<Nordic>(this, android.R.layout.simple_list_item_1, _listToShow){

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            View view = super.getView(position, convertView, parent);
            TextView tv = (TextView) view.findViewById(android.R.id.text1);

            for (int i=0; i < _listToShow.size(); i++){
                Nordic item = _listToShow.get(position);
                if (item.getErgoRisk()<2){
                    tv.setTextColor(getResources().getColor(android.R.color.holo_green_dark));
                }
                if (item.getErgoRisk()>=2 && item.getErgoRisk()<5){
                    tv.setTextColor(getResources().getColor(android.R.color.holo_orange_light));
                }
                if (item.getErgoRisk()>=5 &&item.getErgoRisk()<8){
                    tv.setTextColor( getResources().getColor(android.R.color.holo_orange_dark));
                }
                if (item.getErgoRisk()>=8){
                    tv.setTextColor(getResources().getColor(R.color.design_default_color_error));
                }
            }

            tv.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 25);
            tv.getCompoundPaddingBottom();
            return view;
        }
        };
        lvNordics.setAdapter(aaNordics);

        lvNordics.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent iCrud = new Intent(NordicList.this, NordicCRUD.class);
                Nordic n = (Nordic) lvNordics.getAdapter().getItem(position);
                iCrud.putExtra(OP_VIEWNORDIC, n);
                iCrud.putExtra(OP_USERNORDIC, u);
                startActivity(iCrud);
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu (Menu menu){
        //carregar o menu com o conteúdo
        getMenuInflater().inflate(R.menu.menuaddnordic, menu);

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
    public boolean onOptionsItemSelected(MenuItem it){
        int id = it.getItemId();
        switch (id){
            /*case R.id.it_add:
                newNordic();
                return true;

             */

            case R.id.it_OrdByDate:
                orderList(table, column, sort);
                sort = !sort;
                return true;
            default:
                return super.onOptionsItemSelected(it);
        }
    }


    private void orderList(String table, String col, boolean asc) {
        NordicHelper nh = new NordicHelper(this);
        _listToShow.clear();
        _listToShow = nh.getList(table, col, asc);
        bindNordics();
    }
    public void listPlan(View view) {
        if (!_listToShow.isEmpty()){
            Intent planI = new Intent(NordicList.this, PlanList.class);
            Nordic n = (Nordic) lvNordics.getAdapter().getItem(0);
            planI.putExtra(OP_PLAN_LIST, n);
            startActivity(planI);
        }else {
            Toast.makeText(NordicList.this, "Deve preencher um questionário" , Toast.LENGTH_SHORT).show();        }
    }
    public void backToMain(View view) {
        this.finish();
    }

}