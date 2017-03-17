package com.ruanyun.chezhiyi.commonlib.view.ui.common;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ruanyun.chezhiyi.commonlib.R;
import com.ruanyun.chezhiyi.commonlib.base.AutoLayoutActivity;
import com.ruanyun.chezhiyi.commonlib.model.AccountInfo;
import com.ruanyun.chezhiyi.commonlib.model.User;
import com.ruanyun.chezhiyi.commonlib.model.params.LoginParams;
import com.ruanyun.chezhiyi.commonlib.presenter.LoginPresenter;
import com.ruanyun.chezhiyi.commonlib.util.AppUtility;
import com.ruanyun.chezhiyi.commonlib.util.C;
import com.ruanyun.chezhiyi.commonlib.util.DbHelper;
import com.ruanyun.chezhiyi.commonlib.util.PrefUtility;
import com.ruanyun.chezhiyi.commonlib.view.LoginMvpView;

import retrofit2.Call;

/**
 * Description ：splash  启动页
 * <p/>
 * Created by ycw on 2016/8/9.
 */
public class SplashActivity extends AutoLayoutActivity implements LoginMvpView {

    LoginPresenter loginPresenter = new LoginPresenter();
    Handler mhandler = new Handler();

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        loginPresenter.attachView(this);

        //获取界面小图标:门店详情 案例展示 在线预约 小秘书 推荐项目 文章资讯 产品 团购 限时促销 秒杀 众筹 限时促销 活动招募 猜你喜欢 签到有礼 记账本 v
        if (!PrefUtility.getBoolean(C.PrefName.PREF_HAS_HOME_ICON, false)) {
            app.beanCacheHelper.getHomeIconToDb();
        }
        //第一次进就去引导页
        if (PrefUtility.getBoolean(C.PrefName.PRE_IS_FIRSTIN, true)) {
            showActivity(GuideActivity.class);
            PrefUtility.put(C.PrefName.PRE_IS_FIRSTIN, false);
            finish();
            return;
        }
        initView();
    }

    private void initView() {
        TextView textView = new TextView(mContext);
//        textView.setText("掌上汽服");
        textView.setBackgroundResource(R.drawable.image_splash_new);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 60);
        textView.setGravity(Gravity.CENTER);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        textView.setLayoutParams(params);
        setContentView(textView);
        //缓存中获取登陆成功时保存的登陆名
        String loginName = PrefUtility.get(C.PrefName.PREF_LOGIN_NAME, "");
        // TODO: 2016/8/9 是否是第一次登陆的判断  或  是否需要登录
        //从数据库获取用户信息
        AccountInfo accountInfo = DbHelper.getInstance().getAccountInfoByName(loginName);
        boolean isLogin = PrefUtility.getBoolean(C.PrefName.PREF_IS_LOGIN, false);
        //登陆用户的登陆信息，是否登陆过，当了密码是否为空
        if (accountInfo != null && isLogin && !TextUtils.isEmpty(accountInfo.getPassWord())) {
            LoginParams loginParams = new LoginParams();
            loginParams.setLoginPass(accountInfo.getPassWord());
            /**登陆的用户类型  1 系统用户 2 技师用户 3 客户端用户 */
            loginParams.setUserType(app.isClient() ? "3" : "2");
            //登陆操作
            Call call = app.getApiService().userLogin(accountInfo.getLoginName(), loginParams);
            loginPresenter.userLogin(call);
        } else {
            mhandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    skipActivity(LoginActivity.class);
                }
            }, 2000);
        }
    }


//
//     Call call;
//    private void getUserCarInfos(){
//
//        call =App.getInstance().getApiService().getCarinfoList(app.getCurrentUserNum());
//        call.enqueue(new ResponseCallback<ResultBase<List<CarInfo>>>() {
//            @Override
//            public void onSuccess(Call call, ResultBase<List<CarInfo>> listResultBase) {
//                if(listResultBase.getObj().isEmpty()){
//                    showActivity("com.ruanyun.czy.client.view.ui.my.AddCarInformationActivity");
//
//                }else {
//                    showActivity("com.ruanyun.czy.client.MainActivity");
//                }
//                finish();
//            }
//
//            @Override
//            public void onError(Call call, ResultBase<List<CarInfo>> listResultBase, int errorCode) {
//                showTip("获取车辆信息失败 请重新登录");
//                finish();
//            }
//
//            @Override
//            public void onFail(Call call, String msg) {
//                showTip("获取车辆信息失败 请重新登录");
//                finish();
//            }
//
//            @Override
//            public void onResult() {
//
//            }
//        });
//    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        if(call!=null&&call.isExecuted())
//            call.cancel();
        loginPresenter.detachView();
        mhandler.removeCallbacksAndMessages(null);
        mhandler = null;
    }

    @Override
    public void onHxLoginSuccess() {
        if (app.isClient()) {
            loginPresenter.getUserCarInfos();
        } else {
            showActivity("com.ruanyun.chezhiyi.MainActivity");
            finish();
        }
    }

    @Override
    public void onUserLoginError() {
        showActivity(LoginActivity.class);
        finish();
    }


    @Override
    public void showTip(String msg) {
        AppUtility.showToastMsg(msg);

    }

    @Override
    public void showLoadingView() {
    }

    @Override
    public void dismissLoadingView() {
    }

    @Override
    public void onUserLoginFail() {
        showActivity(LoginActivity.class);
        finish();
    }

    @Override
    public void onHxLoginFail(final String message) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                AppUtility.showToastMsg(message);
                showActivity(LoginActivity.class);
                finish();
            }
        });

    }

    @Override
    public void thirdBindLogin(User user) {

    }

    @Override
    public void userNoCar() {
//        没有车辆 添加车辆信息
        showActivity("com.ruanyun.czy.client.view.ui.my.AddCarInformationActivity");
        finish();
    }

    @Override
    public void userHaseCar() {
//        用户有车辆
        showActivity("com.ruanyun.czy.client.MainActivity");
        finish();
    }

    @Override
    public void getCarError() {
        finish();
    }

    @Override
    public Context getContext() {
        return null;
    }
}
