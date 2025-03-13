package com.cj.myktv.home.view.songlist;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.AsyncListUtil;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.ConvertUtils;

import timber.log.Timber;

/**
 * @Description:
 * @Author: CJ
 * @CreateDate: 2025/3/6 19:21
 */
public class SongRecyclerView extends RecyclerView {

    private AsyncListUtil mAsyncListUtil;

    public SongRecyclerView(@NonNull Context context) {
        super(context);

        init();
    }

    public SongRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        init();
    }

    public SongRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init();
    }

    public void setAsyncListUtil(AsyncListUtil asyncListUtil) {
        mAsyncListUtil = asyncListUtil;
    }

    @Override
    public void setAdapter(@Nullable Adapter adapter) {
        super.setAdapter(adapter);
        post(new Runnable() {
            @Override
            public void run() {
                if (mAsyncListUtil != null) {
                    mAsyncListUtil.refresh();
                }
            }
        });
    }

    private void init() {
        int spanCount = 2;
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), spanCount, GridLayoutManager.HORIZONTAL, false);
        setLayoutManager(layoutManager);
//        addItemDecoration(new GridSpacingItemDecoration(spanCount, ConvertUtils.dp2px(10), false));

        addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                Timber.i("onRangeChanged");
                if (mAsyncListUtil != null) {
                    mAsyncListUtil.onRangeChanged();
                }
            }
        });

        PagerSnapHelper helper = new PagerSnapHelper();
        helper.attachToRecyclerView(this);
    }
}
