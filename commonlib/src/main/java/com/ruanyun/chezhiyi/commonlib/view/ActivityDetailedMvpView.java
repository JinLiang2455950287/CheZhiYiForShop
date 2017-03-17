package com.ruanyun.chezhiyi.commonlib.view;

import com.ruanyun.chezhiyi.commonlib.model.ActivitySignInfo;

/**
 * Description ：
 * <p>
 * Created by ycw on 2016/10/24.
 */
public interface ActivityDetailedMvpView  {

    void getSignInfoSuccess(ActivitySignInfo signInfo);

    void showLoadingView();

    void showTip(String msg);

    void dismissLoadingView();

    void delActivitySuccess();
}
