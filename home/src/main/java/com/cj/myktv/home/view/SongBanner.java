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
import com.cj.myktv.lib_business.bean.Song;
import com.cj.myktv.lib_db.KtvDbHelper;

import java.util.List;

/**
 * @Description:
 * @Author: CJ
 * @CreateDate: 2025/3/13 上午 10:50:52
 */
public class SongBanner extends GridBanner<Song> {

    private String mSearchSpell = "";
    private int mDefaultColor = Color.GREEN;

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
    protected void init() {
        super.init();
        create2((int) KtvDbHelper.getInstance().querySongCount());
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
    protected List<Song> loadData(int pos, int size) {
        return Song.getSongList(KtvDbHelper.getInstance().querySongListBySpell(mSearchSpell, pos, size));
    }

    @Override
    protected void bindItemViewData(View view, Song data) {
        ViewUtils.setClickRippleAnim(view);
        TextView textView = view.findViewById(R.id.song_name);
        setSongName(textView, data.getName(), mSearchSpell);
    }

    private void setSongName(TextView textView, String name, String word){
        int index = name.toUpperCase().indexOf(word.toUpperCase());
        SpannableStringBuilder ssb = new SpannableStringBuilder(name);
        if (index >= 0 && index + word.length() <= name.length()) {
            ssb.setSpan(new ForegroundColorSpan(mDefaultColor), index, index + word.length(), Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
        }
        textView.setText(ssb);
    }

    @Override
    protected int getItemViewLayoutId() {
        return R.layout.item_song;
    }

    public void refreshBySpell(String spell){
        mSearchSpell = spell;
        create();
        create2((int) KtvDbHelper.getInstance().querySongCountBySpell(spell));
    }
}
