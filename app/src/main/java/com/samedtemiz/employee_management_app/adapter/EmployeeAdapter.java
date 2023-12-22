package com.samedtemiz.employee_management_app.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.samedtemiz.employee_management_app.DetailsActivity;
import com.samedtemiz.employee_management_app.databinding.RecyclerRowBinding;
import com.samedtemiz.employee_management_app.db.Employee;
import com.samedtemiz.employee_management_app.db.Singleton;

import java.io.Serializable;
import java.util.ArrayList;

public class EmployeeAdapter extends RecyclerView.Adapter<EmployeeAdapter.EmployeeHolder> {
    public Context context;
    ArrayList<Employee> employeeArrayList;

    public EmployeeAdapter(ArrayList<Employee> employeeArrayList) {
        this.employeeArrayList = employeeArrayList;
    }

    public void setFilteredList(ArrayList<Employee> filteredList){
        this.employeeArrayList = filteredList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public EmployeeHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerRowBinding recyclerRowBinding = RecyclerRowBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new EmployeeHolder(recyclerRowBinding);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull EmployeeHolder holder, @SuppressLint("RecyclerView") int position) {
        byte[] gorsel = employeeArrayList.get(position).getGorsel();
        Bitmap bitmapGorsel = BitmapFactory.decodeByteArray(gorsel, 0, gorsel.length);
        holder.binding.imgGorsel.setImageBitmap(bitmapGorsel);

        holder.binding.txtAdSoyad.setText(employeeArrayList.get(position).getAd() + " " + employeeArrayList.get(position).getSoyad());
        holder.binding.txtPozisyon.setText(employeeArrayList.get(position).getPozisyon());
        holder.binding.txtDepartman.setText(employeeArrayList.get(position).getDepartman());
        holder.binding.mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(holder.itemView.getContext(), DetailsActivity.class);
                //                intent.putExtra("employee", employeeArrayList.get(position));

                Singleton singleton = Singleton.getInstance();
                singleton.setEmployee(employeeArrayList.get(position));

                holder.itemView.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return employeeArrayList.size();
    }

    public class EmployeeHolder extends RecyclerView.ViewHolder {
        private RecyclerRowBinding binding;

        public EmployeeHolder(RecyclerRowBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
