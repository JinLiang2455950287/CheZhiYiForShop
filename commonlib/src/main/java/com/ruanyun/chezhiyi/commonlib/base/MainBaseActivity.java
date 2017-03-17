package com.ruanyun.chezhiyi.commonlib.base;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.hyphenate.EMMessageListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.util.EMLog;
import com.ruanyun.chezhiyi.commonlib.R;
import com.ruanyun.chezhiyi.commonlib.hxchat.Constant;
import com.ruanyun.chezhiyi.commonlib.hxchat.HXHelper;
import com.ruanyun.chezhiyi.commonlib.util.LogX;
import com.ruanyun.chezhiyi.commonlib.view.ui.common.LoginActivity;
import com.ruanyun.chezhiyi.commonlib.view.widget.BottomLayoutTextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ycw on 2016/8/23.
 */
public class MainBaseActivity extends AutoLayoutActivity implements BottomLayoutTextView.onCheckChangedListener {

    protected List<Fragment> fragments = new ArrayList<>();
    protected int currentPageIndex = -1;
    protected List<BottomLayoutTextView> tapList;
    protected boolean[] isFragmentsAdded = {false, false, false, false, false};
    protected boolean isMessage = false;
    protected int msgCount;
    private EMMessageListener messageListener = new EMMessageListener() {

        @Override
        public void onMessageReceived(List<EMMessage> messages) {
            // 提示新消息
            int remindCount = 0;
            for (EMMessage message : messages) {
                HXHelper.getInstance().getNotifier().onNewMsg(message);
                if (!TextUtils.isEmpty(message.getStringAttribute("remindType", ""))) {        //后台推送消息
                    remindCount++;
                    //                    PrefUtility.put(C.PrefName.REMINDTYPE, PrefUtility.getInt(C.PrefName.REMINDTYPE, 0)+1);
                    //                    EventBus.getDefault().postSticky(C.EventKey.REMINDTYPE);  //  司机端  显示有新消息提醒
                    //                    if (message.getStringAttribute("remindType","").equals("GD") && !isClient()){     //  技师端  收到工单的推送消息时
                    //                        getFriendShipInfo(app.getCurrentUserNum());
                    //                        EventBus.getDefault().postSticky(C.EventKey.UPDATE_WORKER_NUMBER);
                    //                    }
                }
            }
            msgCount += messages.size() - remindCount;
            if (msgCount > 0) onReciverMsgCount(msgCount);
        }

        @Override
        public void onCmdMessageReceived(List<EMMessage> messages) {
        }

        @Override
        public void onMessageReadAckReceived(List<EMMessage> messages) {
        }

        @Override
        public void onMessageDeliveryAckReceived(List<EMMessage> message) {
        }

        @Override
        public void onMessageChanged(EMMessage message, Object change) {
        }
    };
    private boolean isExceptionDialogShow = false;
    private AlertDialog.Builder exceptionBuilder;
    private boolean backPressedToExitOnce = false;

