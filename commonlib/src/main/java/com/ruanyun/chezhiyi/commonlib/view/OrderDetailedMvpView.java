package com.ruanyun.chezhiyi.commonlib.view;

import com.ruanyun.chezhiyi.commonlib.base.MvpView;
import com.ruanyun.chezhiyi.commonlib.base.ResultBase;
import com.ruanyun.chezhiyi.commonlib.model.OrderInfo;
import com.ruanyun.chezhiyi.commonlib.model.RefundInfo;

import java.util.List;

/**
 * Description ：
 * <p/>
 * Created by ycw on 2016/9/22.
 */
public interface OrderDetailedMvpView extends MvpView {
    void showLoadingView(String msg);

    void tuikuanSuccess(List<RefundInfo> refundInfoList);

//    void tuikuanError(ResultBase<List<RefundInfo>> listResultBase, int errorCode);

    void dismissLoadingView();

    void showTip(String msg);

    void orderDetailResult(OrderInfo orderInfo);
}
