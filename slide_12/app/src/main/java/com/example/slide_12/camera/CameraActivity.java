package com.example.slide_12.camera;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.ImageView;

import com.example.slide_12.R;

public class CameraActivity extends AppCompatActivity {
    static Bitmap bitmap;
    public static ImageView imageView;
    private Button cameraButton, openCameraButton;
    private static final int TAKE_PICTURE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        imageView = findViewById(R.id.image_view);

        if (bitmap != null) {
            imageView.setImageBitmap(bitmap);
        }

        cameraButton = findViewById(R.id.take_a_pic);
        cameraButton.setOnClickListener(v -> {
            startActivityForResult(
                    new Intent(MediaStore.ACTION_IMAGE_CAPTURE), TAKE_PICTURE);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == TAKE_PICTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");

            imageView.setImageBitmap(imageBitmap);
        }
    }
}