    /**
     * 初始化底部导航栏的文字图片和点击事件
     *
     * @param bottomLayoutTextViews
     * @param strings
     * @param drawables
     */
    protected void initView(List<BottomLayoutTextView> bottomLayoutTextViews, String[] strings, Drawable[] drawables) {
        tapList = bottomLayoutTextViews;
        setBottomTextAndDrawable(bottomLayoutTextViews, strings, drawables);
        setBottomTextListener(bottomLayoutTextViews);
    }

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        getSupportFragmentManager().enableDebugLogging(true);
        LogX.e(TAG, "onCreate: MainBaseActivity   " +
                "\n[bundle] currentIndex " + (bundle == null ? "" : bundle.getInt("currentIndex")) +
                "\n[bundle] isMessage " + (bundle == null ? "" : bundle.getBoolean("isMessage")) +
                "\n[bundle] isExceptionDialogShow " + (bundle == null ? "" : bundle.get("isExceptionDialogShow")));
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        currentPageIndex = savedInstanceState.getInt("currentIndex");
        isMessage = savedInstanceState.getBoolean("isMessage", false);
        isExceptionDialogShow = savedInstanceState.getBoolean("isExceptionDialogShow", false);
        LogX.e(TAG, "\nonRestoreInstanceState: MainBaseActivity  ---- currentIndex -- " + currentPageIndex + "  ----isMessage----" + isMessage);
        //        isFragmentsAdded = savedInstanceState.getBooleanArray("isFragmentsAdded");
        //        LogX.e(TAG, "onRestoreInstanceState: MainBaseActivity   remove  : " + getSupportFragmentManager().getBackStackEntryCount() );
        //          fragments = getSupportFragmentManager().getFragments();
        //        if (!(tapList == null || tapList.isEmpty())) {
        //            LogX.e(TAG, "onRestoreInstanceState: MainBaseActivity   tapList  " + tapList.toArray().toString());
        //            setBottomTextListener(tapList);
        //        }
    }

    protected void onTabClick(int index) {
        unChooseAll();
        currentPageIndex = index;
        tapList.get(index - 1).setChecked(true);
    }

    protected void setBottomTextAndDrawable(List<BottomLayoutTextView> textViews, String[] strings, Drawable[] drawables) {
        for (int i = 0; i < textViews.size(); i++) {
            textViews.get(i).setText(strings[i]);
            textViews.get(i).setCompoundDrawablesWithIntrinsicBounds(null, drawables[i], null, null);
        }
    }

    protected void setBottomTextListener(List<BottomLayoutTextView> textViews) {
        for (BottomLayoutTextView textView : textViews) {
            textView.setOnCheckChangedListener(this);
        }
    }

    protected void unChooseAll() {
        for (BottomLayoutTextView tv : tapList) {
            tv.setChecked(false);
        }
    }

    /**
     * 显示指定的  fragment
     *
     * @param index
     * @param fragment
     */
    protected void showFragmentAtIndex(int index, Fragment fragment) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        LogX.e(TAG, "showFragmentAtIndex: MainBaseActivity ----- index  :  " + index + "");
        if (!isFragmentsAdded[index - 1]) {//未加入系统fragment堆栈
            ft.add(R.id.frag_container, fragment).show(fragment).commit();
            isFragmentsAdded[index - 1] = true;
        } else {
            ft.show(fragment).commit();
        }
    }

    private String makeFragmentName(int index) {
        return "android:switcher:  #" + index + "  Fragment";
    }

    protected void hideAllFragments() {
        if (fragments.size() > 0) for (Fragment fragment : fragments) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.hide(fragment).commitAllowingStateLoss();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt("currentIndex", currentPageIndex);
        outState.putBoolean("isMessage", isMessage);
        outState.putBoolean("isExceptionDialogShow", isExceptionDialogShow);
        LogX.e(TAG, "onSaveInstanceState: MainBaseActivity---  currentPageIndex == " + currentPageIndex + "--- isMessage == " + isMessage);
        //        outState.putBooleanArray("isFragmentsAdded", isFragmentsAdded);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onDestroy() {
        LogX.e(TAG, "onDestroy: MainBaseActivity");
        removeBottomTextCallback();
        super.onDestroy();
    }

    private void removeBottomTextCallback() {
        for (BottomLayoutTextView tv : tapList) {
            tv.removeCallback();
        }
    }

    @Override
    public void onCheckChanged(boolean checked, View view) {
        //LogX.e(TAG, "onCheckChanged: MainBaseActivity" + "  checked   " + checked + "  view id:" + view.getId());
        if (checked) {
            hideAllFragments();
            if (currentPageIndex == 2) {
                if (isMessage) showFragmentAtIndex(5, fragments.get(4));
                    //                    showFragmentAtIndex(2, fragments.get(1));
                else showFragmentAtIndex(2, fragments.get(1));
                //                    showFragmentAtIndex(5, fragments.get(4));
            } else showFragmentAtIndex(currentPageIndex, fragments.get(currentPageIndex - 1));
        }
    }

    @Override
    public void onBackPressed() {
        if (backPressedToExitOnce) {
            super.onBackPressed();
        } else {
            this.backPressedToExitOnce = true;
            Toast.makeText(app, "再按一次退出", Toast.LENGTH_SHORT).show();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    backPressedToExitOnce = false;
                }
            }, 2000);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        LogX.e(TAG, "onStart: MainBaseActivity");
        EMClient.getInstance().chatManager().addMessageListener(messageListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        LogX.e(TAG, "onStop: MainBaseActivity");
        EMClient.getInstance().chatManager().removeMessageListener(messageListener);
    }

    protected void onReciverMsgCount(int msgCount) {

    }

    /**
     * 显示异地登录弹框
     *
     * @param intent
     */
    protected void showExceptionDialogFromIntent(Intent intent) {
        LogX.e(TAG, "showExceptionDialogFromIntent");
        if (!isExceptionDialogShow && intent.getBooleanExtra(Constant.ACCOUNT_CONFLICT, false)) {
            showExceptionDialog();
        }
    }

    /**
     * show the dialog when user met some exception: such as login on another device, user removed or user forbidden
     */
    private void showExceptionDialog() {
        isExceptionDialogShow = true;
        HXHelper.getInstance().logout(false, null);
        if (!MainBaseActivity.this.isFinishing()) {
            // clear up global variables
            try {
                if (exceptionBuilder == null) exceptionBuilder = new AlertDialog.Builder(mContext);
                exceptionBuilder.setTitle(getResources().getString(R.string.Logoff_notification));
                exceptionBuilder.setMessage(R.string.connect_conflict);
                exceptionBuilder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        exceptionBuilder = null;
                        isExceptionDialogShow = false;
                        finish();
                        Intent intent = new Intent(mContext, LoginActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    }
                }).setCancelable(false).create().show();
            } catch (Exception e) {
                EMLog.e(TAG, "---------color conflictBuilder error" + e.getMessage());
            }
        }
    }

}
