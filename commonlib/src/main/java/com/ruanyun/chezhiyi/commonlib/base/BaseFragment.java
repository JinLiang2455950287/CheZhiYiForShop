package com.ruanyun.chezhiyi.commonlib.base;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;

import com.ruanyun.chezhiyi.commonlib.App;
import com.ruanyun.chezhiyi.commonlib.R;

import de.greenrobot.event.EventBus;

/**
 * Description:
 * author: zhangsan on 16/7/19 下午2:50.
 */
public abstract class BaseFragment extends Fragment {
    protected String TAG = getClass().getSimpleName();
    protected View mContentView;
    protected Context mContext;
    protected App app;
    protected boolean isVisiable, isPrepared, isFirstIn=true;

    protected void lazyLoad() {

    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            isVisiable = true;
            onVisiable();
        } else {
           isVisiable=false;
        }
    }

    protected void onVisiable() {
        if(isVisiable&&isFirstIn&&isPrepared){
            isFirstIn=false;
            lazyLoad();
        }

    }


    protected void registerBus() {
        EventBus.getDefault().register(this);
    }

    protected void unRegisterBus() {
        EventBus.getDefault().unregister(this);
    }

    protected void onInVisiable() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        app = App.getInstance();
        mContext = getActivity();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    protected <T extends View> T getView(int viewId) {
        return (T) mContentView.findViewById(viewId);
    }

    protected <T extends View> T getViewFromLayout(@LayoutRes int layout) {
        return (T) LayoutInflater.from(mContext).inflate(layout, null, false);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mContext = null;
    }

    public void showActivity(Intent it) {
        startActivity(it);
    }


    protected void showLoading() {
        app.loadingDialogHelper.showLoading(getActivity(), R.string.loading);
    }

    protected void showLoading(String msg) {
        app.loadingDialogHelper.showLoading(getActivity(), msg);
    }

    protected void dissMissLoading() {
        app.loadingDialogHelper.dissMiss();
    }

    /**
     * show to @param(cls)
     */
    public void showActivity(Class<?> cls, Bundle extras) {
        Intent intent = new Intent(mContext, cls);
        intent.putExtras(extras);
        startActivity(intent);
    }

    /**
     * show to @param(cls)
     */
    public void showActivity(Class<?> cls) {
        Intent intent = new Intent(mContext, cls);
        startActivity(intent);
    }
}
