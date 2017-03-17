package com.ruanyun.chezhiyi.commonlib.view;

import com.ruanyun.chezhiyi.commonlib.base.MvpView;

/**
 * Description ：修改订单状态的界面 实现
 * <p/>
 * Created by ycw on 2016/10/11.
 */
public interface UpStatusMvpView extends MvpView {

    void showLoadingView(String msg);

    void showTip(String msg);

    void cancelSuccess();

    void dismissLoadingView();

    /**
     * 删除订单成功
     */
    void delOrderSuccess();

    void valiteOrderSuccess();
}
