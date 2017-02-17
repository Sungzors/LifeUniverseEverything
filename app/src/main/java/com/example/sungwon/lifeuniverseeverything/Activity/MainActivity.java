package com.example.sungwon.lifeuniverseeverything.Activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sungwon.lifeuniverseeverything.R;
import com.example.sungwon.lifeuniverseeverything.Utility.SQLHelper;

public class MainActivity extends AppCompatActivity {
    ImageView mLuedpic;
    TextView mStart;
    Animation mAnimation;
    SQLHelper mHelper;
    FloatingActionButton mFab;
    static final int REQUEST_IMAGE_CAPTURE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mLuedpic = (ImageView)findViewById(R.id.luedpic);
        mStart = (TextView)findViewById(R.id.StartButton);
        mFab = (FloatingActionButton)findViewById(R.id.camerafab);
        mAnimation = AnimationUtils.loadAnimation(this, R.anim.mainmenuanim);
        mLuedpic.startAnimation(mAnimation); //enables rotatoes in the background pic
        mHelper = new SQLHelper(this);
        mHelper.getWritableDatabase();
        mStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, EverythingListActivity.class);
                startActivity(intent);
            }
        });
        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dispatchTakePictureIntent();
            }
        });
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    protected void onStop() {
        mLuedpic.clearAnimation();
        super.onStop();
        mHelper.close();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            mLuedpic.setImageBitmap(imageBitmap);
        }
    }
}
