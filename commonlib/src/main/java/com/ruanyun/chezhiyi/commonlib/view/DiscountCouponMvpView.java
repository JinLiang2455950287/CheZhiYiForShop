package com.ruanyun.chezhiyi.commonlib.view;


import com.ruanyun.chezhiyi.commonlib.base.ResultBase;
import com.ruanyun.chezhiyi.commonlib.model.OrderGoodsInfo;
import com.ruanyun.chezhiyi.commonlib.model.ProductInfo;

import java.util.List;

/**
 * Description ：优惠券视图
 * <p/>
 * Created by hdl on 2016/9/20.
 */
public interface DiscountCouponMvpView {

    void showLoadingView();

    void dismissLoadingView();

    void showDiscountCouponErrer(String msg);

    void showDiscountCouponSuccess(ResultBase<List<OrderGoodsInfo>> orderGoodsInfoResultBase);

    void showTechnicianDiscountCouponSuccess(ResultBase<List<ProductInfo>> productInfoResultBase);

}
