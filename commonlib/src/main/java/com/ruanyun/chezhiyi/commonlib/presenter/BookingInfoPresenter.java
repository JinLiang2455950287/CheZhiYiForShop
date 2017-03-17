package com.ruanyun.chezhiyi.commonlib.presenter;

import com.ruanyun.chezhiyi.commonlib.base.ResultBase;
import com.ruanyun.chezhiyi.commonlib.data.api.ResponseCallback;
import com.ruanyun.chezhiyi.commonlib.model.AppointmentInfo;
import com.ruanyun.chezhiyi.commonlib.view.BookingInfoMvpView;
import retrofit2.Call;

/**
 * Description ：预约详情Presenter
 * <p/>
 * Created by hdl on 2016/9/20.
 */
public class BookingInfoPresenter implements Presenter<BookingInfoMvpView> {

    BookingInfoMvpView bookingInfoMvpView;
    Call<ResultBase<AppointmentInfo>> call;
    @Override
    public void attachView(BookingInfoMvpView mvpView) {
        bookingInfoMvpView = mvpView;
    }

    @Override
    public void detachView() {
        bookingInfoMvpView = null;
    }

    @Override
    public void onCancel() {
        if (call != null && !call.isCanceled())
            call.cancel();
    }

    /**
     * 获取预约信息
     * @param call
     */
    public void getBookingInfo(Call<ResultBase<AppointmentInfo>> call){
        this.call = call;
        if (bookingInfoMvpView==null) return;
        bookingInfoMvpView.showLoadingView();
        this.call.enqueue(new ResponseCallback<ResultBase<AppointmentInfo>>() {
            @Override
            public void onSuccess(Call call, ResultBase<AppointmentInfo> appointmentInfoResultBase) {
                if (bookingInfoMvpView==null) return;
                bookingInfoMvpView.showGetBookingInfoSuccess(appointmentInfoResultBase);
            }

            @Override
            public void onError(Call call, ResultBase<AppointmentInfo> appointmentInfoResultBase, int errorCode) {
                if (bookingInfoMvpView==null) return;
                bookingInfoMvpView.showGetBookingInfoTip(appointmentInfoResultBase.getMsg());
            }

            @Override
            public void onFail(Call call, String msg) {
                if (bookingInfoMvpView==null) return;
                bookingInfoMvpView.showGetBookingInfoTip(msg);
            }

            @Override
            public void onResult() {
                if (bookingInfoMvpView==null) return;
                bookingInfoMvpView.dismissLoadingView();
            }
        });
    }
}
