package com.cj.myktv.home.view.songlist;

import android.graphics.Rect;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

/**
 * @Description:
 * @Author: CJ
 * @CreateDate: 2025/3/6 20:41
 */
public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {
    private int spanCount; // 网格的列数
    private int spacing; // 间隔的大小
    private boolean includeEdge; // 是否包含边缘的间隔

    public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
        this.spanCount = spanCount;
        this.spacing = spacing;
        this.includeEdge = includeEdge;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        int position = parent.getChildAdapterPosition(view); // 获取当前item的位置
        int column = position % spanCount; // item的列号

        if (includeEdge) {
            outRect.left = spacing - column * spacing / spanCount; // 间隔处理，如果是首列，则不需要左间隔
            outRect.right = (column + 1) * spacing / spanCount; // 间隔处理，如果是末列，则不需要右间隔

            if (position < spanCount) { // 顶部边缘的间隔处理
                outRect.top = spacing;
            }
            outRect.bottom = spacing; // 底部边缘的间隔处理
        } else {
            outRect.left = column * spacing / spanCount; // 间隔处理
            outRect.right = spacing - (column + 1) * spacing / spanCount; // 间隔处理
//            if (position >= spanCount) {
//                outRect.top = spacing; // 间隔处理，不是首行则有上边距
//            }
//            outRect.top = spacing; // 间隔处理，不是首行则有上边距
        }
    }
}