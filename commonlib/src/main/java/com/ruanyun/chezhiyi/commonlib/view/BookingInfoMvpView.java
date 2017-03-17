package com.ruanyun.chezhiyi.commonlib.view;

import com.ruanyun.chezhiyi.commonlib.base.ResultBase;
import com.ruanyun.chezhiyi.commonlib.model.AppointmentInfo;
import com.ruanyun.chezhiyi.commonlib.model.SignAddInfo;

/**
 * Description ：预约详情视图
 * <p/>
 * Created by hdl on 2016/9/20.
 */
public interface BookingInfoMvpView {

    void showLoadingView();

    void dismissLoadingView();

    void showGetBookingInfoTip(String msg);

    void showGetBookingInfoSuccess(ResultBase<AppointmentInfo> appointmentInfoResultBase);
}
