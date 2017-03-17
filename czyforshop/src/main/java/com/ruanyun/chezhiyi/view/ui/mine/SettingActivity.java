package com.ruanyun.chezhiyi.view.ui.mine;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hyphenate.EMCallBack;
import com.wintone.demo.plateid.AuthServiceHelp;
import com.ruanyun.chezhiyi.R;
import com.ruanyun.chezhiyi.commonlib.base.AutoLayoutActivity;
import com.ruanyun.chezhiyi.commonlib.hxchat.HXHelper;
import com.ruanyun.chezhiyi.commonlib.model.Event;
import com.ruanyun.chezhiyi.commonlib.util.AppUtility;
import com.ruanyun.chezhiyi.commonlib.util.C;
import com.ruanyun.chezhiyi.commonlib.util.ImageUtil;
import com.ruanyun.chezhiyi.commonlib.util.PrefUtility;
import com.ruanyun.chezhiyi.commonlib.view.ui.common.AboutActivity;
import com.ruanyun.chezhiyi.commonlib.view.ui.common.LoginActivity;
import com.ruanyun.chezhiyi.commonlib.view.ui.common.UpdateNickNameActivity;
import com.ruanyun.chezhiyi.commonlib.view.ui.common.VerifyPhoneActivity;
import com.ruanyun.chezhiyi.commonlib.view.widget.Topbar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.Subscribe;
import de.greenrobot.event.ThreadMode;

/**
 * 技师的系统设置
 * Created by Sxq on 2016/10/10.
 */
