package com.cj.myktv.home.phantom;

import android.content.Context;
import android.hardware.display.DisplayManager;
import android.os.Build;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;

import com.cj.lib_tools.dialog.DialogUtils;

import timber.log.Timber;

/**
 * @Description:
 * @Author: CJ
 * @CreateDate: 2025/3/10 下午 5:26:39
 */
public class SecondDisplayManager {

    private Display mSecondDisplay;

    private Context mSecondDisplayContext;

    private WindowManager mWindowManager;

    private boolean isEmpty = true;

    private Context mContext;

    public SecondDisplayManager(Context context) {
        mContext = context;
        initDisplay(context);
    }

    private void initDisplay(Context context){
        DisplayManager displayManager = (DisplayManager) context.getSystemService(Context.DISPLAY_SERVICE);
        Display[] displays = displayManager.getDisplays();
        if (displays.length < 2){
            Timber.e("displays.length < 2");
            return;
        }
        Display display = displays[1];
        Timber.d("display[1]:" + display);
        this.mSecondDisplay = display;
        this.mWindowManager = (WindowManager) context.createDisplayContext(display).getSystemService(Context.WINDOW_SERVICE);
        this.mSecondDisplayContext = context.createDisplayContext(this.mSecondDisplay);
    }

    /**
     * 是否空视图
     * @return
     */
    public boolean isEmpty(){
        return isEmpty;
    }

    /**
     * 添加到副屏
     */
    public void addView(View view) {
        try {
            this.mWindowManager.addView(view, createLayoutParams());
        }catch (Exception e){
            e.printStackTrace();
        }
        isEmpty = false;
    }

    /**
     * 从副屏移除
     */
    public void removeView(View view) {
        try {
            this.mWindowManager.removeView(view);
        }catch (Exception e){
            e.printStackTrace();
        }
        isEmpty = true;
    }

    private WindowManager.LayoutParams createLayoutParams(){
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        DialogUtils.setAlertType(layoutParams);
        return layoutParams;
    }
}
