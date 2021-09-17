package com.example.test;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ResultActivity extends AppCompatActivity {

    private Button maladieDocBtn;
    private TextView nomMaladie;

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

        setContentView(R.layout.activity_result);

        nomMaladie = findViewById(R.id.nomDeMaladie);
        maladieDocBtn = findViewById(R.id.maladieDocBtn);

        // Id de la maladie
        int id = (int) (Math.random() * ((5 - 1) + 1)) + 1;

        // Changement du nom de la maladie en fonction de l'id récupéré
        nomMaladie.setText(getMaladie(id)[0]);

        // Le bouton change de lien aussi suivant le meme principe
        maladieDocBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(getMaladie(id)[1]));
                startActivity(browserIntent);
            }
        });
    }

    @Override
    public void onBackPressed() {
        finish();
        startActivity(new Intent(ResultActivity.this, MenuActivity.class));
    }
    // On lit la maladie dectectée a partir d'un id fournit, on recupere ensuite son nom ainsi
    // qu'un lien de documentation qu'on va mettre sur le bouton au plus tard
    private String[] getMaladie(int idMaladie){
        String maladie=null;
        String urlMaladie=null;
        switch (idMaladie){
            case 1:
                maladie="Early blight aternia";
                urlMaladie="https://fr.wikipedia.org/wiki/Taches_de_rouille";
                break;
            case 2:
                maladie= "Mildiou de pomme de terre";
                urlMaladie="https://fr.wikipedia.org/wiki/Mildiou_de_la_pomme_de_terre";
                break;
            case 3:
                maladie= "Milidiou de tomate";
                urlMaladie="http://binette-et-cornichon.com/a/102/";
                break;
            case 4:
                maladie= "Tomato mosaic virus";
                urlMaladie="https://fr.wikipedia.org/wiki/Virus_de_la_mosa%C3%AFque_de_la_tomate";
                break;
            case 5:
                maladie= "Tomato yellow leaf curl virus";
                urlMaladie="https://fr.wikipedia.org/wiki/Virus_des_feuilles_jaunes_en_cuill%C3%A8re_de_la_tomate";
                break;
        }
        String[] maladies = {maladie,urlMaladie};
        return maladies;
    }






}
