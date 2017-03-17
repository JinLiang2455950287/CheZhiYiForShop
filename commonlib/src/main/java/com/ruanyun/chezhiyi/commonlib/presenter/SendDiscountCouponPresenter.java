package com.ruanyun.chezhiyi.commonlib.presenter;

import com.ruanyun.chezhiyi.commonlib.base.PageInfoBase;
import com.ruanyun.chezhiyi.commonlib.base.ResultBase;
import com.ruanyun.chezhiyi.commonlib.data.api.ResponseCallback;
import com.ruanyun.chezhiyi.commonlib.model.AccountBookInfo;
import com.ruanyun.chezhiyi.commonlib.view.RememberingBookMvpView;
import com.ruanyun.chezhiyi.commonlib.view.SendDiscountCouponMvpView;
import retrofit2.Call;

/**
 * 赠送优惠券
 * Created by hdl on 2016/10/08.
 */
public class SendDiscountCouponPresenter implements Presenter<SendDiscountCouponMvpView> {

    SendDiscountCouponMvpView sendDiscountCouponMvpView;
    Call<ResultBase> call;


    @Override
    public void attachView(SendDiscountCouponMvpView mvpView) {
        sendDiscountCouponMvpView = mvpView;
    }

    @Override
    public void detachView() {
        sendDiscountCouponMvpView = null;
    }

    @Override
    public void onCancel() {
        if (call != null && !call.isCanceled())
            call.cancel();
    }

    /**
     * 赠送优惠券
     * @param call
     */
    public void setSendDiscountCouponMvpView(Call<ResultBase> call) {
        this.call = call;
        if (sendDiscountCouponMvpView==null) return;
        sendDiscountCouponMvpView.showLoadingView();
        this.call.enqueue(new ResponseCallback<ResultBase>() {

            @Override
            public void onSuccess(Call call, ResultBase resultBase) {
                if (sendDiscountCouponMvpView==null) return;
                sendDiscountCouponMvpView.showSendDiscountCouponSuccess(resultBase);
            }

            @Override
            public void onError(Call call, ResultBase resultBase, int
                    errorCode) {
                if (sendDiscountCouponMvpView==null) return;
                sendDiscountCouponMvpView.showSendDiscountCouponErrer(resultBase.getMsg());
            }

            @Override
            public void onFail(Call call, String msg) {
                if (sendDiscountCouponMvpView==null) return;
                sendDiscountCouponMvpView.showSendDiscountCouponErrer(msg);
            }

            @Override
            public void onResult() {
                if (sendDiscountCouponMvpView==null) return;
                sendDiscountCouponMvpView.dismissLoadingView();
            }

        });
    }



}
