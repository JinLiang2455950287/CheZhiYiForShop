package com.ruanyun.chezhiyi.commonlib.view;

import com.ruanyun.chezhiyi.commonlib.model.OrderInfo;

/**
 * Created by czy on 2017/3/22.
 * 卡套餐详情界面提交定订单
 */

public interface CardPackageDingDanView {
    void reportDinDanSuccess(OrderInfo orderInfo);
    void reportDinDanFalse();
}
