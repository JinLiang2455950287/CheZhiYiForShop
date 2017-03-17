package com.ruanyun.chezhiyi.commonlib.view;

import com.ruanyun.chezhiyi.commonlib.base.MvpView;
import com.ruanyun.chezhiyi.commonlib.model.OrderInfo;

/**
 * Description ：
 * <p>
 * Created by ycw on 2016/10/15.
 */
public interface ExchangeMvpView extends MvpView{

    void showLoadingView();

    void dismissLoadingView();

    void exchangeSuccess(OrderInfo orderInfo);
}
