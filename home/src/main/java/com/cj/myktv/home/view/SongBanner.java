package com.cj.myktv.home.view;

import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

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
        create2((int) KtvDbHelper.getInstance().getSongCount());
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
        return Song.getSongList( KtvDbHelper.getInstance().getSongList(pos, size));
    }

    @Override
    protected void bindItemViewData(View view, Song data) {
        ViewUtils.setClickRippleAnim(view);
        TextView textView = view.findViewById(R.id.song_name);
        textView.setText(data.getName());
    }

    @Override
    protected int getItemViewLayoutId() {
        return R.layout.item_song;
    }
}
