package com.example.imagetotext3;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.mlkit.common.model.DownloadConditions;
import com.google.mlkit.nl.translate.TranslateLanguage;
import com.google.mlkit.nl.translate.Translation;
import com.google.mlkit.nl.translate.Translator;
import com.google.mlkit.nl.translate.TranslatorOptions;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.PdfTextExtractor;

public class PDFOcrActivity extends AppCompatActivity {

    private Button extractPDFBtn;
    private TextView extractedTV;
    ActivityResultLauncher<Intent> resultLauncher;
    Translator englishHindiTranslator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdfocr);

        extractedTV = findViewById(R.id.idPDFTV);
        extractPDFBtn = findViewById(R.id.idBtnExtract);
        TranslatorOptions options = new TranslatorOptions.Builder()
                .setSourceLanguage(TranslateLanguage.ENGLISH)
                .setTargetLanguage(TranslateLanguage.HINDI)
                .build();
        englishHindiTranslator = Translation.getClient(options);


        resultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        Intent data = result.getData();
                        if(data!= null){
                            Uri uri = data.getData();
                            String path = uri.getPath();
                            extractPDF();
                            Modelmd(extractedTV.getText().toString());
                        }
                    }
                }
        );


        extractPDFBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(ActivityCompat.checkSelfPermission(PDFOcrActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(PDFOcrActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
                }else{
                    selectPDF();
                }
            }
        });



    }



    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == 1 && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
            selectPDF();
        }else{
            Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show();
        }
    }

    private void selectPDF() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("application/pdf");
        resultLauncher.launch(intent);
    }


    private void extractPDF() {
        try {
            String extractedText = "";
            PdfReader reader = new PdfReader("res/raw/sample3.pdf");
            int n = reader.getNumberOfPages();
            for (int i = 0; i < n; i++) {
                extractedText = extractedText + PdfTextExtractor.getTextFromPage(reader, i + 1).trim() + "\n";
            }
            extractedTV.setText(extractedText);
            reader.close();
        } catch (Exception e) {
            extractedTV.setText("Error found is : \n" + e);
        }
    }
    private void Modelmd(String str) {
        DownloadConditions conditions = new DownloadConditions.Builder().requireWifi().build();
        englishHindiTranslator.downloadModelIfNeeded(conditions).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                translate(str);
            }
        });
    }

    private void translate(String str) {
        englishHindiTranslator.translate(str).addOnSuccessListener(new OnSuccessListener<String>() {
            @Override
            public void onSuccess(String s) {
                extractedTV.setText(s);
                extractedTV.setMovementMethod(new ScrollingMovementMethod());
            }
        });
    }

}