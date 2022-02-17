package com.example.imagetotext3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.VideoView;

public class SelectionActivtityHindi extends AppCompatActivity {

    Button buttonhindi, buttonmarathi;
    VideoView videoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selection_activtity_hindi);

        buttonhindi = findViewById(R.id.buttonhindi);
        buttonmarathi = findViewById(R.id.buttonmarathi);
        videoView = findViewById(R.id.videoView);

        buttonhindi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), MainActivityHindi.class));
            }
        });

        String videopath = "android.resource://" + getPackageName() + "/" + R.raw.introvideo;
        Uri uri = Uri.parse(videopath);
        videoView.setVideoURI(uri);

        MediaController mediaController = new MediaController(this);
        videoView.setMediaController(mediaController);
        mediaController.setAnchorView(videoView);


        buttonmarathi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), PDFOcrActivity.class));
            }
        });
    }
}