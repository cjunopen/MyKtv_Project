package com.cj.myktv.home.view;

import android.content.Context;
import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import com.cj.lib_tools.util.ViewUtils;
import com.cj.lib_tools.widget.GridBanner;
import com.cj.myktv.home.R;
import com.cj.myktv.home.interfaces.IHighlightName;
import com.cj.myktv.lib_business.bean.Song;
import com.cj.myktv.lib_db.KtvDbHelper;

import java.util.List;

/**
 * @Description:
 * @Author: CJ
 * @CreateDate: 2025/3/13 上午 10:50:52
 */
public class SongBanner extends GridBanner<Song> {

    private IHighlightName mIHighlightName;

    public SongBanner(Context context) {
        super(context);
    }

    public SongBanner(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SongBanner(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public int getRow() {
        return 2;
    }

    @Override
    public int getCol() {
        return 4;
    }

    @Override
    protected void init() {
        super.init();
        setCanLoop(false);
    }

    @Override
    protected void bindItemViewData(View view, Song data, int pos) {
        ViewUtils.setClickRippleAnim(view);
        TextView textView = view.findViewById(R.id.song_name);
        setSongName(textView, data);
    }

    private void setSongName(TextView textView, Song song){
//        if (mIHighlightName != null) {
//            if (mIHighlightName.highlightName(textView, song)){
//                return;
//            }
//        }
        textView.setText(song.getName());
    }

    public void setIHighlightName(IHighlightName IHighlightName) {
        mIHighlightName = IHighlightName;
    }

    @Override
    protected int getItemViewLayoutId() {
        return R.layout.item_song;
    }
}
