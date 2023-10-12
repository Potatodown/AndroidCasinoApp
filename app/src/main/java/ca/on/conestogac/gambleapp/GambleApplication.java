package ca.on.conestogac.gambleapp;

import android.app.Application;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.sql.Time;
import java.util.ArrayList;

public class GambleApplication extends Application {
    private static final String DB_NAME = "db_stats";
    private static final int DB_VERSION = 1;
    private SQLiteOpenHelper helper;

    @Override
    public void onCreate() {

        helper = new SQLiteOpenHelper(this, DB_NAME, null, DB_VERSION) {

            @Override
            public void onCreate(SQLiteDatabase sqLiteDatabase) {
                sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS tbl_user(" +
                        "money INTEGER, wins INTEGER, loses INTEGER, bears INTEGER, cars INTEGER, houses INTEGER)");
               sqLiteDatabase.execSQL("INSERT INTO tbl_user (money) VALUES(500)");
            }

            @Override
            public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

            }
        };

        super.onCreate();
    }

    public void RemoveMoney(int money) {
        SQLiteDatabase db = helper.getWritableDatabase();
        db.execSQL("INSERT INTO tbl_user (money) VALUES(" + money + ")");
    }

    public void addlose(int lose, int money) {
        SQLiteDatabase db = helper.getWritableDatabase();
        db.execSQL("INSERT INTO tbl_user (money, loses) VALUES(" + money + ", " + lose + ")");
    }

    public void addwin(int win, int money) {
        SQLiteDatabase db = helper.getWritableDatabase();
        db.execSQL("INSERT INTO tbl_user (money, wins) VALUES(" + money + ", " + win + ")");
    }

    public ArrayList getStats() {
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT Sum(money),Sum(wins), Sum(loses) FROM tbl_user", null);
        ArrayList ret = new ArrayList();

        cursor.moveToFirst();
        ret.add(cursor.getInt(0));
        ret.add(cursor.getInt(1));
        ret.add(cursor.getInt(2));
        cursor.close();

        return ret;
    }

    public void buybear(int money, int bear) {
        SQLiteDatabase db = helper.getWritableDatabase();
        db.execSQL("INSERT INTO tbl_user (money, bears) VALUES(" + money + ", "+ bear+")");
    }
    public void buycar(int money, int car) {
        SQLiteDatabase db = helper.getWritableDatabase();
        db.execSQL("INSERT INTO tbl_user (money, cars) VALUES(" + money + ", "+car+")");
    }

    public void buyhouse(int money, int house) {
        SQLiteDatabase db = helper.getWritableDatabase();
        db.execSQL("INSERT INTO tbl_user (money, houses) VALUES(" + money + ","+house+")");
    }

    public ArrayList getPurchases() {
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT Sum(bears),Sum(cars), Sum(houses) FROM tbl_user", null);
        ArrayList ret = new ArrayList();

        cursor.moveToFirst();
        ret.add(cursor.getInt(0));
        ret.add(cursor.getInt(1));
        ret.add(cursor.getInt(2));
        cursor.close();

        return ret;
    }
}
