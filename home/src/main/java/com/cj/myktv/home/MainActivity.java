package com.cj.myktv.home;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewbinding.ViewBinding;

import android.os.Bundle;
import android.view.View;

import com.blankj.utilcode.util.ToastUtils;
import com.cj.lib_tools.util.PermissionUtils;
import com.cj.myktv.home.databinding.ActivityMainBinding;
import com.hjq.permissions.OnPermissionCallback;
import com.hjq.permissions.Permission;

import java.util.List;

import timber.log.Timber;
import xyz.doikki.videocontroller.StandardVideoController;


public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding mViewBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        checkPermission();

        initView();

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
    }

    /**
     * 初始化view
     */
    private void initView() {
        mViewBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(mViewBinding.getRoot());

        mViewBinding.player.setUrl("/sdcard/Download/00001752.ts");
        StandardVideoController controller = new StandardVideoController(this);
        controller.addDefaultControlComponent("标题", false);
        mViewBinding.player.setVideoController(controller);
    }
}