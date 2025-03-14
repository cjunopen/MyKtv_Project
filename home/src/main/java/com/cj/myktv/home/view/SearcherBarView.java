package com.cj.myktv.home.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.cj.myktv.home.R;

/**
 * @Description:
 * @Author: CJ
 * @CreateDate: 2025/3/14 上午 10:07:30
 */
public class SearcherBarView extends ConstraintLayout {

    public interface ISearcherListener{
        void onClickSearch(String word);
    }

    private ISearcherListener mISearcherListener;

    public SearcherBarView(@NonNull Context context) {
        super(context);

        init();
    }

    public SearcherBarView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        init();
    }

    public SearcherBarView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init();
    }

    public SearcherBarView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);

        init();
    }

    public void setISearcherListener(ISearcherListener ISearcherListener) {
        mISearcherListener = ISearcherListener;
    }

    private void init() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.view_searcher_bar, this, true);
        EditText editText = view.findViewById(R.id.editText);

        view.findViewById(R.id.icon_searcher).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mISearcherListener != null) {
                    String word = editText.getText().toString();
                    mISearcherListener.onClickSearch(word);
                }
            }
        });
    }
}
