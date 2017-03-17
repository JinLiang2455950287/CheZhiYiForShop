package com.ruanyun.chezhiyi.commonlib.view;

import com.ruanyun.chezhiyi.commonlib.base.ResultBase;
import com.ruanyun.chezhiyi.commonlib.model.AssistUserInfo;
import com.ruanyun.chezhiyi.commonlib.model.User;
import com.ruanyun.chezhiyi.commonlib.model.WorkOrderInfo;
import com.ruanyun.chezhiyi.commonlib.model.params.WorkOrderSubmitInfo;

import java.util.List;

/**
 * Description ：
 * <p>
 * Created by ycw on 2016/10/19.
 */
public interface WorkOrderDetailedMvpView {

    void dismissLoadingView();

    void showLoadingView();

    /**
     * 获取工单详情
     * @param workOrderInfo
     */
    void getWorkorderInfoSuccess(WorkOrderInfo workOrderInfo);

    void showTip(String msg);

    void showError();

    void getWorkorderGoodsSuccess(ResultBase<List<WorkOrderSubmitInfo.WorkOrderGoods>>
                                          workBayInfoResultBase);

    void getAssistListSuccess(List<AssistUserInfo> assistUserInfo);

    void updateStatusSuccess();

    void getFriendShipInfoSuccess(User user);
}
