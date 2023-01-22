package com.ilal.freepass23;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.github.barteksc.pdfviewer.PDFView;

import java.io.File;

public class PdfViewPage extends AppCompatActivity {

    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static final String[] PERMISSION_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    private String stringFile, stringFile2;
    PDFView pdfView;
    File file;
    String judul;
    Button btnShare;
    String namaFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf_view_page);

        pdfView = findViewById(R.id.pdfView);
        btnShare = findViewById(R.id.btnShare);

        Bundle extras = getIntent().getExtras();

        if (extras != null){
            judul = extras.getString("Judul");
        }

        String stringFile2 = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS).getPath() + "Laporan Pemeriksaan Motor " + judul + ".pdf";
        String stringFile = Environment.getExternalStorageDirectory().getPath() + File.separator + "Laporan Pemeriksaan Motor " + judul + ".pdf";

        pengecekan(PdfViewPage.this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            pdfView.fromFile(new File(stringFile2)).load();
        } else {
            pdfView.fromFile(new File(stringFile)).load();
        }

        btnShare.setOnClickListener(v -> {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                File file = new File(stringFile2);
                if (!file.exists()) {
                    Toast.makeText(PdfViewPage.this, "File tidak tersedia", Toast.LENGTH_LONG).show();
                    return;
                }
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("application/pdf");
                intent.putExtra(Intent.EXTRA_STREAM, Uri.parse("file://" + file));
                startActivity(Intent.createChooser(intent, "Share the file..."));
            } else {
                File file = new File(stringFile);
                if (!file.exists()) {
                    Toast.makeText(PdfViewPage.this, "File tidak tersedia", Toast.LENGTH_LONG).show();
                    return;
                }
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("application/pdf");
                intent.putExtra(Intent.EXTRA_STREAM, Uri.parse("file://" + file));
                startActivity(Intent.createChooser(intent, "Share the file..."));
            }
        });
    }

    private void pengecekan(Activity activity) {
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.READ_EXTERNAL_STORAGE);
        if (permission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSION_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        }
    }
}