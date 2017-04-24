package com.ruanyun.chezhiyi;

import android.Manifest;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.TextView;

import com.ruanyun.chezhiyi.commonlib.base.MainBaseActivity;
import com.ruanyun.chezhiyi.commonlib.hxchat.runtimepermissions.PermissionsManager;
import com.ruanyun.chezhiyi.commonlib.hxchat.runtimepermissions.PermissionsResultAction;
import com.ruanyun.chezhiyi.commonlib.hxchat.ui.ConversationListFragment;
import com.ruanyun.chezhiyi.commonlib.hxchat.ui.PickContactActivity;
import com.ruanyun.chezhiyi.commonlib.hxchat.ui.SeacharAddFriendActivity;
import com.ruanyun.chezhiyi.commonlib.hxchat.widget.TopMenuPopupWindow;
import com.ruanyun.chezhiyi.commonlib.util.AppUtility;
import com.ruanyun.chezhiyi.commonlib.util.C;
import com.ruanyun.chezhiyi.commonlib.util.PrefUtility;
import com.ruanyun.chezhiyi.commonlib.view.ui.HxContactListFragment;
import com.ruanyun.chezhiyi.commonlib.view.widget.BottomLayoutTextView;
import com.ruanyun.chezhiyi.commonlib.view.widget.TopTabButton;
import com.ruanyun.chezhiyi.commonlib.view.widget.Topbar;
import com.ruanyun.chezhiyi.view.ui.main.FindFragment;
import com.ruanyun.chezhiyi.view.ui.main.HomeFragment;
import com.ruanyun.chezhiyi.view.ui.main.MyFragment;
import com.wintone.demo.plateid.AuthServiceHelp;
import com.zhy.autolayout.AutoRelativeLayout;

import java.util.List;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 商家端主页
 */
public class MainActivity extends MainBaseActivity implements TopMenuPopupWindow.onMenuClickListener, Topbar.onTopbarClickListener {

    @BindString(R.string.home)
    String home;
    @BindString(R.string.message)
    String message;
    @BindString(R.string.business)
    String business;
    @BindString(R.string.mine)
    String mine;
    @BindView(R.id.tv_news_count)
    TextView tvNewsCount;
    @BindViews({R.id.tv_home, R.id.tv_news, R.id.tv_find, R.id.tv_person})
    List<BottomLayoutTextView> layoutTextViews;

    // TextView tvSwich,tvInvite,tvAdd,tvGroup;
    // ContactListFragment contactListFragment;
    Topbar topbar;
    TopTabButton topTabButton;
    TopMenuPopupWindow topMenuPopupWindow;
//    ConversationListFragment conversationListFragment;
//    HxContactListFragment hxContactListFragment;
    //ClientContactListFragment hxContactListFragment;

