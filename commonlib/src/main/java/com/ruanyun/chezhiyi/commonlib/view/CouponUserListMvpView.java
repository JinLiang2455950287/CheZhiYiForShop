package com.ruanyun.chezhiyi.commonlib.view;

import com.ruanyun.chezhiyi.commonlib.model.OrderGoodsInfo;
import com.ruanyun.chezhiyi.commonlib.model.User;

import java.util.List;

/**
 * Description ：
 * <p/>
 * Created by ycw on 2016/11/28.
 */
public interface CouponUserListMvpView {
    void showLoadingView(String s);
    void disMissLoadingView();

    void showTips(String msg);

    void getCommonListSuccess(List<OrderGoodsInfo> obj);

    void getUserSuccess(User user);
}