public class SettingActivity extends AutoLayoutActivity implements Topbar.onTopbarClickListener {
    private static final int REQ_AUTHORIZATION_CODE = 299;
    @BindView(R.id.topbar)
    Topbar topbar;
    @BindView(R.id.tv_modify)
    TextView tvModify;
    @BindView(R.id.tv_check_box)
    TextView tvCheckBox;
    @BindView(R.id.tv_clear_cache)
    TextView tvClearCache;
    @BindView(R.id.ll_modify_password)
    LinearLayout llModifyPassword;
    @BindView(R.id.ll_state)
    LinearLayout llState;
    @BindView(R.id.bt_quit)
    Button btQuit;
    @BindView(R.id.tv_authorization)
    TextView tvAuthorization;
    private AlertDialog.Builder clearCacheAlertDialog;
    private ProgressDialog progressDialog;
    private boolean isAuth;  // 是否授权
    private AuthServiceHelp authServiceHelp;

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_setting);
        ButterKnife.bind(this);
        registerBus();
        initView();
    }

    private void initView() {
        topbar.setTttleText("设置")
                .setBackBtnEnable(true)
                .onBackBtnClick()
                .setTopbarClickListener(this);
        if (AppUtility.isNotEmpty(app.getUser().getLoginPass())) {
            tvModify.setText("修改");
        } else {
            tvModify.setText("设置");
        }
        isAuth = PrefUtility.getBoolean(C.PrefName.PREF_AUTHORE, false);
        tvAuthorization.setText(isAuth ? "已授权" : "未授权");
        if (!isAuth) {  // 授权失败 或 没有授权
           authServiceHelp = new AuthServiceHelp(mContext) {
                @Override
                public void authServiceSuccess() {
                    AppUtility.showToastMsg("授权成功");
                    tvAuthorization.setText("已授权");
                }

                @Override
                public void authServiceFailure(String msg) {
                    AppUtility.showToastMsg(msg);
                }
            };
        }
        tvClearCache.setText(ImageUtil.getCacheSize());
        tvCheckBox.setSelected(PrefUtility.getBoolean(C.PrefName.PREF_PUSH_MSG, true));
    }

    @Override
    public void onTobbarViewClick(View v) {
        int id = v.getId();
        if (id == com.ruanyun.chezhiyi.commonlib.R.id.img_btn_left) {
            finish();
        }
    }

    @OnClick({R.id.ll_modify_password, R.id.ll_clear_cache, R.id.bt_quit, R.id.ll_state, R.id.ll_about, R.id.tv_authorization})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_modify_password:
                // TODO: 2016/10/19  修改为   修改登录密码页
                Intent intent = new Intent(mContext, VerifyPhoneActivity.class);
                intent.putExtra(C.IntentKey.VERIFY_PH_ONE_TYPE, VerifyPhoneActivity
                        .TYPE_FORGET_PASSWORD);
                intent.putExtra(C.IntentKey.TOPBAR_TITLE, "修改登录密码");
                showActivity(intent);
                break;
            case R.id.ll_clear_cache:
                showClearTips();
                break;
            case R.id.bt_quit:
                quit();
                break;
            case R.id.ll_state:     //消息推送
                tvCheckBox.setSelected(!tvCheckBox.isSelected());
                PrefUtility.put(C.PrefName.PREF_PUSH_MSG, tvCheckBox.isSelected());
                HXHelper.getInstance().getModel().setSettingMsgNotification(tvCheckBox.isSelected());
                break;
            case R.id.ll_about:     //关于
                showActivity(AboutActivity.class);
                break;
            case R.id.tv_authorization:     //授权设备
                if (!isAuth) {
                    Intent i = new Intent(mContext, UpdateNickNameActivity.class);
                    i.putExtra(C.IntentKey.TOPBAR_TITLE, UpdateNickNameActivity.STRING_AUTHORIZATION_CODE);
                    startActivityForResult(i, REQ_AUTHORIZATION_CODE);
                }
                break;

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case REQ_AUTHORIZATION_CODE:
                    String SN = data.getStringExtra(C.IntentKey.UPDATE_NICKNAME);
                    if (authServiceHelp != null){
                        authServiceHelp.start(SN);
                    }
                    break;
            }
        }
    }

    /**
     * 退出登录的处理
     */
    protected void quit() {
        Dialog alertDialog = new AlertDialog.Builder(this).
                setMessage("确定要退出吗?").
                setPositiveButton(com.ruanyun.chezhiyi.commonlib.R.string.confirmed, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //  Intent intent = new Intent(getApplicationContext(), LoginActivity
                        // .class);
                        //app.exitApp();
                        HXHelper.getInstance().logout(false, new EMCallBack() {
                            @Override
                            public void onSuccess() {
                                PrefUtility.put(C.PrefName.PREF_IS_LOGIN, false);
                                app.exitApp();
                                //onExitFinish();
                                skipActivity(LoginActivity.class);
                            }

                            @Override
                            public void onError(int i, String s) {
                                //AppUtility.showToastMsg("退出失败");
                            }

                            @Override
                            public void onProgress(int i, String s) {

                            }
                        });

                    }
                }).setNegativeButton(com.ruanyun.chezhiyi.commonlib.R.string.cancel, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface arg0, int arg1) {

            }
        }).create();
        alertDialog.show();

    }

    /**
     * 显示清除缓存提示
     */
    private void showClearTips() {
        clearCacheAlertDialog = new AlertDialog.Builder(mContext);
        clearCacheAlertDialog.setMessage("确定清除缓存？")
                .setPositiveButton(R.string.ok, new DialogInterface
                        .OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        ImageUtil.clearImageAllCache();
//                        tvClearCache.setText(ImageUtil.getCacheSize());
                        // TODO: 2016/11/30  缓存
                        showWaitAlert();
                    }
                }).setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

            }
        }).show();
    }

    private void showWaitAlert() {
        progressDialog = new ProgressDialog(mContext, android.R.style.Widget_ProgressBar_Small);
        progressDialog.setMessage("清理中...");
        progressDialog.setProgressStyle(android.R.style.Widget_ProgressBar_Small);
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);
        progressDialog.show();
    }


    /**
     * 清理图片缓存结束后执行
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.MainThread, priority = 126)
    public void clearFinish(Event<String> event) {
        if (event != null && event.key.equals(C.EventKey.CLEAR_FINISH) && event.value.equals(C.EventKey.CLEAR_FINISH)) {
            progressDialog.dismiss();
            tvClearCache.setText(ImageUtil.getCacheSize());
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unRegisterBus();
        if (authServiceHelp!= null)
        authServiceHelp.clear();
    }
}
