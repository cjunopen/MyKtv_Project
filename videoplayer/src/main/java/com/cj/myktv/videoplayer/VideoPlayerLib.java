package com.cj.myktv.videoplayer;

import xyz.doikki.videoplayer.ijk.IjkPlayerFactory;
import xyz.doikki.videoplayer.player.VideoViewConfig;
import xyz.doikki.videoplayer.player.VideoViewManager;

/**
 * @Description:
 * @Author: CJ
 * @CreateDate: 2025/3/3 21:52
 */
public class VideoPlayerLib {
    public static void init(){
        VideoViewManager.setConfig(VideoViewConfig.newBuilder()
                //使用使用IjkPlayer解码
                .setPlayerFactory(IjkPlayerFactory.create())
                .build());
    }
}
