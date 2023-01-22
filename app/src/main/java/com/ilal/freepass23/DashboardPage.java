package com.ilal.freepass23;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.graphics.pdf.PdfDocument;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.DynamicLayout;
import android.text.Layout;
import android.text.TextPaint;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.RetryPolicy;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.ilal.freepass23.sparepart.Ban;
import com.ilal.freepass23.sparepart.BateraiAki;
import com.ilal.freepass23.sparepart.BearingRodaBelakang;
import com.ilal.freepass23.sparepart.BearingRodaDepan;
import com.ilal.freepass23.sparepart.Busi;
import com.ilal.freepass23.sparepart.ComSteer;
import com.ilal.freepass23.sparepart.DriveBeltSet;
import com.ilal.freepass23.sparepart.DriveChainKit;
import com.ilal.freepass23.sparepart.EngineSound;
import com.ilal.freepass23.sparepart.GasketCylinder;
import com.ilal.freepass23.sparepart.KabelGas;
import com.ilal.freepass23.sparepart.KabelKopling;
import com.ilal.freepass23.sparepart.KampasRemBelakang;
import com.ilal.freepass23.sparepart.KampasRemDepan;
import com.ilal.freepass23.sparepart.LampuBelakang;
import com.ilal.freepass23.sparepart.LampuDepan;
import com.ilal.freepass23.sparepart.MinyakRem;
import com.ilal.freepass23.sparepart.OliMesin;
import com.ilal.freepass23.sparepart.OliShockbreaker;
import com.ilal.freepass23.sparepart.OliTransmisi;
import com.ilal.freepass23.sparepart.PadSet;
import com.ilal.freepass23.sparepart.PiringanCakram;
import com.ilal.freepass23.sparepart.RadiatorCoolant;
import com.ilal.freepass23.sparepart.SaringanUdara;
import com.ilal.freepass23.sparepart.SealFrontFork;
import com.ilal.freepass23.sparepart.Shockbreaker;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class DashboardPage extends AppCompatActivity {

    Spinner spinBengkel;

    ImageView imgBan, imgAki, imgBearingRodaBelakang, imgBearingRodaDepan, imgBusi, imgComSteer, imgEngineSound, imgGasketCylinder, imgKabelGas;
    ImageView imgKampasRemBelakang, imgKampasRemDepan, imgLampuDepan, imgLampuBelakang, imgMinyakRem, imgOliMesin, imgOliShockbreaker, imgPadSet;
    ImageView imgPiringanCakram, imgRadiatorCoolant, imgSaringanUdara, imgSealFrontFork, imgShockbreaker, imgDriveBeltSet, imgOliTransmisi;
    ImageView imgDriveChainKit, imgKabelKopling;

    ImageView imgCheckBan, imgCheckAki, imgCheckBearingRodaBelakang, imgCheckBearingRodaDepan, imgCheckBusi, imgCheckComSteer,
            imgCheckEngineSound, imgCheckGasketCylinder, imgCheckKabelGas, imgCheckKampasRemBelakang, imgCheckKampasRemDepan, imgCheckLampuDepan,
            imgCheckLampuBelakang, imgCheckMinyakRem, imgCheckOliMesin, imgCheckOliShockbreaker, imgCheckPadSet, imgCheckPiringanCakram,
            imgCheckRadiatorCoolant, imgCheckSaringanUdara, imgCheckSealFrontFork, imgCheckShockbreaker, imgCheckDriveBeltSet, imgCheckOliTransmisi,
            imgCheckDriveChainKit, imgCheckKabelKopling;
    ImageView imgSilangBan, imgSilangAki, imgSilangBearingRodaBelakang, imgSilangBearingRodaDepan, imgSilangBusi, imgSilangComSteer,
            imgSilangEngineSound, imgSilangGasketCylinder, imgSilangKabelGas, imgSilangKampasRemBelakang, imgSilangKampasRemDepan, imgSilangLampuDepan,
            imgSilangLampuBelakang, imgSilangMinyakRem, imgSilangOliMesin, imgSilangOliShockbreaker, imgSilangPadSet, imgSilangPiringanCakram,
            imgSilangRadiatorCoolant, imgSilangSaringanUdara, imgSilangSealFrontFork, imgSilangShockbreaker, imgSilangDriveBeltSet, imgSilangOliTransmisi,
            imgSilangDriveChainKit, imgSilangKabelKopling;

    EditText edtHasilPemeriksaan, edtTanggalPerbaikan, edtNomorAHASS, edtNamaLengkap, edtNomorTelepon;
    EditText edtNomorPlat, edtKilometer, edtTahunPerakitan, edtNomorMesin, edtNamaMekanik;

    RadioGroup rgTipeMotor, rgService;
    RadioButton rbCUB, rbMatic, rbSport, rbAHASS, rbHomeService;

    CheckBox cbBooking, cbMotorkuX;

    Button btnKeluar, btnBersihkan, btnCetak;

    String alamat1, alamat2, tipeMotor = "", service;
    String buttonBan, buttonBateraiAki, buttonBearingRodaBelakang, buttonBearingRodaDepan, buttonBusi, buttonComSteer, buttonEngineSound, buttonGasketCylinder, buttonKabelGas, buttonKampasRemBelakang, buttonKampasRemDepan,
            buttonLampuDepan, buttonLampuBelakang, buttonMinyakRem, buttonOliMesin, buttonOliShockbreaker, buttonPadSet, buttonPiringanCakram, buttonRadiatorCoolant, buttonSaringanUdara, buttonSealFrontFork, buttonShockbreaker,
            buttonDriveBeltSet, buttonOliTransmisi, buttonDriveChainKit, buttonKabelKopling;
    String hargaBan = "0", hargaBateraiAki = "0", hargaBearingRodaBelakang = "0", hargaBearingRodaDepan = "0", hargaBusi = "0", hargaComSteer = "0", hargaEngineSound = "0", hargaGasketCylinder = "0", hargaKabelGas = "0",
            hargaKampasRemBelakang = "0", hargaKampasRemDepan = "0", hargaLampuDepan = "0", hargaLampuBelakang = "0", hargaMinyakRem = "0", hargaOliMesin = "0", hargaOliShockbreaker = "0", hargaPadSet = "0", hargaPiringanCakram = "0",
            hargaRadiatorCoolant = "0", hargaSaringanUdara = "0", hargaSealFrontFork = "0", hargaShockbreaker = "0", hargaDriveBeltSet = "0", hargaOliTransmisi = "0", hargaDriveChainKit = "0", hargaKabelKopling = "0";
    String komentarBan = "", komentarBateraiAki = "", komentarBearingRodaBelakang = "", komentarBearingRodaDepan = "", komentarBusi = "", komentarComSteer = "", komentarEngineSound = "", komentarGasketCylinder = "",
            komentarKabelGas = "", komentarKampasRemBelakang = "", komentarKampasRemDepan = "", komentarLampuDepan = "", komentarLampuBelakang = "", komentarMinyakRem = "", komentarOliMesin = "", komentarOliShockbreaker = "",
            komentarPadSet = "", komentarPiringanCakram = "", komentarRadiatorCoolant = "", komentarSaringanUdara = "", komentarSealFrontFork = "", komentarShockbreaker = "", komentarDriveBeltSet = "", komentarOliTransmisi = "",
            komentarDriveChainKit = "", komentarKabelKopling = "";
    String booking = "-";
    String url;

    Bitmap fotoBan, fotoBateraiAki, fotoBearingRodaBelakang, fotoBearingRodaDepan, fotoBusi, fotoComSteer, fotoEngineSound, fotoGasketCylinder, fotoKabelGas, fotoKampasRemBelakang, fotoKampasRemDepan, fotoLampuDepan,
            fotoLampuBelakang, fotoMinyakRem, fotoOliMesin, fotoOliShockbreaker, fotoPadSet, fotoPiringanCakram, fotoRadiatorCoolant, fotoSaringanUdara, fotoSealFrontFork, fotoShockbreaker, fotoDriveBeltSet, fotoOliTransmisi,
            fotoDriveChainKit, fotoKabelKopling;
    Bitmap scaleBan, scaleBateraiAki, scaleBearingRodaBelakang, scaleBearingRodaDepan, scaleBusi, scaleComSteer, scaleEngineSound, scaleGasketCylinder, scaleKabelGas, scaleKampasRemBelakang, scaleKampasRemDepan, scaleLampuDepan,
            scaleLampuBelakang, scaleMinyakRem, scaleOliMesin, scaleOliShockbreaker, scalePadSet, scalePiringanCakram, scaleRadiatorCoolant, scaleSaringanUdara, scaleSealFrontFork, scaleShockbreaker, scaleDriveBeltSet,
            scaleOliTransmisi, scaleDriveChainKit, scaleKabelKopling;
    Bitmap matic1, matic2, matic3, sport3, cub3;
    Bitmap scaleMatic1, scaleMatic2, scaleMatic3, scaleSport3, scaleCub3;

    String[] detailAlamat;
    String[] urlAHASS;

    Date dateObj;
    DateFormat dateFormat;

    float hargaban, hargabateraiaki, hargabearingrodabelakang, hargabearingrodadepan, hargabusi,
            hargacomsteer, hargaenginesound, hargagasketcylinder, hargakabelgas, hargakampasrembelakang,
            hargakampasremdepan, hargalampudepan, hargalampubelakang, hargaminyakrem, hargaolimesin,
            hargaolishockbreaker, hargapadset, hargapiringancakram, hargaradiatorcoolant, hargasaringanudara,
            hargasealfrontfork, hargashockbreaker, hargadrivebeltset, hargaolitransmisi, hargadrivechainkit,
            hargakabelkopling;
    float totalharga;
    String totalHarga;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_dashboard_page);
        spinBengkel = findViewById(R.id.spinnerNamaBengkel);

        imgBan = findViewById(R.id.imgBan);
        imgAki = findViewById(R.id.imgBateraiAki);
        imgBearingRodaBelakang = findViewById(R.id.imgBearingRodaBelakang);
        imgBearingRodaDepan = findViewById(R.id.imgBearingRodaDepan);
        imgBusi = findViewById(R.id.imgBusi);
        imgComSteer = findViewById(R.id.imgComSteer);
        imgEngineSound = findViewById(R.id.imgEngineSound);
        imgGasketCylinder = findViewById(R.id.imgGasketCylinder);
        imgKabelGas = findViewById(R.id.imgKabelGas);
        imgKampasRemBelakang = findViewById(R.id.imgKampasRemBelakang);
        imgKampasRemDepan = findViewById(R.id.imgKampasRemDepan);
        imgLampuDepan = findViewById(R.id.imgLampuDepan);
        imgLampuBelakang = findViewById(R.id.imgLampuBelakang);
        imgMinyakRem = findViewById(R.id.imgMinyakRem);
        imgOliMesin = findViewById(R.id.imgOliMesin);
        imgOliShockbreaker = findViewById(R.id.imgOliShockbreaker);
        imgPadSet = findViewById(R.id.imgPadSet);
        imgPiringanCakram = findViewById(R.id.imgPiringanCakram);
        imgRadiatorCoolant = findViewById(R.id.imgRadiatorCoolant);
        imgSaringanUdara = findViewById(R.id.imgSaringanUdara);
        imgSealFrontFork = findViewById(R.id.imgSealFrontFork);
        imgShockbreaker = findViewById(R.id.imgShockbreaker);
        imgDriveBeltSet = findViewById(R.id.imgDriveBeltSet);
        imgOliTransmisi = findViewById(R.id.imgOliTransmisi);
        imgDriveChainKit = findViewById(R.id.imgDriveChainKit);
        imgKabelKopling = findViewById(R.id.imgKabelKopling);

        imgCheckBan = findViewById(R.id.imgCheckBan);
        imgCheckAki = findViewById(R.id.imgCheckBateraiAki);
        imgCheckBearingRodaBelakang = findViewById(R.id.imgCheckBearingRodaBelakang);
        imgCheckBearingRodaDepan = findViewById(R.id.imgCheckBearingRodaDepan);
        imgCheckBusi = findViewById(R.id.imgCheckBusi);
        imgCheckComSteer = findViewById(R.id.imgCheckComSteer);
        imgCheckEngineSound = findViewById(R.id.imgCheckEngineSound);
        imgCheckGasketCylinder = findViewById(R.id.imgCheckGasketCylinder);
        imgCheckKabelGas = findViewById(R.id.imgCheckKabelGas);
        imgCheckKampasRemDepan = findViewById(R.id.imgCheckKampasRemDepan);
        imgCheckKampasRemBelakang = findViewById(R.id.imgCheckKampasRemBelakang);
        imgCheckLampuDepan = findViewById(R.id.imgCheckLampuDepan);
        imgCheckLampuBelakang = findViewById(R.id.imgCheckLampuBelakang);
        imgCheckMinyakRem = findViewById(R.id.imgCheckMinyakRem);
        imgCheckOliMesin = findViewById(R.id.imgCheckOliMesin);
        imgCheckOliShockbreaker = findViewById(R.id.imgCheckOliShockbreaker);
        imgCheckPadSet = findViewById(R.id.imgCheckPadSet);
        imgCheckPiringanCakram = findViewById(R.id.imgCheckPiringanCakram);
        imgCheckRadiatorCoolant = findViewById(R.id.imgCheckRadiatorCoolant);
        imgCheckSaringanUdara = findViewById(R.id.imgCheckSaringanUdara);
        imgCheckSealFrontFork = findViewById(R.id.imgCheckSealFrontFork);
        imgCheckShockbreaker = findViewById(R.id.imgCheckShockbreaker);
        imgCheckDriveBeltSet = findViewById(R.id.imgCheckDriveBeltSet);
        imgCheckOliTransmisi = findViewById(R.id.imgCheckOliTransmisi);
        imgCheckDriveChainKit = findViewById(R.id.imgCheckDriveChainKit);
        imgCheckKabelKopling = findViewById(R.id.imgCheckKabelKopling);

        imgSilangBan = findViewById(R.id.imgSilangBan);
        imgSilangAki = findViewById(R.id.imgSilangBateraiAki);
        imgSilangBearingRodaBelakang = findViewById(R.id.imgSilangBearingRodaBelakang);
        imgSilangBearingRodaDepan = findViewById(R.id.imgSilangBearingRodaDepan);
        imgSilangBusi = findViewById(R.id.imgSilangBusi);
        imgSilangComSteer = findViewById(R.id.imgSilangComSteer);
        imgSilangEngineSound = findViewById(R.id.imgSilangEngineSound);
        imgSilangGasketCylinder = findViewById(R.id.imgSilangGasketCylinder);
        imgSilangKabelGas = findViewById(R.id.imgSilangKabelGas);
        imgSilangKampasRemDepan = findViewById(R.id.imgSilangKampasRemDepan);
        imgSilangKampasRemBelakang = findViewById(R.id.imgSilangKampasRemBelakang);
        imgSilangLampuDepan = findViewById(R.id.imgSilangLampuDepan);
        imgSilangLampuBelakang = findViewById(R.id.imgSilangLampuBelakang);
        imgSilangMinyakRem = findViewById(R.id.imgSilangMinyakRem);
        imgSilangOliMesin = findViewById(R.id.imgSilangOliMesin);
        imgSilangOliShockbreaker = findViewById(R.id.imgSilangOliShockbreaker);
        imgSilangPadSet = findViewById(R.id.imgSilangPadSet);
        imgSilangPiringanCakram = findViewById(R.id.imgSilangPiringanCakram);
        imgSilangRadiatorCoolant = findViewById(R.id.imgSilangRadiatorCoolant);
        imgSilangSaringanUdara = findViewById(R.id.imgSilangSaringanUdara);
        imgSilangSealFrontFork = findViewById(R.id.imgSilangSealFrontFork);
        imgSilangShockbreaker = findViewById(R.id.imgSilangShockbreaker);
        imgSilangDriveBeltSet = findViewById(R.id.imgSilangDriveBeltSet);
        imgSilangOliTransmisi = findViewById(R.id.imgSilangOliTransmisi);
        imgSilangDriveChainKit = findViewById(R.id.imgSilangDriveChainKit);
        imgSilangKabelKopling = findViewById(R.id.imgSilangKabelKopling);

        edtHasilPemeriksaan = findViewById(R.id.edtHasilPemeriksaan);
        edtTanggalPerbaikan = findViewById(R.id.edtTanggalPerbaikan);
        edtNomorAHASS = findViewById(R.id.edtNomorAHASS);
        edtNamaLengkap = findViewById(R.id.edtNamaLengkap);
        edtNomorTelepon = findViewById(R.id.edtNomorTelepon);
        edtNomorPlat = findViewById(R.id.edtNomorPlat);
        edtKilometer = findViewById(R.id.edtKilometer);
        edtTahunPerakitan = findViewById(R.id.edtTahunPerakitan);
        edtNomorMesin = findViewById(R.id.edtNomorMesin);
        edtNamaMekanik = findViewById(R.id.edtNamaMekanik);

        rgTipeMotor = findViewById(R.id.rgTipeMotor);
        rgService = findViewById(R.id.rgJenisService);

        rbCUB = findViewById(R.id.rbCUB);
        rbMatic = findViewById(R.id.rbMatic);
        rbSport = findViewById(R.id.rbSport);
        rbAHASS = findViewById(R.id.rbAHASS);
        rbHomeService = findViewById(R.id.rbHomeService);

        cbBooking = findViewById(R.id.checkBooking);
        cbMotorkuX = findViewById(R.id.checkMotorkuX);

        btnCetak = findViewById(R.id.btnCetak);
        btnBersihkan = findViewById(R.id.btnReload);
        btnKeluar = findViewById(R.id.btnLogout);

        detailAlamat = getResources().getStringArray(R.array.detail_alamat);
        urlAHASS = getResources().getStringArray(R.array.Link_gsheet);

        spinBengkel.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                alamat1 = spinBengkel.getItemAtPosition(position).toString();
                alamat2 = detailAlamat[position];
                url = urlAHASS[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        rgTipeMotor.setOnCheckedChangeListener((group, checkedId) -> {
            do {
                if (checkedId == R.id.rbCUB) {
                    imgDriveBeltSet.setVisibility(View.GONE);
                    imgDriveChainKit.setVisibility(View.VISIBLE);
                    imgOliTransmisi.setVisibility(View.GONE);
                    imgKabelKopling.setVisibility(View.GONE);
                    imgCheckOliTransmisi.setVisibility(View.GONE);
                    imgSilangOliTransmisi.setVisibility(View.GONE);
                    imgCheckKabelKopling.setVisibility(View.GONE);
                    imgSilangKabelKopling.setVisibility(View.GONE);
                    imgCheckDriveBeltSet.setVisibility(View.GONE);
                    imgSilangDriveBeltSet.setVisibility(View.GONE);
                    tipeMotor = "CUB";
                } else if (checkedId == R.id.rbMatic) {
                    imgDriveBeltSet.setVisibility(View.VISIBLE);
                    imgOliTransmisi.setVisibility(View.VISIBLE);
                    imgDriveChainKit.setVisibility(View.GONE);
                    imgKabelKopling.setVisibility(View.GONE);
                    imgCheckDriveChainKit.setVisibility(View.GONE);
                    imgSilangDriveChainKit.setVisibility(View.GONE);
                    imgCheckKabelKopling.setVisibility(View.GONE);
                    imgSilangKabelKopling.setVisibility(View.GONE);
                    tipeMotor = "Matic";
                } else if (checkedId == R.id.rbSport) {
                    imgDriveBeltSet.setVisibility(View.GONE);
                    imgOliTransmisi.setVisibility(View.GONE);
                    imgDriveChainKit.setVisibility(View.VISIBLE);
                    imgKabelKopling.setVisibility(View.VISIBLE);
                    imgCheckOliTransmisi.setVisibility(View.GONE);
                    imgSilangOliTransmisi.setVisibility(View.GONE);
                    imgCheckDriveBeltSet.setVisibility(View.GONE);
                    imgSilangDriveBeltSet.setVisibility(View.GONE);
                    tipeMotor = "Sport";
                }
            } while (rgTipeMotor.isPressed());
        });

        edtTanggalPerbaikan.setOnClickListener(v -> {
            Calendar newCalendar = Calendar.getInstance();
            DatePickerDialog datePickerDialog = new DatePickerDialog(DashboardPage.this, (view, year, monthOfYear, dayOfMonth) -> {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                SimpleDateFormat dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
                edtTanggalPerbaikan.setText(dateFormatter.format(newDate.getTime()));
            }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
            datePickerDialog.show();
        });

        rgService.setOnCheckedChangeListener((group, checkedId) -> {
            do {
                if (checkedId == R.id.rbAHASS) {
                    service = "AHAAS";
                } else if (checkedId == R.id.rbHomeService) {
                    service = "Home Service";
                }
            } while (rgService.isPressed());
        });

        if (cbBooking.isChecked() && cbMotorkuX.isChecked()) {
            booking = "Booking & MotorkuX";
        } else if (cbMotorkuX.isChecked()) {
            booking = "MotorkuX";
        } else if (cbBooking.isChecked()) {
            booking = "Booking";
        }

        imgBan.setOnClickListener(v -> intent(Ban.class, 1));
        imgAki.setOnClickListener(v -> intent(BateraiAki.class, 2));
        imgBearingRodaBelakang.setOnClickListener(v -> intent(BearingRodaBelakang.class, 3));
        imgBearingRodaDepan.setOnClickListener(v -> intent(BearingRodaDepan.class, 4));
        imgBusi.setOnClickListener(v -> intent(Busi.class, 5));
        imgComSteer.setOnClickListener(v -> intent(ComSteer.class, 6));
        imgEngineSound.setOnClickListener(v -> intent(EngineSound.class, 7));
        imgGasketCylinder.setOnClickListener(v -> intent(GasketCylinder.class, 8));
        imgKabelGas.setOnClickListener(v -> intent(KabelGas.class, 9));
        imgKampasRemBelakang.setOnClickListener(v -> intent(KampasRemBelakang.class, 10));
        imgKampasRemDepan.setOnClickListener(v -> intent(KampasRemDepan.class, 11));
        imgLampuDepan.setOnClickListener(v -> intent(LampuDepan.class, 12));
        imgLampuBelakang.setOnClickListener(v -> intent(LampuBelakang.class, 13));
        imgMinyakRem.setOnClickListener(v -> intent(MinyakRem.class, 14));
        imgOliMesin.setOnClickListener(v -> intent(OliMesin.class, 15));
        imgOliShockbreaker.setOnClickListener(v -> intent(OliShockbreaker.class, 16));
        imgPadSet.setOnClickListener(v -> intent(PadSet.class, 17));
        imgPiringanCakram.setOnClickListener(v -> intent(PiringanCakram.class, 18));
        imgRadiatorCoolant.setOnClickListener(v -> intent(RadiatorCoolant.class, 19));
        imgSaringanUdara.setOnClickListener(v -> intent(SaringanUdara.class, 20));
        imgSealFrontFork.setOnClickListener(v -> intent(SealFrontFork.class, 21));
        imgShockbreaker.setOnClickListener(v -> intent(Shockbreaker.class, 22));
        imgDriveBeltSet.setOnClickListener(v -> intent(DriveBeltSet.class, 23));
        imgOliTransmisi.setOnClickListener(v -> intent(OliTransmisi.class, 24));
        imgDriveChainKit.setOnClickListener(v -> intent(DriveChainKit.class, 25));
        imgKabelKopling.setOnClickListener(v -> intent(KabelKopling.class, 26));
    }

    public void Reload(View view) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(DashboardPage.this);
        alertDialogBuilder.setTitle("Konfirmasi Reset");
        alertDialogBuilder.setMessage("Apakah Anda ingin menghapus semua data yang telah Anda input?");
        alertDialogBuilder.setCancelable(false);
        alertDialogBuilder.setPositiveButton("Iya", ((dialog, which) -> {
            finish();
            startActivity(getIntent());
        }));
        alertDialogBuilder.setNegativeButton("Tidak", (dialog, which) -> Toast.makeText(DashboardPage.this, "Anda membatalkan reset", Toast.LENGTH_SHORT).show());
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    public void Logout(View view) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(DashboardPage.this);
        alertDialogBuilder.setTitle("Konfirmasi Keluar");
        alertDialogBuilder.setMessage("Apakah Anda ingin keluar dari aplikasi?");
        alertDialogBuilder.setCancelable(false);
        alertDialogBuilder.setPositiveButton("Iya", (dialog, which) -> finish());
        alertDialogBuilder.setNegativeButton("Tidak", (dialog, which) -> Toast.makeText(DashboardPage.this, "Anda tidak jadi keluar", Toast.LENGTH_SHORT).show());
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    public void Cetak(View view) {
        if (edtNamaLengkap.getText().toString().length() != 0 && edtNomorTelepon.getText().toString().length() != 0
                && edtNomorPlat.getText().toString().length() != 0 && !tipeMotor.equals("")
                && edtKilometer.getText().toString().length() != 0 && edtTahunPerakitan.getText().toString().length() != 0
                && edtNomorMesin.getText().toString().length() != 0 && edtNamaMekanik.getText().toString().length() != 0
                && buttonBan != null && buttonBateraiAki != null && buttonBearingRodaBelakang != null
                && buttonBearingRodaDepan != null && buttonBusi != null && buttonComSteer != null && buttonGasketCylinder != null && buttonEngineSound != null
                && buttonKabelGas != null && buttonKampasRemBelakang != null && buttonKampasRemDepan != null
                && buttonLampuDepan != null && buttonLampuBelakang != null && buttonMinyakRem != null
                && buttonOliMesin != null && buttonOliShockbreaker != null && buttonPadSet != null
                && buttonPiringanCakram != null && buttonRadiatorCoolant != null && buttonSaringanUdara != null
                && buttonSealFrontFork != null && buttonShockbreaker != null && edtHasilPemeriksaan.getText().toString().length() != 0
                && edtTanggalPerbaikan.getText().toString().length() != 0) {
            ActivityCompat.requestPermissions(DashboardPage.this, new String[]{
                    Manifest.permission.WRITE_EXTERNAL_STORAGE}, PackageManager.PERMISSION_GRANTED);
            addItemToSheet();
        } else if (edtNamaLengkap.getText().toString().length() == 0) {
            Toast.makeText(DashboardPage.this, "Nama lengkap pelanggan masih kosong", Toast.LENGTH_SHORT).show();
        } else if (edtNomorTelepon.getText().toString().length() == 0) {
            Toast.makeText(DashboardPage.this, "Nomor telepon pelanggan masih kosong", Toast.LENGTH_SHORT).show();
        } else if (tipeMotor.equals("")) {
            Toast.makeText(DashboardPage.this, "Tipe motor belum dipilih", Toast.LENGTH_SHORT).show();
        } else if (edtNomorPlat.getText().toString().length() == 0) {
            Toast.makeText(DashboardPage.this, "Nomor plat motor masih kosong", Toast.LENGTH_SHORT).show();
        } else if (edtKilometer.getText().toString().length() == 0) {
            Toast.makeText(DashboardPage.this, "Kilometer motor masih kosong", Toast.LENGTH_SHORT).show();
        } else if (edtTahunPerakitan.getText().toString().length() == 0) {
            Toast.makeText(DashboardPage.this, "Tahun perakitan motor masih kosong", Toast.LENGTH_SHORT).show();
        } else if (edtNomorMesin.getText().toString().length() == 0) {
            Toast.makeText(DashboardPage.this, "Nomor mesin masih kosong", Toast.LENGTH_SHORT).show();
        } else if (edtTahunPerakitan.getText().toString().length() == 0) {
            Toast.makeText(DashboardPage.this, "Nama mekanik masih kosong", Toast.LENGTH_SHORT).show();
        } else if (buttonBan == null) {
            Toast.makeText(DashboardPage.this, "Ban belum diperiksa", Toast.LENGTH_SHORT).show();
        } else if (buttonBateraiAki == null) {
            Toast.makeText(DashboardPage.this, "Baterai/Aki belum diperiksa", Toast.LENGTH_SHORT).show();
        } else if (buttonBearingRodaBelakang == null) {
            Toast.makeText(DashboardPage.this, "Bearing roda belakang belum diperiksa", Toast.LENGTH_SHORT).show();
        } else if (buttonBearingRodaDepan == null) {
            Toast.makeText(DashboardPage.this, "Bearing roda depan belum diperiksa", Toast.LENGTH_SHORT).show();
        } else if (buttonBusi == null) {
            Toast.makeText(DashboardPage.this, "Busi belum diperiksa", Toast.LENGTH_SHORT).show();
        } else if (buttonComSteer == null) {
            Toast.makeText(DashboardPage.this, "Com Steer belum diperiksa", Toast.LENGTH_SHORT).show();
        } else if (buttonEngineSound == null) {
            Toast.makeText(DashboardPage.this, "Engine sound belum diperiksa", Toast.LENGTH_SHORT).show();
        } else if (buttonGasketCylinder == null) {
            Toast.makeText(DashboardPage.this, "Gasket cylinder cover belum diperiksa", Toast.LENGTH_SHORT).show();
        } else if (buttonKabelGas == null) {
            Toast.makeText(DashboardPage.this, "Kabel gas belum diperiksa", Toast.LENGTH_SHORT).show();
        } else if (buttonKampasRemBelakang == null) {
            Toast.makeText(DashboardPage.this, "Kampas rem belakang belum diperiksa", Toast.LENGTH_SHORT).show();
        } else if (buttonKampasRemDepan == null) {
            Toast.makeText(DashboardPage.this, "Kampas rem depan belum diperiksa", Toast.LENGTH_SHORT).show();
        } else if (buttonLampuDepan == null) {
            Toast.makeText(DashboardPage.this, "Lampu depan belum diperiksa", Toast.LENGTH_SHORT).show();
        } else if (buttonLampuBelakang == null) {
            Toast.makeText(DashboardPage.this, "Lampu belakang belum diperiksa", Toast.LENGTH_SHORT).show();
        } else if (buttonMinyakRem == null) {
            Toast.makeText(DashboardPage.this, "Minyak rem belum diperiksa", Toast.LENGTH_SHORT).show();
        } else if (buttonOliMesin == null) {
            Toast.makeText(DashboardPage.this, "Oli mesin belum diperiksa", Toast.LENGTH_SHORT).show();
        } else if (buttonOliShockbreaker == null) {
            Toast.makeText(DashboardPage.this, "Oli shockbreaker belum diperiksa", Toast.LENGTH_SHORT).show();
        } else if (buttonPadSet == null) {
            Toast.makeText(DashboardPage.this, "Pad set belum diperiksa", Toast.LENGTH_SHORT).show();
        } else if (buttonPiringanCakram == null) {
            Toast.makeText(DashboardPage.this, "Piringan cakram belum diperiksa", Toast.LENGTH_SHORT).show();
        } else if (buttonRadiatorCoolant == null) {
            Toast.makeText(DashboardPage.this, "Radiator coolant belum diperiksa", Toast.LENGTH_SHORT).show();
        } else if (buttonSaringanUdara == null) {
            Toast.makeText(DashboardPage.this, "Saringan udara belum diperiksa", Toast.LENGTH_SHORT).show();
        } else if (buttonSealFrontFork == null) {
            Toast.makeText(DashboardPage.this, "Seal front fork belum diperiksa", Toast.LENGTH_SHORT).show();
        } else if (buttonShockbreaker == null) {
            Toast.makeText(DashboardPage.this, "Shockbreaker belum diperiksa", Toast.LENGTH_SHORT).show();
        } else if (edtHasilPemeriksaan.getText().toString().length() == 0) {
            Toast.makeText(DashboardPage.this, "Hasil pemeriksaan belum diisi", Toast.LENGTH_SHORT).show();
        } else if (edtTanggalPerbaikan.getText().toString().length() == 0){
            Toast.makeText(DashboardPage.this, "Tanggal pemeriksaan berikutnya belum diisi", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(DashboardPage.this, "Ada field yang kosong", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != Activity.RESULT_OK) return;
        switch (requestCode) {
            case 1:
                buttonBan = data.getStringExtra("buttonBan");
                hargaBan = data.getStringExtra("hargaBan");
                komentarBan = data.getStringExtra("komentarBan");
                fotoBan = (Bitmap) data.getExtras().get("fotoBan");
                scaleBan = Bitmap.createScaledBitmap(fotoBan, 512, 280, false);
                if (buttonBan.equals("Baik")) {
                    imgCheckBan.setVisibility(View.VISIBLE);
                    imgSilangBan.setVisibility(View.INVISIBLE);
                } else {
                    imgSilangBan.setVisibility(View.VISIBLE);
                    imgCheckBan.setVisibility(View.INVISIBLE);
                }
                break;
            case 2:
                buttonBateraiAki = data.getStringExtra("buttonBateraiAki");
                hargaBateraiAki = data.getStringExtra("hargaBateraiAki");
                komentarBateraiAki = data.getStringExtra("komentarBateraiAki");
                fotoBateraiAki = (Bitmap) data.getExtras().get("fotoBateraiAki");
                scaleBateraiAki = Bitmap.createScaledBitmap(fotoBateraiAki, 512, 280, false);
                if (buttonBateraiAki.equals("Baik")) {
                    imgCheckAki.setVisibility(View.VISIBLE);
                    imgSilangAki.setVisibility(View.INVISIBLE);
                } else {
                    imgSilangAki.setVisibility(View.VISIBLE);
                    imgCheckAki.setVisibility(View.INVISIBLE);
                }
                break;
            case 3:
                buttonBearingRodaBelakang = data.getStringExtra("buttonBearingRodaBelakang");
                hargaBearingRodaBelakang = data.getStringExtra("hargaBearingRodaBelakang");
                komentarBearingRodaBelakang = data.getStringExtra("komentarBearingRodaBelakang");
                fotoBearingRodaBelakang = (Bitmap) data.getExtras().get("fotoBearingRodaBelakang");
                scaleBearingRodaBelakang = Bitmap.createScaledBitmap(fotoBearingRodaBelakang, 512, 280, false);
                if (buttonBearingRodaBelakang.equals("Baik")) {
                    imgCheckBearingRodaBelakang.setVisibility(View.VISIBLE);
                    imgSilangBearingRodaBelakang.setVisibility(View.INVISIBLE);
                } else {
                    imgSilangBearingRodaBelakang.setVisibility(View.VISIBLE);
                    imgCheckBearingRodaBelakang.setVisibility(View.INVISIBLE);
                }
                break;
            case 4:
                buttonBearingRodaDepan = data.getStringExtra("buttonBearingRodaDepan");
                hargaBearingRodaDepan = data.getStringExtra("hargaBearingRodaDepan");
                komentarBearingRodaDepan = data.getStringExtra("komentarBearingRodaDepan");
                fotoBearingRodaDepan = (Bitmap) data.getExtras().get("fotoBearingRodaDepan");
                scaleBearingRodaDepan = Bitmap.createScaledBitmap(fotoBearingRodaDepan, 512, 280, false);
                if (buttonBearingRodaDepan.equals("Baik")) {
                    imgCheckBearingRodaDepan.setVisibility(View.VISIBLE);
                    imgSilangBearingRodaDepan.setVisibility(View.INVISIBLE);
                } else {
                    imgSilangBearingRodaDepan.setVisibility(View.VISIBLE);
                    imgCheckBearingRodaDepan.setVisibility(View.INVISIBLE);
                }
                break;
            case 5:
                buttonBusi = data.getStringExtra("buttonBusi");
                hargaBusi = data.getStringExtra("hargaBusi");
                komentarBusi = data.getStringExtra("komentarBusi");
                fotoBusi = (Bitmap) data.getExtras().get("fotoBusi");
                scaleBusi = Bitmap.createScaledBitmap(fotoBusi, 512, 280, false);
                if (buttonBusi.equals("Baik")) {
                    imgCheckBusi.setVisibility(View.VISIBLE);
                    imgSilangBusi.setVisibility(View.INVISIBLE);
                } else {
                    imgSilangBusi.setVisibility(View.VISIBLE);
                    imgCheckBusi.setVisibility(View.INVISIBLE);
                }
                break;
            case 6:
                buttonComSteer = data.getStringExtra("buttonComSteer");
                hargaComSteer = data.getStringExtra("hargaComSteer");
                komentarComSteer = data.getStringExtra("komentarComSteer");
                fotoComSteer = (Bitmap) data.getExtras().get("fotoComSteer");
                scaleComSteer = Bitmap.createScaledBitmap(fotoComSteer, 512, 280, false);
                if (buttonComSteer.equals("Baik")) {
                    imgCheckComSteer.setVisibility(View.VISIBLE);
                    imgSilangComSteer.setVisibility(View.INVISIBLE);
                } else {
                    imgSilangComSteer.setVisibility(View.VISIBLE);
                    imgCheckComSteer.setVisibility(View.INVISIBLE);
                }
                break;
            case 7:
                buttonEngineSound = data.getStringExtra("buttonEngineSound");
                hargaEngineSound = data.getStringExtra("hargaEngineSound");
                komentarEngineSound = data.getStringExtra("komentarEngineSound");
                fotoEngineSound = (Bitmap) data.getExtras().get("fotoEngineSound");
                scaleEngineSound = Bitmap.createScaledBitmap(fotoEngineSound, 512, 280, false);
                if (buttonEngineSound.equals("Baik")) {
                    imgCheckEngineSound.setVisibility(View.VISIBLE);
                    imgSilangEngineSound.setVisibility(View.INVISIBLE);
                } else {
                    imgSilangEngineSound.setVisibility(View.VISIBLE);
                    imgCheckEngineSound.setVisibility(View.INVISIBLE);
                }
                break;
            case 8:
                buttonGasketCylinder = data.getStringExtra("buttonGasketCylinder");
                hargaGasketCylinder = data.getStringExtra("hargaGasketCylinder");
                komentarGasketCylinder = data.getStringExtra("komentarGasketCylinder");
                fotoGasketCylinder = (Bitmap) data.getExtras().get("fotoGasketCylinder");
                scaleGasketCylinder = Bitmap.createScaledBitmap(fotoGasketCylinder, 512, 280, false);
                if (buttonGasketCylinder.equals("Baik")) {
                    imgCheckGasketCylinder.setVisibility(View.VISIBLE);
                    imgSilangGasketCylinder.setVisibility(View.INVISIBLE);
                } else {
                    imgSilangGasketCylinder.setVisibility(View.VISIBLE);
                    imgCheckGasketCylinder.setVisibility(View.INVISIBLE);
                }
                break;
            case 9:
                buttonKabelGas = data.getStringExtra("buttonKabelGas");
                hargaKabelGas = data.getStringExtra("hargaKabelGas");
                komentarKabelGas = data.getStringExtra("komentarKabelGas");
                fotoKabelGas = (Bitmap) data.getExtras().get("fotoKabelGas");
                scaleKabelGas = Bitmap.createScaledBitmap(fotoKabelGas, 512, 280, false);
                if (buttonKabelGas.equals("Baik")) {
                    imgCheckKabelGas.setVisibility(View.VISIBLE);
                    imgSilangKabelGas.setVisibility(View.INVISIBLE);
                } else {
                    imgSilangKabelGas.setVisibility(View.VISIBLE);
                    imgCheckKabelGas.setVisibility(View.INVISIBLE);
                }
                break;
            case 10:
                buttonKampasRemBelakang = data.getStringExtra("buttonKampasRemBelakang");
                hargaKampasRemBelakang = data.getStringExtra("hargaKampasRemBelakang");
                komentarKampasRemBelakang = data.getStringExtra("komentarKampasRemBelakang");
                fotoKampasRemBelakang = (Bitmap) data.getExtras().get("fotoKampasRemBelakang");
                scaleKampasRemBelakang = Bitmap.createScaledBitmap(fotoKampasRemBelakang, 512, 280, false);
                if (buttonKampasRemBelakang.equals("Baik")) {
                    imgCheckKampasRemBelakang.setVisibility(View.VISIBLE);
                    imgSilangKampasRemBelakang.setVisibility(View.INVISIBLE);
                } else {
                    imgSilangKampasRemBelakang.setVisibility(View.VISIBLE);
                    imgCheckKampasRemBelakang.setVisibility(View.INVISIBLE);
                }
                break;
            case 11:
                buttonKampasRemDepan = data.getStringExtra("buttonKampasRemDepan");
                hargaKampasRemDepan = data.getStringExtra("hargaKampasRemDepan");
                komentarKampasRemDepan = data.getStringExtra("komentarKampasRemDepan");
                fotoKampasRemDepan = (Bitmap) data.getExtras().get("fotoKampasRemDepan");
                scaleKampasRemDepan = Bitmap.createScaledBitmap(fotoKampasRemDepan, 512, 280, false);
                if (buttonKampasRemDepan.equals("Baik")) {
                    imgCheckKampasRemDepan.setVisibility(View.VISIBLE);
                    imgSilangKampasRemDepan.setVisibility(View.INVISIBLE);
                } else {
                    imgSilangKampasRemDepan.setVisibility(View.VISIBLE);
                    imgCheckKampasRemDepan.setVisibility(View.INVISIBLE);
                }
                break;
            case 12:
                buttonLampuDepan = data.getStringExtra("buttonLampuDepan");
                hargaLampuDepan = data.getStringExtra("hargaLampuDepan");
                komentarLampuDepan = data.getStringExtra("komentarLampuDepan");
                fotoLampuDepan = (Bitmap) data.getExtras().get("fotoLampuDepan");
                scaleLampuDepan = Bitmap.createScaledBitmap(fotoLampuDepan, 512, 280, false);
                if (buttonLampuDepan.equals("Baik")) {
                    imgCheckLampuDepan.setVisibility(View.VISIBLE);
                    imgSilangLampuDepan.setVisibility(View.INVISIBLE);
                } else {
                    imgSilangLampuDepan.setVisibility(View.VISIBLE);
                    imgCheckLampuDepan.setVisibility(View.INVISIBLE);
                }
                break;
            case 13:
                buttonLampuBelakang = data.getStringExtra("buttonLampuBelakang");
                hargaLampuBelakang = data.getStringExtra("hargaLampuBelakang");
                komentarLampuBelakang = data.getStringExtra("komentarLampuBelakang");
                fotoLampuBelakang = (Bitmap) data.getExtras().get("fotoLampuBelakang");
                scaleLampuBelakang = Bitmap.createScaledBitmap(fotoLampuBelakang, 512, 280, false);
                if (buttonLampuBelakang.equals("Baik")) {
                    imgCheckLampuBelakang.setVisibility(View.VISIBLE);
                    imgSilangLampuBelakang.setVisibility(View.INVISIBLE);
                } else {
                    imgSilangLampuBelakang.setVisibility(View.VISIBLE);
                    imgCheckLampuBelakang.setVisibility(View.INVISIBLE);
                }
                break;
            case 14:
                buttonMinyakRem = data.getStringExtra("buttonMinyakRem");
                hargaMinyakRem = data.getStringExtra("hargaMinyakRem");
                komentarMinyakRem = data.getStringExtra("komentarMinyakRem");
                fotoMinyakRem = (Bitmap) data.getExtras().get("fotoMinyakRem");
                scaleMinyakRem = Bitmap.createScaledBitmap(fotoMinyakRem, 512, 280, false);
                if (buttonMinyakRem.equals("Baik")) {
                    imgCheckMinyakRem.setVisibility(View.VISIBLE);
                    imgSilangMinyakRem.setVisibility(View.INVISIBLE);
                } else {
                    imgSilangMinyakRem.setVisibility(View.VISIBLE);
                    imgCheckMinyakRem.setVisibility(View.INVISIBLE);
                }
                break;
            case 15:
                buttonOliMesin = data.getStringExtra("buttonOliMesin");
                hargaOliMesin = data.getStringExtra("hargaOliMesin");
                komentarOliMesin = data.getStringExtra("komentarOliMesin");
                fotoOliMesin = (Bitmap) data.getExtras().get("fotoOliMesin");
                scaleOliMesin = Bitmap.createScaledBitmap(fotoOliMesin, 512, 280, false);
                if (buttonOliMesin.equals("Baik")) {
                    imgCheckOliMesin.setVisibility(View.VISIBLE);
                    imgSilangOliMesin.setVisibility(View.INVISIBLE);
                } else {
                    imgSilangOliMesin.setVisibility(View.VISIBLE);
                    imgCheckOliMesin.setVisibility(View.INVISIBLE);
                }
                break;
            case 16:
                buttonOliShockbreaker = data.getStringExtra("buttonOliShockbreaker");
                hargaOliShockbreaker = data.getStringExtra("hargaOliShockbreaker");
                komentarOliShockbreaker = data.getStringExtra("komentarOliShockbreaker");
                fotoOliShockbreaker = (Bitmap) data.getExtras().get("fotoOliShockbreaker");
                scaleOliShockbreaker = Bitmap.createScaledBitmap(fotoOliShockbreaker, 512, 280, false);
                if (buttonOliShockbreaker.equals("Baik")) {
                    imgCheckOliShockbreaker.setVisibility(View.VISIBLE);
                    imgSilangOliShockbreaker.setVisibility(View.INVISIBLE);
                } else {
                    imgSilangOliShockbreaker.setVisibility(View.VISIBLE);
                    imgCheckOliShockbreaker.setVisibility(View.INVISIBLE);
                }
                break;
            case 17:
                buttonPadSet = data.getStringExtra("buttonPadSet");
                hargaPadSet = data.getStringExtra("hargaPadSet");
                komentarPadSet = data.getStringExtra("komentarPadSet");
                fotoPadSet = (Bitmap) data.getExtras().get("fotoPadSet");
                scalePadSet = Bitmap.createScaledBitmap(fotoPadSet, 512, 280, false);
                if (buttonPadSet.equals("Baik")) {
                    imgCheckPadSet.setVisibility(View.VISIBLE);
                    imgSilangPadSet.setVisibility(View.INVISIBLE);
                } else {
                    imgSilangPadSet.setVisibility(View.VISIBLE);
                    imgCheckPadSet.setVisibility(View.INVISIBLE);
                }
                break;
            case 18:
                buttonPiringanCakram = data.getStringExtra("buttonPiringanCakram");
                hargaPiringanCakram = data.getStringExtra("hargaPiringanCakram");
                komentarPiringanCakram = data.getStringExtra("komentarPiringanCakram");
                fotoPiringanCakram = (Bitmap) data.getExtras().get("fotoPiringanCakram");
                scalePiringanCakram = Bitmap.createScaledBitmap(fotoPiringanCakram, 512, 280, false);
                if (buttonPiringanCakram.equals("Baik")) {
                    imgCheckPiringanCakram.setVisibility(View.VISIBLE);
                    imgSilangPiringanCakram.setVisibility(View.INVISIBLE);
                } else {
                    imgSilangPiringanCakram.setVisibility(View.VISIBLE);
                    imgCheckPiringanCakram.setVisibility(View.INVISIBLE);
                }
                break;
            case 19:
                buttonRadiatorCoolant = data.getStringExtra("buttonRadiatorCoolant");
                hargaRadiatorCoolant = data.getStringExtra("hargaRadiatorCoolant");
                komentarRadiatorCoolant = data.getStringExtra("komentarRadiatorCoolant");
                fotoRadiatorCoolant = (Bitmap) data.getExtras().get("fotoRadiatorCoolant");
                scaleRadiatorCoolant = Bitmap.createScaledBitmap(fotoRadiatorCoolant, 512, 280, false);
                if (buttonRadiatorCoolant.equals("Baik")) {
                    imgCheckRadiatorCoolant.setVisibility(View.VISIBLE);
                    imgSilangRadiatorCoolant.setVisibility(View.INVISIBLE);
                } else {
                    imgSilangRadiatorCoolant.setVisibility(View.VISIBLE);
                    imgCheckRadiatorCoolant.setVisibility(View.INVISIBLE);
                }
                break;
            case 20:
                buttonSaringanUdara = data.getStringExtra("buttonSaringanUdara");
                hargaSaringanUdara = data.getStringExtra("hargaSaringanUdara");
                komentarSaringanUdara = data.getStringExtra("komentarSaringanUdara");
                fotoSaringanUdara = (Bitmap) data.getExtras().get("fotoSaringanUdara");
                scaleSaringanUdara = Bitmap.createScaledBitmap(fotoSaringanUdara, 512, 280, false);
                if (buttonSaringanUdara.equals("Baik")) {
                    imgCheckSaringanUdara.setVisibility(View.VISIBLE);
                    imgSilangSaringanUdara.setVisibility(View.INVISIBLE);
                } else {
                    imgSilangSaringanUdara.setVisibility(View.VISIBLE);
                    imgCheckSaringanUdara.setVisibility(View.INVISIBLE);
                }
                break;
            case 21:
                buttonSealFrontFork = data.getStringExtra("buttonSealFrontFork");
                hargaSealFrontFork = data.getStringExtra("hargaSealFrontFork");
                komentarSealFrontFork = data.getStringExtra("komentarSealFrontFork");
                fotoSealFrontFork = (Bitmap) data.getExtras().get("fotoSealFrontFork");
                scaleSealFrontFork = Bitmap.createScaledBitmap(fotoSealFrontFork, 512, 280, false);
                if (buttonSealFrontFork.equals("Baik")) {
                    imgCheckSealFrontFork.setVisibility(View.VISIBLE);
                    imgSilangSealFrontFork.setVisibility(View.INVISIBLE);
                } else {
                    imgSilangSealFrontFork.setVisibility(View.VISIBLE);
                    imgCheckSealFrontFork.setVisibility(View.INVISIBLE);
                }
                break;
            case 22:
                buttonShockbreaker = data.getStringExtra("buttonShockbreaker");
                hargaShockbreaker = data.getStringExtra("hargaShockbreaker");
                komentarShockbreaker = data.getStringExtra("komentarShockbreaker");
                fotoShockbreaker = (Bitmap) data.getExtras().get("fotoShockbreaker");
                scaleShockbreaker = Bitmap.createScaledBitmap(fotoShockbreaker, 512, 280, false);
                if (buttonShockbreaker.equals("Baik")) {
                    imgCheckShockbreaker.setVisibility(View.VISIBLE);
                    imgSilangShockbreaker.setVisibility(View.INVISIBLE);
                } else {
                    imgSilangShockbreaker.setVisibility(View.VISIBLE);
                    imgCheckShockbreaker.setVisibility(View.INVISIBLE);
                }
                break;
            case 23:
                buttonDriveBeltSet = data.getStringExtra("buttonDriveBeltSet");
                hargaDriveBeltSet = data.getStringExtra("hargaDriveBeltSet");
                komentarDriveBeltSet = data.getStringExtra("komentarDriveBeltSet");
                fotoDriveBeltSet = (Bitmap) data.getExtras().get("fotoDriveBeltSet");
                scaleDriveBeltSet = Bitmap.createScaledBitmap(fotoDriveBeltSet, 512, 280, false);
                if (buttonDriveBeltSet.equals("Baik")) {
                    imgCheckDriveBeltSet.setVisibility(View.VISIBLE);
                    imgSilangDriveBeltSet.setVisibility(View.INVISIBLE);
                } else {
                    imgSilangDriveBeltSet.setVisibility(View.VISIBLE);
                    imgCheckDriveBeltSet.setVisibility(View.INVISIBLE);
                }
                break;
            case 24:
                buttonOliTransmisi = data.getStringExtra("buttonOliTransmisi");
                hargaOliTransmisi = data.getStringExtra("hargaOliTransmisi");
                komentarOliTransmisi = data.getStringExtra("komentarOliTransmisi");
                fotoOliTransmisi = (Bitmap) data.getExtras().get("fotoOliTransmisi");
                scaleOliTransmisi = Bitmap.createScaledBitmap(fotoOliTransmisi, 512, 280, false);
                if (buttonOliTransmisi.equals("Baik")) {
                    imgCheckOliTransmisi.setVisibility(View.VISIBLE);
                    imgSilangOliTransmisi.setVisibility(View.INVISIBLE);
                } else {
                    imgSilangOliTransmisi.setVisibility(View.VISIBLE);
                    imgCheckOliTransmisi.setVisibility(View.INVISIBLE);
                }
                break;
            case 25:
                buttonDriveChainKit = data.getStringExtra("buttonDriveChainKit");
                hargaDriveChainKit = data.getStringExtra("hargaDriveChainKit");
                komentarDriveChainKit = data.getStringExtra("komentarDriveChainKit");
                fotoDriveChainKit = (Bitmap) data.getExtras().get("fotoDriveChainKit");
                scaleDriveChainKit = Bitmap.createScaledBitmap(fotoDriveChainKit, 512, 280, false);
                if (buttonDriveChainKit.equals("Baik")) {
                    imgCheckDriveChainKit.setVisibility(View.VISIBLE);
                    imgSilangDriveChainKit.setVisibility(View.INVISIBLE);
                } else {
                    imgSilangDriveChainKit.setVisibility(View.VISIBLE);
                    imgCheckDriveChainKit.setVisibility(View.INVISIBLE);
                }
                break;
            case 26:
                buttonKabelKopling = data.getStringExtra("buttonKabelKopling");
                hargaKabelKopling = data.getStringExtra("hargaKabelKopling");
                komentarKabelKopling = data.getStringExtra("komentarKabelKopling");
                fotoKabelKopling = (Bitmap) data.getExtras().get("fotoKabelKopling");
                scaleKabelKopling = Bitmap.createScaledBitmap(fotoKabelKopling, 512, 280, false);
                if (buttonKabelKopling.equals("Baik")) {
                    imgCheckKabelKopling.setVisibility(View.VISIBLE);
                    imgSilangKabelKopling.setVisibility(View.INVISIBLE);
                } else {
                    imgSilangKabelKopling.setVisibility(View.VISIBLE);
                    imgCheckKabelKopling.setVisibility(View.INVISIBLE);
                }
                break;
        }
    }

    private void CreatePDFMatic() {

        matic1 = BitmapFactory.decodeResource(getResources(), R.drawable.lembar1matic);
        matic2 = BitmapFactory.decodeResource(getResources(), R.drawable.lembar2matic);
        matic3 = BitmapFactory.decodeResource(getResources(), R.drawable.lembar3matic);
        scaleMatic1 = Bitmap.createScaledBitmap(matic1, 2480, 3898, false);
        scaleMatic2 = Bitmap.createScaledBitmap(matic2, 2480, 3898, false);
        scaleMatic3 = Bitmap.createScaledBitmap(matic3, 2480, 3898, false);

        hargaban = Float.parseFloat(hargaBan);
        hargabateraiaki = Float.parseFloat(hargaBateraiAki);
        hargabearingrodabelakang = Float.parseFloat(hargaBearingRodaBelakang);
        hargabearingrodadepan = Float.parseFloat(hargaBearingRodaDepan);
        hargabusi = Float.parseFloat(hargaBusi);
        hargacomsteer = Float.parseFloat(hargaComSteer);
        hargaenginesound = Float.parseFloat(hargaEngineSound);
        hargagasketcylinder = Float.parseFloat(hargaGasketCylinder);
        hargakabelgas = Float.parseFloat(hargaKabelGas);
        hargakampasrembelakang = Float.parseFloat(hargaKampasRemBelakang);
        hargakampasremdepan = Float.parseFloat(hargaKampasRemDepan);
        hargalampudepan = Float.parseFloat(hargaLampuDepan);
        hargalampubelakang = Float.parseFloat(hargaLampuBelakang);
        hargaminyakrem = Float.parseFloat(hargaMinyakRem);
        hargaolimesin = Float.parseFloat(hargaOliMesin);
        hargaolishockbreaker = Float.parseFloat(hargaOliShockbreaker);
        hargapadset = Float.parseFloat(hargaPadSet);
        hargapiringancakram = Float.parseFloat(hargaPiringanCakram);
        hargaradiatorcoolant = Float.parseFloat(hargaRadiatorCoolant);
        hargasaringanudara = Float.parseFloat(hargaSaringanUdara);
        hargasealfrontfork = Float.parseFloat(hargaSealFrontFork);
        hargashockbreaker = Float.parseFloat(hargaShockbreaker);
        hargadrivebeltset = Float.parseFloat(hargaDriveBeltSet);
        hargaolitransmisi = Float.parseFloat(hargaOliTransmisi);
        hargadrivechainkit = Float.parseFloat(hargaDriveChainKit);
        hargakabelkopling = Float.parseFloat(hargaKabelKopling);

        dateObj = new Date();
        dateFormat = new SimpleDateFormat("E, dd MMMM yyyy");

        PdfDocument pdfDocument = new PdfDocument();
        Paint paint = new Paint();

        PdfDocument.PageInfo pageInfo1 = new PdfDocument.PageInfo.Builder(2480, 3898, 3).create();
        PdfDocument.Page page1 = pdfDocument.startPage(pageInfo1);
        Canvas canvas1 = page1.getCanvas();

        canvas1.drawBitmap(scaleMatic1, 0, 0, paint);

        paint.setTextSize(35f);
        paint.setColor(getResources().getColor(R.color.white));
        paint.setTextAlign(Paint.Align.LEFT);
        canvas1.drawText(alamat1, 42, 315, paint);
        canvas1.drawText(alamat2, 42, 359, paint);

        paint.setTextSize(40f);
        paint.setColor(getResources().getColor(R.color.black));
        paint.setTextAlign(Paint.Align.LEFT);
        canvas1.drawText(edtNamaLengkap.getText().toString(), 1713, 305, paint);
        canvas1.drawText(edtNomorTelepon.getText().toString(), 1713, 356, paint);
        canvas1.drawText(tipeMotor, 1713, 408, paint);
        canvas1.drawText(edtNomorPlat.getText().toString(), 1713, 460, paint);
        canvas1.drawText(edtNomorMesin.getText().toString(), 1713, 512, paint);
        canvas1.drawText(edtTahunPerakitan.getText().toString(), 1713, 564, paint);
        canvas1.drawText(edtKilometer.getText().toString(), 1713, 615, paint);

        paint.setColor(getResources().getColor(R.color.white));
        canvas1.drawText(edtNamaMekanik.getText().toString(), 452, 498, paint);
        canvas1.drawText(dateFormat.format(dateObj), 452, 539, paint);
        canvas1.drawText(edtNomorAHASS.getText().toString(), 452, 581, paint);

        RectF rectKondisi = new RectF(0, 0, 285f, 202f);
        RectF rectHarga = new RectF(0, 0, 389f, 199f);
        RectF rectKeterangan = new RectF(0, 0, 519f, 199f);
        TextPaint textPaint = new TextPaint();
        textPaint.setTextSize(40f);
        textPaint.setColor(getResources().getColor(R.color.black));
        textPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));

        //Ban
        textPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        rectF(canvas1, buttonBan, textPaint, rectKondisi, 589, 961);
        textPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
        String rupiahBan = formatRupiah(Double.parseDouble(String.valueOf(hargaban)));
        rectF(canvas1, rupiahBan, textPaint, rectHarga, 898, 961);
        rectF(canvas1, komentarBan, textPaint, rectKeterangan, 1845, 961);
        canvas1.drawBitmap(scaleBan, 1320, 804, paint);

        //Aki
        textPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        rectF(canvas1, buttonBateraiAki, textPaint, rectKondisi, 589, 1265);
        textPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
        String rupiahAki = formatRupiah(Double.parseDouble(String.valueOf(hargabateraiaki)));
        rectF(canvas1, rupiahAki, textPaint, rectHarga, 898, 1265);
        rectF(canvas1, komentarBateraiAki, textPaint, rectKeterangan, 1845, 1265);
        canvas1.drawBitmap(scaleBateraiAki, 1320, 1107, paint);

        //Bearing Roda Belakang
        textPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        rectF(canvas1, buttonBearingRodaBelakang, textPaint, rectKondisi, 589, 1541);
        textPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
        String rupiahbearingrodabelakang = formatRupiah(Double.parseDouble(String.valueOf(hargabearingrodabelakang)));
        rectF(canvas1, rupiahbearingrodabelakang, textPaint, rectHarga, 898, 1541);
        rectF(canvas1, komentarBearingRodaBelakang, textPaint, rectKeterangan, 1845, 1541);
        canvas1.drawBitmap(scaleBearingRodaBelakang, 1320, 1410, paint);

        //Bearing Roda Depan
        textPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        rectF(canvas1, buttonBearingRodaDepan, textPaint, rectKondisi, 589, 1843);
        textPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
        String rupiahbearingrodadepan = formatRupiah(Double.parseDouble(String.valueOf(hargabearingrodadepan)));
        rectF(canvas1, rupiahbearingrodadepan, textPaint, rectHarga, 898, 1843);
        rectF(canvas1, komentarBearingRodaDepan, textPaint, rectKeterangan, 1845, 1843);
        canvas1.drawBitmap(scaleBearingRodaDepan, 1320, 1714, paint);

        //Busi
        textPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        rectF(canvas1, buttonBusi, textPaint, rectKondisi, 589, 2173);
        textPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
        String rupiahbusi = formatRupiah(Double.parseDouble(String.valueOf(hargabusi)));
        rectF(canvas1, rupiahbusi, textPaint, rectHarga, 898, 2173);
        rectF(canvas1, komentarBusi, textPaint, rectKeterangan, 1845, 2173);
        canvas1.drawBitmap(scaleBusi, 1320, 2017, paint);

        //Com Steer
        textPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        rectF(canvas1, buttonComSteer, textPaint, rectKondisi, 589, 2476);
        textPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
        String rupiahcomsteer = formatRupiah(Double.parseDouble(String.valueOf(hargacomsteer)));
        rectF(canvas1, rupiahcomsteer, textPaint, rectHarga, 898, 2476);
        rectF(canvas1, komentarComSteer, textPaint, rectKeterangan, 1845, 2476);
        canvas1.drawBitmap(scaleComSteer, 1320, 2319, paint);

        //Engine Sound
        textPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        rectF(canvas1, buttonEngineSound, textPaint, rectKondisi, 589, 2780);
        textPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
        String rupiahenginesound = formatRupiah(Double.parseDouble(String.valueOf(hargaenginesound)));
        rectF(canvas1, rupiahenginesound, textPaint, rectHarga, 898, 2780);
        rectF(canvas1, komentarEngineSound, textPaint, rectKeterangan, 1845, 2780);
        canvas1.drawBitmap(scaleEngineSound, 1321, 2622, paint);

        //Gasket Cylinder Cover
        textPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        rectF(canvas1, buttonGasketCylinder, textPaint, rectKondisi, 589, 3053);
        textPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
        String rupiahgasketcylinder = formatRupiah(Double.parseDouble(String.valueOf(hargagasketcylinder)));
        rectF(canvas1, rupiahgasketcylinder, textPaint, rectHarga, 898, 3053);
        rectF(canvas1, komentarGasketCylinder, textPaint, rectKeterangan, 1845, 3053);
        canvas1.drawBitmap(scaleGasketCylinder, 1320, 2925, paint);

        //Kabel Gas
        textPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        rectF(canvas1, buttonKabelGas, textPaint, rectKondisi, 589, 3384);
        textPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
        String rupiahkabelgas = formatRupiah(Double.parseDouble(String.valueOf(hargakabelgas)));
        rectF(canvas1, rupiahkabelgas, textPaint, rectHarga, 898, 3384);
        rectF(canvas1, komentarKabelGas, textPaint, rectKeterangan, 1845, 3384);
        canvas1.drawBitmap(scaleKabelGas, 1320, 3228, paint);

        //Kampas Rem Belakang
        textPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        rectF(canvas1, buttonKampasRemBelakang, textPaint, rectKondisi, 589, 3662);
        textPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
        String rupiahkampasrembelakang = formatRupiah(Double.parseDouble(String.valueOf(hargakampasrembelakang)));
        rectF(canvas1, rupiahkampasrembelakang, textPaint, rectHarga, 898, 3662);
        rectF(canvas1, komentarKampasRemBelakang, textPaint, rectKeterangan, 1845, 3662);
        canvas1.drawBitmap(scaleKampasRemBelakang, 1320, 3532, paint);

        pdfDocument.finishPage(page1);

        PdfDocument.PageInfo pageInfo2 = new PdfDocument.PageInfo.Builder(2480, 3898, 3).create();
        PdfDocument.Page page2 = pdfDocument.startPage(pageInfo2);
        Canvas canvas2 = page2.getCanvas();

        canvas2.drawBitmap(scaleMatic2, 0, 0, paint);

        paint.setTextSize(35f);
        paint.setColor(getResources().getColor(R.color.white));
        paint.setTextAlign(Paint.Align.LEFT);
        canvas2.drawText(alamat1, 42, 315, paint);
        canvas2.drawText(alamat2, 42, 359, paint);

        paint.setTextSize(40f);
        paint.setColor(getResources().getColor(R.color.black));
        paint.setTextAlign(Paint.Align.LEFT);
        canvas2.drawText(edtNamaLengkap.getText().toString(), 1713, 305, paint);
        canvas2.drawText(edtNomorTelepon.getText().toString(), 1713, 356, paint);
        canvas2.drawText(tipeMotor, 1713, 408, paint);
        canvas2.drawText(edtNomorPlat.getText().toString(), 1713, 460, paint);
        canvas2.drawText(edtNomorMesin.getText().toString(), 1713, 512, paint);
        canvas2.drawText(edtTahunPerakitan.getText().toString(), 1713, 564, paint);
        canvas2.drawText(edtKilometer.getText().toString(), 1713, 615, paint);

        paint.setColor(getResources().getColor(R.color.white));
        canvas2.drawText(edtNamaMekanik.getText().toString(), 452, 498, paint);
        canvas2.drawText(dateFormat.format(dateObj), 452, 539, paint);
        canvas2.drawText(edtNomorAHASS.getText().toString(), 452, 581, paint);

        //Kampas Rem Depan
        textPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        rectF(canvas2, buttonKampasRemDepan, textPaint, rectKondisi, 589, 947);
        textPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
        String rupiahkampasremdepan = formatRupiah(Double.parseDouble(String.valueOf(hargakampasremdepan)));
        rectF(canvas2, rupiahkampasremdepan, textPaint, rectHarga, 896, 947);
        rectF(canvas2, komentarKampasRemDepan, textPaint, rectKeterangan, 1841, 947);
        canvas2.drawBitmap(scaleKampasRemDepan, 1317, 818, paint);

        //Lampu Depan
        textPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        rectF(canvas2, buttonLampuDepan, textPaint, rectKondisi, 589, 1275);
        textPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
        String rupiahlampudepan = formatRupiah(Double.parseDouble(String.valueOf(hargalampudepan)));
        rectF(canvas2, rupiahlampudepan, textPaint, rectHarga, 896, 1275);
        rectF(canvas2, komentarLampuDepan, textPaint, rectKeterangan, 1841, 1275);
        canvas2.drawBitmap(scaleLampuDepan, 1317, 1120, paint);

        //Lampu Belakang
        textPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        rectF(canvas2, buttonLampuBelakang, textPaint, rectKondisi, 589, 1561);
        textPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
        String rupiahlampubelakang = formatRupiah(Double.parseDouble(String.valueOf(hargalampubelakang)));
        rectF(canvas2, rupiahlampubelakang, textPaint, rectHarga, 896, 1561);
        rectF(canvas2, komentarLampuBelakang, textPaint, rectKeterangan, 1841, 1561);
        canvas2.drawBitmap(scaleLampuBelakang, 1317, 1421, paint);

        //Minyak Rem
        textPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        rectF(canvas2, buttonMinyakRem, textPaint, rectKondisi, 5089, 1852);
        textPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
        String rupiahminyakrem = formatRupiah(Double.parseDouble(String.valueOf(hargaminyakrem)));
        rectF(canvas2, rupiahminyakrem, textPaint, rectHarga, 896, 1852);
        rectF(canvas2, komentarMinyakRem, textPaint, rectKeterangan, 1841, 1852);
        canvas2.drawBitmap(scaleMinyakRem, 1317, 1724, paint);

        //Oli Mesin
        textPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        rectF(canvas2, buttonOliMesin, textPaint, rectKondisi, 589, 2181);
        textPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
        String rupiaholimesin = formatRupiah(Double.parseDouble(String.valueOf(hargaolimesin)));
        rectF(canvas2, rupiaholimesin, textPaint, rectHarga, 896, 2181);
        rectF(canvas2, komentarOliMesin, textPaint, rectKeterangan, 1841, 2181);
        canvas2.drawBitmap(scaleOliMesin, 1317, 2026, paint);

        //Oli Shockbreaker
        textPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        rectF(canvas2, buttonOliShockbreaker, textPaint, rectKondisi, 589, 2456);
        textPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
        String rupiaholishockbreaker = formatRupiah(Double.parseDouble(String.valueOf(hargaolishockbreaker)));
        rectF(canvas2, rupiaholishockbreaker, textPaint, rectHarga, 896, 2456);
        rectF(canvas2, komentarOliShockbreaker, textPaint, rectKeterangan, 1841, 2456);
        canvas2.drawBitmap(scaleOliShockbreaker, 1317, 2328, paint);

        //Pad Set
        textPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        rectF(canvas2, buttonPadSet, textPaint, rectKondisi, 589, 2786);
        textPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
        String rupiahpadset = formatRupiah(Double.parseDouble(String.valueOf(hargapadset)));
        rectF(canvas2, rupiahpadset, textPaint, rectHarga, 896, 2786);
        rectF(canvas2, komentarPadSet, textPaint, rectKeterangan, 1841, 2786);
        canvas2.drawBitmap(scalePadSet, 1317, 2630, paint);

        //Piringan Cakram
        textPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        rectF(canvas2, buttonPiringanCakram, textPaint, rectKondisi, 589, 3060);
        textPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
        String rupiahpiringancakram = formatRupiah(Double.parseDouble(String.valueOf(hargapiringancakram)));
        rectF(canvas2, rupiahpiringancakram, textPaint, rectHarga, 896, 3060);
        rectF(canvas2, komentarPiringanCakram, textPaint, rectKeterangan, 1841, 3060);
        canvas2.drawBitmap(scalePiringanCakram, 1317, 2932, paint);

        //Radiator Coolant
        textPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        rectF(canvas2, buttonRadiatorCoolant, textPaint, rectKondisi, 589, 3363);
        textPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
        String rupiahradiatorcoolant = formatRupiah(Double.parseDouble(String.valueOf(hargaradiatorcoolant)));
        rectF(canvas2, rupiahradiatorcoolant, textPaint, rectHarga, 896, 3363);
        rectF(canvas2, komentarRadiatorCoolant, textPaint, rectKeterangan, 1841, 3363);
        canvas2.drawBitmap(scaleRadiatorCoolant, 1317, 3234, paint);

        //Saringan Udara
        textPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        rectF(canvas2, buttonSaringanUdara, textPaint, rectKondisi, 589, 3664);
        textPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
        String rupiahsaringanudara = formatRupiah(Double.parseDouble(String.valueOf(hargasaringanudara)));
        rectF(canvas2, rupiahsaringanudara, textPaint, rectHarga, 896, 3664);
        rectF(canvas2, komentarSaringanUdara, textPaint, rectKeterangan, 1841, 3664);
        canvas2.drawBitmap(scaleSaringanUdara, 1317, 3537, paint);

        pdfDocument.finishPage(page2);

        PdfDocument.PageInfo pageInfo3 = new PdfDocument.PageInfo.Builder(2480, 3898, 3).create();
        PdfDocument.Page page3 = pdfDocument.startPage(pageInfo3);
        Canvas canvas3 = page3.getCanvas();

        canvas3.drawBitmap(scaleMatic3, 0, 0, paint);

        paint.setTextSize(35f);
        paint.setColor(getResources().getColor(R.color.white));
        paint.setTextAlign(Paint.Align.LEFT);
        canvas3.drawText(alamat1, 42, 315, paint);
        canvas3.drawText(alamat2, 42, 359, paint);

        paint.setTextSize(40f);
        paint.setColor(getResources().getColor(R.color.black));
        paint.setTextAlign(Paint.Align.LEFT);
        canvas3.drawText(edtNamaLengkap.getText().toString(), 1713, 305, paint);
        canvas3.drawText(edtNomorTelepon.getText().toString(), 1713, 356, paint);
        canvas3.drawText(tipeMotor, 1713, 408, paint);
        canvas3.drawText(edtNomorPlat.getText().toString(), 1713, 460, paint);
        canvas3.drawText(edtNomorMesin.getText().toString(), 1713, 512, paint);
        canvas3.drawText(edtTahunPerakitan.getText().toString(), 1713, 564, paint);
        canvas3.drawText(edtKilometer.getText().toString(), 1713, 615, paint);

        paint.setColor(getResources().getColor(R.color.white));
        canvas3.drawText(edtNamaMekanik.getText().toString(), 452, 498, paint);
        canvas3.drawText(dateFormat.format(dateObj), 452, 539, paint);
        canvas3.drawText(edtNomorAHASS.getText().toString(), 452, 581, paint);

        //Seal Front Fork
        textPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        rectF(canvas3, buttonSealFrontFork, textPaint, rectKondisi, 589, 940);
        textPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
        String rupiahsealfrontfork = formatRupiah(Double.parseDouble(String.valueOf(hargasealfrontfork)));
        rectF(canvas3, rupiahsealfrontfork, textPaint, rectHarga, 895, 940);
        rectF(canvas3, komentarSealFrontFork, textPaint, rectKeterangan, 1841, 940);
        canvas3.drawBitmap(scaleSealFrontFork, 1317, 807, paint);

        //Shockbreaker
        textPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        rectF(canvas3, buttonShockbreaker, textPaint, rectKondisi, 589, 1268);
        textPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
        String rupiahshockbreaker = formatRupiah(Double.parseDouble(String.valueOf(hargashockbreaker)));
        rectF(canvas3, rupiahshockbreaker, textPaint, rectHarga, 895, 1268);
        rectF(canvas3, komentarShockbreaker, textPaint, rectKeterangan, 1841, 1268);
        canvas3.drawBitmap(scaleShockbreaker, 1317, 1112, paint);

        //Drive Belt Set
        textPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        rectF(canvas3, buttonDriveBeltSet, textPaint, rectKondisi, 589, 1574);
        textPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
        String rupiahdrivebeltset = formatRupiah(Double.parseDouble(String.valueOf(hargadrivebeltset)));
        rectF(canvas3, rupiahdrivebeltset, textPaint, rectHarga, 895, 1574);
        rectF(canvas3, komentarDriveBeltSet, textPaint, rectKeterangan, 1841, 1574);
        canvas3.drawBitmap(scaleDriveBeltSet, 1317, 1414, paint);

        //Oli Transmisi
        textPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        rectF(canvas3, buttonOliTransmisi, textPaint, rectKondisi, 589, 1876);
        textPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
        String rupiaholitransmisi = formatRupiah(Double.parseDouble(String.valueOf(hargaolitransmisi)));
        rectF(canvas3, rupiaholitransmisi, textPaint, rectHarga, 895, 1876);
        rectF(canvas3, komentarOliTransmisi, textPaint, rectKeterangan, 1841, 1876);
        canvas3.drawBitmap(scaleOliTransmisi, 1317, 1716, paint);

        totalharga = hargaban + hargabateraiaki + hargabearingrodabelakang + hargabearingrodadepan + hargabusi +
                hargacomsteer + hargaenginesound + hargagasketcylinder + hargakabelgas + hargakampasrembelakang +
                hargakampasremdepan + hargalampudepan + hargalampubelakang + hargaminyakrem + hargaolimesin +
                hargaolishockbreaker + hargapadset + hargapiringancakram + hargaradiatorcoolant + hargasaringanudara +
                hargasealfrontfork + hargashockbreaker + hargadrivebeltset + hargaolitransmisi + hargadrivechainkit +
                hargakabelkopling;
        totalHarga = formatRupiah(Double.parseDouble(String.valueOf(totalharga)));

        paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
        paint.setColor(getResources().getColor(R.color.black));
        canvas3.drawText(totalHarga, 1361, 2057, paint);
        RectF rectHasil = new RectF(0, 0, 2233f, 204f);
        rectF(canvas3, edtHasilPemeriksaan.getText().toString(), textPaint, rectHasil, 109, 2300);

        paint.setColor(getResources().getColor(R.color.black));
        canvas3.drawText(edtTanggalPerbaikan.getText().toString(), 697, 2782, paint);
        canvas3.drawText(service, 697, 2844, paint);
        canvas3.drawText(booking, 697, 2906, paint);

        pdfDocument.finishPage(page3);

        String stringFile2 = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS).getPath() + "Laporan Pemeriksaan Motor " + edtNamaLengkap.getText().toString() + ".pdf";
        String stringFile = Environment.getExternalStorageDirectory().getPath() + File.separator + "Laporan Pemeriksaan Motor " + edtNamaLengkap.getText().toString() + ".pdf";

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            try {
                ContentValues contentValues = new ContentValues();
                contentValues.put(MediaStore.MediaColumns.DISPLAY_NAME, "Laporan Pemeriksaan Motor " + edtNamaLengkap.getText().toString() + ".pdf");
                contentValues.put(MediaStore.MediaColumns.MIME_TYPE, "application/pdf");
                contentValues.put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_DOCUMENTS);
                Uri uri = getContentResolver().insert(MediaStore.Files.getContentUri("external"), contentValues);
                OutputStream outputStream = getContentResolver().openOutputStream(uri);
                pdfDocument.writeTo(outputStream);
                outputStream.close();
                Toast.makeText(DashboardPage.this, "PDF tersimpan di " + stringFile2, Toast.LENGTH_LONG).show();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            File dirExternal = new File(String.valueOf(Environment.getExternalStorageDirectory()));
            File createDir = new File(dirExternal.getAbsolutePath());
            File file = new File(createDir, "Laporan Pemeriksaan Motor " + edtNamaLengkap.getText().toString() + ".pdf");
            try {
                pdfDocument.writeTo(new FileOutputStream(file));
            } catch (IOException e) {
                e.printStackTrace();
            }
            Toast.makeText(DashboardPage.this, "PDF tersimpan di " + stringFile, Toast.LENGTH_SHORT).show();
        }
        pdfDocument.close();
    }

    private void CreatePDFSport() {

        matic1 = BitmapFactory.decodeResource(getResources(), R.drawable.lembar1matic);
        matic2 = BitmapFactory.decodeResource(getResources(), R.drawable.lembar2matic);
        sport3 = BitmapFactory.decodeResource(getResources(), R.drawable.lembar3sport);
        scaleMatic1 = Bitmap.createScaledBitmap(matic1, 2480, 3898, false);
        scaleMatic2 = Bitmap.createScaledBitmap(matic2, 2480, 3898, false);
        scaleSport3 = Bitmap.createScaledBitmap(sport3, 2480, 3898, false);

        hargaban = Float.parseFloat(hargaBan);
        hargabateraiaki = Float.parseFloat(hargaBateraiAki);
        hargabearingrodabelakang = Float.parseFloat(hargaBearingRodaBelakang);
        hargabearingrodadepan = Float.parseFloat(hargaBearingRodaDepan);
        hargabusi = Float.parseFloat(hargaBusi);
        hargacomsteer = Float.parseFloat(hargaComSteer);
        hargaenginesound = Float.parseFloat(hargaEngineSound);
        hargagasketcylinder = Float.parseFloat(hargaGasketCylinder);
        hargakabelgas = Float.parseFloat(hargaKabelGas);
        hargakampasrembelakang = Float.parseFloat(hargaKampasRemBelakang);
        hargakampasremdepan = Float.parseFloat(hargaKampasRemDepan);
        hargalampudepan = Float.parseFloat(hargaLampuDepan);
        hargalampubelakang = Float.parseFloat(hargaLampuBelakang);
        hargaminyakrem = Float.parseFloat(hargaMinyakRem);
        hargaolimesin = Float.parseFloat(hargaOliMesin);
        hargaolishockbreaker = Float.parseFloat(hargaOliShockbreaker);
        hargapadset = Float.parseFloat(hargaPadSet);
        hargapiringancakram = Float.parseFloat(hargaPiringanCakram);
        hargaradiatorcoolant = Float.parseFloat(hargaRadiatorCoolant);
        hargasaringanudara = Float.parseFloat(hargaSaringanUdara);
        hargasealfrontfork = Float.parseFloat(hargaSealFrontFork);
        hargashockbreaker = Float.parseFloat(hargaShockbreaker);
        hargadrivebeltset = Float.parseFloat(hargaDriveBeltSet);
        hargaolitransmisi = Float.parseFloat(hargaOliTransmisi);
        hargadrivechainkit = Float.parseFloat(hargaDriveChainKit);
        hargakabelkopling = Float.parseFloat(hargaKabelKopling);

        dateObj = new Date();
        dateFormat = new SimpleDateFormat("E, dd MMMM yyyy");

        PdfDocument pdfDocument = new PdfDocument();
        Paint paint = new Paint();

        PdfDocument.PageInfo pageInfo1 = new PdfDocument.PageInfo.Builder(2480, 3898, 3).create();
        PdfDocument.Page page1 = pdfDocument.startPage(pageInfo1);
        Canvas canvas1 = page1.getCanvas();

        canvas1.drawBitmap(scaleMatic1, 0, 0, paint);

        paint.setTextSize(35f);
        paint.setColor(getResources().getColor(R.color.white));
        paint.setTextAlign(Paint.Align.LEFT);
        canvas1.drawText(alamat1, 42, 315, paint);
        canvas1.drawText(alamat2, 42, 359, paint);

        paint.setTextSize(40f);
        paint.setColor(getResources().getColor(R.color.black));
        paint.setTextAlign(Paint.Align.LEFT);
        canvas1.drawText(edtNamaLengkap.getText().toString(), 1713, 305, paint);
        canvas1.drawText(edtNomorTelepon.getText().toString(), 1713, 356, paint);
        canvas1.drawText(tipeMotor, 1713, 408, paint);
        canvas1.drawText(edtNomorPlat.getText().toString(), 1713, 460, paint);
        canvas1.drawText(edtNomorMesin.getText().toString(), 1713, 512, paint);
        canvas1.drawText(edtTahunPerakitan.getText().toString(), 1713, 564, paint);
        canvas1.drawText(edtKilometer.getText().toString(), 1713, 615, paint);

        paint.setColor(getResources().getColor(R.color.white));
        canvas1.drawText(edtNamaMekanik.getText().toString(), 452, 498, paint);
        canvas1.drawText(dateFormat.format(dateObj), 452, 539, paint);
        canvas1.drawText(edtNomorAHASS.getText().toString(), 452, 581, paint);

        RectF rectKondisi = new RectF(0, 0, 285f, 202f);
        RectF rectHarga = new RectF(0, 0, 389f, 199f);
        RectF rectKeterangan = new RectF(0, 0, 519f, 199f);
        TextPaint textPaint = new TextPaint();
        textPaint.setTextSize(30f);
        textPaint.setColor(getResources().getColor(R.color.black));
        textPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));

        //Ban
        textPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        rectF(canvas1, buttonBan, textPaint, rectKondisi, 589, 961);
        textPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
        String rupiahBan = formatRupiah(Double.parseDouble(String.valueOf(hargaban)));
        rectF(canvas1, rupiahBan, textPaint, rectHarga, 898, 961);
        rectF(canvas1, komentarBan, textPaint, rectKeterangan, 1845, 961);
        canvas1.drawBitmap(scaleBan, 1320, 804, paint);

        //Aki
        textPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        rectF(canvas1, buttonBateraiAki, textPaint, rectKondisi, 589, 1265);
        textPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
        String rupiahAki = formatRupiah(Double.parseDouble(String.valueOf(hargabateraiaki)));
        rectF(canvas1, rupiahAki, textPaint, rectHarga, 898, 1265);
        rectF(canvas1, komentarBateraiAki, textPaint, rectKeterangan, 1845, 1265);
        canvas1.drawBitmap(scaleBateraiAki, 1320, 1107, paint);

        //Bearing Roda Belakang
        textPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        rectF(canvas1, buttonBearingRodaBelakang, textPaint, rectKondisi, 589, 1541);
        textPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
        String rupiahbearingrodabelakang = formatRupiah(Double.parseDouble(String.valueOf(hargabearingrodabelakang)));
        rectF(canvas1, rupiahbearingrodabelakang, textPaint, rectHarga, 898, 1541);
        rectF(canvas1, komentarBearingRodaBelakang, textPaint, rectKeterangan, 1845, 1541);
        canvas1.drawBitmap(scaleBearingRodaBelakang, 1320, 1410, paint);

        //Bearing Roda Depan
        textPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        rectF(canvas1, buttonBearingRodaDepan, textPaint, rectKondisi, 589, 1843);
        textPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
        String rupiahbearingrodadepan = formatRupiah(Double.parseDouble(String.valueOf(hargabearingrodadepan)));
        rectF(canvas1, rupiahbearingrodadepan, textPaint, rectHarga, 898, 1843);
        rectF(canvas1, komentarBearingRodaDepan, textPaint, rectKeterangan, 1845, 1843);
        canvas1.drawBitmap(scaleBearingRodaDepan, 1320, 1714, paint);

        //Busi
        textPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        rectF(canvas1, buttonBusi, textPaint, rectKondisi, 589, 2173);
        textPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
        String rupiahbusi = formatRupiah(Double.parseDouble(String.valueOf(hargabusi)));
        rectF(canvas1, rupiahbusi, textPaint, rectHarga, 898, 2173);
        rectF(canvas1, komentarBusi, textPaint, rectKeterangan, 1845, 2173);
        canvas1.drawBitmap(scaleBusi, 1320, 2017, paint);

        //Com Steer
        textPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        rectF(canvas1, buttonComSteer, textPaint, rectKondisi, 589, 2476);
        textPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
        String rupiahcomsteer = formatRupiah(Double.parseDouble(String.valueOf(hargacomsteer)));
        rectF(canvas1, rupiahcomsteer, textPaint, rectHarga, 898, 2476);
        rectF(canvas1, komentarComSteer, textPaint, rectKeterangan, 1845, 2476);
        canvas1.drawBitmap(scaleComSteer, 1320, 2319, paint);

        //Engine Sound
        textPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        rectF(canvas1, buttonEngineSound, textPaint, rectKondisi, 589, 2780);
        textPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
        String rupiahenginesound = formatRupiah(Double.parseDouble(String.valueOf(hargaenginesound)));
        rectF(canvas1, rupiahenginesound, textPaint, rectHarga, 898, 2780);
        rectF(canvas1, komentarEngineSound, textPaint, rectKeterangan, 1845, 2780);
        canvas1.drawBitmap(scaleEngineSound, 1321, 2622, paint);

        //Gasket Cylinder Cover
        textPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        rectF(canvas1, buttonGasketCylinder, textPaint, rectKondisi, 589, 3053);
        textPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
        String rupiahgasketcylinder = formatRupiah(Double.parseDouble(String.valueOf(hargagasketcylinder)));
        rectF(canvas1, rupiahgasketcylinder, textPaint, rectHarga, 898, 3053);
        rectF(canvas1, komentarGasketCylinder, textPaint, rectKeterangan, 1845, 3053);
        canvas1.drawBitmap(scaleGasketCylinder, 1320, 2925, paint);

        //Kabel Gas
        textPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        rectF(canvas1, buttonKabelGas, textPaint, rectKondisi, 589, 3384);
        textPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
        String rupiahkabelgas = formatRupiah(Double.parseDouble(String.valueOf(hargakabelgas)));
        rectF(canvas1, rupiahkabelgas, textPaint, rectHarga, 898, 3384);
        rectF(canvas1, komentarKabelGas, textPaint, rectKeterangan, 1845, 3384);
        canvas1.drawBitmap(scaleKabelGas, 1320, 3228, paint);

        //Kampas Rem Belakang
        textPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        rectF(canvas1, buttonKampasRemBelakang, textPaint, rectKondisi, 589, 3662);
        textPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
        String rupiahkampasrembelakang = formatRupiah(Double.parseDouble(String.valueOf(hargakampasrembelakang)));
        rectF(canvas1, rupiahkampasrembelakang, textPaint, rectHarga, 898, 3662);
        rectF(canvas1, komentarKampasRemBelakang, textPaint, rectKeterangan, 1845, 3662);
        canvas1.drawBitmap(scaleKampasRemBelakang, 1320, 3532, paint);

        pdfDocument.finishPage(page1);

        PdfDocument.PageInfo pageInfo2 = new PdfDocument.PageInfo.Builder(2480, 3898, 3).create();
        PdfDocument.Page page2 = pdfDocument.startPage(pageInfo2);
        Canvas canvas2 = page2.getCanvas();

        canvas2.drawBitmap(scaleMatic2, 0, 0, paint);

        paint.setTextSize(35f);
        paint.setColor(getResources().getColor(R.color.white));
        paint.setTextAlign(Paint.Align.LEFT);
        canvas2.drawText(alamat1, 42, 315, paint);
        canvas2.drawText(alamat2, 42, 359, paint);

        paint.setTextSize(40f);
        paint.setColor(getResources().getColor(R.color.black));
        paint.setTextAlign(Paint.Align.LEFT);
        canvas2.drawText(edtNamaLengkap.getText().toString(), 1713, 305, paint);
        canvas2.drawText(edtNomorTelepon.getText().toString(), 1713, 356, paint);
        canvas2.drawText(tipeMotor, 1713, 408, paint);
        canvas2.drawText(edtNomorPlat.getText().toString(), 1713, 460, paint);
        canvas2.drawText(edtNomorMesin.getText().toString(), 1713, 512, paint);
        canvas2.drawText(edtTahunPerakitan.getText().toString(), 1713, 564, paint);
        canvas2.drawText(edtKilometer.getText().toString(), 1713, 615, paint);

        paint.setColor(getResources().getColor(R.color.white));
        canvas2.drawText(edtNamaMekanik.getText().toString(), 452, 498, paint);
        canvas2.drawText(dateFormat.format(dateObj), 452, 539, paint);
        canvas2.drawText(edtNomorAHASS.getText().toString(), 452, 581, paint);

        //Kampas Rem Depan
        textPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        rectF(canvas2, buttonKampasRemDepan, textPaint, rectKondisi, 589, 947);
        textPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
        String rupiahkampasremdepan = formatRupiah(Double.parseDouble(String.valueOf(hargakampasremdepan)));
        rectF(canvas2, rupiahkampasremdepan, textPaint, rectHarga, 896, 947);
        rectF(canvas2, komentarKampasRemDepan, textPaint, rectKeterangan, 1841, 947);
        canvas2.drawBitmap(scaleKampasRemDepan, 1317, 818, paint);

        //Lampu Depan
        textPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        rectF(canvas2, buttonLampuDepan, textPaint, rectKondisi, 589, 1275);
        textPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
        String rupiahlampudepan = formatRupiah(Double.parseDouble(String.valueOf(hargalampudepan)));
        rectF(canvas2, rupiahlampudepan, textPaint, rectHarga, 896, 1275);
        rectF(canvas2, komentarLampuDepan, textPaint, rectKeterangan, 1841, 1275);
        canvas2.drawBitmap(scaleLampuDepan, 1317, 1120, paint);

        //Lampu Belakang
        textPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        rectF(canvas2, buttonLampuBelakang, textPaint, rectKondisi, 589, 1561);
        textPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
        String rupiahlampubelakang = formatRupiah(Double.parseDouble(String.valueOf(hargalampubelakang)));
        rectF(canvas2, rupiahlampubelakang, textPaint, rectHarga, 896, 1561);
        rectF(canvas2, komentarLampuBelakang, textPaint, rectKeterangan, 1841, 1561);
        canvas2.drawBitmap(scaleLampuBelakang, 1317, 1421, paint);

        //Minyak Rem
        textPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        rectF(canvas2, buttonMinyakRem, textPaint, rectKondisi, 5089, 1852);
        textPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
        String rupiahminyakrem = formatRupiah(Double.parseDouble(String.valueOf(hargaminyakrem)));
        rectF(canvas2, rupiahminyakrem, textPaint, rectHarga, 896, 1852);
        rectF(canvas2, komentarMinyakRem, textPaint, rectKeterangan, 1841, 1852);
        canvas2.drawBitmap(scaleMinyakRem, 1317, 1724, paint);

        //Oli Mesin
        textPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        rectF(canvas2, buttonOliMesin, textPaint, rectKondisi, 589, 2181);
        textPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
        String rupiaholimesin = formatRupiah(Double.parseDouble(String.valueOf(hargaolimesin)));
        rectF(canvas2, rupiaholimesin, textPaint, rectHarga, 896, 2181);
        rectF(canvas2, komentarOliMesin, textPaint, rectKeterangan, 1841, 2181);
        canvas2.drawBitmap(scaleOliMesin, 1317, 2026, paint);

        //Oli Shockbreaker
        textPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        rectF(canvas2, buttonOliShockbreaker, textPaint, rectKondisi, 589, 2456);
        textPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
        String rupiaholishockbreaker = formatRupiah(Double.parseDouble(String.valueOf(hargaolishockbreaker)));
        rectF(canvas2, rupiaholishockbreaker, textPaint, rectHarga, 896, 2456);
        rectF(canvas2, komentarOliShockbreaker, textPaint, rectKeterangan, 1841, 2456);
        canvas2.drawBitmap(scaleOliShockbreaker, 1317, 2328, paint);

        //Pad Set
        textPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        rectF(canvas2, buttonPadSet, textPaint, rectKondisi, 589, 2786);
        textPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
        String rupiahpadset = formatRupiah(Double.parseDouble(String.valueOf(hargapadset)));
        rectF(canvas2, rupiahpadset, textPaint, rectHarga, 896, 2786);
        rectF(canvas2, komentarPadSet, textPaint, rectKeterangan, 1841, 2786);
        canvas2.drawBitmap(scalePadSet, 1317, 2630, paint);

        //Piringan Cakram
        textPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        rectF(canvas2, buttonPiringanCakram, textPaint, rectKondisi, 589, 3060);
        textPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
        String rupiahpiringancakram = formatRupiah(Double.parseDouble(String.valueOf(hargapiringancakram)));
        rectF(canvas2, rupiahpiringancakram, textPaint, rectHarga, 896, 3060);
        rectF(canvas2, komentarPiringanCakram, textPaint, rectKeterangan, 1841, 3060);
        canvas2.drawBitmap(scalePiringanCakram, 1317, 2932, paint);

        //Radiator Coolant
        textPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        rectF(canvas2, buttonRadiatorCoolant, textPaint, rectKondisi, 589, 3363);
        textPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
        String rupiahradiatorcoolant = formatRupiah(Double.parseDouble(String.valueOf(hargaradiatorcoolant)));
        rectF(canvas2, rupiahradiatorcoolant, textPaint, rectHarga, 896, 3363);
        rectF(canvas2, komentarRadiatorCoolant, textPaint, rectKeterangan, 1841, 3363);
        canvas2.drawBitmap(scaleRadiatorCoolant, 1317, 3234, paint);

        //Saringan Udara
        textPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        rectF(canvas2, buttonSaringanUdara, textPaint, rectKondisi, 589, 3664);
        textPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
        String rupiahsaringanudara = formatRupiah(Double.parseDouble(String.valueOf(hargasaringanudara)));
        rectF(canvas2, rupiahsaringanudara, textPaint, rectHarga, 896, 3664);
        rectF(canvas2, komentarSaringanUdara, textPaint, rectKeterangan, 1841, 3664);
        canvas2.drawBitmap(scaleSaringanUdara, 1317, 3537, paint);

        pdfDocument.finishPage(page2);

        PdfDocument.PageInfo pageInfo3 = new PdfDocument.PageInfo.Builder(2480, 3898, 3).create();
        PdfDocument.Page page3 = pdfDocument.startPage(pageInfo3);
        Canvas canvas3 = page3.getCanvas();

        canvas3.drawBitmap(scaleSport3, 0, 0, paint);

        paint.setTextSize(35f);
        paint.setColor(getResources().getColor(R.color.white));
        paint.setTextAlign(Paint.Align.LEFT);
        canvas3.drawText(alamat1, 42, 315, paint);
        canvas3.drawText(alamat2, 42, 359, paint);

        paint.setTextSize(40f);
        paint.setColor(getResources().getColor(R.color.black));
        paint.setTextAlign(Paint.Align.LEFT);
        canvas3.drawText(edtNamaLengkap.getText().toString(), 1713, 305, paint);
        canvas3.drawText(edtNomorTelepon.getText().toString(), 1713, 356, paint);
        canvas3.drawText(tipeMotor, 1713, 408, paint);
        canvas3.drawText(edtNomorPlat.getText().toString(), 1713, 460, paint);
        canvas3.drawText(edtNomorMesin.getText().toString(), 1713, 512, paint);
        canvas3.drawText(edtTahunPerakitan.getText().toString(), 1713, 564, paint);
        canvas3.drawText(edtKilometer.getText().toString(), 1713, 615, paint);

        paint.setColor(getResources().getColor(R.color.white));
        canvas3.drawText(edtNamaMekanik.getText().toString(), 452, 498, paint);
        canvas3.drawText(dateFormat.format(dateObj), 452, 539, paint);
        canvas3.drawText(edtNomorAHASS.getText().toString(), 452, 581, paint);

        //Seal Front Fork
        textPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        rectF(canvas3, buttonSealFrontFork, textPaint, rectKondisi, 589, 940);
        textPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
        String rupiahsealfrontfork = formatRupiah(Double.parseDouble(String.valueOf(hargasealfrontfork)));
        rectF(canvas3, rupiahsealfrontfork, textPaint, rectHarga, 895, 940);
        rectF(canvas3, komentarSealFrontFork, textPaint, rectKeterangan, 1841, 940);
        canvas3.drawBitmap(scaleSealFrontFork, 1317, 807, paint);

        //Shockbreaker
        textPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        rectF(canvas3, buttonShockbreaker, textPaint, rectKondisi, 589, 1268);
        textPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
        String rupiahshockbreaker = formatRupiah(Double.parseDouble(String.valueOf(hargashockbreaker)));
        rectF(canvas3, rupiahshockbreaker, textPaint, rectHarga, 895, 1268);
        rectF(canvas3, komentarShockbreaker, textPaint, rectKeterangan, 1841, 1268);
        canvas3.drawBitmap(scaleShockbreaker, 1317, 1112, paint);

        //Drive Chain Kit
        textPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        rectF(canvas3, buttonDriveChainKit, textPaint, rectKondisi, 589, 1574);
        textPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
        String rupiahdrivechainkit = formatRupiah(Double.parseDouble(String.valueOf(hargadrivechainkit)));
        rectF(canvas3, rupiahdrivechainkit, textPaint, rectHarga, 895, 1574);
        rectF(canvas3, komentarDriveChainKit, textPaint, rectKeterangan, 1841, 1574);
        canvas3.drawBitmap(scaleDriveChainKit, 1317, 1414, paint);

        //Kabel Kopling
        textPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        rectF(canvas3, buttonKabelKopling, textPaint, rectKondisi, 589, 1876);
        textPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
        String rupiahkabelkopling = formatRupiah(Double.parseDouble(String.valueOf(hargakabelkopling)));
        rectF(canvas3, rupiahkabelkopling, textPaint, rectHarga, 895, 1876);
        rectF(canvas3, komentarKabelKopling, textPaint, rectKeterangan, 1841, 1876);
        canvas3.drawBitmap(scaleKabelKopling, 1317, 1716, paint);

        totalharga = hargaban + hargabateraiaki + hargabearingrodabelakang + hargabearingrodadepan + hargabusi +
                hargacomsteer + hargaenginesound + hargagasketcylinder + hargakabelgas + hargakampasrembelakang +
                hargakampasremdepan + hargalampudepan + hargalampubelakang + hargaminyakrem + hargaolimesin +
                hargaolishockbreaker + hargapadset + hargapiringancakram + hargaradiatorcoolant + hargasaringanudara +
                hargasealfrontfork + hargashockbreaker + hargadrivebeltset + hargaolitransmisi + hargadrivechainkit +
                hargakabelkopling;
        totalHarga = formatRupiah(Double.parseDouble(String.valueOf(totalharga)));

        paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
        paint.setColor(getResources().getColor(R.color.black));
        canvas3.drawText(totalHarga, 1361, 2057, paint);
        RectF rectHasil = new RectF(0, 0, 2233f, 204f);
        rectF(canvas3, edtHasilPemeriksaan.getText().toString(), textPaint, rectHasil, 109, 2300);

        paint.setColor(getResources().getColor(R.color.black));
        canvas3.drawText(edtTanggalPerbaikan.getText().toString(), 697, 2782, paint);
        canvas3.drawText(service, 697, 2844, paint);
        canvas3.drawText(booking, 697, 2906, paint);

        pdfDocument.finishPage(page3);

        String stringFile2 = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS).getPath() + "Laporan Pemeriksaan Motor " + edtNamaLengkap.getText().toString() + ".pdf";
        String stringFile = Environment.getExternalStorageDirectory().getPath() + File.separator + "Laporan Pemeriksaan Motor " + edtNamaLengkap.getText().toString() + ".pdf";

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            try {
                ContentValues contentValues = new ContentValues();
                contentValues.put(MediaStore.MediaColumns.DISPLAY_NAME, "Laporan Pemeriksaan Motor " + edtNamaLengkap.getText().toString() + ".pdf");
                contentValues.put(MediaStore.MediaColumns.MIME_TYPE, "application/pdf");
                contentValues.put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_DOCUMENTS);
                Uri uri = getContentResolver().insert(MediaStore.Files.getContentUri("external"), contentValues);
                OutputStream outputStream = getContentResolver().openOutputStream(uri);
                pdfDocument.writeTo(outputStream);
                outputStream.close();
                Toast.makeText(DashboardPage.this, "PDF tersimpan di " + stringFile2, Toast.LENGTH_LONG).show();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            File dirExternal = new File(String.valueOf(Environment.getExternalStorageDirectory()));
            File createDir = new File(dirExternal.getAbsolutePath());
            File file = new File(createDir, "Laporan Pemeriksaan Motor " + edtNamaLengkap.getText().toString() + ".pdf");
            try {
                pdfDocument.writeTo(new FileOutputStream(file));
            } catch (IOException e) {
                e.printStackTrace();
            }
            Toast.makeText(DashboardPage.this, "PDF tersimpan di " + stringFile, Toast.LENGTH_SHORT).show();
        }
        pdfDocument.close();
    }

    private void CreatePDFCUB() {

        matic1 = BitmapFactory.decodeResource(getResources(), R.drawable.lembar1matic);
        matic2 = BitmapFactory.decodeResource(getResources(), R.drawable.lembar2matic);
        cub3 = BitmapFactory.decodeResource(getResources(), R.drawable.lembar3cub);
        scaleMatic1 = Bitmap.createScaledBitmap(matic1, 2480, 3898, false);
        scaleMatic2 = Bitmap.createScaledBitmap(matic2, 2480, 3898, false);
        scaleCub3 = Bitmap.createScaledBitmap(cub3, 2480, 3898, false);

        hargaban = Float.parseFloat(hargaBan);
        hargabateraiaki = Float.parseFloat(hargaBateraiAki);
        hargabearingrodabelakang = Float.parseFloat(hargaBearingRodaBelakang);
        hargabearingrodadepan = Float.parseFloat(hargaBearingRodaDepan);
        hargabusi = Float.parseFloat(hargaBusi);
        hargacomsteer = Float.parseFloat(hargaComSteer);
        hargaenginesound = Float.parseFloat(hargaEngineSound);
        hargagasketcylinder = Float.parseFloat(hargaGasketCylinder);
        hargakabelgas = Float.parseFloat(hargaKabelGas);
        hargakampasrembelakang = Float.parseFloat(hargaKampasRemBelakang);
        hargakampasremdepan = Float.parseFloat(hargaKampasRemDepan);
        hargalampudepan = Float.parseFloat(hargaLampuDepan);
        hargalampubelakang = Float.parseFloat(hargaLampuBelakang);
        hargaminyakrem = Float.parseFloat(hargaMinyakRem);
        hargaolimesin = Float.parseFloat(hargaOliMesin);
        hargaolishockbreaker = Float.parseFloat(hargaOliShockbreaker);
        hargapadset = Float.parseFloat(hargaPadSet);
        hargapiringancakram = Float.parseFloat(hargaPiringanCakram);
        hargaradiatorcoolant = Float.parseFloat(hargaRadiatorCoolant);
        hargasaringanudara = Float.parseFloat(hargaSaringanUdara);
        hargasealfrontfork = Float.parseFloat(hargaSealFrontFork);
        hargashockbreaker = Float.parseFloat(hargaShockbreaker);
        hargadrivebeltset = Float.parseFloat(hargaDriveBeltSet);
        hargaolitransmisi = Float.parseFloat(hargaOliTransmisi);
        hargadrivechainkit = Float.parseFloat(hargaDriveChainKit);
        hargakabelkopling = Float.parseFloat(hargaKabelKopling);

        dateObj = new Date();
        dateFormat = new SimpleDateFormat("E, dd MMMM yyyy");

        PdfDocument pdfDocument = new PdfDocument();
        Paint paint = new Paint();

        PdfDocument.PageInfo pageInfo1 = new PdfDocument.PageInfo.Builder(2480, 3898, 3).create();
        PdfDocument.Page page1 = pdfDocument.startPage(pageInfo1);
        Canvas canvas1 = page1.getCanvas();

        canvas1.drawBitmap(scaleMatic1, 0, 0, paint);

        paint.setTextSize(35f);
        paint.setColor(getResources().getColor(R.color.white));
        paint.setTextAlign(Paint.Align.LEFT);
        canvas1.drawText(alamat1, 42, 315, paint);
        canvas1.drawText(alamat2, 42, 359, paint);

        paint.setTextSize(40f);
        paint.setColor(getResources().getColor(R.color.black));
        paint.setTextAlign(Paint.Align.LEFT);
        canvas1.drawText(edtNamaLengkap.getText().toString(), 1713, 305, paint);
        canvas1.drawText(edtNomorTelepon.getText().toString(), 1713, 356, paint);
        canvas1.drawText(tipeMotor, 1713, 408, paint);
        canvas1.drawText(edtNomorPlat.getText().toString(), 1713, 460, paint);
        canvas1.drawText(edtNomorMesin.getText().toString(), 1713, 512, paint);
        canvas1.drawText(edtTahunPerakitan.getText().toString(), 1713, 564, paint);
        canvas1.drawText(edtKilometer.getText().toString(), 1713, 615, paint);

        paint.setColor(getResources().getColor(R.color.white));
        canvas1.drawText(edtNamaMekanik.getText().toString(), 452, 498, paint);
        canvas1.drawText(dateFormat.format(dateObj), 452, 539, paint);
        canvas1.drawText(edtNomorAHASS.getText().toString(), 452, 581, paint);

        RectF rectKondisi = new RectF(0, 0, 285f, 202f);
        RectF rectHarga = new RectF(0, 0, 389f, 199f);
        RectF rectKeterangan = new RectF(0, 0, 519f, 199f);
        TextPaint textPaint = new TextPaint();
        textPaint.setTextSize(30f);
        textPaint.setColor(getResources().getColor(R.color.black));
        textPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));

        //Ban
        textPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        rectF(canvas1, buttonBan, textPaint, rectKondisi, 589, 961);
        textPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
        String rupiahBan = formatRupiah(Double.parseDouble(String.valueOf(hargaban)));
        rectF(canvas1, rupiahBan, textPaint, rectHarga, 898, 961);
        rectF(canvas1, komentarBan, textPaint, rectKeterangan, 1845, 961);
        canvas1.drawBitmap(scaleBan, 1320, 804, paint);

        //Aki
        textPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        rectF(canvas1, buttonBateraiAki, textPaint, rectKondisi, 589, 1265);
        textPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
        String rupiahAki = formatRupiah(Double.parseDouble(String.valueOf(hargabateraiaki)));
        rectF(canvas1, rupiahAki, textPaint, rectHarga, 898, 1265);
        rectF(canvas1, komentarBateraiAki, textPaint, rectKeterangan, 1845, 1265);
        canvas1.drawBitmap(scaleBateraiAki, 1320, 1107, paint);

        //Bearing Roda Belakang
        textPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        rectF(canvas1, buttonBearingRodaBelakang, textPaint, rectKondisi, 589, 1541);
        textPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
        String rupiahbearingrodabelakang = formatRupiah(Double.parseDouble(String.valueOf(hargabearingrodabelakang)));
        rectF(canvas1, rupiahbearingrodabelakang, textPaint, rectHarga, 898, 1541);
        rectF(canvas1, komentarBearingRodaBelakang, textPaint, rectKeterangan, 1845, 1541);
        canvas1.drawBitmap(scaleBearingRodaBelakang, 1320, 1410, paint);

        //Bearing Roda Depan
        textPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        rectF(canvas1, buttonBearingRodaDepan, textPaint, rectKondisi, 589, 1843);
        textPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
        String rupiahbearingrodadepan = formatRupiah(Double.parseDouble(String.valueOf(hargabearingrodadepan)));
        rectF(canvas1, rupiahbearingrodadepan, textPaint, rectHarga, 898, 1843);
        rectF(canvas1, komentarBearingRodaDepan, textPaint, rectKeterangan, 1845, 1843);
        canvas1.drawBitmap(scaleBearingRodaDepan, 1320, 1714, paint);

        //Busi
        textPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        rectF(canvas1, buttonBusi, textPaint, rectKondisi, 589, 2173);
        textPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
        String rupiahbusi = formatRupiah(Double.parseDouble(String.valueOf(hargabusi)));
        rectF(canvas1, rupiahbusi, textPaint, rectHarga, 898, 2173);
        rectF(canvas1, komentarBusi, textPaint, rectKeterangan, 1845, 2173);
        canvas1.drawBitmap(scaleBusi, 1320, 2017, paint);

        //Com Steer
        textPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        rectF(canvas1, buttonComSteer, textPaint, rectKondisi, 589, 2476);
        textPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
        String rupiahcomsteer = formatRupiah(Double.parseDouble(String.valueOf(hargacomsteer)));
        rectF(canvas1, rupiahcomsteer, textPaint, rectHarga, 898, 2476);
        rectF(canvas1, komentarComSteer, textPaint, rectKeterangan, 1845, 2476);
        canvas1.drawBitmap(scaleComSteer, 1320, 2319, paint);

        //Engine Sound
        textPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        rectF(canvas1, buttonEngineSound, textPaint, rectKondisi, 589, 2780);
        textPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
        String rupiahenginesound = formatRupiah(Double.parseDouble(String.valueOf(hargaenginesound)));
        rectF(canvas1, rupiahenginesound, textPaint, rectHarga, 898, 2780);
        rectF(canvas1, komentarEngineSound, textPaint, rectKeterangan, 1845, 2780);
        canvas1.drawBitmap(scaleEngineSound, 1321, 2622, paint);

        //Gasket Cylinder Cover
        textPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        rectF(canvas1, buttonGasketCylinder, textPaint, rectKondisi, 589, 3053);
        textPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
        String rupiahgasketcylinder = formatRupiah(Double.parseDouble(String.valueOf(hargagasketcylinder)));
        rectF(canvas1, rupiahgasketcylinder, textPaint, rectHarga, 898, 3053);
        rectF(canvas1, komentarGasketCylinder, textPaint, rectKeterangan, 1845, 3053);
        canvas1.drawBitmap(scaleGasketCylinder, 1320, 2925, paint);

        //Kabel Gas
        textPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        rectF(canvas1, buttonKabelGas, textPaint, rectKondisi, 589, 3384);
        textPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
        String rupiahkabelgas = formatRupiah(Double.parseDouble(String.valueOf(hargakabelgas)));
        rectF(canvas1, rupiahkabelgas, textPaint, rectHarga, 898, 3384);
        rectF(canvas1, komentarKabelGas, textPaint, rectKeterangan, 1845, 3384);
        canvas1.drawBitmap(scaleKabelGas, 1320, 3228, paint);

        //Kampas Rem Belakang
        textPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        rectF(canvas1, buttonKampasRemBelakang, textPaint, rectKondisi, 589, 3662);
        textPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
        String rupiahkampasrembelakang = formatRupiah(Double.parseDouble(String.valueOf(hargakampasrembelakang)));
        rectF(canvas1, rupiahkampasrembelakang, textPaint, rectHarga, 898, 3662);
        rectF(canvas1, komentarKampasRemBelakang, textPaint, rectKeterangan, 1845, 3662);
        canvas1.drawBitmap(scaleKampasRemBelakang, 1320, 3532, paint);

        pdfDocument.finishPage(page1);

        PdfDocument.PageInfo pageInfo2 = new PdfDocument.PageInfo.Builder(2480, 3898, 3).create();
        PdfDocument.Page page2 = pdfDocument.startPage(pageInfo2);
        Canvas canvas2 = page2.getCanvas();

        canvas2.drawBitmap(scaleMatic2, 0, 0, paint);

        paint.setTextSize(35f);
        paint.setColor(getResources().getColor(R.color.white));
        paint.setTextAlign(Paint.Align.LEFT);
        canvas2.drawText(alamat1, 42, 315, paint);
        canvas2.drawText(alamat2, 42, 359, paint);

        paint.setTextSize(40f);
        paint.setColor(getResources().getColor(R.color.black));
        paint.setTextAlign(Paint.Align.LEFT);
        canvas2.drawText(edtNamaLengkap.getText().toString(), 1713, 305, paint);
        canvas2.drawText(edtNomorTelepon.getText().toString(), 1713, 356, paint);
        canvas2.drawText(tipeMotor, 1713, 408, paint);
        canvas2.drawText(edtNomorPlat.getText().toString(), 1713, 460, paint);
        canvas2.drawText(edtNomorMesin.getText().toString(), 1713, 512, paint);
        canvas2.drawText(edtTahunPerakitan.getText().toString(), 1713, 564, paint);
        canvas2.drawText(edtKilometer.getText().toString(), 1713, 615, paint);

        paint.setColor(getResources().getColor(R.color.white));
        canvas2.drawText(edtNamaMekanik.getText().toString(), 452, 498, paint);
        canvas2.drawText(dateFormat.format(dateObj), 452, 539, paint);
        canvas2.drawText(edtNomorAHASS.getText().toString(), 452, 581, paint);

        //Kampas Rem Depan
        textPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        rectF(canvas2, buttonKampasRemDepan, textPaint, rectKondisi, 589, 947);
        textPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
        String rupiahkampasremdepan = formatRupiah(Double.parseDouble(String.valueOf(hargakampasremdepan)));
        rectF(canvas2, rupiahkampasremdepan, textPaint, rectHarga, 896, 947);
        rectF(canvas2, komentarKampasRemDepan, textPaint, rectKeterangan, 1841, 947);
        canvas2.drawBitmap(scaleKampasRemDepan, 1317, 818, paint);

        //Lampu Depan
        textPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        rectF(canvas2, buttonLampuDepan, textPaint, rectKondisi, 589, 1275);
        textPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
        String rupiahlampudepan = formatRupiah(Double.parseDouble(String.valueOf(hargalampudepan)));
        rectF(canvas2, rupiahlampudepan, textPaint, rectHarga, 896, 1275);
        rectF(canvas2, komentarLampuDepan, textPaint, rectKeterangan, 1841, 1275);
        canvas2.drawBitmap(scaleLampuDepan, 1317, 1120, paint);

        //Lampu Belakang
        textPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        rectF(canvas2, buttonLampuBelakang, textPaint, rectKondisi, 589, 1561);
        textPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
        String rupiahlampubelakang = formatRupiah(Double.parseDouble(String.valueOf(hargalampubelakang)));
        rectF(canvas2, rupiahlampubelakang, textPaint, rectHarga, 896, 1561);
        rectF(canvas2, komentarLampuBelakang, textPaint, rectKeterangan, 1841, 1561);
        canvas2.drawBitmap(scaleLampuBelakang, 1317, 1421, paint);

        //Minyak Rem
        textPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        rectF(canvas2, buttonMinyakRem, textPaint, rectKondisi, 5089, 1852);
        textPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
        String rupiahminyakrem = formatRupiah(Double.parseDouble(String.valueOf(hargaminyakrem)));
        rectF(canvas2, rupiahminyakrem, textPaint, rectHarga, 896, 1852);
        rectF(canvas2, komentarMinyakRem, textPaint, rectKeterangan, 1841, 1852);
        canvas2.drawBitmap(scaleMinyakRem, 1317, 1724, paint);

        //Oli Mesin
        textPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        rectF(canvas2, buttonOliMesin, textPaint, rectKondisi, 589, 2181);
        textPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
        String rupiaholimesin = formatRupiah(Double.parseDouble(String.valueOf(hargaolimesin)));
        rectF(canvas2, rupiaholimesin, textPaint, rectHarga, 896, 2181);
        rectF(canvas2, komentarOliMesin, textPaint, rectKeterangan, 1841, 2181);
        canvas2.drawBitmap(scaleOliMesin, 1317, 2026, paint);

        //Oli Shockbreaker
        textPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        rectF(canvas2, buttonOliShockbreaker, textPaint, rectKondisi, 589, 2456);
        textPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
        String rupiaholishockbreaker = formatRupiah(Double.parseDouble(String.valueOf(hargaolishockbreaker)));
        rectF(canvas2, rupiaholishockbreaker, textPaint, rectHarga, 896, 2456);
        rectF(canvas2, komentarOliShockbreaker, textPaint, rectKeterangan, 1841, 2456);
        canvas2.drawBitmap(scaleOliShockbreaker, 1317, 2328, paint);

        //Pad Set
        textPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        rectF(canvas2, buttonPadSet, textPaint, rectKondisi, 589, 2786);
        textPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
        String rupiahpadset = formatRupiah(Double.parseDouble(String.valueOf(hargapadset)));
        rectF(canvas2, rupiahpadset, textPaint, rectHarga, 896, 2786);
        rectF(canvas2, komentarPadSet, textPaint, rectKeterangan, 1841, 2786);
        canvas2.drawBitmap(scalePadSet, 1317, 2630, paint);

        //Piringan Cakram
        textPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        rectF(canvas2, buttonPiringanCakram, textPaint, rectKondisi, 589, 3060);
        textPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
        String rupiahpiringancakram = formatRupiah(Double.parseDouble(String.valueOf(hargapiringancakram)));
        rectF(canvas2, rupiahpiringancakram, textPaint, rectHarga, 896, 3060);
        rectF(canvas2, komentarPiringanCakram, textPaint, rectKeterangan, 1841, 3060);
        canvas2.drawBitmap(scalePiringanCakram, 1317, 2932, paint);

        //Radiator Coolant
        textPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        rectF(canvas2, buttonRadiatorCoolant, textPaint, rectKondisi, 589, 3363);
        textPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
        String rupiahradiatorcoolant = formatRupiah(Double.parseDouble(String.valueOf(hargaradiatorcoolant)));
        rectF(canvas2, rupiahradiatorcoolant, textPaint, rectHarga, 896, 3363);
        rectF(canvas2, komentarRadiatorCoolant, textPaint, rectKeterangan, 1841, 3363);
        canvas2.drawBitmap(scaleRadiatorCoolant, 1317, 3234, paint);

        //Saringan Udara
        textPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        rectF(canvas2, buttonSaringanUdara, textPaint, rectKondisi, 589, 3664);
        textPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
        String rupiahsaringanudara = formatRupiah(Double.parseDouble(String.valueOf(hargasaringanudara)));
        rectF(canvas2, rupiahsaringanudara, textPaint, rectHarga, 896, 3664);
        rectF(canvas2, komentarSaringanUdara, textPaint, rectKeterangan, 1841, 3664);
        canvas2.drawBitmap(scaleSaringanUdara, 1317, 3537, paint);

        pdfDocument.finishPage(page2);

        PdfDocument.PageInfo pageInfo3 = new PdfDocument.PageInfo.Builder(2480, 3898, 3).create();
        PdfDocument.Page page3 = pdfDocument.startPage(pageInfo3);
        Canvas canvas3 = page3.getCanvas();

        canvas3.drawBitmap(cub3, 0, 0, paint);

        paint.setTextSize(35f);
        paint.setColor(getResources().getColor(R.color.white));
        paint.setTextAlign(Paint.Align.LEFT);
        canvas3.drawText(alamat1, 42, 315, paint);
        canvas3.drawText(alamat2, 42, 359, paint);

        paint.setTextSize(40f);
        paint.setColor(getResources().getColor(R.color.black));
        paint.setTextAlign(Paint.Align.LEFT);
        canvas3.drawText(edtNamaLengkap.getText().toString(), 1713, 305, paint);
        canvas3.drawText(edtNomorTelepon.getText().toString(), 1713, 356, paint);
        canvas3.drawText(tipeMotor, 1713, 408, paint);
        canvas3.drawText(edtNomorPlat.getText().toString(), 1713, 460, paint);
        canvas3.drawText(edtNomorMesin.getText().toString(), 1713, 512, paint);
        canvas3.drawText(edtTahunPerakitan.getText().toString(), 1713, 564, paint);
        canvas3.drawText(edtKilometer.getText().toString(), 1713, 615, paint);

        paint.setColor(getResources().getColor(R.color.white));
        canvas3.drawText(edtNamaMekanik.getText().toString(), 452, 498, paint);
        canvas3.drawText(dateFormat.format(dateObj), 452, 539, paint);
        canvas3.drawText(edtNomorAHASS.getText().toString(), 452, 581, paint);

        //Seal Front Fork
        textPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        rectF(canvas3, buttonSealFrontFork, textPaint, rectKondisi, 589, 940);
        textPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
        String rupiahsealfrontfork = formatRupiah(Double.parseDouble(String.valueOf(hargasealfrontfork)));
        rectF(canvas3, rupiahsealfrontfork, textPaint, rectHarga, 895, 940);
        rectF(canvas3, komentarSealFrontFork, textPaint, rectKeterangan, 1841, 940);
        canvas3.drawBitmap(scaleSealFrontFork, 1317, 807, paint);

        //Shockbreaker
        textPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        rectF(canvas3, buttonShockbreaker, textPaint, rectKondisi, 589, 1268);
        textPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
        String rupiahshockbreaker = formatRupiah(Double.parseDouble(String.valueOf(hargashockbreaker)));
        rectF(canvas3, rupiahshockbreaker, textPaint, rectHarga, 895, 1268);
        rectF(canvas3, komentarShockbreaker, textPaint, rectKeterangan, 1841, 1268);
        canvas3.drawBitmap(scaleShockbreaker, 1317, 1112, paint);

        //Drive Chain Kit
        textPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        rectF(canvas3, buttonDriveChainKit, textPaint, rectKondisi, 589, 1574);
        textPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
        String rupiahdrivechainkit = formatRupiah(Double.parseDouble(String.valueOf(hargadrivechainkit)));
        rectF(canvas3, rupiahdrivechainkit, textPaint, rectHarga, 895, 1574);
        rectF(canvas3, komentarDriveChainKit, textPaint, rectKeterangan, 1841, 1574);
        canvas3.drawBitmap(scaleDriveChainKit, 1317, 1414, paint);

        totalharga = hargaban + hargabateraiaki + hargabearingrodabelakang + hargabearingrodadepan + hargabusi +
                hargacomsteer + hargaenginesound + hargagasketcylinder + hargakabelgas + hargakampasrembelakang +
                hargakampasremdepan + hargalampudepan + hargalampubelakang + hargaminyakrem + hargaolimesin +
                hargaolishockbreaker + hargapadset + hargapiringancakram + hargaradiatorcoolant + hargasaringanudara +
                hargasealfrontfork + hargashockbreaker + hargadrivebeltset + hargaolitransmisi + hargadrivechainkit +
                hargakabelkopling;
        totalHarga = formatRupiah(Double.parseDouble(String.valueOf(totalharga)));

        paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
        paint.setColor(getResources().getColor(R.color.black));
        canvas3.drawText(totalHarga, 1361, 1801, paint);
        RectF rectHasil = new RectF(0, 0, 2231f, 204f);
        rectF(canvas3, edtHasilPemeriksaan.getText().toString(), textPaint, rectHasil, 124, 2007);

        paint.setColor(getResources().getColor(R.color.black));
        canvas3.drawText(edtTanggalPerbaikan.getText().toString(), 697, 2470, paint);
        canvas3.drawText(service, 697, 2533, paint);
        canvas3.drawText(booking, 697, 2906, paint);

        pdfDocument.finishPage(page3);

        String stringFile2 = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS).getPath() + "Laporan Pemeriksaan Motor " + edtNamaLengkap.getText().toString() + ".pdf";
        String stringFile = Environment.getExternalStorageDirectory().getPath() + File.separator + "Laporan Pemeriksaan Motor " + edtNamaLengkap.getText().toString() + ".pdf";

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            try {
                ContentValues contentValues = new ContentValues();
                contentValues.put(MediaStore.MediaColumns.DISPLAY_NAME, "Laporan Pemeriksaan Motor " + edtNamaLengkap.getText().toString() + ".pdf");
                contentValues.put(MediaStore.MediaColumns.MIME_TYPE, "application/pdf");
                contentValues.put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_DOCUMENTS);
                Uri uri = getContentResolver().insert(MediaStore.Files.getContentUri("external"), contentValues);
                OutputStream outputStream = getContentResolver().openOutputStream(uri);
                pdfDocument.writeTo(outputStream);
                outputStream.close();
                Toast.makeText(DashboardPage.this, "PDF tersimpan di " + stringFile2, Toast.LENGTH_LONG).show();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            File dirExternal = new File(String.valueOf(Environment.getExternalStorageDirectory()));
            File createDir = new File(dirExternal.getAbsolutePath());
            File file = new File(createDir, "Laporan Pemeriksaan Motor " + edtNamaLengkap.getText().toString() + ".pdf");
            try {
                pdfDocument.writeTo(new FileOutputStream(file));
            } catch (IOException e) {
                e.printStackTrace();
            }
            Toast.makeText(DashboardPage.this, "PDF tersimpan di " + stringFile, Toast.LENGTH_SHORT).show();
        }
        pdfDocument.close();
    }

    private void addItemToSheet() {
        ProgressDialog loading = ProgressDialog.show(this, "Mengirim data ke server", "Harap tunggu");
        String nama = edtNamaLengkap.getText().toString().trim();
        String nomorTelepon = edtNomorTelepon.getText().toString().trim();
        String tipemotor = tipeMotor;
        String nomorPlat = edtNomorPlat.getText().toString().trim();
        String kilometer = edtKilometer.getText().toString().trim();
        String tahun = edtTahunPerakitan.getText().toString().trim();
        String nomormesin = edtNomorMesin.getText().toString().trim();
        String namaMekanik = edtNamaMekanik.getText().toString().trim();
        String bengkel = alamat1;
        String ban = buttonBan;
        String bateraiaki = buttonBateraiAki;
        String bearingrodabelakang = buttonBearingRodaBelakang;
        String bearingrodadepan = buttonBearingRodaDepan;
        String busi = buttonBusi;
        String comsteer = buttonComSteer;
        String enginesound = buttonEngineSound;
        String gasketeylinder = buttonGasketCylinder;
        String kabelgas = buttonKabelGas;
        String kampasrembelakang = buttonKampasRemBelakang;
        String kampasremdepan = buttonKampasRemDepan;
        String lampudepan = buttonLampuDepan;
        String lampubelakang = buttonLampuBelakang;
        String minyakrem = buttonMinyakRem;
        String olimesin = buttonOliMesin;
        String olishockbreaker = buttonOliShockbreaker;
        String padset = buttonPadSet;
        String piringancakram = buttonPiringanCakram;
        String radiatorcoolant = buttonRadiatorCoolant;
        String saringanudara = buttonSaringanUdara;
        String sealfrontfork = buttonSealFrontFork;
        String shockbreaker = buttonShockbreaker;
        String drivebeltset = buttonDriveBeltSet;
        String olitransmisi = buttonOliTransmisi;
        String drivechainkit = buttonDriveChainKit;
        String kabelkopling = buttonKabelKopling;

        StringRequest stringRequest = new StringRequest(Request.Method.POST, "https://script.google.com/macros/s/AKfycbw4cRdC5fsH_-yGwN5JPTN9MvVCoY0jI4Ko8Wm_tdKr84Pg--c/exec",
                response -> {
                    loading.dismiss();
                    Toast.makeText(DashboardPage.this, response, Toast.LENGTH_LONG).show();
                    addItemToSheetToBengkel();
                },
                error -> {
                    Toast.makeText(DashboardPage.this, "Terjadi kesalahan, Harap mengaktifkan internet Anda", Toast.LENGTH_SHORT).show();
                    loading.dismiss();
                }
        ) {
            protected Map<String, String> getParams() {
                Map<String, String> parmas = new HashMap<>();

                //here we pass params
                parmas.put("action", "addItem");
                parmas.put("nama", nama);
                parmas.put("nomorTelepon", nomorTelepon);
                parmas.put("tipeMotor", tipeMotor);
                parmas.put("nomorPlat", nomorPlat);
                parmas.put("kilometer", kilometer);
                parmas.put("tahun", tahun);
                parmas.put("nomormesin", nomormesin);
                parmas.put("namaMekanik", namaMekanik);
                parmas.put("bengkel", bengkel);
                parmas.put("ban", ban);
                parmas.put("bateraiaki", bateraiaki);
                parmas.put("bearingrodabelakang", bearingrodabelakang);
                parmas.put("bearingrodadepan", bearingrodadepan);
                parmas.put("busi", busi);
                parmas.put("comsteer", comsteer);
                parmas.put("enginesound", enginesound);
                parmas.put("gasketcylinder", gasketeylinder);
                parmas.put("kabelgas", kabelgas);
                parmas.put("kampasrembelakang", kampasrembelakang);
                parmas.put("kampasremdepan", kampasremdepan);
                parmas.put("lampudepan", lampudepan);
                parmas.put("lampubelakang", lampubelakang);
                parmas.put("minyakrem", minyakrem);
                parmas.put("olimesin", olimesin);
                parmas.put("olishockbreaker", olishockbreaker);
                parmas.put("padset", padset);
                parmas.put("piringancakram", piringancakram);
                parmas.put("radiatorcoolant", radiatorcoolant);
                parmas.put("saringanudara", saringanudara);
                parmas.put("sealfrontfork", sealfrontfork);
                parmas.put("shockbreaker", shockbreaker);
                if (tipemotor.equals("Matic")) {
                    parmas.put("drivebeltset", drivebeltset);
                    parmas.put("olitransmisi", olitransmisi);
                } else if (tipemotor.equals("CUB")) {
                    parmas.put("drivechainkit", drivechainkit);
                } else if (tipemotor.equals("Sport")) {
                    parmas.put("drivechainkit", drivechainkit);
                    parmas.put("kabelkopling", kabelkopling);
                }

                return parmas;
            }
        };

        int socketTimeOut = 50000;
        RetryPolicy retryPolicy = new DefaultRetryPolicy(socketTimeOut, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(retryPolicy);

        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(stringRequest);
    }

    private void addItemToSheetToBengkel() {
        ProgressDialog loading = ProgressDialog.show(this, "Membuat File Laporan", "Harap tunggu");
        String nama = edtNamaLengkap.getText().toString().trim();
        String nomorTelepon = edtNomorTelepon.getText().toString().trim();
        String tipemotor = tipeMotor;
        String nomorPlat = edtNomorPlat.getText().toString().trim();
        String kilometer = edtKilometer.getText().toString().trim();
        String tahun = edtTahunPerakitan.getText().toString().trim();
        String nomormesin = edtNomorMesin.getText().toString().trim();
        String namaMekanik = edtNamaMekanik.getText().toString().trim();
        String bengkel = alamat1;
        String ban = buttonBan;
        String bateraiaki = buttonBateraiAki;
        String bearingrodabelakang = buttonBearingRodaBelakang;
        String bearingrodadepan = buttonBearingRodaDepan;
        String busi = buttonBusi;
        String comsteer = buttonComSteer;
        String enginesound = buttonEngineSound;
        String gasketeylinder = buttonGasketCylinder;
        String kabelgas = buttonKabelGas;
        String kampasrembelakang = buttonKampasRemBelakang;
        String kampasremdepan = buttonKampasRemDepan;
        String lampudepan = buttonLampuDepan;
        String lampubelakang = buttonLampuBelakang;
        String minyakrem = buttonMinyakRem;
        String olimesin = buttonOliMesin;
        String olishockbreaker = buttonOliShockbreaker;
        String padset = buttonPadSet;
        String piringancakram = buttonPiringanCakram;
        String radiatorcoolant = buttonRadiatorCoolant;
        String saringanudara = buttonSaringanUdara;
        String sealfrontfork = buttonSealFrontFork;
        String shockbreaker = buttonShockbreaker;
        String drivebeltset = buttonDriveBeltSet;
        String olitransmisi = buttonOliTransmisi;
        String drivechainkit = buttonDriveChainKit;
        String kabelkopling = buttonKabelKopling;

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                response -> {
                    loading.dismiss();
                    Toast.makeText(DashboardPage.this, "Sedang Memproses PDF, Harap tunggu...", Toast.LENGTH_LONG).show();
                    if (tipemotor == "Matic") {
                        CreatePDFMatic();
                        Intent intent = new Intent(DashboardPage.this, PdfShare.class);
                        intent.putExtra("Nama", edtNamaLengkap.getText().toString());
                        intent.putExtra("Desc", edtHasilPemeriksaan.getText().toString());
                        startActivity(intent);
                    } else if (tipemotor == "Sport") {
                        CreatePDFSport();
                        Intent intent = new Intent(DashboardPage.this, PdfShare.class);
                        intent.putExtra("Nama", edtNamaLengkap.getText().toString());
                        intent.putExtra("Desc", edtHasilPemeriksaan.getText().toString());
                        startActivity(intent);
                    } else if (tipemotor == "CUB") {
                        CreatePDFCUB();
                        Intent intent = new Intent(DashboardPage.this, PdfShare.class);
                        intent.putExtra("Nama", edtNamaLengkap.getText().toString());
                        intent.putExtra("Desc", edtHasilPemeriksaan.getText().toString());
                        startActivity(intent);
                    }
                },
                error -> {
                    Toast.makeText(DashboardPage.this, "Terjadi kesalahan, Harap mengaktifkan internet Anda", Toast.LENGTH_SHORT).show();
                    loading.dismiss();
                }
        ) {
            protected Map<String, String> getParams() {
                Map<String, String> parmas = new HashMap<>();

                //here we pass params
                parmas.put("action", "addItem");
                parmas.put("nama", nama);
                parmas.put("nomorTelepon", nomorTelepon);
                parmas.put("tipeMotor", tipeMotor);
                parmas.put("nomorPlat", nomorPlat);
                parmas.put("kilometer", kilometer);
                parmas.put("tahun", tahun);
                parmas.put("nomormesin", nomormesin);
                parmas.put("namaMekanik", namaMekanik);
                parmas.put("bengkel", bengkel);
                parmas.put("ban", ban);
                parmas.put("bateraiaki", bateraiaki);
                parmas.put("bearingrodabelakang", bearingrodabelakang);
                parmas.put("bearingrodadepan", bearingrodadepan);
                parmas.put("busi", busi);
                parmas.put("comsteer", comsteer);
                parmas.put("enginesound", enginesound);
                parmas.put("gasketcylinder", gasketeylinder);
                parmas.put("kabelgas", kabelgas);
                parmas.put("kampasrembelakang", kampasrembelakang);
                parmas.put("kampasremdepan", kampasremdepan);
                parmas.put("lampudepan", lampudepan);
                parmas.put("lampubelakang", lampubelakang);
                parmas.put("minyakrem", minyakrem);
                parmas.put("olimesin", olimesin);
                parmas.put("olishockbreaker", olishockbreaker);
                parmas.put("padset", padset);
                parmas.put("piringancakram", piringancakram);
                parmas.put("radiatorcoolant", radiatorcoolant);
                parmas.put("saringanudara", saringanudara);
                parmas.put("sealfrontfork", sealfrontfork);
                parmas.put("shockbreaker", shockbreaker);

                if (tipemotor.equals("Matic")) {
                    parmas.put("drivebeltset", drivebeltset);
                    parmas.put("olitransmisi", olitransmisi);
                } else if (tipemotor.equals("CUB")) {
                    parmas.put("drivechainkit", drivechainkit);
                } else if (tipemotor.equals("Sport")) {
                    parmas.put("kabelkopling", kabelkopling);
                    parmas.put("drivechainkit", drivechainkit);
                }
                return parmas;
            }
        };

        int socketTimeOut = 50000;
        RetryPolicy retryPolicy = new DefaultRetryPolicy(socketTimeOut, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(retryPolicy);

        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(stringRequest);
    }

    public void intent(Class view, int requestCode) {
        Intent intent = new Intent(DashboardPage.this, view);
        startActivityForResult(intent, requestCode);
    }

    public void rectF(Canvas canvas, String s, TextPaint textPaint, RectF rectF, float dx, float dy) {
        DynamicLayout dynamicLayout = new DynamicLayout(s, textPaint, (int) rectF.width(), Layout.Alignment.ALIGN_CENTER, 1, 1, false);
        canvas.save();
        canvas.translate(dx, dy);
        dynamicLayout.draw(canvas);
        canvas.restore();
    }

    private String formatRupiah(Double number) {
        Locale localeID = new Locale("in", "ID");
        NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);
        return formatRupiah.format(number);
    }
}