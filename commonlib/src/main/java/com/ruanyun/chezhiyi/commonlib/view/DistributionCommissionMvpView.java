package com.ruanyun.chezhiyi.commonlib.view;

import com.ruanyun.chezhiyi.commonlib.model.User;
import com.ruanyun.chezhiyi.commonlib.model.WorkOrderCommissionInfo;

import java.util.List;

/**
 * Description ：
 * <p/>
 * Created by ycw on 2016/11/4.
 */
public interface DistributionCommissionMvpView {
    void disMissLoadingView();

    void showLoadingView();

    void getDistrubuteUserSuccess(WorkOrderCommissionInfo workOrderCommissionInfo);

    void showTips(String msg);

    void saveDistrubutionSuccess();
}
