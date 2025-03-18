package com.cj.myktv.home.interfaces;

import android.widget.TextView;

import com.cj.myktv.lib_business.bean.Song;

/**
 * @Description: textview的名字高亮
 * @Author: CJ
 * @CreateDate: 2025/3/17 21:11
 */
public interface IHighlightName {
    boolean highlightName(TextView textView, Song song);
}
