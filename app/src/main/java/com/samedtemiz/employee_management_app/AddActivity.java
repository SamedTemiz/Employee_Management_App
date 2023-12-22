package com.samedtemiz.employee_management_app;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.ImageDecoder;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.github.dhaval2404.imagepicker.ImagePicker;
import com.samedtemiz.employee_management_app.databinding.ActivityAddBinding;
import com.samedtemiz.employee_management_app.db.DatabaseHelper;
import com.samedtemiz.employee_management_app.db.Employee;
import com.samedtemiz.employee_management_app.utils.FormValidator;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

public class AddActivity extends AppCompatActivity {

    ActivityAddBinding binding;
    Bitmap photo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Ekle butonu
        binding.btnEkle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseHelper dbHelper = new DatabaseHelper(AddActivity.this);

                String ad = binding.txtAd.getText().toString().trim();
                String soyad = binding.txtSoyad.getText().toString().trim();
                String pozisyon = binding.txtPozisyon.getText().toString().trim();
                String departman = binding.txtDepartman.getText().toString().trim();
                String telNo = binding.txtTelno.getText().toString().trim();
                String eposta = binding.txtEposta.getText().toString().trim();

                // Öğelerde bir sorun yoksa
                if (!checkFields()) {
                    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                    photo.compress(Bitmap.CompressFormat.PNG, 50, outputStream);
                    byte[] gorsel = outputStream.toByteArray();

                    // Yeni çalışan için nesne oluşturuluyor
                    Employee employee = new Employee(ad, soyad, pozisyon, departman, telNo, eposta, gorsel);

                    // Nesneyi veri tabanına ekliyoruz
                    dbHelper.addEmployee(employee);

                    clearFields();
                } else {
                    Toast.makeText(AddActivity.this, "Lütfen hataları düzeltin", Toast.LENGTH_LONG).show();
                }
            }
        });


        // Temizle butonu
        binding.btnTemizle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearFields();
            }
        });
    }

    public void addPhoto(View view) {
        requestRuntimePermissions();
    }

    private void startImagePicker() {
        // İzinler verilince çağrılıyor
        ImagePicker.with(AddActivity.this)
                .crop(1f, 1f)
                .compress(1024)
                .start();
    }

    // Seçilen veya çekilen fotoğraf için metot
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Uri uri = data.getData();
        try {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.P) {
                ImageDecoder.Source source = ImageDecoder.createSource(getContentResolver(), uri);
                photo = ImageDecoder.decodeBitmap(source);

                binding.imgPhoto.setImageBitmap(photo);
            } else {
                photo = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);

                binding.imgPhoto.setImageBitmap(photo);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Form öğelerini kontrol için metot
    public boolean checkFields() {
        boolean isError = false;

        Drawable drawable = binding.imgPhoto.getDrawable();
        if (drawable != null && drawable.getConstantState().equals(getResources().getDrawable(R.drawable.take_photo).getConstantState())) {
            isError = true;

            binding.imgPhoto.setBackgroundColor(ActivityCompat.getColor(this, R.color.peach));
        }


        if (FormValidator.isEmpty(binding.txtAd)) {
            FormValidator.setError(binding.txtAd, "Ad alanı boş bırakılamaz.");
            isError = true;
        }

        if (FormValidator.isEmpty(binding.txtSoyad)) {
            FormValidator.setError(binding.txtSoyad, "Soyad alanı boş bırakılamaz.");
            isError = true;
        }

        if (FormValidator.isEmpty(binding.txtPozisyon)) {
            FormValidator.setError(binding.txtPozisyon, "Pozisyon alanı boş bırakılamaz.");
            isError = true;
        }

        if (FormValidator.isEmpty(binding.txtDepartman)) {
            FormValidator.setError(binding.txtDepartman, "Departman alanı boş bırakılamaz.");
            isError = true;
        }

        if (FormValidator.isEmpty(binding.txtTelno)) {
            FormValidator.setError(binding.txtTelno, "Telefon alanı boş bırakılamaz.");
            isError = true;
        }

        if (!FormValidator.isEmpty(binding.txtEposta)) {
            if (!FormValidator.isValidEmail(binding.txtEposta.getText().toString().trim())){
                FormValidator.setError(binding.txtEposta, "Geçerli bir e-posta değil.");
                isError = true;
            }
        }

        return isError;
    }

    // Form öğelerini temizlemek için metot
    public void clearFields() {
        binding.imgPhoto.setImageResource(R.drawable.take_photo);
        binding.imgPhoto.setBackgroundColor(ActivityCompat.getColor(this, R.color.grey));

        binding.txtAd.setText("");
        binding.txtSoyad.setText("");
        binding.txtPozisyon.setText("");
        binding.txtDepartman.setText("");
        binding.txtTelno.setText("");
        binding.txtEposta.setText("");

        binding.txtAd.requestFocus();
    }

    // Kamera ve galeri için izin metot
    public void requestRuntimePermissions() {
        List<String> permissionsNeeded = new ArrayList<>();

        // Kamera izni
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            permissionsNeeded.add(Manifest.permission.CAMERA);
        }

        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.S) { // Android 32 ve altı için
            // Galeri izni
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                permissionsNeeded.add(Manifest.permission.READ_EXTERNAL_STORAGE);
            }
        }

        if (!permissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(this, permissionsNeeded.toArray(new String[0]), 100);
        } else {
            // Kamera ve galeri başlatılıyor
            startImagePicker();
        }
    }

    // İzin durumları kontrol için metot
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 100) {
            boolean allGranted = true;
            for (int result : grantResults) {
                if (result != PackageManager.PERMISSION_GRANTED) {
                    allGranted = false;
                    break;
                }
            }

            if (allGranted) {
                // Kamera ve galeri başlatılıyor
                startImagePicker();
            } else {
                boolean showRationale = false;
                for (String permission : permissions) {
                    if (ActivityCompat.shouldShowRequestPermissionRationale(this, permission)) {
                        showRationale = true;
                        break;
                    }
                }

                if (!showRationale) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setMessage("Bu özellikler için izinler reddedildi. Devam etmek için lütfen ayarlardan izinleri etkinleştirin.")
                            .setTitle("İzin Gerekli")
                            .setCancelable(false)
                            .setNegativeButton("İptal", ((dialog, which) -> dialog.dismiss()))
                            .setPositiveButton("Ayarlar", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                    Uri uri = Uri.fromParts("package", getPackageName(), null);
                                    intent.setData(uri);
                                    startActivity(intent);
                                }
                            });
                    builder.show();
                } else {
                    requestRuntimePermissions();
                }
            }
        }
    }

}