package com.ruanyun.chezhiyi.commonlib.view.widget;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.ruanyun.chezhiyi.commonlib.R;

/**
 * 我的爱车  弹出框
 * Created by Sxq on 2016/9/21.
 */
public class VehicleDialogFragmnet extends DialogFragment {
    private View view;
    private ImageView imgCancel;
    private TextView tvTitle;
    private String title;
    private Context mContext;
    private int imgs;
    private ImageView ivDriveLicense;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        view = inflater.inflate(R.layout.verhicle_info_dialog, container,
                false);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
    }

    private void initView() {
        imgCancel= (ImageView) view.findViewById(R.id.iv_cancel);
        imgCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        tvTitle= (TextView) view.findViewById(R.id.tv_title);
        if (title != null && title.length() > 0) {
            tvTitle.setText(title);
        }
        ivDriveLicense= (ImageView) view.findViewById(R.id.iv_drive_license);
        if (ivDriveLicense!=null){
            ivDriveLicense.setImageResource(imgs);
        }

    }
    public String getTitle() {
        return title;
    }

    public VehicleDialogFragmnet setTitle(String title) {
        this.title = title;
        if (tvTitle != null) {
            tvTitle.setText(title);
        }
        return this;
    }
    public VehicleDialogFragmnet setImgs(int imged) {
        this.imgs = imged;
        if (ivDriveLicense!=null){
            ivDriveLicense.setImageResource(imged);
        }
        return this;
    }

    public int getImgs() {
        return imgs;
    }
}
