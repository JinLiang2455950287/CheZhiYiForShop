package com.ruanyun.chezhiyi.commonlib.view;

import com.ruanyun.chezhiyi.commonlib.base.MvpView;

/**
 * Description ：取消预约的view
 * <p/>
 * Created by ycw on 2016/10/10.
 */
public interface CancelMakeMvpView extends MvpView {


    void showTip(String msg);

    void dismissLoadingView();

    void showLoadingView();

    void cancelSuccess();
}
