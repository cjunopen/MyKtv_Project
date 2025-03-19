package com.cj.myktv.home;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.blankj.utilcode.util.ToastUtils;
import com.cj.lib_tools.util.PermissionUtils;
import com.cj.myktv.home.databinding.ActivityMainBinding;
import com.cj.myktv.home.manager.search.BaseSearchListener;
import com.cj.myktv.home.manager.search.DefaultSearchListener;
import com.cj.myktv.home.manager.search.FirstSpellSearchListener;
import com.cj.myktv.home.manager.search.KeywordSearchListener;
import com.cj.myktv.home.phantom.HdmiViewManager;
import com.cj.myktv.home.view.SearcherBarView;
import com.hjq.permissions.OnPermissionCallback;
import com.hjq.permissions.Permission;

import java.util.List;

import timber.log.Timber;


public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding mViewBinding;

    private HdmiViewManager mHdmiViewManager;

    private BaseSearchListener mKeywordSearchListener, mDefaultSearchListener, mSpellSearchListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        checkPermission();

        initView();

        mHdmiViewManager = new HdmiViewManager(this);
        mHdmiViewManager.hidePhantom();

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

        mViewBinding.btnMv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mHdmiViewManager.showOrHidePhantom();
            }
        });

        mKeywordSearchListener = new KeywordSearchListener();
        mDefaultSearchListener = new DefaultSearchListener();
        mSpellSearchListener = new FirstSpellSearchListener();

        bannerSetDefault();

        mViewBinding.searcher.setISearcherListener(new SearcherBarView.ISearcherListener() {
            @Override
            public void onClickSearch(String word) {
                Timber.i("onClickSearch: " + word);
                if (TextUtils.isEmpty(word)){
                    bannerSetDefault();
                }else {
                    mSpellSearchListener.setInput(word);
                    mViewBinding.songBanner.refresh(mSpellSearchListener);
                    mViewBinding.songBanner.setIHighlightName(mSpellSearchListener);
                }
            }
        });
    }

    private void bannerSetDefault(){
        mViewBinding.songBanner.setIHighlightName(mDefaultSearchListener);
        mViewBinding.songBanner.refresh(mDefaultSearchListener);
    }
}