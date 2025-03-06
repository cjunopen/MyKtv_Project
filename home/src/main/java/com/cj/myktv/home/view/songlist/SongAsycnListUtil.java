package com.cj.myktv.home.view.songlist;

import static java.sql.Types.NULL;

import android.bluetooth.BluetoothManager;
import android.os.SystemClock;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.AsyncListUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cj.myktv.lib_business.bean.Song;
import com.cj.myktv.lib_db.KtvDbHelper;
import com.cj.myktv.lib_db.database.TblSong;

import java.util.List;

import timber.log.Timber;

/**
 * @Description:
 * @Author: CJ
 * @CreateDate: 2025/3/6 19:49
 */
public class SongAsycnListUtil extends AsyncListUtil<Song> {

    public SongAsycnListUtil(RecyclerView recyclerView){
        super(Song.class, 20, new MyDataCallback(), new MyViewCallback(recyclerView));
    }

    private static class MyDataCallback extends AsyncListUtil.DataCallback<Song> {

        @Override
        public int refreshData() {
            //更新数据的元素个数。
            //假设预先设定更新若干条。
            int count = Integer.MAX_VALUE;
            Timber.i( "refreshData:" + count);
            return count;
        }

        /**
         * 在这里完成数据加载的耗时任务。
         *
         * @param data
         * @param startPosition
         * @param itemCount
         */
        @Override
        public void fillData(Song[] data, int startPosition, int itemCount) {
            Timber.i( "fillData:" + startPosition + "," + itemCount);
            List<TblSong> tblSongs =  KtvDbHelper.getInstance().getTblSongDao().queryBuilder()
                    .offset(startPosition)
                    .limit(itemCount)
                    .list();
            for (int i = 0; i < itemCount; i++) {
                if (i > tblSongs.size()){
                    break;
                }
                data[i] = new Song(tblSongs.get(i));
            }
        }
    }

    private static class MyViewCallback extends AsyncListUtil.ViewCallback {

        private RecyclerView mRecyclerView;

        public MyViewCallback(RecyclerView recyclerView) {
            mRecyclerView = recyclerView;
        }

        /**
         * @param outRange
         */
        @Override
        public void getItemRangeInto(int[] outRange) {
            getOutRange(outRange);

            /**
             * 如果当前的RecyclerView为空，主动为用户加载数据.
             * 假设预先加载若干条数据
             *
             */
            if (outRange[0] == NULL && outRange[1] == NULL) {
                Timber.i( "当前RecyclerView为空！");
                outRange[0] = 0;
                outRange[1] = 9;
            }

//            Timber.i( "getItemRangeInto,当前可见position: " + outRange[0] + " ~ " + outRange[1]);
        }

        @Override
        public void onDataRefresh() {
            int[] outRange = new int[2];
            getOutRange(outRange);
            mRecyclerView.getAdapter().notifyItemRangeChanged(outRange[0], outRange[1] - outRange[0] + 1);

//            Timber.i( "onDataRefresh:"+outRange[0]+","+outRange[1]);
        }

        @Override
        public void onItemLoaded(int position) {
            mRecyclerView.getAdapter().notifyItemChanged(position);
//            Timber.i( "onItemLoaded:" + position);
        }

        private void getOutRange(int[] outRange){
            outRange[0] = ((LinearLayoutManager) mRecyclerView.getLayoutManager()).findFirstVisibleItemPosition();
            outRange[1] = ((LinearLayoutManager) mRecyclerView.getLayoutManager()).findLastVisibleItemPosition();
        }
    }
}
