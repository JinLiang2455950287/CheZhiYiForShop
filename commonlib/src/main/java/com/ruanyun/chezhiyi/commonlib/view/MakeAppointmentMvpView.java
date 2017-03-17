package com.ruanyun.chezhiyi.commonlib.view;

import com.ruanyun.chezhiyi.commonlib.base.MvpView;
import com.ruanyun.chezhiyi.commonlib.model.AppointmentInfo;

/**
 * Description ：添加预约
 * <p>
 * Created by ycw on 2016/9/13.
 */
public interface MakeAppointmentMvpView extends MvpView {
    void showMakeAppointmentLoadingView(String msg);

    void makeAppointmentOnsuccess(AppointmentInfo appointmentInfo);

    void makeAppointmentOnResult();

    void showTip(String msg);

}
