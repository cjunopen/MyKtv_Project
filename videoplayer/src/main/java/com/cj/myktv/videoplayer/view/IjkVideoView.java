package com.cj.myktv.videoplayer.view;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import timber.log.Timber;
import tv.danmaku.ijk.media.player.misc.ITrackInfo;
import tv.danmaku.ijk.media.player.misc.IjkTrackInfo;
import xyz.doikki.videoplayer.ijk.IjkPlayer;
import xyz.doikki.videoplayer.player.BaseVideoView;
import xyz.doikki.videoplayer.player.VideoView;

/**
 * @Description:
 * @Author: CJ
 * @CreateDate: 2025/3/18 21:22
 */
public class IjkVideoView extends BaseVideoView<IjkPlayer> {

    public IjkVideoView(@NonNull Context context) {
        super(context);
    }

    public IjkVideoView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public IjkVideoView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * 切到目标音轨
     * @param trackNo 目标音轨, 0-右声道,或第一条音轨; 1左声道,或第二条音轨; 3-立体声,或第三条音轨
     */
    public boolean switchTrack(int trackNo){
        Timber.i("switchTrack, trackNo: %d", trackNo);
        IjkTrackInfo[] ijkTrackInfos = mMediaPlayer.getTrackInfo();
        for (int i = 0; i < ijkTrackInfos.length; i++) {
            if (ijkTrackInfos[i].getTrackType() != ITrackInfo.MEDIA_TRACK_TYPE_AUDIO){
                continue;
            }
            if (trackNo == 0){
                Timber.i("switchTrack i: %d", i);
                mMediaPlayer.selectTrack(i);
                return true;
            }
            trackNo--;
        }
        return false;
    }
}
