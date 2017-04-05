package com.ruanyun.chezhiyi.commonlib.presenter;

import com.ruanyun.chezhiyi.commonlib.base.ResultBase;
import com.ruanyun.chezhiyi.commonlib.data.api.ResponseCallback;
import com.ruanyun.chezhiyi.commonlib.model.CardPackageDetailInfo;
import com.ruanyun.chezhiyi.commonlib.util.LogX;
import com.ruanyun.chezhiyi.commonlib.view.CardPackageInfoView;

import retrofit2.Call;

/**
 * Created by czy on 2017/3/21.
 * 套餐详情
 */

public class CardPackageInfoPresenter implements Presenter<CardPackageInfoView> {
    private CardPackageInfoView cardPackageInfoView;
    private Call<ResultBase<CardPackageDetailInfo>> call;

    @Override
    public void attachView(CardPackageInfoView mvpView) {
        this.cardPackageInfoView = mvpView;

    }

    public void getCardPackageInfo(Call<ResultBase<CardPackageDetailInfo>> call) {
        this.call = call;
        call.enqueue(new ResponseCallback<ResultBase<CardPackageDetailInfo>>() {
            @Override
            public void onSuccess(Call call, ResultBase<CardPackageDetailInfo> resultBase) {
                LogX.e("套餐详情getCardPackageInfoonSuccess", resultBase.getObj().toString());
                cardPackageInfoView.getDataSuccess(resultBase.getObj());
            }

            @Override
            public void onError(Call call, ResultBase resultBase, int errorCode) {
                LogX.e("套餐详情getCardPackageInfoonError", resultBase.getObj().toString());
            }

            @Override
            public void onFail(Call call, String msg) {
                LogX.e("套餐详情getCardPackageInfoonFail", msg.toString());
            }

            @Override
            public void onResult() {

            }
        });
    }


    @Override
    public void detachView() {
        cardPackageInfoView = null;
    }

    @Override
    public void onCancel() {
        if (call != null && !call.isCanceled())
            call.cancel();

    }
}
