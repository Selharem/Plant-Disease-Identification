package com.example.test;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.regex.Pattern;

public class NewAccountActivity extends AppCompatActivity {
    private FirebaseAuth mauth;
    private static final String TAG = "AuthActivity";
    private static final Pattern PASS_CHECK = Pattern.compile("^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?!.*\\s).*$");
    private static final Pattern EMAIL_CHECK = Pattern.compile("^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(?:\\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*$");
    private static boolean check = false;

    //------------- Les composants de notre layout --------//

    EditText Caseemail, Casenom, Caseprenom, Casepassword, Caseconfirmpass;
    Spinner mySpinner;
    Button btn_registre;
    ImageView eye, eye1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_account);

        //----------- L'initiation ----------------------------//
        Casenom = findViewById(R.id.Nom);
        Caseprenom = findViewById(R.id.Prenom);
        Caseemail = findViewById(R.id.email);
        Casepassword = findViewById(R.id.pass);
        Caseconfirmpass = findViewById(R.id.Confirm);
        btn_registre = findViewById(R.id.regbtn);
        eye = findViewById(R.id.img);
        eye1 = findViewById(R.id.img1);
        mySpinner = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(NewAccountActivity.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.names));
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mySpinner.setAdapter(myAdapter);

        mauth = FirebaseAuth.getInstance();


        //---------- Les Actions -----------------------//
        eye.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!check) {
                    eye.setImageResource(R.drawable.show);
                    Casepassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    check = true;
                } else {
                    eye.setImageResource(R.drawable.hide);
                    Casepassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    check = false;
                }
            }
        });
        eye1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!check) {
                    eye1.setImageResource(R.drawable.show);
                    Caseconfirmpass.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    check = true;
                } else {
                    eye1.setImageResource(R.drawable.hide);
                    Caseconfirmpass.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    check = false;
                }
            }
        });
        btn_registre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //-------Récuperation des information de l'utilisateur -------//
                String nom, prenom, email, password, passwordConf;
                nom = Casenom.getText().toString();
                prenom = Caseprenom.getText().toString();
                email = Caseemail.getText().toString();
                password = Casepassword.getText().toString();
                passwordConf = Caseconfirmpass.getText().toString();
                String region = mySpinner.getSelectedItem().toString(); ////  variable de sortie de la region

                Toast.makeText(NewAccountActivity.this, email,
                        Toast.LENGTH_SHORT).show();
                boolean isvalide = validateEmail(email, Caseemail) & validatePassword(password, Casepassword) & isVide(nom, prenom);

                if (isvalide & confirm(password, passwordConf)) {

                    mauth.createUserWithEmailAndPassword(email, password)
                            .addOnCompleteListener(NewAccountActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        // Sign in success, update UI with the signed-in user's information
                                        Log.d(TAG, "createUserWithEmail:success");
                                        startActivity(new Intent(NewAccountActivity.this, MenuActivity.class));

                                    } else {
                                        // If sign in fails, display a message to the user.
                                        Log.d(TAG, "createUserWithEmail:failure", task.getException());
                                        Toast.makeText(NewAccountActivity.this, "Authe Failed.",
                                                Toast.LENGTH_SHORT).show();

                                    }

                                    // ...
                                }
                            });

                }
            }
        });
    }


    private boolean confirm(String e1, String e2) {
        if (e1.equals(e2)) {
            return true;
        } else {
            Caseconfirmpass.setError(getString(R.string.confondu));
            return false;
        }

    }

    public boolean validateEmail(String email, EditText em) {
        if (email.isEmpty()) {
            em.setError(getString(R.string.vide));
            return false;
        }
        if (!EMAIL_CHECK.matcher(email).matches()) {
            em.setError(getString(R.string.format));
            return false;
        }
        return true;
    }

    public boolean validatePassword(String pass, EditText passF) {
        if (pass.isEmpty()) {
            passF.setError(getString(R.string.vide));
            return false;
        }
        if (!PASS_CHECK.matcher(pass).matches()) {
            passF.setError(getString(R.string.pass));
            return false;
        }
        return true;
    }

    public boolean isVide(String s1, String s2) {
        if (s1.isEmpty())
            Casenom.setError(getString(R.string.vide));
        if (s2.isEmpty())
            Caseprenom.setError(getString(R.string.vide));
        if (s1.isEmpty() && s2.isEmpty())
            return false;
        return true;

    }

}