    AuthServiceHelp authServiceHelp;
    String[] permissions = {Manifest.permission.READ_PHONE_STATE, Manifest.permission.CAMERA,
            Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.WRITE_SETTINGS, Manifest.permission.WRITE_EXTERNAL_STORAGE};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        LeakCanary.install(app);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initView();
        authServiceHelp = new AuthServiceHelp(mContext) {
            @Override
            public void authServiceSuccess() {
                PrefUtility.put(C.PrefName.PREF_AUTHORE, true);
            }

            @Override
            public void authServiceFailure(String msg) {
                PrefUtility.put(C.PrefName.PREF_AUTHORE, false);
            }
        };

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            PermissionsManager.getInstance().requestPermissionsIfNecessaryForResult(this, permissions, new PermissionsResultAction() {

                @Override
                public void onGranted() {
                    authServiceHelp.start();
                }

                @Override
                public void onDenied(String permission) {

                }
            });
        } else {
            authServiceHelp.start();
        }
    }

    private void initView() {
        String[] strings = {home, message, business, mine};
        Drawable[] drawables = {ContextCompat.getDrawable(mContext, R.drawable.ic_bottom_home),
                ContextCompat.getDrawable(mContext, R.drawable.ic_bottom_news),
                ContextCompat.getDrawable(mContext, R.drawable.ic_bottom_store),
                ContextCompat.getDrawable(mContext, R.drawable.ic_bottom_personal)};
        super.initView(layoutTextViews, strings, drawables);
        init();
        onTabClick(1);
    }

    private void init() {
        topbar = getView(R.id.topbar);
        topTabButton = getViewFromLayout(R.layout.layout_toptabbar, topbar, false);
        topTabButton.setRightText("通讯录");
        topTabButton.setLeftText("消息");
        topTabButton.setRightTabStatus(true);//
        topTabButton.onLeftTabClick(this, "onLeftTabClick");
        topTabButton.onRightTabClick(this, "onRightTabClick");
        topbar.getTvTitle().setVisibility(View.GONE);
        topbar.addViewToTopbar(topTabButton, (AutoRelativeLayout.LayoutParams) topTabButton.getLayoutParams())
                .enableRightImageBtn()
                .setRightImgBtnBg(R.drawable.nav_add)
                .setTopbarClickListener(this)
                .onRightImgBtnClick();
        topMenuPopupWindow = new TopMenuPopupWindow(this, TopMenuPopupWindow.MODE_MULTIPLE);
        topMenuPopupWindow.setMenuClickListener(this);
        //        conversationListFragment = new ConversationListFragment();
        //        hxContactListFragment = new HxContactListFragment();
        // statusbarTint(topbar);

        fragments.add(new HomeFragment());
        fragments.add(/*hxContactListFragment*/new HxContactListFragment());
        fragments.add(new FindFragment());
        fragments.add(new MyFragment());
        fragments.add(/*conversationListFragment*/new ConversationListFragment());
    }

    public void onLeftTabClick() {
        isMessage = true;
        onTabClick(2);
    }

    public void onRightTabClick() {
        isMessage = false;
        onTabClick(2);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        topTabButton.setLeftTabStatus(isMessage);
        if (fragments != null && fragments.size() > 0)
            onTabClick(currentPageIndex);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        AppUtility.fixInputMethodManagerLeak(this);
        if (authServiceHelp != null)
            authServiceHelp.clear();
    }

    @Override
    public void menuMsgClick() {
        Intent intent = new Intent(mContext, PickContactActivity.class);
        intent.putExtra(C.IntentKey.PICK_CONTACT_PAGE_TYPE, PickContactActivity.TYPE_NEW_GROUP);
        showActivity(intent);
    }

    @Override
    public void menuAddFriendClick() {
        showActivity(SeacharAddFriendActivity.class);
    }

    @Override
    public void onTobbarViewClick(View v) {
        switch (v.getId()) {
            case R.id.img_btn_right:
                if (topMenuPopupWindow != null) {
                    topMenuPopupWindow.showAsDropDownRightPadding(topbar, 40);
                }
                break;
        }
    }


    @OnClick({R.id.tv_home, R.id.tv_news, R.id.tv_find, R.id.tv_person})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_home:
                onTabClick(1);
                break;
            case R.id.tv_news:
                msgCount = 0;
                tvNewsCount.setVisibility(View.GONE);
                onTabClick(2);
                break;
            case R.id.tv_find:
                onTabClick(3);
                break;
            case R.id.tv_person:
                onTabClick(4);
                break;
        }
    }

    @Override
    protected void onReciverMsgCount(final int msgCount) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                tvNewsCount.setVisibility(View.VISIBLE);
                if (msgCount > 99) {
                    tvNewsCount.setText("99+");
                } else {
                    tvNewsCount.setText(Integer.toString(msgCount));
                }
            }
        });
    }

    @Override
    protected void onTabClick(int index) {
        super.onTabClick(index);
        topbar.setVisibility(index == 2 ? View.VISIBLE : View.GONE);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        showExceptionDialogFromIntent(intent);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        PermissionsManager.getInstance().notifyPermissionsChange(permissions, grantResults);
    }

    /**
     * =========================================分割线====================================================
     */
//
//    public AuthService.MyBinder authBinder;
//    private int ReturnAuthority = -1;
//    //授权验证服务绑定后的操作与start识别服务
//    public ServiceConnection authConn = new ServiceConnection() {
//        @Override
//        public void onServiceDisconnected(ComponentName name) {
//            authBinder = null;
//        }
//
//        @Override
//        public void onServiceConnected(ComponentName name, IBinder service) {
//            authBinder = (AuthService.MyBinder) service;
////            Toast.makeText(getApplicationContext(), R.string.auth_check_service_bind_success, Toast.LENGTH_SHORT).show();
//            try {
////                TelephonyManager tm=(TelephonyManager)mContext.getSystemService(Context.TELEPHONY_SERVICE);
////                LogX.i("ycw", "------------->  "+tm.getDeviceId());
//                PlateAuthParameter pap = new PlateAuthParameter();
//                pap.sn = C.SN;
//                pap.authFile = "";
//                pap.devCode = Devcode.DEVCODE/*tm.getDeviceId()*/;
//                ReturnAuthority = authBinder.getAuth(pap);
////                String sn = "UR265PUNJVVYPW1YYT32YYH3R";
////                String authFile = "";
////                ReturnAuthority = authBinder.getAuth(sn, authFile);
////                if (ReturnAuthority != 0) {
////                    Toast.makeText(getApplicationContext(), getString(R.string.license_verification_failed) + ":" + ReturnAuthority, Toast.LENGTH_LONG).show();
////                } else {
////                    Toast.makeText(getApplicationContext(), R.string.license_verification_success, Toast.LENGTH_LONG).show();
////                }
//            } catch (Exception e) {
//                Toast.makeText(getApplicationContext(), R.string.failed_check_failure, Toast.LENGTH_SHORT).show();
//                e.printStackTrace();
//            } finally {
//                if (authBinder != null) {
//                    unbindService(authConn);
//                }
//            }
//        }
//    };
}
