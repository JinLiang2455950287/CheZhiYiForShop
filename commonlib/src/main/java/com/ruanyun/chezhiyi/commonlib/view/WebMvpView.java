package com.ruanyun.chezhiyi.commonlib.view;

import com.ruanyun.chezhiyi.commonlib.base.MvpView;
import com.ruanyun.chezhiyi.commonlib.base.ResultBase;
import com.ruanyun.chezhiyi.commonlib.model.ActivityInfo;
import com.ruanyun.chezhiyi.commonlib.model.OrderInfo;

/**
 * Description ：
 * <p>
 * Created by ycw on 2016/9/20.
 */
public interface WebMvpView extends MvpView {
    void showLoadingView();

    void dismissLoadingView();

    void showTip(String msg);

//    void getSeckillInfoSuccess(SeckillDetailInfo seckillDetailInfo);
//
//    void getCrowdInfoSuccess(CrowdFundingInfo crowdFundingInfo);
//
//    void getPromotionInfoSuccess(PromotionInfo promotionInfo);
//
//    void getProductInfoSuccess(ProductInfo productInfo);

    void getActivitySuccess(ResultBase<ActivityInfo> resultBase);

    void exchangeSuccess(OrderInfo orderInfo);

    void delActivitySuccess();
}
