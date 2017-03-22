package com.ruanyun.chezhiyi.commonlib.base;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;

import com.ruanyun.chezhiyi.commonlib.App;
import com.ruanyun.chezhiyi.commonlib.R;
import com.ruanyun.chezhiyi.commonlib.util.LogX;
import com.ruanyun.chezhiyi.commonlib.util.StatusBarUtils;

import de.greenrobot.event.EventBus;

/**
 * Description:
 * author: zhangsan on 16/7/19 下午2:49.
 */
public class BaseActivity extends AppCompatActivity {
    protected String TAG = getClass().getSimpleName();
    protected Context mContext;
    protected App app;

    protected <T extends View> T getView(int viewId) {
        return (T) findViewById(viewId);
    }

    protected <T extends View> T getViewFromLayout(@LayoutRes int layout) {
        return getViewFromLayout(layout, null, false);
    }

    protected <T extends View> T getViewFromLayout(@LayoutRes int layout, ViewGroup root, boolean attach) {
        return (T) LayoutInflater.from(mContext).inflate(layout, root, attach);
    }

    protected <T extends View> T getViewFromLayout(@LayoutRes int layout, @IdRes int viewId) {
        View v = LayoutInflater.from(mContext).inflate(layout, null, false);
        return (T) v.findViewById(viewId);
    }

    @Override
    protected void onCreate(Bundle bundle) {
        // TODO Auto-generated method stub
        super.onCreate(bundle);
        mContext = this;
        app = App.getInstance();
        app.pushActivity(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        app.popActivity(this);
        mContext = null;
    }

    /**
     * 状态栏透明
     *
     * @author zhangsan
     * @date 16/8/24 下午2:01
     */
    protected void statusbarTint(View topbar) {
        StatusBarUtils.from(this)
                .setLightStatusBar(true)
                .setTransparentStatusbar(true)
                .setTransparentNavigationbar(true)
                .setActionbarView(topbar)
                .process();
    }

    /**
     * show to @param(cls)
     */
    public void showActivity(Intent it) {
        startActivity(it);
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

    /**
     * skip to @param(cls), and this finish() method
     */
    public void skipActivity(Class<?> cls) {
        showActivity(cls);
        finish();
    }

    /**
     * 启动包名的    activity
     *
     * @param clasName
     */
    public void showActivity(String packageName, String clasName) {
        Intent i = new Intent();
//        i.setComponent(new ComponentName(getPackageName(), clasName));
        i.setClassName(packageName, clasName);
        startActivity(i);
    }

    public void showActivity(String clasName) {
        Intent i = new Intent();
//        i.setComponent(new ComponentName(getPackageName(), clasName));
        i.setClassName(mContext, clasName);
        startActivity(i);
    }

    protected void showLoading() {
        app.loadingDialogHelper.showLoading(this, R.string.loading);
    }

    protected void showLoading(String msg) {
        app.loadingDialogHelper.showLoading(this, msg);
    }

    protected void showIgnoreStatu(String msg) {
        app.loadingDialogHelper.showIgnoreStatu(this, msg);
    }

    protected void dissMissLoading() {
        app.loadingDialogHelper.dissMiss();
    }

    protected void registerBus() {
        EventBus.getDefault().register(this);
    }

    protected void unRegisterBus() {
        EventBus.getDefault().unregister(this);
    }

    /**
     * true -  客户端
     * false - 商家端
     *
     * @return boolean
     */
    public boolean isClient() {
        LogX.i(TAG, "packageName ---->  " + getPackageName());
        return getPackageName().contains("client");
    }

//    /*关闭软键盘*/
//    public void CloseKeyBoard() {
//        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
//        if (imm != null) {
//            imm.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(), 0);
//        }
//    }

}
