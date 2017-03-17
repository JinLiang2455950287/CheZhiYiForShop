package com.ruanyun.chezhiyi.commonlib.view;

import com.ruanyun.chezhiyi.commonlib.base.ResultBase;
import com.ruanyun.chezhiyi.commonlib.model.OrderInfo;

import java.math.BigDecimal;

/**
 * 记账本的视图
 * Created by hdl on 2016/9/10.
 */
public interface AccountBookMoneySumMvpView {

    void showLoadingView(String msg);

    void dismissLoadingView();

    void showAccountBookMoneySumSuccessTip(BigDecimal resultBase);

    void showAccountBookMoneySumFail();

    void orderDetailResult(OrderInfo orderInfo);
}
