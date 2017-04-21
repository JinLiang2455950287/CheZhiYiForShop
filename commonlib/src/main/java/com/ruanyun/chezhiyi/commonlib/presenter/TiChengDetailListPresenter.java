package com.ruanyun.chezhiyi.commonlib.presenter;


import com.ruanyun.chezhiyi.commonlib.base.ResultBase;
import com.ruanyun.chezhiyi.commonlib.model.TiChengListModel;
import com.ruanyun.chezhiyi.commonlib.util.LogX;
import com.ruanyun.chezhiyi.commonlib.view.TiChengDetailListView;
import com.ruanyun.chezhiyi.commonlib.view.TiChengListView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 施工/销售列表详情提成
 * Created by czy on 2017/4/18.
 */

public class TiChengDetailListPresenter implements Presenter<TiChengDetailListView> {
    private TiChengDetailListView tiChengDetailListView;

    private Call<ResultBase> call;

    @Override
    public void attachView(TiChengDetailListView mvpView) {
        this.tiChengDetailListView = mvpView;
    }

    public void getTiChengYearList(Call<ResultBase> call) {
        this.call = call;
        call.enqueue(new Callback<ResultBase>() {
            @Override
            public void onResponse(Call<ResultBase> call, Response<ResultBase> response) {
                LogX.e("提成Yearlistpersenter", response.body().getObj().toString());
                tiChengDetailListView.getTiChengListSuccess(response.body());
            }

            @Override
            public void onFailure(Call<ResultBase> call, Throwable t) {
                LogX.e("提成YearlistpersenteronError", t.toString() + "");
            }
        });
    }


    @Override
    public void detachView() {
        tiChengDetailListView = null;
    }

    @Override
    public void onCancel() {
        if (call != null && !call.isCanceled())
            call.cancel();
    }
}
