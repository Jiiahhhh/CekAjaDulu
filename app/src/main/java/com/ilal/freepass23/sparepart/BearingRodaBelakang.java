package com.ilal.freepass23.sparepart;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import com.ilal.freepass23.DashboardPage;
import com.ilal.freepass23.R;

import java.text.NumberFormat;
import java.util.Locale;

public class BearingRodaBelakang extends AppCompatActivity {

    private static final int CAMERA_PERM_CODE = 101;
    private static final int CAMERA_REQUEST_CODE = 102;
    Button btnOKE, btnTIDAK, btnSelesai;
    EditText edtHarga, edtKomentar;
    ImageView imgFoto;
    String resultButton, resultHarga="0", resultKomentar="-";
    Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bearing_roda_belakang);

        btnOKE = findViewById(R.id.btnBearingRodaBelakangOKE);
        btnTIDAK = findViewById(R.id.btnBearingRodaBelakangTIDAK);
        btnSelesai = findViewById(R.id.btnBearingRodaBelakangSELESAI);
        edtHarga = findViewById(R.id.edtHargaBearingRodaBelakang);
        edtKomentar = findViewById(R.id.edtKomentarBearingRodaBelakang);
        imgFoto = findViewById(R.id.imgFotoBearingRodaBelakang);

        resultKomentar = edtKomentar.getText().toString();

        do {
            btnOKE.setOnClickListener(v -> {
                Toast.makeText(getApplicationContext(), "Tombol OKE ditekan", Toast.LENGTH_SHORT).show();
                resultButton = "Baik";
                btnOKE.setBackground(getResources().getDrawable(R.drawable.green_button));
                btnOKE.setTextColor(getResources().getColor(R.color.black));
                btnTIDAK.setBackground(getResources().getDrawable(R.drawable.button_border_red));
                btnTIDAK.setTextColor(getResources().getColor(R.color.colorPrimary));
            });
            btnTIDAK.setOnClickListener(v -> {
                Toast.makeText(getApplicationContext(), "Tombol TIDAK ditekan", Toast.LENGTH_SHORT).show();
                resultButton = "Tidak Baik";
                btnTIDAK.setBackground(getResources().getDrawable(R.drawable.red_button));
                btnTIDAK.setTextColor(getResources().getColor(R.color.white));
                btnOKE.setBackground(getResources().getDrawable(R.drawable.button_border_red));
                btnOKE.setTextColor(getResources().getColor(R.color.colorPrimary));
            });
        } while (btnOKE.isPressed() || btnTIDAK.isPressed());

        edtHarga.addTextChangedListener(new TextWatcher() {
            private String setEditText = edtHarga.getText().toString().trim();

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().equals(setEditText)) {
                    edtHarga.removeTextChangedListener(this);
                    String replace = s.toString().replaceAll("[Rp. ]", "");
                    if (!replace.isEmpty()) {
                        setEditText = formatRupiah(Double.parseDouble(replace));
                    } else {
                        setEditText = "";
                    }
                    edtHarga.setText(setEditText);
                    edtHarga.setSelection(setEditText.length());
                    edtHarga.addTextChangedListener(this);
                    resultHarga = edtHarga.getText().toString();
                    String becak = resultHarga.copyValueOf("Rp.".toCharArray());
                    resultHarga = resultHarga.replace(becak, "");
                    resultHarga = resultHarga.replace(".", "");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        imgFoto.setOnClickListener(v -> askCameraPermission());

        btnSelesai.setOnClickListener(v -> {
            resultKomentar = edtKomentar.getText().toString();
            if (resultButton == null || bitmap == null){
                Toast.makeText(BearingRodaBelakang.this, "Harap mengisi field yang tersedia", Toast.LENGTH_LONG).show();
            } else {
                Intent intent = new Intent(BearingRodaBelakang.this, DashboardPage.class);
                intent.putExtra("buttonBearingRodaBelakang", resultButton);
                intent.putExtra("hargaBearingRodaBelakang", resultHarga);
                intent.putExtra("komentarBearingRodaBelakang", resultKomentar);
                intent.putExtra("fotoBearingRodaBelakang", bitmap);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }

    private void askCameraPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, CAMERA_PERM_CODE);
        } else {
            openCamera();
        }
    }

    private void openCamera() {
        Intent camera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(camera, CAMERA_REQUEST_CODE);
        Toast.makeText(this, "Harap memposisikan kamera secara landscape", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0 && data == null) {
            bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.inputcamera);
            imgFoto.setImageBitmap(bitmap);
        } else if (resultCode == RESULT_OK) {
            bitmap = (Bitmap) data.getExtras().get("data");
            imgFoto.setImageBitmap(bitmap);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (grantResults.length < 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            openCamera();
        } else {
            Toast.makeText(this, "Izin Camera dibutuhkan", Toast.LENGTH_SHORT).show();
        }
    }

    private String formatRupiah(Double number) {
        Locale localeID = new Locale("IND", "ID");
        NumberFormat numberFormat = NumberFormat.getCurrencyInstance(localeID);
        String formatRupiah = numberFormat.format(number);
        String[] split = formatRupiah.split(",");
        int length = split[0].length();
        return split[0].substring(0, 2) + ". " + split[0].substring(2, length);
    }
}