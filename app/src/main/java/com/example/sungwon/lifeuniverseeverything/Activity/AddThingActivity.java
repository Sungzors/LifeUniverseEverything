package com.example.sungwon.lifeuniverseeverything.Activity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Spinner;

import com.example.sungwon.lifeuniverseeverything.Class.Everything;
import com.example.sungwon.lifeuniverseeverything.R;
import com.example.sungwon.lifeuniverseeverything.Utility.SQLHelper;
import com.squareup.picasso.Picasso;

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
    String mCatselect;
    int mRating = 0;
    String mPicURL = "";


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
        mLUEpic = (ImageView)findViewById(R.id.addImage);
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

        mLUEpic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(AddThingActivity.this);
                final EditText urlinput = new EditText(AddThingActivity.this);
                urlinput.setInputType(InputType.TYPE_CLASS_TEXT);
                builder.setView(urlinput);
                builder.setMessage(R.string.imageaddtitle)
                        .setPositiveButton(R.string.positivebutton, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                mPicURL = urlinput.getText().toString();
                                Picasso.with(AddThingActivity.this).load(mPicURL).into(mLUEpic);
                            }
                        })
                        .setNegativeButton(R.string.negativebutton, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                Dialog dialog = builder.create();
                dialog.show();
            }
        });
        mAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = mNameTextAdd.getText().toString();
                String tags = mTagAdd.getText().toString();
                String rev = mRevAdd.getText().toString();
                int cid = mHelper.getCategoryid(mCatselect);

                Everything thing = new Everything(cid, name, mRating, rev, tags );
                if(!mPicURL.isEmpty()){
                    thing.setmPicURL(mPicURL);
                }
                int bb = mHelper.insertEverything(thing);
                mHelper.insertTag(thing, bb);

                Intent intent = new Intent(AddThingActivity.this, EverythingListActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        mCatselect = adapterView.getItemAtPosition(i).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        mCatselect = "state of being";
    }
}
