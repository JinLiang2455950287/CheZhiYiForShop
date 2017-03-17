package com.ruanyun.chezhiyi.commonlib.view;

import com.ruanyun.chezhiyi.commonlib.model.OrderInfo;

/**
 * Created by Administrator on 2016/11/11.
 */
public interface SystemRemindMvpView {

    void showLoadingView(String msg);

    void orderDetailResult(OrderInfo orderInfo);

    void dismissLoadingView();

    /**
     * 修改消息状态
     */
    void updateRemindSuccess();

    void showTips(String msg);
}
