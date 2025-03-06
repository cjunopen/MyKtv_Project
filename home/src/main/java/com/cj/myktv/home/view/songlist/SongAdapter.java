package com.cj.myktv.home.view.songlist;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.AsyncListUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.cj.myktv.home.R;
import com.cj.myktv.home.databinding.ItemSongBinding;
import com.cj.myktv.lib_business.bean.Song;

/**
 * @Description:
 * @Author: CJ
 * @CreateDate: 2025/3/6 20:14
 */
public class SongAdapter extends RecyclerView.Adapter<SongAdapter.ViewHolder>{

    private AsyncListUtil<Song> mAsyncListUtil;

    public void setAsyncListUtil(AsyncListUtil asyncListUtil) {
        mAsyncListUtil = asyncListUtil;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemSongBinding binding = ItemSongBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Song song = mAsyncListUtil.getItem(position);
        holder.bindData(song);
    }

    @Override
    public int getItemCount() {
        return mAsyncListUtil.getItemCount();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ItemSongBinding mItemSongBinding;

        public ViewHolder(ItemSongBinding binding) {
            super(binding.getRoot());
            mItemSongBinding = binding;
        }

        void bindData(Song song){
            if (song == null) {
                return;
            }
            mItemSongBinding.songName.setText(song.getName());
        }
    }
}
