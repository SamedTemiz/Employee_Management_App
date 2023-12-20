package com.samedtemiz.employee_management_app.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

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
                        "eposta TEXT" + ");";

        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_NAME);
        onCreate(db);
    }
}
