package com.cj.myktv.home;

import android.app.Presentation;
import android.content.Context;
import android.hardware.display.DisplayManager;
import android.os.Bundle;
import android.view.Display;

import com.blankj.utilcode.util.Utils;
import com.cj.myktv.home.databinding.ViewSecondDisplayBinding;
import com.cj.myktv.lib_db.KtvDbHelper;

import timber.log.Timber;
import xyz.doikki.videocontroller.StandardVideoController;

/**
 * @Description:
 * @Author: CJ
 * @CreateDate: 2025/3/7 上午 11:16:06
 */
public class MvPresentation extends Presentation {

    private ViewSecondDisplayBinding mBinding;

    public MvPresentation(Context outerContext, Display display) {
        super(outerContext, display);
    }

    public MvPresentation(Context outerContext, Display display, int theme) {
        super(outerContext, display, theme);
    }

    public static MvPresentation getInstance(){
        return MvPresentation.SingletonHolder.INSTANCE;
    }

    private static class SingletonHolder{
        private static MvPresentation INSTANCE = createMvPresentation();
    }


    private static MvPresentation createMvPresentation(){
        DisplayManager mDisplayManager = (DisplayManager) Utils.getApp().getSystemService(Context.DISPLAY_SERVICE);
        Display[] displays = mDisplayManager.getDisplays();

        Timber.d("DisplayManager displays length:"+displays.length);
        if (displays.length < 2) {
            Timber.d("setContent failed cause -> 设置异显失败！获取不到第二块屏幕");
            return null;
        }
        Timber.d("DisplayManager display[1]:"+displays[1]);

        return new MvPresentation(Utils.getApp(), displays[1], 1);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initView();
    }

    private void initView() {
        mBinding = ViewSecondDisplayBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());

        mBinding.player.setUrl("/sdcard/Download/00001752.ts");
        StandardVideoController controller = new StandardVideoController(getContext());
        controller.addDefaultControlComponent("标题", false);
        mBinding.player.setVideoController(controller);
        mBinding.player.start();
    }
}
