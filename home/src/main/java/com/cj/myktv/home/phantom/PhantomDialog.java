package com.cj.myktv.home.phantom;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.cj.lib_tools.dialog.BaseDialog;
import com.cj.myktv.home.R;
import com.cj.myktv.home.databinding.DialogHdmiBinding;

/**
 * @Description:
 * @Author: CJ
 * @CreateDate: 2025/3/11 下午 3:02:20
 */
public class PhantomDialog extends BaseDialog {

    private DialogHdmiBinding mBinding;

    public PhantomDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DialogHdmiBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());
        getWindow().getAttributes().width = getWindow().getAttributes().height = ViewGroup.LayoutParams.MATCH_PARENT;
        getWindow().getAttributes().windowAnimations = 0;
    }

    public void addView(View view){
        mBinding.container.addView(view);
    }

    public void removeView(){
        mBinding.container.removeAllViews();
    }
}
