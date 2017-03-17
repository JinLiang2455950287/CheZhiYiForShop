package com.ruanyun.chezhiyi.commonlib.view;

import com.ruanyun.chezhiyi.commonlib.model.OrderInfo;

/**
 * Description ：
 * <p/>
 * Created by ycw on 2016/11/2.
 */
public interface SubmitWorkOrderMvpView {
    void disMissLoadingView();

    void showLoadingView();

    void addJieSuanSuccess(OrderInfo orderInfo);
}
