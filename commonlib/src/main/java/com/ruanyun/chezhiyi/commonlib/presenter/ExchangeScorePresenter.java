package com.ruanyun.chezhiyi.commonlib.presenter;

import com.ruanyun.chezhiyi.commonlib.base.ResultBase;
import com.ruanyun.chezhiyi.commonlib.data.api.ResponseCallback;
import com.ruanyun.chezhiyi.commonlib.model.OrderInfo;
import com.ruanyun.chezhiyi.commonlib.view.ExchangeMvpView;

import retrofit2.Call;

/**
 * Description ：积分兑换的
 * <p>
 * Created by ycw on 2016/10/15.
 */
public class ExchangeScorePresenter implements Presenter<ExchangeMvpView> {

    ExchangeMvpView exchangeMvpView;
    Call<ResultBase<OrderInfo>> orderCall;

    @Override
    public void attachView(ExchangeMvpView mvpView) {
        exchangeMvpView = mvpView;
    }

    @Override
    public void detachView() {
        exchangeMvpView = null;
    }

    @Override
    public void onCancel() {

    }

    /**
     * 	金额+积分兑换     或    	纯积分兑换
     */
    public void exchange(Call<ResultBase<OrderInfo>> orderCall) {
        if (exchangeMvpView == null) return;
        exchangeMvpView.showLoadingView();
        this.orderCall = orderCall;
        this.orderCall.enqueue(new ResponseCallback<ResultBase<OrderInfo>>() {
            @Override
            public void onSuccess(Call call, ResultBase<OrderInfo> orderInfoResultBase) {
                if (exchangeMvpView == null) return;
                exchangeMvpView.exchangeSuccess(orderInfoResultBase.getObj());
            }

            @Override
            public void onError(Call call, ResultBase<OrderInfo> orderInfoResultBase, int errorCode) {

            }

            @Override
            public void onFail(Call call, String msg) {

            }

            @Override
            public void onResult() {
                if (exchangeMvpView == null) return;
                exchangeMvpView.dismissLoadingView();
            }
        });
    }

}
