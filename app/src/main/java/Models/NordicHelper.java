package Models;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class NordicHelper extends SQLiteOpenHelper {
    private SQLiteDatabase myDB;
    public NordicHelper (Context appContext){
        super(appContext, dbWrapperSetup.BD_NOME, null, dbWrapperSetup.BD_VERSAO);
    }
    @Override
    public void onCreate(SQLiteDatabase db){
        dbWrapperSetup.onCreateDatabase(db);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldV, int newV){
        // aqui devemos testar se é atualizavel ou não antes de chamar a função
        dbWrapperSetup.onUpgradeDatabase(db, oldV, newV);
    }
    public void openLocalDB(){
        myDB = this.getWritableDatabase();
    }
    private String sortList(boolean sort){
        return (sort) ? " ASC" : " DESC";
    }
    public List<Nordic> getList(String table, String column, boolean sort) {
        //verificar se a BD está aberta ou não
        if (myDB == null) openLocalDB();
        List<Nordic> listToShow = new ArrayList<Nordic>();
        String queryList = "SELECT * FROM " + table + " ORDER BY "+ column + sortList(sort);
        Cursor cursor = myDB.rawQuery(queryList, null);
        if (cursor.moveToFirst()) {
            do {
                //colocar em string ou int o valor alocado ao índice da coluna que tem o nome indentificado pela constante
                String uuidValueFromDB = cursor.getString(cursor.getColumnIndex(dbWrapperSetup.COL_NORDIC_UID));
                Nordic nordicToList = new Nordic(uuidValueFromDB);
                nordicToList.setFkIdUser(cursor.getString(cursor.getColumnIndex(dbWrapperSetup.COL_NORDIC_FKUSER)));
                nordicToList.setFillDate(cursor.getString(cursor.getColumnIndex(dbWrapperSetup.COL_NORDIC_FILLDATE)));
                nordicToList.setNeck(cursor.getInt(cursor.getColumnIndex(dbWrapperSetup.COL_NORDIC_NECK)));
                nordicToList.setShoulder(cursor.getInt(cursor.getColumnIndex(dbWrapperSetup.COL_NORDIC_SHOULDER)));
                nordicToList.setUpBack(cursor.getInt(cursor.getColumnIndex(dbWrapperSetup.COL_NORDIC_UPBACK)));
                nordicToList.setElbow(cursor.getInt(cursor.getColumnIndex(dbWrapperSetup.COL_NORDIC_ELBOW)));
                nordicToList.setWrstHand(cursor.getInt(cursor.getColumnIndex(dbWrapperSetup.COL_NORDIC_WRSTHAND)));
                nordicToList.setLowBack(cursor.getInt(cursor.getColumnIndex(dbWrapperSetup.COL_NORDIC_LOWBACK)));
                nordicToList.setHip(cursor.getInt(cursor.getColumnIndex(dbWrapperSetup.COL_NORDIC_HIP)));
                nordicToList.setKnee(cursor.getInt(cursor.getColumnIndex(dbWrapperSetup.COL_NORDIC_KNEE)));
                nordicToList.setAnkFeet(cursor.getInt(cursor.getColumnIndex(dbWrapperSetup.COL_NORDIC_ANKFEET)));
                nordicToList.setErgoRisk();
                listToShow.add(nordicToList);
            } while (cursor.moveToNext());//verificar se existem mais registos
        }
        return listToShow;
    }
    public boolean add(Nordic nordic){
        if (myDB ==null) openLocalDB();
        ContentValues contentor = new ContentValues();
        contentor.put(dbWrapperSetup.COL_NORDIC_UID, nordic.getId());
        contentor.put(dbWrapperSetup.COL_NORDIC_FKUSER, nordic.getFkIdUser());
        contentor.put(dbWrapperSetup.COL_NORDIC_FILLDATE, nordic.getFillDate());
        contentor.put(dbWrapperSetup.COL_NORDIC_NECK, nordic.getNeck());
        contentor.put(dbWrapperSetup.COL_NORDIC_SHOULDER, nordic.getShoulder());
        contentor.put(dbWrapperSetup.COL_NORDIC_UPBACK, nordic.getUpBack());
        contentor.put(dbWrapperSetup.COL_NORDIC_ELBOW, nordic.getElbow());
        contentor.put(dbWrapperSetup.COL_NORDIC_WRSTHAND, nordic.getWrstHand());
        contentor.put(dbWrapperSetup.COL_NORDIC_LOWBACK, nordic.getLowBack());
        contentor.put(dbWrapperSetup.COL_NORDIC_HIP, nordic.getHip());
        contentor.put(dbWrapperSetup.COL_NORDIC_KNEE, nordic.getKnee());
        contentor.put(dbWrapperSetup.COL_NORDIC_ANKFEET, nordic.getAnkFeet());
        //despejar o contentor dentro de uma tabela
        long idAutoNumber = myDB.insert(dbWrapperSetup.TBL_NORDIC, null, contentor);

        return (idAutoNumber > 0) ? true : false;
    }
    public boolean updateNordic(Nordic nordic){
        if (myDB==null) openLocalDB();
        ContentValues contentor = new ContentValues();
        contentor.put(dbWrapperSetup.COL_NORDIC_UID, nordic.getId());
        contentor.put(dbWrapperSetup.COL_NORDIC_FKUSER, nordic.getFkIdUser());
        contentor.put(dbWrapperSetup.COL_NORDIC_FILLDATE, nordic.getFillDate());
        contentor.put(dbWrapperSetup.COL_NORDIC_NECK, nordic.getNeck());
        contentor.put(dbWrapperSetup.COL_NORDIC_SHOULDER, nordic.getShoulder());
        contentor.put(dbWrapperSetup.COL_NORDIC_UPBACK, nordic.getUpBack());
        contentor.put(dbWrapperSetup.COL_NORDIC_ELBOW, nordic.getElbow());
        contentor.put(dbWrapperSetup.COL_NORDIC_WRSTHAND, nordic.getWrstHand());
        contentor.put(dbWrapperSetup.COL_NORDIC_LOWBACK, nordic.getLowBack());
        contentor.put(dbWrapperSetup.COL_NORDIC_HIP, nordic.getHip());
        contentor.put(dbWrapperSetup.COL_NORDIC_KNEE, nordic.getKnee());
        contentor.put(dbWrapperSetup.COL_NORDIC_ANKFEET, nordic.getAnkFeet());

        //Atualizar linha
        int affectedRows= myDB.update(dbWrapperSetup.TBL_NORDIC, contentor, dbWrapperSetup.COL_NORDIC_UID+ " = ?",
                new String[]{String.valueOf(nordic.getId())});
        return (affectedRows > 0 ) ? true : false;
    }
    public boolean deleteNordic(Nordic nordic) {
        if(myDB == null) openLocalDB();
        long affectedRows = myDB.delete(dbWrapperSetup.TBL_NORDIC,
                dbWrapperSetup.COL_NORDIC_UID + " = ? ", new String[] {String.valueOf(nordic.getId())});
        return (affectedRows > 0) ? true : false;
    }
    public int getNrContactos() {
        if (myDB==null) openLocalDB();
        String sqlInstruction = "SELECT COUNT(*) FROM " + dbWrapperSetup.TBL_NORDIC;
        int nrRegs = (int) DatabaseUtils.longForQuery(myDB, sqlInstruction, null);
        return nrRegs;
    }

}
