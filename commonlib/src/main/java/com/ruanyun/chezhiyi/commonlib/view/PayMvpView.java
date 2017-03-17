package com.ruanyun.chezhiyi.commonlib.view;

import com.ruanyun.chezhiyi.commonlib.base.MvpView;
import com.ruanyun.chezhiyi.commonlib.model.OrderInfo;
import com.ruanyun.chezhiyi.commonlib.model.WeiXinOrderInfo;

/**
 * Description ：支付界面
 * <p/>
 * Created by ycw on 2016/10/11.
 */
public interface PayMvpView extends MvpView {

    void showTip(String msg);

    void showLoadingView(String msg);

    void dismissLoadingView();

    void weiXinPaySuccess(WeiXinOrderInfo weiXinOrderInfo);

    void alipayConfigSuccess(String aliPayConfigInfo);

    void showAlipayConfigError();

    void accountPaySuccess();

    void orderDetailResult(OrderInfo orderInfo);
}
