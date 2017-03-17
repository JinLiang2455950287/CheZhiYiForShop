package com.ruanyun.chezhiyi.commonlib.view;

import com.ruanyun.chezhiyi.commonlib.model.ThirdInfo;
import com.ruanyun.chezhiyi.commonlib.model.User;

import java.util.List;

/**
 * Description ：第三方登录的view
 * <p/>
 * Created by ycw on 2016/10/17.
 */
public interface ThirdMvpView {

    void getListSuccess(List<ThirdInfo> thirdInfo);

    void showLoadingView();

    void dismissLoadingView();

    void showTip(String msg);

    void thirdBindLogin(User user);

    void delThirdSuccess();
}
