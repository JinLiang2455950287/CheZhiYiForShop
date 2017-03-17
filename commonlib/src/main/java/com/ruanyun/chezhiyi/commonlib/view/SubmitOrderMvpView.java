package com.ruanyun.chezhiyi.commonlib.view;

import com.ruanyun.chezhiyi.commonlib.base.MvpView;
import com.ruanyun.chezhiyi.commonlib.base.ResultBase;
import com.ruanyun.chezhiyi.commonlib.model.OrderGoodsInfo;
import com.ruanyun.chezhiyi.commonlib.model.OrderInfo;

import java.util.List;

/**
 * Description ：
 * <p/>
 * Created by ycw on 2016/9/20.
 */
public interface SubmitOrderMvpView extends MvpView {
    void showLoadingView();

    void showTip(String msg);

    void dismissLoadingView();

    void makeOrderSuccess(OrderInfo result);

    void makeOrderError();

    void showDiscountCouponSuccess(ResultBase<List<OrderGoodsInfo>> orderGoodsInfoResultBase);
}
