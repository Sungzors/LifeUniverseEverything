package com.example.sungwon.lifeuniverseeverything;

import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

public class EverythingListActivity extends AppCompatActivity implements SearchSettingFragment.SearchSettingListener{

    ListView mEverythingView;
    Button mAlpha;
    Button mCata;
    Button mRata;
    TextView mEverythingName;
    ImageView mEverythingPic;
    TextView mCategoryText;
    TextView mRatingNum;
    SQLHelper helper;
    CursorAdapter mCursorAdapter;
    String mSetting = "everything";
    int mStackLevel = 0;
    boolean aasc = true;
    boolean casc = true;
    boolean rasc = true;

    @Override
    public void onSearchButtonSubmit(String setting) {
        mSetting = setting;
        Toast.makeText(EverythingListActivity.this, "Search setting changed!", Toast.LENGTH_SHORT).show();
    }

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



        handleIntent(getIntent());
        helper = new SQLHelper(EverythingListActivity.this);
        helper.getReadableDatabase();

        final Cursor cursor = SQLHelper.getInstance(EverythingListActivity.this).getEverything();
        cursor.moveToFirst();

        mCursorAdapter = new CursorAdapter(EverythingListActivity.this, cursor, 0) {
            @Override
            public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
                return LayoutInflater.from(context).inflate(R.layout.list_view, viewGroup ,false);
            }

            @Override
            public void bindView(View view, Context context, Cursor cursor) {
                mEverythingName = (TextView) view.findViewById(R.id.everythingName);
                mEverythingName.setText(cursor.getString(cursor.getColumnIndex(SQLHelper.everythingTable.COLUMN_EVERYTHING)));
                String cate = helper.getCategory(cursor.getInt(cursor.getColumnIndex(SQLHelper.everythingTable.COLUMN_CATEGORY_ID)));
                mEverythingPic = (ImageView) findViewById(R.id.everythingPic);
                mCategoryText = (TextView) view.findViewById(R.id.categoryText);
                mCategoryText.setText(cate + " ");
                mRatingNum = (TextView) view.findViewById(R.id.ratingNumber);
                mRatingNum.setText(" " + String.valueOf(cursor.getInt(cursor.getColumnIndex(SQLHelper.everythingTable.COLUMN_RATINGS))) + " / 10");
                String imgurl = cursor.getString(cursor.getColumnIndex(SQLHelper.everythingTable.COLUMN_PICTURE));
                try{
                    Picasso.with(context).load(imgurl).into(mEverythingPic);
                }catch(IllegalArgumentException e){
                    Log.d("Picasso", e.getMessage());
                }
            }
        };

        mEverythingView.setAdapter(mCursorAdapter);


        mEverythingView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Bundle bundle = new Bundle();
                bundle.putLong("rid", l);

                ReviewFragment revfrag = new ReviewFragment();
                revfrag.setArguments(bundle);
                showReviewDialog(l);
            }
        });

        mEverythingView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                helper.deleteOneEverything((int)l);
                Cursor cursor = SQLHelper.getInstance(EverythingListActivity.this).getEverything();
                cursor.moveToFirst();
                mCursorAdapter.changeCursor(cursor);
                return true;
            }
        });


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EverythingListActivity.this, AddThingActivity.class);
                startActivity(intent);
            }
        });

        mAlpha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cursor cursor = helper.getEverythingSort(SQLHelper.everythingTable.COLUMN_EVERYTHING, aasc);
                aasc = !aasc;
                mCursorAdapter.changeCursor(cursor);
            }
        });
        mCata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cursor cursor = helper.getEverythingSort(SQLHelper.everythingTable.COLUMN_CATEGORY_ID, casc);
                casc = !casc;
                mCursorAdapter.changeCursor(cursor);
            }
        });
        mRata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cursor cursor = helper.getEverythingSort(SQLHelper.everythingTable.COLUMN_RATINGS, rasc);
                rasc = !rasc;
                mCursorAdapter.changeCursor(cursor);
            }
        });
    }

    @Override
    protected void onNewIntent(Intent intent) {
        handleIntent(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search_menu, menu);

        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView =
                (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName()));
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.settingButt){
            showDialog();
        }
        return super.onOptionsItemSelected(item);
    }

    private void handleIntent(Intent intent) {

        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            Cursor cursor = SQLHelper.getInstance(EverythingListActivity.this).getSpecificThing(query, mSetting);


            mCursorAdapter.changeCursor(cursor);
        }
    }
    public void showDialog() {
        mStackLevel++;

        // DialogFragment.show() will take care of adding the fragment
        // in a transaction.  We also want to remove any currently showing
        // dialog, so make our own transaction and take care of that here.
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        Fragment prev = getFragmentManager().findFragmentByTag("dialog");
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);

        // Create and show the dialog.
        DialogFragment newFragment = SearchSettingFragment.newInstance(mStackLevel);
        newFragment.show(ft, "dialog");
    }

    public void showReviewDialog(long l) {


        // DialogFragment.show() will take care of adding the fragment
        // in a transaction.  We also want to remove any currently showing
        // dialog, so make our own transaction and take care of that here.
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        Fragment prev = getFragmentManager().findFragmentByTag("review");
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);

        // Create and show the dialog.
        DialogFragment newFragment = ReviewFragment.newInstance(l);
        newFragment.show(ft, "review");
    }

}
