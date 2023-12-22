package com.samedtemiz.employee_management_app;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageDecoder;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Toast;

import com.github.dhaval2404.imagepicker.ImagePicker;
import com.samedtemiz.employee_management_app.databinding.ActivityDetailsBinding;
import com.samedtemiz.employee_management_app.db.DatabaseHelper;
import com.samedtemiz.employee_management_app.db.Employee;
import com.samedtemiz.employee_management_app.db.Singleton;
import com.samedtemiz.employee_management_app.utils.FormValidator;

import java.io.ByteArrayOutputStream;

public class DetailsActivity extends AppCompatActivity {

    ActivityDetailsBinding binding;
    Employee selectedEmployee;
    Bitmap newPhoto;
    DatabaseHelper dbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        dbHelper = new DatabaseHelper(DetailsActivity.this);

        binding.detailScreen.setVisibility(View.VISIBLE);
        binding.updateScreen.setVisibility(View.INVISIBLE);

        // Ana sayfadan gelen verileri aldık
        getIntentData();

        // Detayları gösteriyoruz
        showDetails();
    }

    public void getIntentData() {
        Singleton singleton = Singleton.getInstance();
        selectedEmployee = singleton.getEmployee();
    }

    public void showDetails(){
        // Detail Screen
        Bitmap bitmapGorsel = BitmapFactory.decodeByteArray(selectedEmployee.getGorsel(), 0, selectedEmployee.getGorsel().length);
        binding.imgDetailGorsel.setImageBitmap(bitmapGorsel);

        binding.txtDetailAdSoyad.setText(selectedEmployee.getAd() + " " + selectedEmployee.getSoyad());
        binding.txtDetailPozisyon.setText(selectedEmployee.getPozisyon());
        binding.txtDetailDepartman.setText(selectedEmployee.getDepartman());
        binding.txtDetailTelefon.setText(selectedEmployee.getTelNo());

        if(selectedEmployee.getEposta().isEmpty()){
            binding.txtDetailEposta.setText("- YOK -");
        }else{
            binding.txtDetailEposta.setText(selectedEmployee.getEposta());
        }

        // Update Screen
        binding.imgUpdGorsel.setImageBitmap(bitmapGorsel);
        binding.txtUpdAd.setText(selectedEmployee.getAd());
        binding.txtUpdSoyad.setText(selectedEmployee.getSoyad());
        binding.txtUpdPozisyon.setText(selectedEmployee.getPozisyon());
        binding.txtUpdDepartman.setText(selectedEmployee.getDepartman());
        binding.txtUpdTelno.setText(selectedEmployee.getTelNo());
        binding.txtUpdEposta.setText(selectedEmployee.getEposta());
    }

    public void backToDetails(View view) {
        binding.detailScreen.setVisibility(View.VISIBLE);

        binding.updateScreen.setVisibility(View.GONE);
    }

    public void goToUpdate(View view) {
        binding.detailScreen.setVisibility(View.GONE);

        binding.updateScreen.setVisibility(View.VISIBLE);
    }

    public void startImagePicker(View view) {
        // İzinler verilince çağrılıyor
        ImagePicker.with(DetailsActivity.this)
                .crop(1f, 1f)
                .compress(1024)
                .start();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Uri uri = data.getData();
        try {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.P) {
                ImageDecoder.Source source = ImageDecoder.createSource(getContentResolver(), uri);
                newPhoto = ImageDecoder.decodeBitmap(source);

                binding.imgUpdGorsel.setImageBitmap(newPhoto);
            } else {
                newPhoto = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);

                binding.imgUpdGorsel.setImageBitmap(newPhoto);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateEmployee(View view){
        String ad = binding.txtUpdAd.getText().toString().trim();
        String soyad = binding.txtUpdSoyad.getText().toString().trim();
        String pozisyon = binding.txtUpdPozisyon.getText().toString().trim();
        String departman = binding.txtUpdDepartman.getText().toString().trim();
        String telNo = binding.txtUpdTelno.getText().toString().trim();
        String eposta = binding.txtUpdEposta.getText().toString().trim();

        // Öğelerde bir sorun yoksa
        if (!checkFields()) {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            newPhoto.compress(Bitmap.CompressFormat.PNG, 50, outputStream);
            byte[] gorsel = outputStream.toByteArray();

            // Çalışan, güncellenmiş bilgileri ile tekrar oluşturuluyor
            Employee employee = new Employee(ad, soyad, pozisyon, departman, telNo, eposta, gorsel);
            employee.setId(selectedEmployee.getId());

            // Nesneyi veri tabanına ekliyoruz
            dbHelper.updateEmployee(employee);

            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(DetailsActivity.this, "Lütfen hataları düzeltin", Toast.LENGTH_LONG).show();
        }
    }

    public void deleteEmployee(View view){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Çalışanı Sil");
        builder.setMessage("Seçilen çalışanı silmek istediğinizden emin misiniz?");

        builder.setPositiveButton("Evet", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // Seçilen çalışanı siliyoruz
                dbHelper.deleteEmployee(selectedEmployee.getId());
                Toast.makeText(getApplicationContext(), "Çalışan silindi!", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(DetailsActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        builder.setNegativeButton("Hayır", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // işlem iptal
                dialog.dismiss();
            }
        });

        builder.show();
    }

    public boolean checkFields() {
        boolean isError = false;

        Drawable drawable = binding.imgUpdGorsel.getDrawable();
        if (drawable != null && drawable.getConstantState().equals(getResources().getDrawable(R.drawable.take_photo).getConstantState())) {
            isError = true;

            binding.imgUpdGorsel.setBackgroundColor(ActivityCompat.getColor(this, R.color.peach));
        }


        if (FormValidator.isEmpty(binding.txtUpdAd)) {
            FormValidator.setError(binding.txtUpdAd, "Ad alanı boş bırakılamaz.");
            isError = true;
        }

        if (FormValidator.isEmpty(binding.txtUpdSoyad)) {
            FormValidator.setError(binding.txtUpdSoyad, "Soyad alanı boş bırakılamaz.");
            isError = true;
        }

        if (FormValidator.isEmpty(binding.txtUpdPozisyon)) {
            FormValidator.setError(binding.txtUpdPozisyon, "Pozisyon alanı boş bırakılamaz.");
            isError = true;
        }

        if (FormValidator.isEmpty(binding.txtUpdDepartman)) {
            FormValidator.setError(binding.txtUpdDepartman, "Departman alanı boş bırakılamaz.");
            isError = true;
        }

        if (FormValidator.isEmpty(binding.txtUpdTelno)) {
            FormValidator.setError(binding.txtUpdTelno, "Telefon alanı boş bırakılamaz.");
            isError = true;
        }

        if (!FormValidator.isEmpty(binding.txtUpdEposta)) {
            if (!FormValidator.isValidEmail(binding.txtUpdEposta.getText().toString().trim())){
                FormValidator.setError(binding.txtUpdEposta, "Geçerli bir e-posta değil.");
                isError = true;
            }
        }

        return isError;
    }
}