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

import static java.lang.Math.toIntExact;

/**
 * Created by SungWon on 9/9/2016.
 */
public class ReviewFragment extends DialogFragment {

    static SearchSettingFragment newInstance(int num) {
        SearchSettingFragment f = new SearchSettingFragment();

        // Supply num input as an argument.
        Bundle args = new Bundle();
        args.putInt("num", num);
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

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_review, container, false);
        long rid = getArguments().getLong("rid");
        mRevName = (TextView)v.findViewById(R.id.revName);
        mRevCat = (TextView)v.findViewById(R.id.revCat);
        mRevTag = (TextView)v.findViewById(R.id.revTag);
        mRevRev = (TextView)v.findViewById(R.id.revRev);
        mRatingRev = (RatingBar)v.findViewById(R.id.ratingRev);
        mHelper = new SQLHelper(getActivity());
        mBack = (Button)v.findViewById(R.id.backButton);

        int ridi = toIntExact(rid);
        Cursor cursor = mHelper.getEverythingByID(ridi);

        mRevName.setText(cursor.getString(cursor.getColumnIndex(SQLHelper.everythingTable.COLUMN_EVERYTHING)));
        mRevCat.setText(mHelper.getCategory(cursor.getInt(cursor.getColumnIndex(SQLHelper.everythingTable.COLUMN_CATEGORY_ID))));
        mRevTag.setText(mHelper.getTag(cursor.getInt(cursor.getColumnIndex(SQLHelper.everythingTable.COLUMN_TAGS_ID))));
        mRevRev.setText(cursor.getString(cursor.getColumnIndex(SQLHelper.everythingTable.COLUMN_REVIEW)));
        mRatingRev.setRating(cursor.getInt(cursor.getColumnIndex(SQLHelper.everythingTable.COLUMN_RATINGS))/2);

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
