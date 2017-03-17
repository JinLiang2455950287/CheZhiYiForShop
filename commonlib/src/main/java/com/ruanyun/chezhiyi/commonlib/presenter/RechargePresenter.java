package com.ruanyun.chezhiyi.commonlib.presenter;

import com.ruanyun.chezhiyi.commonlib.base.ResultBase;
import com.ruanyun.chezhiyi.commonlib.data.api.ResponseCallback;
import com.ruanyun.chezhiyi.commonlib.model.RechangeInfo;
import com.ruanyun.chezhiyi.commonlib.view.RechargeMvpView;


import retrofit2.Call;

/**
 * 充值
 * Created by Sxq on 2016/10/10.
 */
public class RechargePresenter implements Presenter<RechargeMvpView> {
    RechargeMvpView rechargeMvpView;
    Call<ResultBase<RechangeInfo>> resultBaseCall;

    @Override
    public void attachView(RechargeMvpView mvpView) {
        rechargeMvpView = mvpView;
    }

    @Override
    public void detachView() {
        rechargeMvpView = null;

    }

    @Override
    public void onCancel() {
        if (resultBaseCall != null && !resultBaseCall.isCanceled()) {
            resultBaseCall.cancel();
        }
    }

    //充值
    public void Recharge(Call call) {
//        rechargeMvpView.showLoadingView();
        resultBaseCall = call;
        resultBaseCall.enqueue(new ResponseCallback<ResultBase<RechangeInfo>>() {

            @Override
            public void onSuccess(Call call, ResultBase<RechangeInfo> orderInfoResultBase) {
                rechargeMvpView.onSuccess(orderInfoResultBase);
            }

            @Override
            public void onError(Call call, ResultBase<RechangeInfo> orderInfoResultBase, int errorCode) {
                rechargeMvpView.onError(orderInfoResultBase);
            }

            @Override
            public void onFail(Call call, String msg) {
                rechargeMvpView.onFail();
            }

            @Override
            public void onResult() {

            }
        });
    }
}
