package com.example.test;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.SpannableString;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.text.style.UnderlineSpan;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.util.SharedPreferencesUtils;
import com.google.android.gms.safetynet.SafetyNet;
import com.google.android.gms.safetynet.SafetyNetApi;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Locale;
import java.util.regex.Pattern;

public class AuthActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks {

    //-----------Les composantes de notre layout -----------//

    private EditText passwordCase,emailCase;
    private Button LoginBtn,googleBtn;
    private TextView registre,forgotPass;
    private CheckBox checkbox;
    private ImageView eye;
    private ProgressBar progressBar;

    //----------Les Variables Dynamiqye && Static && Final--------------//

    private static final String TAG = "AuthActivity";
    private static final Pattern PASS_CHECK = Pattern.compile("^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?!.*\\s).*$");
    private static final Pattern EMAIL_CHECK = Pattern.compile("^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(?:\\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*$");
    private static int nbrAuth=0;
    private static boolean check=false;
    private boolean checkAuth=false;
    final int RC_SIGN_IN = 123;
    private final int Google_Sign = 123;
    static AlertDialog dialog;
    static AlertDialog.Builder mBuilder;
    private Locale myLocale;
    private String currentLanguage ="en",currentLang;
    static boolean choice=false;


    //-------- Les Variables Utilisées dans Firebase Et Google Api ------//

