package com.ruanyun.chezhiyi.commonlib.view;

import com.ruanyun.chezhiyi.commonlib.model.CarBookingInfo;
import com.ruanyun.chezhiyi.commonlib.model.User;


import java.util.List;

/**
 * Description:
 * author: zhangsan on 16/11/3 下午1:47.
 */
public interface QuikWashCarMvpView {
    void showToast(String msg);
    void showLoadingView(String msg);
    void disMissLoadingView();
    /**提交洗车工单成功回调  **/
    void submitWashCarSuccess();
    /** 获取空闲技师回调 **/
    void onFreeTechnicanResult(List<User> technicians);
    /** 扫描成功回调 **/
    void onScanCarBookingResult(CarBookingInfo carBookingInfo);
}
