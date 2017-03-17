package com.ruanyun.czy.client.view.ui.my;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hyphenate.EMCallBack;
import com.ruanyun.chezhiyi.commonlib.base.AutoLayoutActivity;
import com.ruanyun.chezhiyi.commonlib.hxchat.HXHelper;
import com.ruanyun.chezhiyi.commonlib.model.AccountMoneyInfo;
import com.ruanyun.chezhiyi.commonlib.model.Event;
import com.ruanyun.chezhiyi.commonlib.model.ThirdInfo;
import com.ruanyun.chezhiyi.commonlib.model.User;
import com.ruanyun.chezhiyi.commonlib.presenter.LoginPresenter;
import com.ruanyun.chezhiyi.commonlib.presenter.ThriedPresenter;
import com.ruanyun.chezhiyi.commonlib.util.AppUtility;
import com.ruanyun.chezhiyi.commonlib.util.C;
import com.ruanyun.chezhiyi.commonlib.util.ImageUtil;
import com.ruanyun.chezhiyi.commonlib.util.LogX;
import com.ruanyun.chezhiyi.commonlib.util.PrefUtility;
import com.ruanyun.chezhiyi.commonlib.view.ThirdMvpView;
import com.ruanyun.chezhiyi.commonlib.view.ui.common.AboutActivity;
import com.ruanyun.chezhiyi.commonlib.view.ui.common.LoginActivity;
import com.ruanyun.chezhiyi.commonlib.view.ui.common.VerifyPhoneActivity;
import com.ruanyun.chezhiyi.commonlib.view.widget.Topbar;
import com.ruanyun.czy.client.R;
import com.ruanyun.czy.client.view.ui.account.SetTradPwdActivity;

import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import de.greenrobot.event.Subscribe;
import de.greenrobot.event.ThreadMode;
import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * 系统设置
 * Created by Sxq on 2016/10/9.
 */
