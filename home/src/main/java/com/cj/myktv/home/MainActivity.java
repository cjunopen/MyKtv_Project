package com.cj.myktv.home;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.blankj.utilcode.util.ToastUtils;
import com.cj.lib_tools.util.PermissionUtils;
import com.cj.lib_tools.widget.GridBanner;
import com.cj.myktv.home.databinding.ActivityMainBinding;
import com.cj.myktv.home.phantom.PhantomHelper;
import com.cj.myktv.home.view.SongBanner;
import com.cj.myktv.lib_business.bean.Song;
import com.cj.myktv.lib_db.KtvDbHelper;
import com.hjq.permissions.OnPermissionCallback;
import com.hjq.permissions.Permission;

import java.util.List;

import timber.log.Timber;


public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding mViewBinding;

    private PhantomHelper mPhantomHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        checkPermission();

        initView();

        mPhantomHelper = new PhantomHelper(this);
        mPhantomHelper.hidePhantom();
    }

    @Override
    protected void onResume() {
        super.onResume();
        test();
    }

    private void test() {

    }

    /**
     * 检查权限
     */
    private void checkPermission() {
        PermissionUtils.checkPermissions(this,
                new String[]{Permission.READ_EXTERNAL_STORAGE, Permission.WRITE_EXTERNAL_STORAGE},
                new OnPermissionCallback() {
                    @Override
                    public void onGranted(@androidx.annotation.NonNull List<String> permissions, boolean allGranted) {
                        if (allGranted) {
                            Timber.i("权限申请通过");
                        } else {
                            ToastUtils.showLong("权限不完整");
                            finish();
                        }
                    }
                });

        PermissionUtils.checkAlertDialogPermissions(this);
    }

    /**
     * 初始化view
     */
    private void initView() {
        mViewBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(mViewBinding.getRoot());

        initSongBanner();
//        AsyncListUtil asyncListUtil = new SongAsycnListUtil(mViewBinding.songRv);
//        SongAdapter songAdapter = new SongAdapter();
//        songAdapter.setAsyncListUtil(asyncListUtil);
//        mViewBinding.songRv.setAsyncListUtil(asyncListUtil);
//        mViewBinding.songRv.setAdapter(songAdapter);

        mViewBinding.btnMv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPhantomHelper.showOrHidePhantom();
            }
        });
    }

    private void initSongBanner() {
        SongBanner banner = mViewBinding.songBanner;
    }
}