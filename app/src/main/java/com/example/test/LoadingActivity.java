package com.example.test;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;

public class LoadingActivity extends AppCompatActivity {

    ProgressBar loadingProgressBar;

    @Override
    protected void onResume() {
        super.onResume();

        // Changement de langue
        ChangeLanguage.changeLanguage(this,ChangeLanguage.langue);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Changement de langue
        ChangeLanguage.changeLanguage(this,ChangeLanguage.langue);

        setContentView(R.layout.activity_loading);

        loadingProgressBar = findViewById(R.id.loadingProgressBar);

        loadingProgressBar.isShown();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(LoadingActivity.this,ResultActivity.class);
                startActivity(intent);
                finish();
            }
        },5000);
    }

    @Override
    public void onBackPressed() {
        // On ne fait rien ici
    }
}
