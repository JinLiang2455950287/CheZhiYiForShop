package com.ruanyun.chezhiyi.commonlib.view;

import com.ruanyun.chezhiyi.commonlib.base.MvpView;
import com.ruanyun.chezhiyi.commonlib.base.ResultBase;
import com.ruanyun.chezhiyi.commonlib.model.User;

import cn.sharesdk.framework.Platform;

/**
 * Description ：
 *
 * Created by ycw on 2016/8/4.
 */
public interface LoginMvpView extends MvpView {

    void onHxLoginSuccess();

    void onUserLoginError();

    void showTip(String msg);

    void showLoadingView();

    void dismissLoadingView();

    void onUserLoginFail();

    void onHxLoginFail(String message);

    void thirdBindLogin(User user);

    void userNoCar();

    void userHaseCar();

    void getCarError();
}
