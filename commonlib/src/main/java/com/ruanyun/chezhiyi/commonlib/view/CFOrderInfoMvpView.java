package com.ruanyun.chezhiyi.commonlib.view;

import com.ruanyun.chezhiyi.commonlib.model.OrderInfo;

/**
 * Description ：
 * <p/>
 * Created by ycw on 2016/10/29.
 */
public interface CFOrderInfoMvpView {
    void showLoadingView(String msg);

    void orderDetailResult(OrderInfo orderInfo);

    void dismissLoadingView();
}
