package com.example.test;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class DocumentationActivity extends AppCompatActivity implements View.OnClickListener {

    private Button earlyBlightAternia, lateBlightMildiouP, lateBlightMildiouT, tomatoMosaicVirus, tomatoYellowLeafCurlVirus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_documentation);

        earlyBlightAternia = findViewById(R.id.earlyBlightAternia);
        lateBlightMildiouP = findViewById(R.id.lateBlightMildiouP);
        lateBlightMildiouT = findViewById(R.id.lateBlightMildiouT);
        tomatoMosaicVirus = findViewById(R.id.tomatoMosaicVirus);
        tomatoYellowLeafCurlVirus = findViewById(R.id.tomatoYellowLeafCurlVirus);

        earlyBlightAternia.setOnClickListener(this);
        lateBlightMildiouT.setOnClickListener(this);
        lateBlightMildiouP.setOnClickListener(this);
        tomatoMosaicVirus.setOnClickListener(this);
        tomatoYellowLeafCurlVirus.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent browserIntent;
        switch (v.getId()) {
            case R.id.earlyBlightAternia:
                browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://fr.wikipedia.org/wiki/Taches_de_rouille"));
                startActivity(browserIntent);
                break;
            case R.id.lateBlightMildiouP:
                browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://fr.wikipedia.org/wiki/Mildiou_de_la_pomme_de_terre"));
                startActivity(browserIntent);
                break;
            case R.id.lateBlightMildiouT:
                browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://binette-et-cornichon.com/a/102/"));
                startActivity(browserIntent);
                break;
            case R.id.tomatoMosaicVirus:
                browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://fr.wikipedia.org/wiki/Virus_de_la_mosa%C3%AFque_de_la_tomate"));
                startActivity(browserIntent);
                break;
            case R.id.tomatoYellowLeafCurlVirus:
                browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://fr.wikipedia.org/wiki/Virus_des_feuilles_jaunes_en_cuill%C3%A8re_de_la_tomate"));
                startActivity(browserIntent);
                break;
        }
    }

    @Override
    public void onBackPressed() {
        finish();
        startActivity(new Intent(DocumentationActivity.this, MenuActivity.class));
    }
}

