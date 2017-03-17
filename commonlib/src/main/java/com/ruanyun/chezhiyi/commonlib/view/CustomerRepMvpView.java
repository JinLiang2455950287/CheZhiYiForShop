package com.ruanyun.chezhiyi.commonlib.view;

import com.ruanyun.chezhiyi.commonlib.model.CarBookingInfo;
import com.ruanyun.chezhiyi.commonlib.model.CustomerRepUiModel;
import com.ruanyun.chezhiyi.commonlib.model.User;
import com.ruanyun.chezhiyi.commonlib.model.WorkBayInfo;

import java.util.List;

/**
 * Description:
 * author: zhangsan on 16/10/8 上午8:47.
 */
public interface CustomerRepMvpView {
    void showToast(String msg);
    void showLoadingView(String msg);
    void disMissLoadingView();
    void onScanCarBookingSuccess(CarBookingInfo carBookingInfo);
    void onScanCarBookingFail();
    void onScanCarBookingResult();
    void onNotSpendingServiceResult(String projectNum, List<CustomerRepUiModel> goodsInfos, int relataedPostion);
    void onFreeWorkbayResult(String projectNum,List<WorkBayInfo> workBayInfos,int relataedPostion);

    void onFreeTechnicanResult(String projectNum,List<User> result,int relataedPostion);

    void submitWorkOrderSuccess();

    void saveCarMileageSuccess();
}
