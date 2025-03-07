package com.cj.myktv.home;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.AsyncListUtil;
import androidx.recyclerview.widget.GridLayoutManager;

import android.os.Bundle;

import com.blankj.utilcode.util.GsonUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.cj.lib_tools.util.PermissionUtils;
import com.cj.myktv.home.databinding.ActivityMainBinding;
import com.cj.myktv.home.view.songlist.SongAdapter;
import com.cj.myktv.home.view.songlist.SongAsycnListUtil;
import com.cj.myktv.lib_db.KtvDbHelper;
import com.cj.myktv.lib_db.database.TblSong;
import com.cj.myktv.lib_db.database.TblSongDao;
import com.hjq.permissions.OnPermissionCallback;
import com.hjq.permissions.Permission;

import java.util.List;

import timber.log.Timber;


public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding mViewBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        checkPermission();

        initView();

        MvPresentation.getInstance().show();

    }

    @Override
    protected void onResume() {
        super.onResume();
        test();
    }

    private void test() {
        KtvDbHelper.getInstance().getTblSongDao(new KtvDbHelper.IDao<TblSongDao>() {
            @Override
            public void onGetDao(TblSongDao tblSongDao) {
                TblSong song = tblSongDao.queryBuilder().where(TblSongDao.Properties.Id.eq(2L)).unique();
                Timber.i("song: " + GsonUtils.toJson(song));
            }
        });
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

        AsyncListUtil asyncListUtil = new SongAsycnListUtil(mViewBinding.songRv);
        SongAdapter songAdapter = new SongAdapter();
        songAdapter.setAsyncListUtil(asyncListUtil);
        mViewBinding.songRv.setAsyncListUtil(asyncListUtil);
        mViewBinding.songRv.setAdapter(songAdapter);

    }
}