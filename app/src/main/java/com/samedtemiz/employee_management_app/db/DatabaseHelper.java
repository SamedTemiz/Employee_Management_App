package com.samedtemiz.employee_management_app.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {

    private Context context;

    // Database bilgileri
    private static final String DATABASE_NAME = "BilgiYon.db";
    private static final int DATABASE_VERSION = 1;

    // Tablo bilgileri
    private static final String TABLE_NAME = "employess";

    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query =
                "CREATE TABLE " + TABLE_NAME + "(" +
                        "_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "ad TEXT," +
                        "soyad TEXT," +
                        "pozisyon TEXT," +
                        "departman TEXT," +
                        "telNo TEXT," +
                        "eposta TEXT," +
                        "gorsel BLOB" + ");";

        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean addEmployee(Employee employee) {
        Boolean result = false;

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put("ad", employee.getAd());
        cv.put("soyad", employee.getSoyad());
        cv.put("pozisyon", employee.getPozisyon());
        cv.put("departman", employee.getDepartman());
        cv.put("telNo", employee.getTelNo());
        cv.put("eposta", employee.getEposta());
        cv.put("gorsel", employee.getGorsel());

        try {
            result = db.insert(TABLE_NAME, null, cv) != -1 ? true : false;
        } catch (Exception e) {
            e.getLocalizedMessage();
        } finally {
            db.close();
        }

        return result;
    }

    public Cursor getAllData() {
        String query = "SELECT * FROM " + TABLE_NAME + " ORDER BY _id DESC";
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if (db != null) {
            cursor = db.rawQuery(query, null);
        }

        return cursor;
    }

    public boolean updateEmployee(Employee employee) {
        Boolean result = false;

        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues cv = new ContentValues();

        cv.put("ad", employee.getAd());
        cv.put("soyad", employee.getSoyad());
        cv.put("pozisyon", employee.getPozisyon());
        cv.put("departman", employee.getDepartman());
        cv.put("telNo", employee.getTelNo());
        cv.put("eposta", employee.getEposta());
        cv.put("gorsel", employee.getGorsel());

        try {
            long resultValue = db.update(TABLE_NAME, cv, "_id=?", new String[]{String.valueOf(employee.getId())});
            if (resultValue != -1) {
                result = true;
            }
        } catch (Exception e) {
            e.getLocalizedMessage();
        } finally {
            db.close();
        }

        return result;
    }

    public boolean deleteEmployee(int id) {
        Boolean result = false;

        SQLiteDatabase db = this.getWritableDatabase();

        try {
            long resultValue = db.delete(TABLE_NAME, "_id=?", new String[]{String.valueOf(id)});
            if(resultValue != -1){
                result = true;
            }
        } catch (Exception e) {
            e.getLocalizedMessage();
        }finally {
            db.close();
        }

        return result;
    }
}
