package com.ruanyun.chezhiyi.commonlib.presenter;


import com.ruanyun.chezhiyi.commonlib.base.ResultBase;

import retrofit2.Call;

import com.ruanyun.chezhiyi.commonlib.data.api.ResponseCallback;
import com.ruanyun.chezhiyi.commonlib.view.RebackPayMvpView;


/**
 * Created by czy on 2017/3/29.
 */

public class RebackPayPrsenter implements Presenter<RebackPayMvpView> {
    private RebackPayMvpView rebackPayMvpView;
    private Call<ResultBase> call;

    @Override
    public void attachView(RebackPayMvpView mvpView) {
        this.rebackPayMvpView = mvpView;

    }

    /*退款原因*/
    public void getRebackReason(Call<ResultBase> call) {
        this.call = call;
        call.enqueue(new ResponseCallback<ResultBase>() {
            @Override
            public void onSuccess(Call call, ResultBase resultBase) {
                rebackPayMvpView.getRebackSccess(resultBase);
            }

            @Override
            public void onError(Call call, ResultBase resultBase, int errorCode) {

            }

            @Override
            public void onFail(Call call, String msg) {

            }

            @Override
            public void onResult() {

            }
        });
    }

    @Override
    public void detachView() {
        rebackPayMvpView = null;
    }

    @Override
    public void onCancel() {
        if (call != null && !call.isCanceled())
            call.cancel();
    }
}
