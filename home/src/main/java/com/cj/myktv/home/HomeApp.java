package com.cj.myktv.home;

import android.app.Application;

import com.cj.lib_tools.util.ToolsLib;
import com.cj.myktv.lib_netapi.datacenter.DataCenterApi;
import com.cj.myktv.videoplayer.VideoPlayerLib;

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

        DataCenterApi.getInstance().init();
    }
}
