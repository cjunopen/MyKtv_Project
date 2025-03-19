package com.cj.myktv.home.manager;

import com.cj.myktv.home.MvPresentation;
import com.cj.myktv.videoplayer.view.IjkVideoView;

import timber.log.Timber;

/**
 * @Description:
 * @Author: CJ
 * @CreateDate: 2025/3/19 20:24
 */
public class PlayCtrlManager {

    public static final int TRACK_ACCOMPANY = 1;  //伴唱音轨
    public static final int TRACK_ORIGINAL = 0;  //原唱音轨

    private int mCurrentTrack = 0;

    private IjkVideoView mIjkVideoView;

    public static PlayCtrlManager getInstance(){
        return SingletonHolder.INSTANCE;
    }

    private static class SingletonHolder{
        private static PlayCtrlManager INSTANCE = new PlayCtrlManager();
    }

    public void setIjkVideoView(IjkVideoView ijkVideoView) {
        mIjkVideoView = ijkVideoView;
    }

    /**
     * 切换原伴唱
     * @return
     */
    public boolean switchOriOrAccompany(){
        mCurrentTrack = mCurrentTrack == TRACK_ACCOMPANY ? TRACK_ORIGINAL : TRACK_ACCOMPANY;
        boolean ret = mIjkVideoView.switchTrack(mCurrentTrack);
        Timber.i("switchOriOrAccompany ret: %s", ret);
        return ret;
    }
}
