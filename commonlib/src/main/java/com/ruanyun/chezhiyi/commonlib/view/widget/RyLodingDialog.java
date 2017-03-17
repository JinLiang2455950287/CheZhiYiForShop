package com.ruanyun.chezhiyi.commonlib.view.widget;

import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.app.DialogFragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import com.ruanyun.chezhiyi.commonlib.App;
import com.ruanyun.chezhiyi.commonlib.R;
import com.ruanyun.chezhiyi.commonlib.util.LogX;
import com.zhy.autolayout.utils.AutoUtils;

/**
 * Description:
 * author: jery on 2016/5/6 9:50.
 */
public class RyLodingDialog extends DialogFragment {

    TextView tvMessage;
    private View mContentView;
    //AutoLinearLayout llRoot;
    private String msg = "加载中...";


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LogX.d("RyLodingDialog", "onCreate");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (null == mContentView) {
            mContentView = inflater.inflate(R.layout.view_loading_dialog, container, false);
            tvMessage = (TextView) mContentView.findViewById(R.id.tv_message);
            // llRoot = (AutoLinearLayout) mContentView.findViewById(R.id.ll_root);
            // llRoot.getBackground().setAlpha(60);
            tvMessage.setText(msg);
        }
        ColorDrawable colorDrawable = new ColorDrawable(getResources().getColor(R.color.black));
        colorDrawable.setAlpha(120);
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        getDialog().getWindow().setBackgroundDrawable(colorDrawable);
        getDialog().setCanceledOnTouchOutside(false);
        LogX.d("RyLodingDialog", "onCreateView");
//        mContentView.setMinimumHeight(350);
//        mContentView.setMinimumWidth(350);
        AutoUtils.auto(mContentView);
        return mContentView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        LogX.d("RyLodingDialog", "onActivityCreated");
    }

    public static RyLodingDialog newInstance() {
        RyLodingDialog lodingDialog = new RyLodingDialog();
        return lodingDialog;
    }

    public void setMessage(String msg) {
        if (TextUtils.isEmpty(msg))
            return;
        this.msg = msg;
    }

    public void setMessage(@StringRes int stringId) {
        this.msg = App.getInstance().getString(stringId);
    }

    @Override
    public void onDestroyView() {
        LogX.d("RyLodingDialog", "onDestroyView");
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        mContentView = null;
        tvMessage = null;
        LogX.d("RyLodingDialog", "onDestroy");
        super.onDestroy();
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);  //getActivity().getLocalClassName()
        LogX.d("RyLodingDialog", "onDismiss");
//        AppBus.getInstance().post(C.Event.LOADING_DISSMISS);
    }
}
