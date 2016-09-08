package com.example.sungwon.lifeuniverseeverything;

import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

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
    static SearchSettingFragment newInstance(int num) {
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

        mNum = getArguments().getInt("num");
        int theme = 0;
        switch ((mNum-1)%6) {
            case 4: theme = android.R.style.Theme_Holo; break;
            case 5: theme = android.R.style.Theme_Holo_Light_Dialog; break;
            case 6: theme = android.R.style.Theme_Holo_Light; break;
            case 7: theme = android.R.style.Theme_Holo_Light_Panel; break;
            case 8: theme = android.R.style.Theme_Holo_Light; break;
        }
        setStyle(DialogFragment.STYLE_NORMAL, theme);
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
