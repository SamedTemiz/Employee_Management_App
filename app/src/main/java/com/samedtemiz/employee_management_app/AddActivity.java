package com.samedtemiz.employee_management_app;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class AddActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
    }

    public void clearFields(View view) {
        Toast.makeText(this, "TEMİZLENDİ", Toast.LENGTH_SHORT).show();
    }

    public void addPhoto(View view){
        Toast.makeText(this, "FOTO EKLENDİ", Toast.LENGTH_SHORT).show();
    }
}