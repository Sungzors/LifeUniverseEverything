package com.example.sungwon.lifeuniverseeverything;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Spinner;

import java.util.ArrayList;

public class AddThingActivity extends AppCompatActivity {
    ImageView mLUEpic;
    EditText mNameTextAdd;
    Spinner mCatSpinner;
    EditText mTagAdd;
    EditText mCatAdd;
    RatingBar mRatingBar;
    Button mAddButton;
    SQLHelper mHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_thing);
        mNameTextAdd = (EditText)findViewById(R.id.addName);
        mCatSpinner = (Spinner)findViewById(R.id.catSpinner);
        mTagAdd = (EditText)findViewById(R.id.tagAdd);
        mCatAdd = (EditText)findViewById(R.id.catAdd);
        mRatingBar = (RatingBar)findViewById(R.id.ratingBar);
        mAddButton = (Button)findViewById(R.id.addButton);
        mHelper = new SQLHelper(this);
        ArrayList<String> catarray= SQLHelper.getInstance(this).getAllCats();
        ArrayAdapter<CharSequence> adapter = new ArrayAdapter<CharSequence>(this, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapter.addAll(catarray);
        mCatSpinner.setAdapter(adapter);
        mAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }
}
