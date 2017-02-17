package com.example.sungwon.lifeuniverseeverything.Activity.Fragment;

import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.sungwon.lifeuniverseeverything.R;

/**
 * Created by SungWon on 9/7/2016.
 */
public class SearchSettingFragment extends DialogFragment{

    int mNum;
    private SearchSettingListener callback;

    public interface SearchSettingListener {
        void onSearchButtonSubmit(String setting);
    }

    /**
     * Create a new instance of MyDialogFragment, providing "num"
     * as an argument.
     */
    public static SearchSettingFragment newInstance(int num) {
        SearchSettingFragment f = new SearchSettingFragment();

        // Supply num input as an argument.
        Bundle args = new Bundle();
        args.putInt("num", num);
        f.setArguments(args);

        return f;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            callback = (SearchSettingListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException("Calling Fragment must implement OnAddFriendListener");
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setStyle(DialogFragment.STYLE_NORMAL, android.R.style.Theme_Holo_Light_Dialog_NoActionBar);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.dialog_search_setting, container, false);
        Button everythingSearch = (Button)v.findViewById(R.id.everythingSearch);
        Button tagSearch = (Button)v.findViewById(R.id.tagSearch);
        Button catSearch = (Button)v.findViewById(R.id.catSearch);
        everythingSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String everything = "everything";
                callback.onSearchButtonSubmit(everything);

                dismiss();
            }
        });
        tagSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String tag = "tag";
                callback.onSearchButtonSubmit(tag);
                dismiss();
            }
        });
        catSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String cat = "cat";
                callback.onSearchButtonSubmit(cat);
                dismiss();
            }
        });

        return v;
    }
}
