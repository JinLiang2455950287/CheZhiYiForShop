package com.ruanyun.chezhiyi.commonlib.presenter;

import com.ruanyun.chezhiyi.commonlib.base.ResultBase;
import com.ruanyun.chezhiyi.commonlib.data.api.ResponseCallback;
import com.ruanyun.chezhiyi.commonlib.model.CardPackageListModel;
import com.ruanyun.chezhiyi.commonlib.util.LogX;
import com.ruanyun.chezhiyi.commonlib.view.CardPackageView;

import java.util.List;

import retrofit2.Call;

/**
 * Created by czy on 2017/3/21.
 */

public class CardPackagePresenter implements Presenter<CardPackageView> {
    private CardPackageView cardPackageView;

    private Call<ResultBase<List<CardPackageListModel>>> call;

    @Override
    public void attachView(CardPackageView mvpView) {
        this.cardPackageView = mvpView;
    }

    public void getCardPackageList(Call<ResultBase<List<CardPackageListModel>>> call) {
        this.call = call;
        call.enqueue(new ResponseCallback<ResultBase<List<CardPackageListModel>>>() {
            @Override
            public void onSuccess(Call call, ResultBase<List<CardPackageListModel>> listResultBase) {
                LogX.e("卡套餐persenter", listResultBase.getObj().toString());
                //[CardPackageListModel{access_token='null', msg='null', result=0, obj=null}]
                cardPackageView.getDataSuccess(listResultBase.getObj());
                cardPackageView.dismissLoadingView();
            }

            @Override
            public void onError(Call call, ResultBase<List<CardPackageListModel>> listResultBase, int errorCode) {
                LogX.e("卡套餐persenteronError", errorCode+"");
            }

            @Override
            public void onFail(Call call, String msg) {
                LogX.e("卡套餐persenteronFail", msg+"");
            }

            @Override
            public void onResult() {
                LogX.e("卡套餐persenteronResult", "onResult");
            }
        });
    }

    @Override
    public void detachView() {
        cardPackageView = null;
    }

    @Override
    public void onCancel() {
        if (call != null && !call.isCanceled())
            call.cancel();
    }
}
