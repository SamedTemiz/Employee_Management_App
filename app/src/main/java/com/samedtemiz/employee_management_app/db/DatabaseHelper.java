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

    public void addEmployee(Employee employee) {
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
            long result = db.insert(TABLE_NAME, null, cv);

            if (result == -1) {
                // Failed
                Toast.makeText(context, "Çalışan ekleme başarısız!", Toast.LENGTH_SHORT).show();
            } else {
                // Success
                Toast.makeText(context, "Çalışan eklendi!", Toast.LENGTH_SHORT).show();
            }

        } catch (Exception e) {
            e.getLocalizedMessage();
        }

        db.close();
    }

    public Cursor getAllData(){
        String query = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if(db != null){
            cursor = db.rawQuery(query, null);
        }

        return cursor;
    }

    public void updateEmployee(Employee employee){
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
            long result = db.update(TABLE_NAME, cv, "_id=?", new String[]{String.valueOf(employee.getId())});

            if (result == -1) {
                // Failed
                Toast.makeText(context, "Güncelleme başarısız!", Toast.LENGTH_SHORT).show();
            } else {
                // Success
                Toast.makeText(context, "Güncelleme başarılı.", Toast.LENGTH_SHORT).show();
            }

        } catch (Exception e) {
            e.getLocalizedMessage();
        }

        db.close();
    }

    public void deleteEmployee(int id){
        SQLiteDatabase db  = this.getWritableDatabase();

        try {
            long result = db.delete(TABLE_NAME, "_id=?", new String[]{String.valueOf(id)});

            if (result == -1) {
                // Failed
                Toast.makeText(context, "Çalışan silme başarısız!", Toast.LENGTH_SHORT).show();
            } else {
                // Success
                Toast.makeText(context, "Silme başarılı.", Toast.LENGTH_SHORT).show();
            }

        } catch (Exception e) {
            e.getLocalizedMessage();
        }

        db.close();
    }

    public void searchEmployee(String searchText){
        SQLiteDatabase db  = this.getReadableDatabase();

        try{
            
        }catch (Exception e){

        }

        db.close();
    }
}
