package com.ruanyun.chezhiyi.commonlib.presenter;


import com.ruanyun.chezhiyi.commonlib.model.TiChengInfoModel;
import com.ruanyun.chezhiyi.commonlib.util.LogX;
import com.ruanyun.chezhiyi.commonlib.view.TiChengView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 施工/销售提成
 * Created by czy on 2017/4/18.
 */

public class TiChengPresenter implements Presenter<TiChengView> {
    private TiChengView tiChengView;

    private Call<TiChengInfoModel> call;

    @Override
    public void attachView(TiChengView mvpView) {
        this.tiChengView = mvpView;
    }

    public void getTiChengInfo(Call<TiChengInfoModel> call) {
        this.call = call;
        call.enqueue(new Callback<TiChengInfoModel>() {
            @Override
            public void onResponse(Call<TiChengInfoModel> call, Response<TiChengInfoModel> response) {
                LogX.e("提成persenter", response.body().toString());
                tiChengView.getTiChengSuccess(response.body());
            }

            @Override
            public void onFailure(Call<TiChengInfoModel> call, Throwable t) {
                LogX.e("提成persenteronError", t.toString() + "");
            }
        });
    }

    @Override
    public void detachView() {
        tiChengView = null;
    }

    @Override
    public void onCancel() {
        if (call != null && !call.isCanceled())
            call.cancel();
    }
}
