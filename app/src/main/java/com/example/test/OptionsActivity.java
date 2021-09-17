package com.example.test;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;

import com.google.firebase.database.core.view.Change;

import androidx.appcompat.app.AppCompatActivity;

public class OptionsActivity extends AppCompatActivity {

    RadioButton frenchRadioBtn, arabicRadioBtn;
    Button aboutBtn;
    static Boolean arabicChecked=false/*=(ChangeLanguage.langue=="ar")?true:false*/, frenchChecked=true /*=(ChangeLanguage.langue=="en")?true:false*/;

    @Override
    protected void onResume() {
        super.onResume();

        // Changement de langue
        ChangeLanguage.changeLanguage(this, ChangeLanguage.langue);

        arabicRadioBtn.setChecked(arabicChecked);
        frenchRadioBtn.setChecked(frenchChecked);
:QA
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Changement de langue
        ChangeLanguage.changeLanguage(this, ChangeLanguage.langue);

        setContentView(R.layout.activity_options);

        frenchRadioBtn = findViewById(R.id.frenchRadioButton);
        arabicRadioBtn = findViewById(R.id.arabicRadioButton);

        aboutBtn = findViewById(R.id.aboutButton);

        frenchRadioBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!frenchChecked) {
                    ChangeLanguage.changeLanguage(OptionsActivity.this,  ChangeLanguage.langue = "en");
                    finish();
                    startActivity(getIntent());
                }
                frenchChecked = true;
                arabicChecked = false;
            }
        });

        arabicRadioBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!arabicChecked) {
                    ChangeLanguage.changeLanguage(OptionsActivity.this, ChangeLanguage.langue = "ar");
                    finish();
                    startActivity(getIntent());
                }
                frenchChecked = false;
                arabicChecked = true;
            }
        });
    }

    @Override
    public void onBackPressed() {
        finish();
        startActivity(new Intent(OptionsActivity.this, MenuActivity.class));
    }
}
