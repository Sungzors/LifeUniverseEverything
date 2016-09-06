package com.example.sungwon.lifeuniverseeverything;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class EverythingListActivity extends AppCompatActivity {

    ListView mEverythingView;
    Button mAlpha;
    Button mCata;
    Button mRata;
    TextView mEverythingName;
    ImageView mEverythingPic;
    TextView mCategoryText;
    TextView mRatingNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_everything_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mEverythingView = (ListView) findViewById(R.id.list_view);
        mAlpha = (Button) findViewById(R.id.alphabetizor);
        mCata = (Button) findViewById(R.id.categorizor);
        mRata = (Button) findViewById(R.id.ratingzor);
        mEverythingName = (TextView) findViewById(R.id.everythingName);
        mEverythingPic = (ImageView) findViewById(R.id.everythingPic);
        mCategoryText = (TextView) findViewById(R.id.categoryText);
        mRatingNum = (TextView) findViewById(R.id.ratingNumber);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

}
