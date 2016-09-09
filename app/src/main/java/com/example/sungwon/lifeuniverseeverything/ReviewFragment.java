package com.example.sungwon.lifeuniverseeverything;

import android.app.DialogFragment;
import android.app.FragmentManager;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

/**
 * Created by SungWon on 9/9/2016.
 */
public class ReviewFragment extends DialogFragment {

    static ReviewFragment newInstance(long num) {
        ReviewFragment f = new ReviewFragment();

        // Supply num input as an argument.
        Bundle args = new Bundle();
        args.putLong("num", num);
        f.setArguments(args);

        return f;
    }

    TextView mRevName;
    TextView mRevCat;
    TextView mRevTag;
    TextView mRevRev;
    RatingBar mRatingRev;
    SQLHelper mHelper;
    Button mBack;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setStyle(DialogFragment.STYLE_NORMAL,android.R.style.Theme_Holo_Light_Dialog_NoActionBar);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_review, container, false);
        Bundle bundle = this.getArguments();
        long rid = bundle.getLong("num");
        mRevName = (TextView)v.findViewById(R.id.revName);
        mRevCat = (TextView)v.findViewById(R.id.revCat);
        mRevTag = (TextView)v.findViewById(R.id.revTag);
        mRevRev = (TextView)v.findViewById(R.id.revRev);
        mRatingRev = (RatingBar)v.findViewById(R.id.ratingRev);
        mHelper = new SQLHelper(getActivity());
        mBack = (Button)v.findViewById(R.id.backButton);

        int ridi = (int) rid;
        Cursor cursor = mHelper.getEverythingByID(ridi);
        cursor.moveToFirst();

        mRevName.setText(cursor.getString(cursor.getColumnIndex(SQLHelper.everythingTable.COLUMN_EVERYTHING)));
        mRevCat.setText(mHelper.getCategory(cursor.getInt(cursor.getColumnIndex(SQLHelper.everythingTable.COLUMN_CATEGORY_ID))));
        mRevTag.setText(mHelper.getTag(cursor.getInt(cursor.getColumnIndex(SQLHelper.everythingTable.COLUMN_TAGS_ID))));
        mRevRev.setText(cursor.getString(cursor.getColumnIndex(SQLHelper.everythingTable.COLUMN_REVIEW)));
        mRatingRev.setRating(((float)cursor.getInt(cursor.getColumnIndex(SQLHelper.everythingTable.COLUMN_RATINGS)))/2);

        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.popBackStackImmediate();
            }
        });
        return v;
    }
}
