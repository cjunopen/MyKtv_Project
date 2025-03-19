package com.cj.myktv.home.phantom;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View;

import com.blankj.utilcode.util.ThreadUtils;
import com.cj.lib_tools.util.rxjava.RxjavaUtils;
import com.cj.myktv.home.R;
import com.cj.myktv.videoplayer.view.IjkVideoView;

import java.util.concurrent.TimeUnit;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableEmitter;
import io.reactivex.rxjava3.core.ObservableOnSubscribe;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import xyz.doikki.videocontroller.StandardVideoController;
import xyz.doikki.videoplayer.player.VideoView;

/**
 * @Description: MV展示在副屏或幻影
 * @Author: CJ
 * @CreateDate: 2025/3/11 下午 3:11:42
 */
public class HdmiViewManager {

    private SecondDisplayManager mSecondDisplayManager;

    private PhantomDialog mPhantomDialog;

    private View mHdmiView;

    private int mDelay = 100;

    private ObservableEmitter<PhantomEnum> mObservableEmitter;

    enum PhantomEnum {
        SHOW,
        HIDE
    }

    public HdmiViewManager(Activity activity) {
        mSecondDisplayManager = new SecondDisplayManager(activity);

        mPhantomDialog = new PhantomDialog(activity);
        mPhantomDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                mPhantomDialog.removeView();
                mObservableEmitter.onNext(PhantomEnum.HIDE);
            }
        });

        initHdmiView(activity);

        initEmitter();
    }

    public IjkVideoView getIjkVideoView(){
        return mHdmiView.findViewById(R.id.player);
    }

    private void initHdmiView(Context context){
        mHdmiView = View.inflate(context, R.layout.view_second_display, null);

        IjkVideoView videoView = mHdmiView.findViewById(R.id.player);
        videoView.setUrl("/sdcard/Download/00001752.ts");
        StandardVideoController controller = new StandardVideoController(context);
        controller.addDefaultControlComponent("标题", false);
        videoView.setVideoController(controller);
        videoView.start();
    }

    private void initEmitter(){
        Observable.create(new ObservableOnSubscribe<PhantomEnum>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<PhantomEnum> emitter) throws Throwable {
                mObservableEmitter = emitter;
            }
        })
                .compose(RxjavaUtils.obs_io_main())
                .throttleLast(mDelay, TimeUnit.MILLISECONDS)
                .subscribe(new Observer<PhantomEnum>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull PhantomEnum phantomEnum) {
                        if (phantomEnum == PhantomEnum.SHOW) {
                            showPhantom();
                        }else if (phantomEnum == PhantomEnum.HIDE){
                            hidePhantom();
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        initEmitter();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    /**
     * 显示或隐藏
     */
    public void showOrHidePhantom(){
        mObservableEmitter.onNext(isShowingPhantom() ? PhantomEnum.HIDE : PhantomEnum.SHOW);
    }

    /**
     * 是否在显示幻影
     * @return
     */
    public boolean isShowingPhantom(){
        return mSecondDisplayManager.isEmpty();
    }

    /**
     * 显示幻影
     */
    public void showPhantom(){
        if (!mSecondDisplayManager.isEmpty()){
            mSecondDisplayManager.removeView(mHdmiView);

            ThreadUtils.runOnUiThreadDelayed(new Runnable() {
                @Override
                public void run() {
                    mPhantomDialog.show();
                    mPhantomDialog.addView(mHdmiView);
                }
            }, mDelay);
        }
    }

    /**
     * 隐藏幻影
     */
    public void hidePhantom(){
        if (mSecondDisplayManager.isEmpty()) {
            mPhantomDialog.dismiss();

            ThreadUtils.runOnUiThreadDelayed(new Runnable() {
                @Override
                public void run() {
                    mSecondDisplayManager.addView(mHdmiView);
                }
            }, mDelay);
        }
    }

    /**
     * 显示副屏内容
     */
    public void showSecondDisplay(){
        hidePhantom();
    }
}