package com.example.imagetotext3;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.mlkit.common.model.DownloadConditions;
import com.google.mlkit.nl.translate.TranslateLanguage;
import com.google.mlkit.nl.translate.Translation;
import com.google.mlkit.nl.translate.Translator;
import com.google.mlkit.nl.translate.TranslatorOptions;

public class TranslationActivityMarathi extends AppCompatActivity {
    TextView textView;
    Button button;
    String str;


    Translator englishHindiTranslator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_translation_marathi);


        textView = findViewById(R.id.textView);
        button = findViewById(R.id.button);

        str = getIntent().getStringExtra("mytext123");
        textView.setText(getIntent().getStringExtra("mytext123"));
        textView.setMovementMethod(new ScrollingMovementMethod());


        TranslatorOptions options = new TranslatorOptions.Builder()
                .setSourceLanguage(TranslateLanguage.ENGLISH)
                .setTargetLanguage(TranslateLanguage.MARATHI)
                .build();
        englishHindiTranslator = Translation.getClient(options);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Modelmd(str);
            }
        });
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
                textView.setText(s);
            }
        });
    }
}