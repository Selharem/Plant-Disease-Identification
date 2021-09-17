package com.example.test;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;

import java.util.List;

public class MenuActivity extends AppCompatActivity{

    private Button logout,photobtn;
    private FirebaseAuth mAuth;
    private ProgressBar progressBar;
    GoogleSignInClient mGoogleSignInClient;
    private static final String TAG = "AuthActivity";
    private Button btnVerify;
    private TextView verify;
    private Button lancerAnalyseBtn, mapBtn, documentationBtn, optionBtn;
    private static final int REQUEST_LOCATION = 1;

    LocationManager locationManager;
    String latitude,longitude;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
       // photobtn = findViewById(R.id.photo);
        ///location

        ActivityCompat.requestPermissions(this,new String[]
                {Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);

       /* photobtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                locationManager=(LocationManager) getSystemService(Context.LOCATION_SERVICE);

                //Check gps is enable or not

                if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER))
                {
                    //Write Function To enable gps

                    OnGPS();
                }
                else
                {
                    //GPS is already On then

                    getLocation();
                }
            }
        });*/










        //

        //-----------------------------------------almine---//



        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        logout = findViewById(R.id.logout);
        progressBar = findViewById(R.id.progress);
        verify = findViewById(R.id.txtV);
        btnVerify = findViewById(R.id.btnVerify);
        String g = logout.getText().toString();

        lancerAnalyseBtn = (Button) findViewById(R.id.analyseButton);
        mapBtn = (Button) findViewById(R.id.mapButton);
        documentationBtn = (Button) findViewById(R.id.docButton);
        optionBtn = (Button) findViewById(R.id.optionsButton);




        mapBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Objet de verification des permissions
                PermissionListener permissionListener = new PermissionListener() {
                    @Override
                    public void onPermissionGranted() {
                        //Si la permission est donnée, on passe à l'activité suivante
                        Intent intent = new Intent(MenuActivity.this, MapActivity.class);
                        startActivity(intent);

                    }

                    @Override
                    public void onPermissionDenied(List<String> deniedPermissions) {
                        // Pas de permission donnée, on affiche un message
                        Toast
                                .makeText(MenuActivity.this,
                                        "Veuillez accorder la permission pour continuer",
                                        Toast.LENGTH_SHORT)
                                .show();
                    }
                };

                TedPermission.with(MenuActivity.this)
                        .setPermissionListener(permissionListener)
                        .setPermissions(Manifest.permission.ACCESS_FINE_LOCATION)
                        .check();
            }
        });

        documentationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MenuActivity.this,DocumentationActivity.class);
                startActivity(intent);
            }
        });

        optionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent( MenuActivity.this,OptionsActivity.class);
                startActivity(intent);
            }
        });


        lancerAnalyseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MenuActivity.this, PriseDePhotoActivity.class);
                startActivity(intent);
            }
        });








        if(currentUser.isEmailVerified()){
            verify.setVisibility(View.VISIBLE);
        }
        else
            btnVerify.setVisibility(View.VISIBLE);
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                progressBar.setVisibility(View.VISIBLE);
                mGoogleSignInClient.signOut().addOnCompleteListener(MenuActivity.this,
                        new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                Switch();
                            }
                        });

            }
        });

        btnVerify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendEmailVerification();
            }
        });



    }

    //*-----------------------------------------------------------------//







    //}



  public void Switch(){
      startActivity(new Intent(MenuActivity.this, AuthActivity.class ));
    }


    private void sendEmailVerification() {

        // Disable button
        // Send verification email

        // [START send_email_verification]

        final FirebaseUser user = mAuth.getCurrentUser();

        user.sendEmailVerification()

                .addOnCompleteListener(this, new OnCompleteListener<Void>() {

                    @Override

                    public void onComplete(@NonNull Task<Void> task) {

                        // [START_EXCLUDE]

                        // Re-enable button





                        if (task.isSuccessful()) {
                            //verify.setVisibility(View.VISIBLE);
                            Toast.makeText(MenuActivity.this,

                                    "Verification email sent to " + user.getEmail(),

                                    Toast.LENGTH_SHORT).show();



                        } else {

                            Log.e(TAG, "sendEmailVerification", task.getException());

                            Toast.makeText(MenuActivity.this,

                                    "Failed to send verification email.",

                                    Toast.LENGTH_SHORT).show();

                        }

                        // [END_EXCLUDE]

                    }

                });

        // [END send_email_verification]

    }


    private void getLocation() {

        //Check Permissions again

        if (ActivityCompat.checkSelfPermission(MenuActivity.this,Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(MenuActivity.this,

                Manifest.permission.ACCESS_COARSE_LOCATION) !=PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this,new String[]
                    {Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);
        }
        else
        {
            Location LocationGps= locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            Location LocationNetwork=locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            Location LocationPassive=locationManager.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);

            if (LocationGps !=null)
            {
                double lat=LocationGps.getLatitude();
                double longi=LocationGps.getLongitude();

                latitude=String.valueOf(lat);
                longitude=String.valueOf(longi);
                String msg = "Your Location"+latitude+" "+longitude;
                Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
            }
            else if (LocationNetwork !=null)
            {
                double lat=LocationNetwork.getLatitude();
                double longi=LocationNetwork.getLongitude();

                latitude=String.valueOf(lat);
                longitude=String.valueOf(longi);

                String msg = "Your Location"+latitude+" "+longitude;
                Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
            }
            else if (LocationPassive !=null)
            {
                double lat=LocationPassive.getLatitude();
                double longi=LocationPassive.getLongitude();

                latitude=String.valueOf(lat);
                longitude=String.valueOf(longi);

                String msg = "Your Location :"+latitude+" "+longitude;
                Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
            }
            else
            {
                Toast.makeText(this, "Can't Get Your Location", Toast.LENGTH_SHORT).show();
            }

            //Thats All Run Your App
        }

    }



    private void OnGPS() {

        final AlertDialog.Builder builder= new AlertDialog.Builder(this);

        builder.setMessage("Enable GPS").setCancelable(false).setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
            }
        }).setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                dialog.cancel();
            }
        });
        final AlertDialog alertDialog=builder.create();
        alertDialog.show();
    }

    @Override
    public void onBackPressed() {
        finish();
        this.finishAffinity();
    }


}
