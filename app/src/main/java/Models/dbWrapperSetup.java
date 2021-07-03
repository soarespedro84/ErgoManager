package Models;

import android.database.sqlite.SQLiteDatabase;
import android.media.Image;

public class dbWrapperSetup {
    public static final String BD_NOME = "bdErgoManager";
    public static final int BD_VERSAO = 1;

    public static final String TBL_USER  = "tblUser";

    public static final String COL_USER_AUTONUMBER = "id";
    public static final String COL_USER_UID = "idUser";
    public static final String COL_USER_NAME = "name";
    public static final String COL_USER_EMAIL = "email";
    public static final String COL_USER_PIN = "pin";
    public static final String COL_USER_BIRTHDATE = "birthDate";
    public static final String COL_USER_AGE = "age";
    public static final String COL_USER_GENRE = "genre";
    public static final String COL_USER_HEIGHT = "height";
    public static final String COL_USER_WEIGHT = "weight";
    public static final String COL_USER_IMC = "imc";
    public static final String COL_USER_SMOKER = "smoker";
    public static final String COL_USER_ACTIVE = "active";


    public static final String TBL_NORDIC  = "tblNordic";

    public static final String COL_NORDIC_AUTONUMBER = "id";
    public static final String COL_NORDIC_UID = "idNordic";
    public static final String COL_NORDIC_FKUSER = "fkUser";
    public static final String COL_NORDIC_FILLDATE = "fillDate";
    public static final String COL_NORDIC_NECK = "neck";
    public static final String COL_NORDIC_SHOULDER = "shoulder";
    public static final String COL_NORDIC_UPBACK = "upBack";
    public static final String COL_NORDIC_ELBOW = "elbow";
    public static final String COL_NORDIC_WRSTHAND = "wrstHand";
    public static final String COL_NORDIC_LOWBACK = "lowBack";
    public static final String COL_NORDIC_HIP = "hip";
    public static final String COL_NORDIC_KNEE = "knee";
    public static final String COL_NORDIC_ANKFEET = "ankfeet";


    //CRIAR TABELAS
    public static final String Q_TBL_USER_CREATE =
            "CREATE TABLE " + TBL_USER + " (" +
                    COL_USER_AUTONUMBER + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COL_USER_UID + " TEXT, " +
                    COL_USER_NAME + " TEXT, " +
                    COL_USER_EMAIL + " TEXT, " +
                    COL_USER_PIN + " TEXT, " +
                    COL_USER_BIRTHDATE + " TEXT, "+
                    COL_USER_AGE + " INTEGER, "+
                    COL_USER_GENRE + " TEXT, " +
                    COL_USER_HEIGHT + " REAL, " +
                    COL_USER_WEIGHT + " REAL, " +
                    COL_USER_IMC + " REAL, " +
                    COL_USER_SMOKER + " TEXT, " +
                    COL_USER_ACTIVE + " INTEGER " +
                    ") ";

        public static final String Q_TBL_NORDIC_CREATE =
            "CREATE TABLE " + TBL_NORDIC + " (" +
                    COL_NORDIC_AUTONUMBER + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COL_NORDIC_UID + " TEXT, " +
                    COL_NORDIC_FKUSER + " TEXT, " +
                    COL_NORDIC_FILLDATE + " TEXT, " +
                    COL_NORDIC_NECK + " INTEGER, " +
                    COL_NORDIC_SHOULDER + " INTEGER, " +
                    COL_NORDIC_UPBACK + " INTEGER, " +
                    COL_NORDIC_ELBOW + " INTEGER, " +
                    COL_NORDIC_WRSTHAND + " INTEGER, " +
                    COL_NORDIC_LOWBACK + " INTEGER, " +
                    COL_NORDIC_HIP + " INTEGER, " +
                    COL_NORDIC_KNEE + " INTEGER, " +
                    COL_NORDIC_ANKFEET + " INTEGER " +
                    ") ";

    //estas funções são criadas por nós, não são eventos
    public static void onCreateDatabase(SQLiteDatabase db){
        db.execSQL((Q_TBL_USER_CREATE));
        db.execSQL((Q_TBL_NORDIC_CREATE));
    }

    public static void onUpgradeDatabase(SQLiteDatabase db, int oldVersion, int newVersion){
        db.execSQL("DROP TABLE IF EXISTS "+ TBL_USER);
        db.execSQL("DROP TABLE IF EXISTS "+ TBL_NORDIC);
        onCreateDatabase(db);
    }

}
