package com.example.test;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import cz.msebera.android.httpclient.Header;

public class PreviewActivity extends AppCompatActivity {

    Button validerBtn, resizeBtn, rotateBtn;
    ImageView imageView;
    //pour serveur
    RequestParams params;
    AsyncHttpClient client;
    static Bitmap imageBitmap;

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

        setContentView(R.layout.activity_preview);

        validerBtn = findViewById(R.id.validerButton);
        resizeBtn = findViewById(R.id.resizeButton);
        rotateBtn = findViewById(R.id.rotationButton);

        imageView = findViewById(R.id.previewImage);

        // Si on lance une capture par la camera, on récupere l'image
        if (PriseDePhotoActivity.choix == PriseDePhotoActivity.REQUEST_IMAGE_CAPTURE) {
            imageBitmap = BitmapFactory.decodeFile(getIntent().getStringExtra("image_path"));
            imageView.setImageBitmap(imageBitmap);
            imageView.setRotation(imageView.getRotation());
        }

        // Sinon on récupere une image de la gallerie
        else {
            try {
                imageBitmap = MediaStore.Images.Media.getBitmap(
                        getContentResolver(),
                        Uri.parse(getIntent().getStringExtra("image_uri")));
                imageView.setImageBitmap(imageBitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        validerBtn.setOnClickListener(new View.OnClickListener() {
                                          @Override
                                          public void onClick(View v) {
                                              Intent intent = new Intent(PreviewActivity.this, LoadingActivity.class);
                                              startActivity(intent);
                                              finish();
                                              ByteArrayOutputStream stream = new ByteArrayOutputStream();
                                              imageBitmap.compress(Bitmap.CompressFormat.JPEG, 50, stream); //compress to which format you want.
                                              byte[] byte_arr = stream.toByteArray();
                                              String imageStr = Base64.encodeToString(byte_arr, 1);
                                              Log.d("aaaaa", imageStr);
                                              params=new RequestParams();
                                              params.put("image",imageStr);
                                              client=new AsyncHttpClient();
                                              Log.d("ok", "onClick: c est bon");
                                              client.post(getApplicationContext(),"http://192.168.43.36:8080/PlanteApp/register",params,new JsonHttpResponseHandler() {
                                                  @Override
                                                  public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                                                      super.onSuccess(statusCode, headers, response);
                                                      Toast.makeText(PreviewActivity.this, "Submit Success"+response, Toast.LENGTH_SHORT).show();
                                                      Log.d("ok", "onSuccess: ok");
                                                  }

                                                  @Override
                                                  public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                                                      super.onFailure(statusCode, headers, responseString, throwable);
                                                      Toast.makeText(PreviewActivity.this, "Submit not Success", Toast.LENGTH_SHORT).show();
                                                      Log.d("ok", "onSuccess: ok 2");
                                                  }});}

                                      }
        );

        resizeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(PreviewActivity.this, "Resized", Toast.LENGTH_LONG).show();
            }
        });

        rotateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageView.setRotation(imageView.getRotation() - 90);
                Toast.makeText(PreviewActivity.this, "Rotated", Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onBackPressed() {
        finish();
        startActivity(new Intent(PreviewActivity.this,PriseDePhotoActivity.class));
    }
}
