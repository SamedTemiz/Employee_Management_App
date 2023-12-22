package com.samedtemiz.employee_management_app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.samedtemiz.employee_management_app.adapter.EmployeeAdapter;
import com.samedtemiz.employee_management_app.databinding.ActivityMainBinding;
import com.samedtemiz.employee_management_app.db.DatabaseHelper;
import com.samedtemiz.employee_management_app.db.Employee;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    /*
                # Fotoğraf ile birlikte çalışan eklenebilecek ekran
                # Çalışanların listelendiği ekran
                # Çalışan bilgileri detay ekranı
                */
    private ActivityMainBinding binding;
    private RecyclerView recyclerView;
    private EmployeeAdapter employeeAdapter;
    private FloatingActionButton add_button;

    private DatabaseHelper db;
    private ArrayList<Employee> employee_list;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.searchView.clearFocus();
        binding.searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                searchEmployee(newText);
                return true;
            }
        });

        recyclerView = findViewById(R.id.recyclerView);
        add_button = findViewById(R.id.add_button);
        add_button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddActivity.class);
                startActivity(intent);
            }
        });

        db = new DatabaseHelper(this);
        employee_list = new ArrayList<>();

        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        employeeAdapter = new EmployeeAdapter(employee_list);
        binding.recyclerView.setAdapter(employeeAdapter);

        getDataFromDatabase();

        if(employee_list.size() == 0) {
            binding.btnSampleData.setVisibility(View.VISIBLE);
        }else{
            binding.btnSampleData.setVisibility(View.GONE);
        }
    }

    private void searchEmployee(String text) {
        ArrayList<Employee> filteredList = new ArrayList<>();
        for (Employee item : employee_list) {
            if (item.getAd().toLowerCase().contains(text.toLowerCase())
                    || item.getSoyad().toLowerCase().contains(text.toLowerCase())
            ) {
                filteredList.add(item);
            }
        }

        if (!filteredList.isEmpty()) {
            employeeAdapter.setFilteredList(filteredList);
        }
    }

    public void getDataFromDatabase() {
        Cursor cursor = db.getAllData();

        try {

            if (cursor.getCount() == 0) {
                Toast.makeText(this, "Veri yok.", Toast.LENGTH_SHORT).show();
            } else {
                int adIx = cursor.getColumnIndex("ad");
                int soyadIx = cursor.getColumnIndex("soyad");
                int pozisyonIx = cursor.getColumnIndex("pozisyon");
                int departmanIx = cursor.getColumnIndex("departman");
                int telNoIx = cursor.getColumnIndex("telNo");
                int epostaIx = cursor.getColumnIndex("eposta");
                int gorselIx = cursor.getColumnIndex("gorsel");

                while (cursor.moveToNext()) {
                    Employee employee = new Employee(
                            cursor.getString(adIx),
                            cursor.getString(soyadIx),
                            cursor.getString(pozisyonIx),
                            cursor.getString(departmanIx),
                            cursor.getString(telNoIx),
                            cursor.getString(epostaIx),
                            cursor.getBlob(gorselIx)
                    );

                    employee.setId(cursor.getInt(0));

                    employee_list.add(employee);
                }
            }

            employeeAdapter.notifyDataSetChanged();

            cursor.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addSampleData(View view){
        employee_list = Employee.createSampleEmployee(this);
        employeeAdapter.notifyDataSetChanged();

        recreate();
    }
}