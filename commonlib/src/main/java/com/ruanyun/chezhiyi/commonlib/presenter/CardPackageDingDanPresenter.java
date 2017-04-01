package com.ruanyun.chezhiyi.commonlib.presenter;

import com.ruanyun.chezhiyi.commonlib.base.ResultBase;
import com.ruanyun.chezhiyi.commonlib.data.api.ResponseCallback;
import com.ruanyun.chezhiyi.commonlib.model.OrderInfo;
import com.ruanyun.chezhiyi.commonlib.view.CardPackageDingDanView;

import retrofit2.Call;

/**
 * Created by czy on 2017/3/22.
 * 卡套餐详情界面订单提交
 */

public class CardPackageDingDanPresenter implements Presenter<CardPackageDingDanView> {
    private CardPackageDingDanView cardPackageDingDanView;
    Call<ResultBase<OrderInfo>> call;

    @Override
    public void attachView(CardPackageDingDanView mvpView) {
        this.cardPackageDingDanView = mvpView;
    }

    public void getDingDanData(Call<ResultBase<OrderInfo>> call) {
        this.call = call;
        call.enqueue(new ResponseCallback<ResultBase<OrderInfo>>() {
            @Override
            public void onSuccess(Call call, ResultBase<OrderInfo> resultBase) {
                if (resultBase.getResult() == 1) {
                    cardPackageDingDanView.reportDinDanSuccess(resultBase.getObj());
                }
            }

            @Override
            public void onError(Call call, ResultBase<OrderInfo> resultBase, int errorCode) {
                cardPackageDingDanView.reportDinDanFalse();
            }

            @Override
            public void onFail(Call call, String msg) {
                cardPackageDingDanView.reportDinDanFalse();
            }

            @Override
            public void onResult() {
            }
        });
    }

    @Override
    public void detachView() {
        cardPackageDingDanView = null;
    }

    @Override
    public void onCancel() {
        if (call != null && !call.isCanceled())
            call.cancel();
    }
}