         FirebaseDatabase database;
         LocationManager locationManager;
         GoogleSignInClient mGoogleSignInClient;
         GoogleApiClient gac;
         String key = "6LfFgsoUAAAAADj0TSB7JL17sD3i1fNi6WJd7LhQ";
         private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);

     //-------------------L'initialisation de notre Compostants---------------//
        passwordCase = findViewById(R.id.pass);
        emailCase = findViewById(R.id.email);
        LoginBtn = findViewById(R.id.regbtn);
        googleBtn = findViewById(R.id.btnGoogle);
        checkbox = findViewById(R.id.check_box);
        eye = findViewById(R.id.img);
        registre = findViewById(R.id.register);
        forgotPass = findViewById((R.id.forgotPass));
        progressBar = findViewById(R.id.progress);
        //-------------------------------------------------------------------//

     //------------------ Choisir une Langue -----------------//

        if(!choice){
            mBuilder = new AlertDialog.Builder(AuthActivity.this);
            View myView = getLayoutInflater().inflate(R.layout.activity_language, null);
            final RadioButton radioAr = myView.findViewById(R.id.radioArab);
            Button ok = myView.findViewById(R.id.Ok);
            mBuilder.setView(myView);
            dialog = mBuilder.create();
            dialog.show();
            ok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(radioAr.isChecked()){
                        currentLanguage = getIntent().getStringExtra(currentLang);
                        ChangeLanguage.changeLanguage(AuthActivity.this,ChangeLanguage.langue = "ar");
                    /*emailCase.setHint(R.string.email);
                    passwordCase.setHint(R.string.password);
                    LoginBtn.setText(R.string.login);
                    googleBtn.setText(R.string.login);
                    registre.setText(R.string.registre);
                    forgotPass.setText(R.string.ressetP);
                    change(emailCase,passwordCase,eye);
                    dialog.dismiss();*/
                        dialog.dismiss();
                    }
                    else
                        ChangeLanguage.changeLanguage(AuthActivity.this,ChangeLanguage.langue = "en");
                        dialog.dismiss();
                }
            });


        }








     //-----Underline-------//

        Underline(registre,registre.getText().toString());
        Underline(forgotPass,forgotPass.getText().toString());
     //--------------------//

     //----------Firebase && Google---------//

        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("users").child("jaja").setValue("haha");
        mAuth = FirebaseAuth.getInstance();
        //**Create a Google Api
           final GoogleApiClient gac = new GoogleApiClient.Builder(this)
                .addApi(SafetyNet.API)
                .addConnectionCallbacks(AuthActivity.this)
                .build();
                gac.connect();
         //------------------



     //------------------------------------//

     //-----------------Les Actions---------------//

        checkbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkbox.isChecked()){
                    SafetyNet.SafetyNetApi.verifyWithRecaptcha(gac,key)
                            .setResultCallback(new ResultCallback<SafetyNetApi.RecaptchaTokenResult>() {
                                @Override
                                public void onResult(@NonNull SafetyNetApi.RecaptchaTokenResult recaptchaTokenResult) {
                                    Status status = recaptchaTokenResult.getStatus();
                                    if((status != null) && status.isSuccess()){
                                        Toast.makeText(getApplicationContext()
                                                ,"SuccessfuLly ...."
                                                ,Toast.LENGTH_SHORT).show();

                                        checkbox.setTextColor(Color.GREEN);
                                        checkAuth = true;
                                    }

                                }
                            });

                }
                else
                    checkbox.setTextColor(Color.RED);
            }
        });

        LoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String password = passwordCase.getText().toString();
                String email = emailCase.getText().toString();
                if(nbrAuth==3){
                    opendialog("Vous avez depasser le nombre");
                    LoginBtn.setEnabled(false);
                    new CountDownTimer(30000, 10) { //Set Timer for 5 seconds
                        public void onTick(long millisUntilFinished) {

                            LoginBtn.getBackground().setAlpha(128);
                        }

                        @Override
                        public void onFinish() {
                            LoginBtn.setEnabled(true);
                            LoginBtn.getBackground().setAlpha(255);
                        }
                    }.start();
                }
                SignButton(email,password);




            }
        });
        eye.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!check){
                    eye.setImageResource(R.drawable.show);
                    passwordCase.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    check = true;
                }
                else{
                    eye.setImageResource(R.drawable.hide);
                    passwordCase.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    check = false;
                }
            }
        });
        // TODO 7yd had zbl
       /* registre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = emailCase.getText().toString();
                String password = passwordCase.getText().toString();
                boolean isValid = validateEmail(email,emailCase) & validatePassword(password, passwordCase);
                if(checkRecaptcha() & isValid){

                    mAuth.createUserWithEmailAndPassword(email, password)
                            .addOnCompleteListener(AuthActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        // Sign in success, update UI with the signed-in user's information
                                        Log.d(TAG, "createUserWithEmail:success");
                                        startActivity(new Intent(AuthActivity.this,MenuActivity.class ));

                                    } else {
                                        // If sign in fails, display a message to the user.
                                        Log.d(TAG, "createUserWithEmail:failure", task.getException());
                                        Toast.makeText(AuthActivity.this, "Authe Failed.",
                                                Toast.LENGTH_SHORT).show();

                                    }

                                    // ...
                                }
                            });
                }

            }
        });*/

        forgotPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBuilder = new AlertDialog.Builder(AuthActivity.this);
                View myView = getLayoutInflater().inflate(R.layout.activity_dialog_password, null);
                final EditText Email = (EditText) myView.findViewById(R.id.Email);
                final  Button send = (Button) myView.findViewById(R.id.send);
                mBuilder.setView(myView);
                dialog = mBuilder.create();
                dialog.show();

                send.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String email = Email.getText().toString();
                        if(validateEmail(email,Email))
                            RessetPassword(email);

                    }
                });


            }
        });

        registre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkRecaptcha())
                   startActivity(new Intent(AuthActivity.this, NewAccountActivity.class ));

            }
        });
      //-------------------------------------------------------------------------//

      //******* Partie Google Sign IN *******//

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        googleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkRecaptcha())
                    signIn();
            }
        });

       //------------------------------------------//
    }

    //************** Définition des Fonctions ************//

    void signIn() {
        progressBar.setVisibility(View.VISIBLE);
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    public void opendialog(String message){
        Dialog D =  new Dialog(message);
        D.show(getSupportFragmentManager(), "example");
    }
    public  boolean validateEmail(String email, EditText em){
        if(email.isEmpty()){
            em.setError( getString(R.string.vide));
            return false;
        }
        if(!EMAIL_CHECK.matcher(email).matches()){
            em.setError( getString(R.string.format));
            return false;
        }
       return  true;
    }
    public boolean checkRecaptcha(){
        if(checkAuth)
            return true;
        else{

            Toast.makeText(AuthActivity.this, getString(R.string.captcha),
                    Toast.LENGTH_SHORT).show();
            return false;
        }
    }
    public boolean validatePassword(String pass, EditText passF){
        if(pass.isEmpty()){
            passF.setError(getString (R.string.vide));
            return false;
        }

        return true;
    }


    public void onStart() {

        super.onStart();

        // Check if user is signed in (non-null) and update UI accordingly.

        FirebaseUser currentUser = mAuth.getCurrentUser();

        updateUI(currentUser);

    }

    private void updateUI(FirebaseUser user){

        if(user != null){
            finish();
            startActivity(new Intent(AuthActivity.this, MenuActivity.class ));
        }

        else{

        }


    }

    private void RessetPassword(String Email){

        mAuth.sendPasswordResetEmail(Email)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.d("EMAIL", "Email sent.");
                            Toast.makeText(AuthActivity.this, "Check your Email",
                                    Toast.LENGTH_SHORT).show();

                            dialog.dismiss();


                        }
                        else
                        {
                            Log.d("EMAIL", "Email not sent.");
                        }
                    }
                });



    }

    private void SignButton(String email, String password){

        boolean isValid = validateEmail(email,emailCase) & validatePassword(password, passwordCase);
        if(checkRecaptcha() && isValid){
            progressBar.setVisibility(View.VISIBLE);
            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(AuthActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                nbrAuth = 0;
                                // Sign in success, update UI with the signed-in user's information
                                Log.d(TAG, "signInWithEmail:success");
                                //FirebaseUser user = mAuth.getCurrentUser();
                                finish();
                                startActivity(new Intent(AuthActivity.this, MenuActivity.class ));


                            } else {
                                if(nbrAuth++ ==3)
                                    nbrAuth = 0;
                                progressBar.setVisibility(View.INVISIBLE);
                                // If sign in fails, display a message to the user.
                                Log.w(TAG, "signInWithEmail:failure", task.getException());
                                Toast.makeText(AuthActivity.this, getText(R.string.Auth),
                                        Toast.LENGTH_SHORT).show();

                            }

                            // ...
                        }
                    });
        }





    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct){
        Log.d(TAG, "firebaseAuthWithGoogle:" + acct.getId());

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            progressBar.setVisibility(View.INVISIBLE);
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            finish();
                            startActivity(new Intent(AuthActivity.this, MenuActivity.class ));

                        } else {
                            progressBar.setVisibility(View.INVISIBLE);
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());


                        }

                        // ...
                    }
                });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Log.w(TAG, "Google sign in failed", e);
                // ...
            }
        }
    }
    //------------------------------------------------------------------------///
    public void Underline(TextView txt,String message){ // function to underline a text
        SpannableString content = new SpannableString(message);
        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
        txt.setText(content);
    }

  //-------------- function to change image ------------------------//

  public void change(EditText e1, EditText e2,ImageView v){
      e1.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.avatar, 0);
      e2.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.padlock, 0);
  }

    public void setLocale(String localeName) {

        myLocale = new Locale(localeName);
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = myLocale;
        res.updateConfiguration(conf, dm);
        Intent refresh = new Intent(this, AuthActivity.class);
        refresh.putExtra(currentLang, localeName);
        choice = true;
        finish();
        startActivity(refresh);

    }

    @Override
    public void onBackPressed() {
        finish();
        this.finishAffinity();
    }


}
