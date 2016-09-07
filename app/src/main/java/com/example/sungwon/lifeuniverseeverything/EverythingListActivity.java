package com.example.sungwon.lifeuniverseeverything;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CursorAdapter;
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
    SQLHelper helper;

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

        mEverythingPic = (ImageView) findViewById(R.id.everythingPic);
        mRatingNum = (TextView) findViewById(R.id.ratingNumber);
        helper = new SQLHelper(EverythingListActivity.this);
        helper.getReadableDatabase();

        Cursor cursor = SQLHelper.getInstance(EverythingListActivity.this).getEverything();
        cursor.moveToFirst();

        CursorAdapter cursorAdapter = new CursorAdapter(EverythingListActivity.this, cursor, 0) {
            @Override
            public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
                return LayoutInflater.from(context).inflate(R.layout.list_view, viewGroup ,false);
            }

            @Override
            public void bindView(View view, Context context, Cursor cursor) {
                mEverythingName = (TextView) view.findViewById(R.id.everythingName);
                mEverythingName.setText(cursor.getString(cursor.getColumnIndex(SQLHelper.everythingTable.COLUMN_EVERYTHING)));
                String cate = helper.getCategory(cursor.getInt(cursor.getColumnIndex(SQLHelper.everythingTable.COLUMN_CATEGORY_ID)));
                mCategoryText = (TextView) view.findViewById(R.id.categoryText);
                mCategoryText.setText(cate);
            }
        };

        mEverythingView.setAdapter(cursorAdapter);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

}
