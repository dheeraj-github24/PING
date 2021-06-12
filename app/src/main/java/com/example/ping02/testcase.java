package com.example.ping02;


import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;


import androidx.appcompat.app.AppCompatActivity;

public class testcase extends Activity {
    private static int RESULT_LOAD_IMAGE = 1;
    private ImageView profilepicturechange;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_testcase);
        profilepicturechange = findViewById(R.id.profilepicture1);
        profilepicturechange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //takepicture();
            }
        });
    }
}

