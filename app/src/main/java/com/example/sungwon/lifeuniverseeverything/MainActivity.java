package com.example.sungwon.lifeuniverseeverything;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    ImageView mLuedpic;
    TextView mStart;
    Animation mAnimation;
    SQLHelper mHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mLuedpic = (ImageView)findViewById(R.id.luedpic);
        mStart = (TextView)findViewById(R.id.StartButton);
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
    }

    @Override
    protected void onStop() {
        mLuedpic.clearAnimation();
        super.onStop();
        mHelper.close();
    }
}
