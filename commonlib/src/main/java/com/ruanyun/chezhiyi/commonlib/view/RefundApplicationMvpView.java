package com.ruanyun.chezhiyi.commonlib.view;

import com.ruanyun.chezhiyi.commonlib.base.MvpView;

/**
 * 申请退款
 * Created by Sxq on 2016/9/22.
 */
public interface RefundApplicationMvpView extends MvpView {
    void refundApplicationMsg(String msg);

    void showLoadingView();

    void dismissLoadingView();

    void refundApplicationSuccess();
}
