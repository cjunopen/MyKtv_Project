package com.cj.myktv.home;

import android.app.Application;

import com.cj.lib_tools.util.ToolsLib;
import com.cj.videoplayer.VideoPlayerLib;

/**
 * @Description:
 * @Author: CJ
 * @CreateDate: 2025/3/4 21:08
 */
public class HomeApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        ToolsLib.init();

        VideoPlayerLib.init();
    }
}
