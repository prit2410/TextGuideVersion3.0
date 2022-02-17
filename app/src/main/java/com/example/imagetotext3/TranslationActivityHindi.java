package com.example.imagetotext3;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.speech.tts.TextToSpeech;
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

import org.w3c.dom.Text;

import java.util.Locale;

public class TranslationActivityHindi extends AppCompatActivity {


    TextView textView;
    Button button, TexttoSpeech;
    String str;
    TextToSpeech textToSpeech;

    Translator englishHindiTranslator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_translation_hindi);

        textView = findViewById(R.id.textView);
        button = findViewById(R.id.button);
        TexttoSpeech = findViewById(R.id.TexttoSpeech);


        textToSpeech = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int i) {

                // if No error is found then only it will run
                if(i!=TextToSpeech.ERROR){
                    // To Choose language of speech
                    textToSpeech.setLanguage(Locale.US);
                }
            }
        });
        String a = textView.getText().toString();
        // Adding OnClickListener
        TexttoSpeech.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textToSpeech.speak(textView.getText().toString(),TextToSpeech.QUEUE_FLUSH,null);
            }
        });





        str = getIntent().getStringExtra("mytext");
        textView.setText(getIntent().getStringExtra("mytext"));
        textView.setMovementMethod(new ScrollingMovementMethod());

        TranslatorOptions options = new TranslatorOptions.Builder()
                .setSourceLanguage(TranslateLanguage.ENGLISH)
                .setTargetLanguage(TranslateLanguage.HINDI)
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
                textView.setMovementMethod(new ScrollingMovementMethod());
            }
        });
    }





}