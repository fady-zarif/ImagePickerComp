package com.example.imagepicker;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;

import com.example.imagepickercomp.IPickMedia;
import com.example.imagepickercomp.PickMediaView;

public class MainActivity extends AppCompatActivity {
    PickMediaView pickMediaView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pickMediaView = findViewById(R.id.pickMediaView);
        pickMediaView.handlePickMedia(4, new IPickMedia.IHandlePickImage() {
            @Override
            public void onHandleAddPickImage() {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                intent.setType("image/*");
                startActivityForResult(intent, PickMediaView.PICK_IMAGE_CODE);
            }
        });

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == PickMediaView.PICK_IMAGE_CODE) {
                pickMediaView.setDataFromOnResult(data.getData());
            }
        }
    }

}