public class SettingActivity extends AutoLayoutActivity implements Topbar.onTopbarClickListener,
        ThirdMvpView, PlatformActionListener {


    public static final String WECHAT = "Wechat";
    public static final String QQ = "QQ";
    public static final String SINA = "Sina";
    @BindView(R.id.topbar)
    Topbar topbar;
    @BindView(R.id.tv_modify_password)
    TextView tvModifyPassword;
    @BindView(R.id.ll_modify_password)
    LinearLayout llModifyPassword;
    @BindView(R.id.tv_modify_pay_password)
    TextView tvModifyPayPassword;
    @BindView(R.id.ll_modify_pay_password)
    LinearLayout llModifyPayPassword;
    @BindView(R.id.tv_set_wx)
    TextView tvSetWx;
    @BindView(R.id.rl_set_wx)
    RelativeLayout rlSetWx;
    @BindView(R.id.tv_set_qq)
    TextView tvSetQq;
    @BindView(R.id.rl_set_qq)
    RelativeLayout rlSetQq;
    @BindView(R.id.tv_set_xl)
    TextView tvSetXl;
    @BindView(R.id.rl_set_xl)
    RelativeLayout rlSetXl;
    @BindView(R.id.tv_msg_switch)
    TextView tvMsgSwitch;
    @BindView(R.id.ll_msg_switch)
    LinearLayout llMsgSwitch;
    @BindView(R.id.tv_cache_num)
    TextView tvCacheNum;
    @BindView(R.id.ll_cache_num)
    LinearLayout llCacheNum;
    @BindView(R.id.bt_quit)
    Button btQuit;

    boolean bindQQ = false, bindWeChat = false, bindSina = false;
    ThriedPresenter presenter = new ThriedPresenter();
    private int thirdType;
    private AlertDialog.Builder clearCachealertDialog;
    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        registerBus();
        presenter.attachView(this);
        setContentView(R.layout.activity_setting);
        ButterKnife.bind(this);
        initView();
        getThirdList();
        app.beanCacheHelper.getAccountMoney();
    }

    /**
     * 获取第三方已绑定的 数据
     */
    private void getThirdList() {
        //  进入界面请求第三方绑定数据
        presenter.getThirdList(app.getApiService().listThird(app.getCurrentUserNum()));
    }

    private void initView() {
        topbar.setTttleText("设置")
                .setBackBtnEnable(true)
                .onBackBtnClick()
                .setTopbarClickListener(this);
        if (app.getUser() == null) return;
        if (AppUtility.isNotEmpty(app.getUser().getLoginPass())) {
            tvModifyPassword.setText("修改");
        } else {
            tvModifyPassword.setText("设置");
        }
        tvCacheNum.setText(ImageUtil.getCacheSize());
        tvMsgSwitch.setSelected(PrefUtility.getBoolean(C.PrefName.PREF_PUSH_MSG, true));
    }

    @Override
    public void onTobbarViewClick(View v) {
        int id = v.getId();
        if (id == com.ruanyun.chezhiyi.commonlib.R.id.img_btn_left) {
            finish();
        }
    }

    @OnClick({R.id.ll_modify_password, R.id.ll_modify_pay_password, R.id.rl_set_wx, R.id
            .rl_set_qq, R.id.rl_set_xl, R.id.ll_msg_switch, R.id.ll_cache_num, R.id.bt_quit, R.id.ll_about})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_modify_password:   //点击修改密码
                // TODO: 2016/10/19 修改为   修改登录密码页
                Intent intent = new Intent(mContext, VerifyPhoneActivity.class);
                intent.putExtra(C.IntentKey.VERIFY_PH_ONE_TYPE, VerifyPhoneActivity
                        .TYPE_FORGET_PASSWORD);
                intent.putExtra(C.IntentKey.TOPBAR_TITLE, "修改登录密码");
                showActivity(intent);
                break;
            case R.id.ll_modify_pay_password:   //点击修改支付密码
                showActivity(SetTradPwdActivity.class);
                break;
            case R.id.rl_set_wx:    //点击绑定或解绑微信
                if (bindWeChat) {
                    createAlert("确定解除绑定微信？", WECHAT);
                } else {
                    //绑定微信
                    authorizeLogin(LoginPresenter.WEIXIN_LOGIN);
                    thirdType = 2;
                }
                break;
            case R.id.rl_set_qq:    //点击绑定或解绑qq
                if (bindQQ) {
                    createAlert("确定解除绑定QQ？", QQ);
                } else {
                    //绑定QQ
                    authorizeLogin(LoginPresenter.QQ_LOGIN);
                    thirdType = 1;
                }
                break;
            case R.id.rl_set_xl:    //点击绑定或解绑新浪
                if (bindSina) {
                    createAlert("确定解除绑定新浪？", SINA);
                } else {
                    //绑定新浪
                    authorizeLogin(LoginPresenter.SINAWEIBO_LOGIN);
                    thirdType = 3;
                }
                break;
            case R.id.ll_msg_switch:    //推送通知开关
                // TODO: 2016/10/22 推送通知开关
                tvMsgSwitch.setSelected(!tvMsgSwitch.isSelected());
                PrefUtility.put(C.PrefName.PREF_PUSH_MSG, tvMsgSwitch.isSelected());
                HXHelper.getInstance().getModel().setSettingMsgNotification(tvMsgSwitch.isSelected());
                break;
            case R.id.ll_cache_num:    //清除缓存
                // TODO: 2016/10/22  清除缓存
                showClearTips();
                break;
            case R.id.bt_quit:    //退出登录
                //退出登录
                quit();
                break;
            case R.id.ll_about:    //关于
                showActivity(AboutActivity.class);
                break;

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
                        //  Intent intent = new Intent(getApplicationContext(), LoginActivity .class);
                        //app.exitApp();
                        HXHelper.getInstance().logout(false, new EMCallBack() {
                            @Override
                            public void onSuccess() {
                                PrefUtility.put(C.PrefName.PREF_IS_LOGIN, false);
                                app.exitApp();
                                //onExitFinish();
                                showActivity(LoginActivity.class);
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
        clearCachealertDialog = new AlertDialog.Builder(mContext);
        clearCachealertDialog.setMessage("确定清除缓存？")
                .setPositiveButton(R.string.ok, new DialogInterface
                        .OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        ImageUtil.clearImageAllCache();
                        // TODO: 2016/11/30  缓存
                        showWaitAlert();
                    }
                }).setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

            }
        }).show();
    }

    private void  showWaitAlert(){
        progressDialog = new ProgressDialog(mContext, android.R.style.Widget_ProgressBar_Small);
        progressDialog.setMessage("清理中...");
        progressDialog.setProgressStyle(android.R.style.Widget_ProgressBar_Small);
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    @Subscribe(threadMode = ThreadMode.MainThread, priority = 126)
    public void clearFinish(Event<String> event) {
        if (event != null && event.key.equals(C.EventKey.CLEAR_FINISH) && event.value.equals(C.EventKey.CLEAR_FINISH)) {
            progressDialog.dismiss();
            tvCacheNum.setText(ImageUtil.getCacheSize());
        }
    }

    /**
     * 解绑弹窗
     *
     * @param message
     * @param tag
     */
    private void createAlert(String message, final String tag) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(mContext);
        alertDialogBuilder.setMessage(message)
                .setPositiveButton(R.string.ok, new DialogInterface
                        .OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        int type = 0;
                        switch (tag) {
                            case QQ:
                                type = 1;
                                break;
                            case WECHAT:
                                type = 2;
                                break;
                            case SINA:
                                type = 3;
                                break;
                        }
                        if (type != 0) {
                            presenter.delThird(app.getApiService().delThird(app.getCurrentUserNum
                                    (), type));
                        }
                    }
                }).setNegativeButton(R.string.cancel, new DialogInterface
                .OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
            }
        }).show();
    }

    /**
     * 授权登陆
     *
     * @param platFormName
     */
    public void authorizeLogin(String platFormName) {
        Platform platform = ShareSDK.getPlatform(platFormName);
        platform.setPlatformActionListener(this);
        platform.authorize();
    }


    @Override
    public void getListSuccess(List<ThirdInfo> thirdInfoList) {
        // 获取到已绑定的列表  显示界面
        LogX.d(TAG, thirdInfoList.toString());
        tvSetQq.setText((bindQQ = getBind(thirdInfoList,  1)) ? "已绑定" : "绑定");
        tvSetWx.setText((bindWeChat = getBind(thirdInfoList, 2)) ? "已绑定" : "绑定");
        tvSetXl.setText((bindSina = getBind(thirdInfoList, 3)) ? "已绑定" : "绑定");
    }

    private boolean getBind(List<ThirdInfo> thirdInfoList, int i) {
        boolean bindType = false;
        for (ThirdInfo thirdInfo : thirdInfoList) {
            if (thirdInfo.getThirdType() == i) {
                bindType = true;
                break;
            }
        }
        return bindType;
    }

    @Override
    public void showLoadingView() {
        showLoading("加载中...");
    }

    @Override
    public void dismissLoadingView() {
        dissMissLoading();
    }

    @Override
    public void showTip(String msg) {
        AppUtility.showToastMsg(msg);
    }

    @Override
    public void thirdBindLogin(User user) {
        //app.setUser(user);
        getThirdList();
    }

    @Override
    public void delThirdSuccess() {
        getThirdList();
    }

    HashMap<String, RequestBody> map = new HashMap<>();

    @Override
    public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
        LogX.d(TAG, "-----authorizeLogin---  onComplete  ---->");
        map.put("userType", RequestBody.create(MediaType.parse("text/plain"), isClient() ? "3" : "2"));
         map.put("nickName", RequestBody.create(MediaType.parse("text/plain"), ""));
        map.put("thirdType", RequestBody.create(MediaType.parse("text/plain"), String.valueOf(thirdType)));
        map.put("thirdNum", RequestBody.create(MediaType.parse("text/plain"), platform.getDb().getUserId()));
        presenter.addThird(app.getApiService().addThird(app.getUser().getLoginName(), map));
    }

    @Override
    public void onError(Platform platform, int i, Throwable throwable) {
        LogX.d(TAG, "-----authorizeLogin---  onError  ---->");
        showTip(throwable.getMessage());
    }

    @Override
    public void onCancel(Platform platform, int i) {
        LogX.d(TAG, "-----authorizeLogin---  onCancel  ---->");

    }

    @Subscribe(threadMode = ThreadMode.MainThread, priority = 124)
    public void getAccountMoneySuccess(Event<AccountMoneyInfo> event) {
        if (event != null && event.key.equals(C.EventKey.ACCOUNT_MONEY)) {
            AccountMoneyInfo moneyInfo = event.value;
            if (AppUtility.isNotEmpty(moneyInfo.getPayPassword())) {
                tvModifyPayPassword.setText("修改");
            } else {
                tvModifyPayPassword.setText("设置");
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unRegisterBus();
    }
}
