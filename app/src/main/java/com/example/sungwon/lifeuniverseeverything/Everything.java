package com.example.sungwon.lifeuniverseeverything;

import java.util.Arrays;
import java.util.List;

/**
 * Created by SungWon on 9/6/2016.
 */
public class Everything {

    String mName;
    String mTag;
    int mCat_id;
    int mRating;
    String mReview;
    String mPicURL;


    public Everything(int mCat_id, String mName, int mRating, String mReview, String mTag) {
        this.mCat_id = mCat_id;
        this.mName = mName;
        this.mRating = mRating;
        this.mReview = mReview;
        this.mTag = (mTag.isEmpty())? "default" :  mTag;
    }

    public void setmRating(int mRating) {
        this.mRating = mRating;
    }

    public void setmReview(String mReview) {
        this.mReview = mReview;
    }

    public void setmTag(String mTag) {
        StringBuilder str = new StringBuilder(this.mTag);
        str.append(", " + mTag);
    }

    public String getmPicURL() {
        return mPicURL;
    }

    public void setmPicURL(String mPicURL) {

        this.mPicURL = mPicURL;
    }

    public int getmCat_id() {

        return mCat_id;
    }

    public String getmName() {
        return mName;
    }

    public int getmRating() {
        return mRating;
    }

    public String getmReview() {
        return mReview;
    }

    public List<String> getmTag() {
        List<String> items = Arrays.asList(mTag.split("\\s*,\\s*"));
        return items;
    }

    public String getmTag(boolean tag){
        return (tag)? mTag: null;
    }
}
