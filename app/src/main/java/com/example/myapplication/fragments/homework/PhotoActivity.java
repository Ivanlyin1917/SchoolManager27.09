package com.example.myapplication.fragments.homework;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.widget.ImageView;

import com.example.myapplication.R;

public class PhotoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);
        ImageView ivPhoto = findViewById(R.id.big_photo);
        Intent photoIntent = getIntent();
        String photo = photoIntent.getStringExtra("photo");
        //Декодируем String в изображение
        byte []decodedString = Base64.decode(photo,Base64.URL_SAFE);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString,0,decodedString.length);
        ivPhoto.setImageBitmap(decodedByte);
    }
}
