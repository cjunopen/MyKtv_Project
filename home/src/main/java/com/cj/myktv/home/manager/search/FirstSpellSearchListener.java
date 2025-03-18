package com.cj.myktv.home.manager.search;

import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.widget.TextView;

import com.cj.lib_tools.util.StringUtils;
import com.cj.myktv.home.interfaces.IHighlightName;
import com.cj.myktv.lib_business.bean.Song;
import com.cj.myktv.lib_db.KtvDbHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description: 首拼搜索
 * @Author: CJ
 * @CreateDate: 2025/3/17 21:05
 */
public class FirstSpellSearchListener extends BaseSearchListener{
    @Override
    public int getDataCount() {
        return (int) KtvDbHelper.getInstance().querySongCountBySpell(mInput);
    }

    @Override
    public List<Song> loadData(int pos, int size) {
        return Song.getSongList(KtvDbHelper.getInstance().querySongListBySpell(mInput, pos, size));
    }

    @Override
    public boolean highlightName(TextView textView, Song song) {
        if (StringUtils.containsChinese(song.getName())){
            return highlightNameForExistChinese(textView, song);
        }else if (StringUtils.isEnglishOnly(song.getName())){
            return highlightNameForOnlyEnglish(textView, song);
        }
        return false;
    }

    /**
     * 纯英文的高亮
     * @param textView
     * @param song
     */
    private boolean highlightNameForOnlyEnglish(TextView textView, Song song){
        String[] engWord = song.getName().split(" ");
        if (engWord.length == -1){
            return false;
        }
        SpannableStringBuilder builder = new SpannableStringBuilder(song.getName());

        int engWordIndex = 0;
        for (int i = 0; i < mInput.length(); i++) {
            for (int j = engWordIndex; j < engWord.length; j++) {
                if (engWord[j].substring(0, 1).equalsIgnoreCase(mInput.substring(i, i + 1))){
                    engWordIndex = j;
                    int startIndex = song.getName().indexOf(engWord[j]);
                    builder.setSpan(new ForegroundColorSpan(Color.RED), startIndex, startIndex + 1, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
                }
            }
        }
        textView.setText(builder);
        return true;
    }

    /**
     * 带中文的高亮
     */
    private boolean highlightNameForExistChinese(TextView textView, Song song){
        String name = song.getName();
        List<Integer> chineseIndexs = new ArrayList<>();
        for (int i = 0; i < name.length(); i++) {
            if (StringUtils.isChinese(name.charAt(i))){
                chineseIndexs.add(i);
            }
        }

        int index = song.getSpell().toUpperCase().indexOf(mInput.toUpperCase());  //wdhxd匹配hx，index就是2
        if (index == -1){
            return false;
        }
        SpannableStringBuilder builder = new SpannableStringBuilder(song.getName());
        for (int i = 0; i < mInput.length(); i++) {
            int chineseIndex = chineseIndexs.get(index + i);
            builder.setSpan(new ForegroundColorSpan(Color.RED), chineseIndex, chineseIndex + 1, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
        }
        textView.setText(builder);
        return true;
    }

}
