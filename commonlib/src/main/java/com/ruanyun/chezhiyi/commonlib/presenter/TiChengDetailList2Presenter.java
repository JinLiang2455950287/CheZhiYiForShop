package com.ruanyun.chezhiyi.commonlib.presenter;


import com.ruanyun.chezhiyi.commonlib.base.ResultBase;
import com.ruanyun.chezhiyi.commonlib.model.TiChengListPublicInfo;
import com.ruanyun.chezhiyi.commonlib.util.LogX;
import com.ruanyun.chezhiyi.commonlib.view.TiChengDetailList2View;
import com.ruanyun.chezhiyi.commonlib.view.TiChengDetailListView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 施工/销售列表详情提成
 * Created by czy on 2017/4/18.
 */

public class TiChengDetailList2Presenter implements Presenter<TiChengDetailList2View> {
    private TiChengDetailList2View tiChengDetailList2View;

    private Call<ResultBase<TiChengListPublicInfo>> call;

    @Override
    public void attachView(TiChengDetailList2View mvpView) {
        this.tiChengDetailList2View = mvpView;
    }

    public void getTiChengYearList2(Call<ResultBase<TiChengListPublicInfo>> call) {
        this.call = call;
        call.enqueue(new Callback<ResultBase<TiChengListPublicInfo>>() {
            @Override
            public void onResponse(Call<ResultBase<TiChengListPublicInfo>> call, Response<ResultBase<TiChengListPublicInfo>> response) {
                LogX.e("提成Yearlist2persenter", response.body().getObj().toString());
                tiChengDetailList2View.getTiChengList2Success(response.body().getObj());
            }

            @Override
            public void onFailure(Call<ResultBase<TiChengListPublicInfo>> call, Throwable t) {
                LogX.e("提成Yearlist2persenteronError", t.toString() + "");
            }
        });
    }


    @Override
    public void detachView() {
        tiChengDetailList2View = null;
    }

    @Override
    public void onCancel() {
        if (call != null && !call.isCanceled())
            call.cancel();
    }
}
