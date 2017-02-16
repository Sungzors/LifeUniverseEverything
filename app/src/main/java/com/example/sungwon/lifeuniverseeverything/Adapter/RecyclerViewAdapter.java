package com.example.sungwon.lifeuniverseeverything.Adapter;

import android.app.Activity;
import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sungwon.lifeuniverseeverything.R;
import com.example.sungwon.lifeuniverseeverything.Fragment.ReviewFragment;
import com.example.sungwon.lifeuniverseeverything.Utility.SQLHelper;
import com.squareup.picasso.Picasso;

import java.util.Random;

/**
 * Created by SungWon on 11/15/2016.
 */

public class RecyclerViewAdapter extends CursorRecyclerViewAdapter<RecyclerViewAdapter.ViewHolder> {
    Context context;

    public RecyclerViewAdapter(Context context, Cursor cursor) {

        super(context, cursor);
        this.context = context;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView everythingPic;
        public TextView everythingName;
        public TextView categoryText;
        public TextView ratingNum;
        public CardView cardView;


        public ViewHolder(View view) {
            super(view);

            everythingPic = (ImageView)view.findViewById(R.id.everythingPic);
            everythingName = (TextView)view.findViewById(R.id.everythingName);
            categoryText = (TextView)view.findViewById(R.id.categoryText);
            ratingNum = (TextView)view.findViewById(R.id.ratingNumber);
            cardView = (CardView) view.findViewById(R.id.cardView);
        }

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view,parent,false);
        ViewHolder viewHolder = new ViewHolder(itemView);

        return viewHolder;
    }

    private final static int FADE_DURATION = 500;

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, Cursor cursor) {
        viewHolder.everythingName.setText(cursor.getString(cursor.getColumnIndex(SQLHelper.everythingTable.COLUMN_EVERYTHING)));
        SQLHelper helper = new SQLHelper(context);
        String cate = helper.getCategory(cursor.getInt(cursor.getColumnIndex(SQLHelper.everythingTable.COLUMN_CATEGORY_ID)));
        viewHolder.categoryText.setText(cate + " ");
        viewHolder.ratingNum.setText(" " + String.valueOf(cursor.getInt(cursor.getColumnIndex(SQLHelper.everythingTable.COLUMN_RATINGS))) + " / 10");
        String imgurl = cursor.getString(cursor.getColumnIndex(SQLHelper.everythingTable.COLUMN_PICTURE));
        try{
            Picasso.with(context).load(imgurl).into(viewHolder.everythingPic);
        }catch(IllegalArgumentException e){
            Log.d("Picasso", e.getMessage());
        }
        setFadeAnimation(viewHolder.cardView);
        setScaleAnimation(viewHolder.cardView);
        setAnimation(viewHolder.cardView, lastPosition);
        final Integer position = cursor.getPosition();
        viewHolder.cardView.setTag(position);
        viewHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Cursor cursor = getCursor();
                int position = ((Integer) view.getTag()).intValue();
                cursor.moveToPosition(position);
                int id = cursor.getInt(cursor.getColumnIndex(SQLHelper.everythingTable._ID));
                Bundle bundle = new Bundle();
                bundle.putLong("rid", id);

                ReviewFragment revfrag = new ReviewFragment();
                revfrag.setArguments(bundle);
                showReviewDialog(id);

//                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
//
//                    View image2 = view.findViewById(R.id.imageView);
//                    ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(((Activity) view.getContext()), image2, "imageViewTransition");
//                    view.getContext().startActivity(intent, options.toBundle());
//                }else{
//                    view.getContext().startActivity(intent);
//                }
            }
        });
    }

    private void setFadeAnimation(View view) {
        AlphaAnimation anim = new AlphaAnimation(0.0f, 1.0f);
        anim.setDuration(FADE_DURATION);
        view.startAnimation(anim);
    }
    private void setScaleAnimation(View view) {
        ScaleAnimation anim = new ScaleAnimation(0.0f, 1.0f, 0.0f, 1.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        anim.setDuration(FADE_DURATION);
        view.startAnimation(anim);
    }
    private int lastPosition = -1;

    private void setAnimation(View viewToAnimate, int position) {
        // If the bound view wasn't previously displayed on screen, it's animated
        if (position > lastPosition) {
            ScaleAnimation anim = new ScaleAnimation(0.0f, 1.0f, 0.0f, 1.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
            anim.setDuration(new Random().nextInt(501));//to make duration random number between [0,501)
            viewToAnimate.startAnimation(anim);
            lastPosition = position;
        }
    }
    public void showReviewDialog(long l) {


        // DialogFragment.show() will take care of adding the fragment
        // in a transaction.  We also want to remove any currently showing
        // dialog, so make our own transaction and take care of that here.
        FragmentTransaction ft = ((Activity)context).getFragmentManager().beginTransaction();
        Fragment prev = ((Activity)context).getFragmentManager().findFragmentByTag("review");
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);

        // Create and show the dialog.
        DialogFragment newFragment = ReviewFragment.newInstance(l);
        newFragment.show(ft, "review");
    }
}
