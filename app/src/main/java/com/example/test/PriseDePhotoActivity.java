package com.example.test;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class PriseDePhotoActivity extends AppCompatActivity {

    private Button cameraBtn, uploadBtn;

    static final int REQUEST_IMAGE_UPLOAD = 0;
    static final int REQUEST_IMAGE_CAPTURE = 1;
    static int choix;
    String currentImagePath = null;

    @Override
    protected void onResume() {
        super.onResume();

        // Changement de langue
        ChangeLanguage.changeLanguage(this, ChangeLanguage.langue);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        // Changement de langue
        ChangeLanguage.changeLanguage(this, ChangeLanguage.langue);

        setContentView(R.layout.activity_prise_de_photo);

        cameraBtn = findViewById(R.id.takeButton);
        uploadBtn = findViewById(R.id.uploadButton);


        cameraBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Objet de verification des permissions
                PermissionListener permissionListener = new PermissionListener() {
                    @Override
                    public void onPermissionGranted() {
                        // Si la permission est donnée, on lance la caméra
                        launchCamera();
                    }

                    @Override
                    public void onPermissionDenied(List<String> deniedPermissions) {
                        // Si on ne donne pas la permission, on affiche un texte et on ne fait rien
                        Toast
                                .makeText(
                                        PriseDePhotoActivity.this,
                                        "Veuillez accorder la permission pour continuer",
                                        Toast.LENGTH_SHORT)
                                .show();
                    }
                };

                // Verification de la permission de la camera
                TedPermission.with(PriseDePhotoActivity.this)
                        .setPermissionListener(permissionListener)
                        .setPermissions(Manifest.permission.CAMERA, Manifest.permission.ACCESS_FINE_LOCATION)
                        .check();
            }
        });

        uploadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Objet de verification des permissions
                PermissionListener permissionListener = new PermissionListener() {
                    @Override
                    public void onPermissionGranted() {

                        // Si la permission est accordée, on ouvre la gallerie
                        openGallery();
                    }

                    @Override
                    public void onPermissionDenied(List<String> deniedPermissions) {
                        // Si on ne donne pas la permission, on affiche un texte et on ne fait rien
                        Toast
                                .makeText(
                                        PriseDePhotoActivity.this,
                                        "Veuillez accorder la permission pour continuer",
                                        Toast.LENGTH_SHORT)
                                .show();
                    }
                };

                // Verification de la permission de la camera
                TedPermission.with(PriseDePhotoActivity.this)
                        .setPermissionListener(permissionListener)
                        .setPermissions(Manifest.permission.READ_EXTERNAL_STORAGE)
                        .check();
            }

        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Passage à l'activité suivante :

        // Switch après la camera
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            // Switch de fenetres
            Intent switchToPreview = new Intent(PriseDePhotoActivity.this, PreviewActivity
                    .class);
            switchToPreview.putExtra("image_path", currentImagePath);
            startActivity(switchToPreview);
        }

        // Switch après la gallerie
        if (requestCode == REQUEST_IMAGE_UPLOAD && resultCode == RESULT_OK) {
            try {
                Uri imageUri = data.getData();
                // Switch de fenetres
                Intent switchToPreview = new Intent(PriseDePhotoActivity.this, PreviewActivity
                        .class);
                // On passe l'uri de l'image au prochain intent
                switchToPreview.putExtra("image_uri", imageUri.toString());
                startActivity(switchToPreview);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    // Lancement de la caméra
    private void launchCamera() {
        choix = REQUEST_IMAGE_CAPTURE;
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        File imageFile = null;
        try {
            imageFile = getImageFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (imageFile != null) {
            Uri imageUri = FileProvider.getUriForFile(this, "com.example.test", imageFile);
            cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
            startActivityForResult(cameraIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    // Ouverture de la gallerie pour upload
    private void openGallery() {
        choix = REQUEST_IMAGE_UPLOAD;
        Intent galleryIntent = new Intent();
        galleryIntent.setType("image/*");
        galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(galleryIntent, "Select Picture"), REQUEST_IMAGE_UPLOAD);
    }

    /**
     * Create an image file
     */
    private File getImageFile() throws IOException {

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageName = "jpg_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);

        File imageFile = File.createTempFile(imageName, ".jpg", storageDir);
        currentImagePath = imageFile.getAbsolutePath();
        return imageFile;
    }

    @Override
    public void onBackPressed() {
        finish();
        startActivity(new Intent(PriseDePhotoActivity.this, MenuActivity.class));
    }
}
