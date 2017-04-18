package com.ruanyun.chezhiyi.commonlib.presenter;


import com.ruanyun.chezhiyi.commonlib.base.ResultBase;
import com.ruanyun.chezhiyi.commonlib.model.TiChengListModel;
import com.ruanyun.chezhiyi.commonlib.util.LogX;
import com.ruanyun.chezhiyi.commonlib.view.TiChengListView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 施工/销售列表提成
 * Created by czy on 2017/4/18.
 */

public class TiChengListPresenter implements Presenter<TiChengListView> {
    private TiChengListView tiChengListView;

    private Call<ResultBase<List<TiChengListModel>>> call;

    @Override
    public void attachView(TiChengListView mvpView) {
        this.tiChengListView = mvpView;
    }

    public void getTiChengList(Call<ResultBase<List<TiChengListModel>>> call) {
        this.call = call;
        call.enqueue(new Callback<ResultBase<List<TiChengListModel>>>() {
            @Override
            public void onResponse(Call<ResultBase<List<TiChengListModel>>> call, Response<ResultBase<List<TiChengListModel>>> response) {
                LogX.e("提成listpersenter", response.body().getObj().toString());
                tiChengListView.getTiChengListSuccess(response.body().getObj());
            }

            @Override
            public void onFailure(Call<ResultBase<List<TiChengListModel>>> call, Throwable t) {
                LogX.e("提成listpersenteronError", t.toString() + "");
            }
        });
    }

    @Override
    public void detachView() {
        tiChengListView = null;
    }

    @Override
    public void onCancel() {
        if (call != null && !call.isCanceled())
            call.cancel();
    }
}
