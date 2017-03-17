package com.ruanyun.chezhiyi.commonlib.view;

import com.ruanyun.chezhiyi.commonlib.base.ResultBase;
import com.ruanyun.chezhiyi.commonlib.model.OrderGoodsInfo;
import com.ruanyun.chezhiyi.commonlib.model.WorkBayInfo;
import com.ruanyun.chezhiyi.commonlib.model.params.WorkOrderSubmitInfo;

import java.util.List;

/**
 * Description ：等候区详情视图
 * <p/>
 * Created by hdl on 2016/10/9.
 */

public interface WaitingAreaDetailsMvpView {

    void showLoadingView();

    void dismissLoadingView();

    void showLeisureStationErrer(String msg);

    void showLeisureStationSuccess(ResultBase<List<WorkBayInfo>> workBayInfoResultBase);

    void showWorkorderGoodsSuccess(ResultBase<List<OrderGoodsInfo>> workBayInfoResultBase);

    void showOrderReceivingSuccess(ResultBase resultBase);

}
