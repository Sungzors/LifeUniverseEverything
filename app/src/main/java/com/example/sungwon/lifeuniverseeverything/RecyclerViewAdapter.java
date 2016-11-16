package com.example.sungwon.lifeuniverseeverything;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

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
    }
}
