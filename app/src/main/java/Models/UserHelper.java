package Models;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class UserHelper extends SQLiteOpenHelper {
    private SQLiteDatabase myDB;
    public UserHelper (Context appContext){
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
    public List<User> getUserList(String tabela){
        if (myDB == null) openLocalDB();
        List<User> userList = new ArrayList<>();
        String queryLista = "SELECT * FROM " + tabela;
        Cursor cursor = myDB.rawQuery(queryLista, null);
        if (cursor.moveToFirst()) {
            do {
                String uuidValueFromDB = cursor.getString(cursor.getColumnIndex(dbWrapperSetup.COL_USER_UID));
                User userToShow = new User(uuidValueFromDB);
                userToShow.setName(cursor.getString(cursor.getColumnIndex(dbWrapperSetup.COL_USER_NAME)));
                userToShow.setEmail(cursor.getString(cursor.getColumnIndex(dbWrapperSetup.COL_USER_EMAIL)));
                userToShow.setPIN(cursor.getString(cursor.getColumnIndex(dbWrapperSetup.COL_USER_PIN)));
                userToShow.setBirthDate(cursor.getString(cursor.getColumnIndex(dbWrapperSetup.COL_USER_BIRTHDATE)));
                userToShow.setAge(cursor.getString(cursor.getColumnIndex(dbWrapperSetup.COL_USER_AGE)));
                userToShow.setGenre(cursor.getString(cursor.getColumnIndex(dbWrapperSetup.COL_USER_GENRE)));
                userToShow.setHeight(cursor.getDouble(cursor.getColumnIndex(dbWrapperSetup.COL_USER_HEIGHT)));
                userToShow.setWeight(cursor.getDouble(cursor.getColumnIndex(dbWrapperSetup.COL_USER_WEIGHT)));
                userToShow.setIMC(cursor.getDouble(cursor.getColumnIndex(dbWrapperSetup.COL_USER_HEIGHT)),cursor.getDouble(cursor.getColumnIndex(dbWrapperSetup.COL_USER_WEIGHT)));
                userToShow.setSmoker(cursor.getString(cursor.getColumnIndex(dbWrapperSetup.COL_USER_SMOKER)));
                userToShow.setActive(cursor.getInt(cursor.getColumnIndex(dbWrapperSetup.COL_USER_ACTIVE)));

                userList.add(userToShow);

            } while (cursor.moveToNext());//verificar se existem mais registos
        }

        return userList;
    }
    public User getUser(String tabela){
        if (myDB == null) openLocalDB();
        User user = new User();
        String queryLista = "SELECT * FROM " + tabela;
        Cursor cursor = myDB.rawQuery(queryLista, null);
        if (cursor.moveToFirst()) {
            do {
                String uuidValueFromDB = cursor.getString(cursor.getColumnIndex(dbWrapperSetup.COL_USER_UID));
                User userToShow = new User(uuidValueFromDB);
                userToShow.setName(cursor.getString(cursor.getColumnIndex(dbWrapperSetup.COL_USER_NAME)));
                userToShow.setEmail(cursor.getString(cursor.getColumnIndex(dbWrapperSetup.COL_USER_EMAIL)));
                userToShow.setPIN(cursor.getString(cursor.getColumnIndex(dbWrapperSetup.COL_USER_PIN)));
                userToShow.setBirthDate(cursor.getString(cursor.getColumnIndex(dbWrapperSetup.COL_USER_BIRTHDATE)));
                userToShow.setAge(cursor.getString(cursor.getColumnIndex(dbWrapperSetup.COL_USER_AGE)));
                userToShow.setGenre(cursor.getString(cursor.getColumnIndex(dbWrapperSetup.COL_USER_GENRE)));
                userToShow.setHeight(cursor.getDouble(cursor.getColumnIndex(dbWrapperSetup.COL_USER_HEIGHT)));
                userToShow.setWeight(cursor.getDouble(cursor.getColumnIndex(dbWrapperSetup.COL_USER_WEIGHT)));
                userToShow.setIMC(cursor.getDouble(cursor.getColumnIndex(dbWrapperSetup.COL_USER_HEIGHT)),cursor.getDouble(cursor.getColumnIndex(dbWrapperSetup.COL_USER_WEIGHT)));
                userToShow.setSmoker(cursor.getString(cursor.getColumnIndex(dbWrapperSetup.COL_USER_SMOKER)));
                userToShow.setActive(cursor.getInt(cursor.getColumnIndex(dbWrapperSetup.COL_USER_ACTIVE)));

                user=userToShow;

            } while (cursor.moveToNext());//verificar se existem mais registos
        }
            return user;
    }
    public boolean add(User user){
        if (myDB ==null) openLocalDB();
        ContentValues contentor = new ContentValues();
        contentor.put(dbWrapperSetup.COL_USER_UID, user.getID());
        contentor.put(dbWrapperSetup.COL_USER_NAME, user.getName());
        contentor.put(dbWrapperSetup.COL_USER_EMAIL, user.getEmail());
        contentor.put(dbWrapperSetup.COL_USER_PIN, user.getPIN());
        contentor.put(dbWrapperSetup.COL_USER_BIRTHDATE, user.getBirthDate());
        contentor.put(dbWrapperSetup.COL_USER_AGE, user.getAge());
        contentor.put(dbWrapperSetup.COL_USER_GENRE, user.getGenre());
        contentor.put(dbWrapperSetup.COL_USER_HEIGHT, user.getHeight());
        contentor.put(dbWrapperSetup.COL_USER_WEIGHT, user.getWeight());
        contentor.put(dbWrapperSetup.COL_USER_IMC, user.getIMC());
        contentor.put(dbWrapperSetup.COL_USER_SMOKER, user.isSmoker());
        contentor.put(dbWrapperSetup.COL_USER_ACTIVE, user.getActive());

        long idAutoNumber = myDB.insert(dbWrapperSetup.TBL_USER, null, contentor);

        return (idAutoNumber > 0) ? true : false;
    }
    public boolean updateUser(User u){
        if (myDB==null) openLocalDB();
        ContentValues contentor = new ContentValues();
        contentor.put(dbWrapperSetup.COL_USER_UID, u.getID());
        contentor.put(dbWrapperSetup.COL_USER_NAME, u.getName());
        contentor.put(dbWrapperSetup.COL_USER_EMAIL, u.getEmail());
        contentor.put(dbWrapperSetup.COL_USER_PIN, u.getPIN());
        contentor.put(dbWrapperSetup.COL_USER_BIRTHDATE, u.getBirthDate());
        contentor.put(dbWrapperSetup.COL_USER_AGE, u.getAge());
        contentor.put(dbWrapperSetup.COL_USER_GENRE, u.getGenre());
        contentor.put(dbWrapperSetup.COL_USER_HEIGHT, u.getHeight());
        contentor.put(dbWrapperSetup.COL_USER_WEIGHT, u.getWeight());
        contentor.put(dbWrapperSetup.COL_USER_IMC, u.getIMC());
        contentor.put(dbWrapperSetup.COL_USER_SMOKER, u.isSmoker());
        contentor.put(dbWrapperSetup.COL_USER_ACTIVE, u.getActive());

        //Atualizar linha
        int nrAfectedRecs= myDB.update(dbWrapperSetup.TBL_USER, contentor, dbWrapperSetup.COL_USER_UID+ " = ?",
                new String[]{String.valueOf(u.getID())});
        return (nrAfectedRecs > 0 ) ? true : false;
    }
    public boolean deleteUser(User u) {

        if(myDB == null) openLocalDB();
        long nrAfectedRecs = myDB.delete(dbWrapperSetup.TBL_USER,
                dbWrapperSetup.COL_USER_UID + " = ? ", new String[] {String.valueOf(u.getID())});
        return (nrAfectedRecs > 0) ? true : false;

    }
}
