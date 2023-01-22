package com.ilal.freepass23;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;

import com.github.barteksc.pdfviewer.PDFView;

import java.io.File;

public class PdfShare extends AppCompatActivity {

    private static final String[] PERMISSION_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    TextView tvJudul, tvDesc;
    PDFView pdfView;
    CardView cardView;
    Button btnShare, btnLagi, btnExplorer;
    String judul, desc, title, title2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf_share);

        tvJudul = findViewById(R.id.tvJudul);
        tvDesc = findViewById(R.id.tvDesc);
        btnShare = findViewById(R.id.btnShare2);
        btnLagi = findViewById(R.id.btnBuatLagi);
        cardView = findViewById(R.id.card);
        pdfView = findViewById(R.id.pdfView1);
        btnExplorer = findViewById(R.id.btnExplorer);

        title = "Laporan Pemeriksaan Motor ";

        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            judul = extras.getString("Nama");
            desc = extras.getString("Desc");
        }

        String stringFile2 = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS).getPath() + "Laporan Pemeriksaan Motor " + judul + ".pdf";
        String stringFile = Environment.getExternalStorageDirectory().getPath() + File.separator + "Laporan Pemeriksaan Motor " + judul + ".pdf";
        title2 = title + judul;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            tvJudul.setText(title2);
            tvDesc.setText(stringFile2);
        } else {
            tvJudul.setText(title2);
            tvDesc.setText(stringFile);
        }

        cardView.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), PdfViewPage.class);
            intent.putExtra("Judul", judul);
            startActivity(intent);
        });

        pengecekan(PdfShare.this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            pdfView.fromFile(new File(stringFile2)).load();
        } else {
            pdfView.fromFile(new File(stringFile)).load();
        }

        btnShare.setOnClickListener(v -> {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                File file = new File(stringFile2);
                if (!file.exists()) {
                    Toast.makeText(PdfShare.this, "File tidak tersedia", Toast.LENGTH_LONG).show();
                    return;
                }
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("application/pdf");
                intent.putExtra(Intent.EXTRA_STREAM, Uri.parse("file://" + file));
                startActivity(Intent.createChooser(intent, "Share the file..."));
            } else {
                File file = new File(stringFile);
                if (!file.exists()) {
                    Toast.makeText(PdfShare.this, "File tidak tersedia", Toast.LENGTH_LONG).show();
                    return;
                }
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("application/pdf");
                intent.putExtra(Intent.EXTRA_STREAM, Uri.parse("file://" + file));
                startActivity(Intent.createChooser(intent, "Share the file..."));
            }
        });

        btnLagi.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), DashboardPage.class);
            ProgressDialog dialog = ProgressDialog.show(this, "Menyiapkan Freepass", "Harap Tunggu...", true);
            new Thread(() -> {
                try {
                    Thread.sleep(3000);
                    dialog.dismiss();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
            startActivity(intent);
        });

        btnExplorer.setOnClickListener(v -> {
            String stringfile2 = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS).getPath();
            String stringfile = Environment.getExternalStorageDirectory().getPath();
            Intent intent;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                Uri uri = Uri.parse(stringfile2);
                intent = new Intent(Intent.ACTION_PICK);
                intent.setDataAndType(uri, "*/*");
                startActivity(intent);
            } else {
                Uri uri = Uri.parse(stringfile);
                intent = new Intent(Intent.ACTION_PICK);
                intent.setDataAndType(uri, "*/*");
                startActivity(intent);
            }
            startActivity(intent);
        });
    }

    private void pengecekan(Activity activity) {
        ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission
                        .READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                PackageManager.PERMISSION_GRANTED);
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
    }
}