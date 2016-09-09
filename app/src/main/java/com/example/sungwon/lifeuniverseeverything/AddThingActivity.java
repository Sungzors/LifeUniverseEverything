package com.example.sungwon.lifeuniverseeverything;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Spinner;

import java.util.ArrayList;

public class AddThingActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
    ImageView mLUEpic;
    EditText mNameTextAdd;
    Spinner mCatSpinner;
    EditText mTagAdd;
    EditText mRevAdd;
    RatingBar mRatingBar;
    Button mAddButton;
    SQLHelper mHelper;
    String catselect;
    int mRating = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_thing);
        mNameTextAdd = (EditText)findViewById(R.id.addName);
        mCatSpinner = (Spinner)findViewById(R.id.catSpinner);
        mTagAdd = (EditText)findViewById(R.id.tagAdd);
        mRevAdd = (EditText)findViewById(R.id.revAdd);
        mRatingBar = (RatingBar)findViewById(R.id.ratingBar);
        mAddButton = (Button)findViewById(R.id.addButton);
        mHelper = new SQLHelper(this);
        ArrayList<String> catarray= SQLHelper.getInstance(this).getAllCats();
        ArrayAdapter<CharSequence> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapter.addAll(catarray);
        mCatSpinner.setAdapter(adapter);
        mCatSpinner.setOnItemSelectedListener(this);
        mRatingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                mRating = (int) (v * 2);
            }
        });
        mAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = mNameTextAdd.getText().toString();
                String tags = mTagAdd.getText().toString();
                String rev = mRevAdd.getText().toString();
                int cid = mHelper.getCategoryid(catselect);

                Everything thing = new Everything(cid, name, mRating, rev, tags );
                int i = mHelper.insertEverything(thing);
                mHelper.insertTag(thing, i);

                Intent intent = new Intent(AddThingActivity.this, EverythingListActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        catselect = adapterView.getItemAtPosition(i).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        catselect = "state of being";
    }
}
