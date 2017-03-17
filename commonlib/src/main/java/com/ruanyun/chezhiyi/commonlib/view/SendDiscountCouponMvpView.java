package com.ruanyun.chezhiyi.commonlib.view;

import com.ruanyun.chezhiyi.commonlib.base.ResultBase;
import com.ruanyun.chezhiyi.commonlib.model.WorkBayInfo;

import java.util.List;

/**
 * Description ：赠送优惠券视图
 * <p/>
 * Created by hdl on 2016/10/9.
 */

public interface SendDiscountCouponMvpView {

    void showLoadingView();

    void dismissLoadingView();

    void showSendDiscountCouponErrer(String msg);

    void showSendDiscountCouponSuccess(ResultBase resultBase);
}
