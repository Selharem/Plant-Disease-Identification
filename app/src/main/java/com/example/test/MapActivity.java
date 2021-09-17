package com.example.test;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.Switch;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback {

    private Button selectDateBtn;
    private DatePickerDialog.OnDateSetListener listen;
    private static String Maladie1 = "Early Blight Atenia";
    private static String Maladie2 = "Late Blight Mildiou(Tomate)";
    private static String Maladie3 = "Late Blight Mildiou(Pommes de Terre)";
    private static String Maladie4 = "Tomato Mosaic Virus";
    private static String Maladie5 = "Tomato Yellow leaf curl virus";

    private Switch m1Switch, m2Switch, m3Switch, m4Switch, m5Switch;

    private static GoogleMap mMap;

    private static Spinner spinner;

    private static final String[] paths = {"Choisir Maladie", Maladie1, Maladie2, Maladie3, Maladie4, Maladie5};

    private Object Tableau[][] = new Object[3][20];

    private Date date[] = new Date[20];
    private static Date dateChoisie;

    private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        try {
            dateChoisie = format.parse("1980-01-01");
        } catch (ParseException e) {
            e.printStackTrace();
        }

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.mapFragment);

        mapFragment.getMapAsync(this);

        selectDateBtn = (Button) findViewById(R.id.selectDateBtn);

        m1Switch = findViewById(R.id.m1Switch);
        m2Switch = findViewById(R.id.m2Switch);
        m3Switch = findViewById(R.id.m3Switch);
        m4Switch = findViewById(R.id.m4Switch);
        m5Switch = findViewById(R.id.m5Switch);

        selectDateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog dialog = new DatePickerDialog(MapActivity.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth, listen, year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();

            }
        });
        listen = new DatePickerDialog.OnDateSetListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onDateSet(DatePicker view, int yy, int mm, int jj) {
                onMapReady(mMap, yy, mm, jj);
                m1Switch.performClick();
                m1Switch.performClick();


                m2Switch.performClick();
                m2Switch.performClick();


                m3Switch.performClick();
                m3Switch.performClick();


                m4Switch.performClick();
                m4Switch.performClick();


                m5Switch.performClick();
                m5Switch.performClick();
            }
        };

        m1Switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                for (int i = 0; i < 20; i++) {
                    Marker marker = (Marker) Tableau[0][i];
                    Date date = (Date) Tableau[2][i];
                    if (Tableau[1][i] == Maladie1) {

                        Calendar cal = Calendar.getInstance();
                        int year = cal.get(Calendar.YEAR);
                        int month = cal.get(Calendar.MONTH);
                        int day = cal.get(Calendar.DAY_OF_MONTH);

                        try {
                            Date dateActuelle = format.parse(year + "-" + month + "-" + day);
                            if (dateChoisie.compareTo(date) <= 0 && dateActuelle.compareTo(date) >= 0) {
                                marker.setVisible(isChecked);
                            }
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        });

        m2Switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                for (int i = 0; i < 20; i++) {
                    Marker marker = (Marker) Tableau[0][i];
                    Date date = (Date) Tableau[2][i];
                    if (Tableau[1][i] == Maladie2) {

                        Calendar cal = Calendar.getInstance();
                        int year = cal.get(Calendar.YEAR);
                        int month = cal.get(Calendar.MONTH);
                        int day = cal.get(Calendar.DAY_OF_MONTH);

                        try {
                            Date dateActuelle = format.parse(year + "-" + month + "-" + day);
                            if (dateChoisie.compareTo(date) <= 0 && dateActuelle.compareTo(date) >= 0) {
                                marker.setVisible(isChecked);
                            }
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        });

        m3Switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                for (int i = 0; i < 20; i++) {
                    Marker marker = (Marker) Tableau[0][i];
                    Date date = (Date) Tableau[2][i];
                    if (Tableau[1][i] == Maladie3) {

                        Calendar cal = Calendar.getInstance();
                        int year = cal.get(Calendar.YEAR);
                        int month = cal.get(Calendar.MONTH);
                        int day = cal.get(Calendar.DAY_OF_MONTH);

                        try {
                            Date dateActuelle = format.parse(year + "-" + month + "-" + day);
                            if (dateChoisie.compareTo(date) <= 0 && dateActuelle.compareTo(date) >= 0) {
                                marker.setVisible(isChecked);
                            }
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        });

        m4Switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                for (int i = 0; i < 20; i++) {
                    Marker marker = (Marker) Tableau[0][i];
                    Date date = (Date) Tableau[2][i];
                    if (Tableau[1][i] == Maladie4) {

                        Calendar cal = Calendar.getInstance();
                        int year = cal.get(Calendar.YEAR);
                        int month = cal.get(Calendar.MONTH);
                        int day = cal.get(Calendar.DAY_OF_MONTH);

                        try {
                            Date dateActuelle = format.parse(year + "-" + month + "-" + day);
                            if (dateChoisie.compareTo(date) <= 0 && dateActuelle.compareTo(date) >= 0) {
                                marker.setVisible(isChecked);
                            }
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        });

        m5Switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                for (int i = 0; i < 20; i++) {
                    Marker marker = (Marker) Tableau[0][i];
                    Date date = (Date) Tableau[2][i];
                    if (Tableau[1][i] == Maladie5) {

                        Calendar cal = Calendar.getInstance();
                        int year = cal.get(Calendar.YEAR);
                        int month = cal.get(Calendar.MONTH);
                        int day = cal.get(Calendar.DAY_OF_MONTH);

                        try {
                            Date dateActuelle = format.parse(year + "-" + month + "-" + day);
                            if (dateChoisie.compareTo(date) <= 0 && dateActuelle.compareTo(date) >= 0) {
                                marker.setVisible(isChecked);
                            }
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        });

    }


    public void setMarker() {

        Tableau[1][0] = Tableau[1][5] = Tableau[1][6] = Tableau[1][7] = Maladie1;
        Tableau[1][1] = Tableau[1][8] = Tableau[1][9] = Tableau[1][10] = Maladie2;
        Tableau[1][2] = Tableau[1][11] = Tableau[1][12] = Tableau[1][13] = Maladie3;
        Tableau[1][3] = Tableau[1][14] = Tableau[1][15] = Tableau[1][16] = Maladie4;
        Tableau[1][4] = Tableau[1][17] = Tableau[1][18] = Tableau[1][19] = Maladie5;

        Tableau[0][0] = new LatLng(30.136814, -7.762198);
        Tableau[0][1] = new LatLng(29.641514, -8.508942);
        Tableau[0][2] = new LatLng(27.908272, -11.012729);
        Tableau[0][3] = new LatLng(32.464585, -8.333237);
        Tableau[0][4] = new LatLng(31.906707, -5.741598);
        Tableau[0][5] = new LatLng(30.818526, -7.454715);
        Tableau[0][6] = new LatLng(30.060774, -7.981829);
        Tableau[0][7] = new LatLng(31.082342, -6.224785);
        Tableau[0][8] = new LatLng(30.478266, -8.245385);
        Tableau[0][9] = new LatLng(27.713926, -10.002429);
        Tableau[0][10] = new LatLng(32.723754, -2.139657);
        Tableau[0][11] = new LatLng(31.869394, -8.860351);
        Tableau[0][12] = new LatLng(30.969367, -4.950928);
        Tableau[0][13] = new LatLng(30.402491, -7.366863);
        Tableau[0][14] = new LatLng(27.947099, -11.012729);
        Tableau[0][15] = new LatLng(34.552942, -3.984554);
        Tableau[0][16] = new LatLng(32.353283, -8.377164);
        Tableau[0][17] = new LatLng(32.538709, -7.103307);
        Tableau[0][18] = new LatLng(32.501655, -3.764923);
        Tableau[0][19] = new LatLng(27.363230, -11.364138);

        try {

            Tableau[2][0] = format.parse("2019-12-28");
            Tableau[2][1] = format.parse("2019-10-30");
            Tableau[2][2] = format.parse("2009-10-22");
            Tableau[2][3] = format.parse("2009-09-11");
            Tableau[2][4] = format.parse("2009-8-1");
            Tableau[2][5] = format.parse("2005-7-2");
            Tableau[2][6] = format.parse("2009-6-16");
            Tableau[2][7] = format.parse("1999-5-25");
            Tableau[2][8] = format.parse("2004-4-14");
            Tableau[2][9] = format.parse("2003-3-26");
            Tableau[2][10] = format.parse("2002-2-13");
            Tableau[2][11] = format.parse("2001-1-3");
            Tableau[2][12] = format.parse("2000-12-9");
            Tableau[2][13] = format.parse("2018-11-7");
            Tableau[2][14] = format.parse("2016-10-18");
            Tableau[2][15] = format.parse("2015-9-23");
            Tableau[2][16] = format.parse("2010-8-21");
            Tableau[2][17] = format.parse("2011-7-20");
            Tableau[2][18] = format.parse("2012-6-5");
            Tableau[2][19] = format.parse("2009-5-4");

        } catch (ParseException e) {
            e.printStackTrace();
        }


        for (int i = 0; i < 20; i++) {

            if (Tableau[1][i] == Maladie1)
                Tableau[0][i] = mMap.addMarker(new MarkerOptions().title(Maladie1)
                        .position((LatLng) Tableau[0][i]).icon(BitmapDescriptorFactory
                                .defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
            if (Tableau[1][i] == Maladie2)
                Tableau[0][i] = mMap.addMarker(new MarkerOptions().title(Maladie2)
                        .position((LatLng) Tableau[0][i]).icon(BitmapDescriptorFactory
                                .defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));
            if (Tableau[1][i] == Maladie3)
                Tableau[0][i] = mMap.addMarker(new MarkerOptions().title(Maladie3)
                        .position((LatLng) Tableau[0][i]).icon(BitmapDescriptorFactory
                                .defaultMarker(BitmapDescriptorFactory.HUE_YELLOW)));
            if (Tableau[1][i] == Maladie4)
                Tableau[0][i] = mMap.addMarker(new MarkerOptions().title(Maladie4)
                        .position((LatLng) Tableau[0][i]).icon(BitmapDescriptorFactory
                                .defaultMarker(BitmapDescriptorFactory.HUE_RED)));
            if (Tableau[1][i] == Maladie5)
                Tableau[0][i] = mMap.addMarker(new MarkerOptions().title(Maladie5)
                        .position((LatLng) Tableau[0][i]).icon(BitmapDescriptorFactory
                                .defaultMarker(BitmapDescriptorFactory.HUE_ORANGE)));
        }

        for (int i = 0; i < 20; i++) {
            Marker marker = (Marker) Tableau[0][i];
            marker.setVisible(false);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        LatLng maroc = new LatLng(30.3999984, -9.5999976);
        int padding = 0; // offset from edges of the map in pixels
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(maroc, 5);
        mMap.moveCamera(cameraUpdate);
        googleMap.getUiSettings().setScrollGesturesEnabled(false);
        googleMap.setMaxZoomPreference(14.0f);
        googleMap.setMinZoomPreference(4.0f);
        setMarker();
    }


    //FILTRAGE PAR MALADIE
    public void onMapReady(GoogleMap googleMap, String string, boolean display) {
        mMap = googleMap;
        setMarker();
        for (int i = 0; i < 20; i++) {
            Marker marker = (Marker) Tableau[0][i];
            if (Tableau[1][i] == string) {
                marker.setVisible(display);
            }
        }
    }

    //FILTRAGE PAR DATE
    public void onMapReady(GoogleMap googleMap, int yy, int mm, int jj) {
        // On met la map
        mMap = googleMap;
        mMap.clear();

        // On met tout les markers en invisible
        setMarker();
        try {

            // La date est fournie par la boite de dialogues
            Date DatePicked = format.parse(yy + "-" + mm + "-" + jj);
            dateChoisie = format.parse(yy + "-" + mm + "-" + jj);

        } catch (ParseException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onBackPressed() {
        // Retour au menu
        finish();
        startActivity(new Intent(MapActivity.this, MenuActivity.class));
    }
}
