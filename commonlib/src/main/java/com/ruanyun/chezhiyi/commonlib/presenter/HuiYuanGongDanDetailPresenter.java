package com.ruanyun.chezhiyi.commonlib.presenter;

import com.ruanyun.chezhiyi.commonlib.base.ResultBase;
import com.ruanyun.chezhiyi.commonlib.data.api.ResponseCallback;
import com.ruanyun.chezhiyi.commonlib.model.MenDianGongDanDetailInfo;
import com.ruanyun.chezhiyi.commonlib.model.MenDianGongDanInfo;
import com.ruanyun.chezhiyi.commonlib.presenter.Presenter;
import com.ruanyun.chezhiyi.commonlib.util.LogX;
import com.ruanyun.chezhiyi.commonlib.view.HuiYuanGongDanDetailView;
import com.ruanyun.chezhiyi.commonlib.view.HuiYuanGongDanTongJiView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 门店 工单detail统计统计
 * Created by czy on 2017/4/20.
 */

public class HuiYuanGongDanDetailPresenter implements Presenter<HuiYuanGongDanDetailView> {

    private HuiYuanGongDanDetailView huiYuanGongDanDetailView;

    private Call<ResultBase<MenDianGongDanDetailInfo>> call;

    @Override
    public void attachView(HuiYuanGongDanDetailView mvpView) {
        this.huiYuanGongDanDetailView = mvpView;
    }

    public void getGongDanTongJiDtailInfo(Call<ResultBase<MenDianGongDanDetailInfo>> call) {
        this.call = call;
//        call.enqueue(new Callback<ResultBase>() {
//            @Override
//            public void onResponse(Call<ResultBase> call, Response<ResultBase> response) {
//                LogX.e("工单Detailpersenter", response.body().getObj().toString());
////                huiYuanGongDanDetailView.getGongDanDetailSuccess(response.body().getObj().toString());
//            }
//
//            @Override
//            public void onFailure(Call<ResultBase> call, Throwable t) {
//
//            }
//        });
        call.enqueue(new ResponseCallback<ResultBase<MenDianGongDanDetailInfo>>() {
            @Override
            public void onSuccess(Call call, ResultBase<MenDianGongDanDetailInfo> resultBase) {
                LogX.e("工单Detailpersenter", resultBase.getObj().toString());
                huiYuanGongDanDetailView.getGongDanDetailSuccess(resultBase.getObj());
            }

            @Override
            public void onError(Call call, ResultBase resultBase, int errorCode) {

            }

            @Override
            public void onFail(Call call, String msg) {
                LogX.e("工单persenteronError", msg.toString() + "");
            }

            @Override
            public void onResult() {

            }
        });

    }

    @Override
    public void detachView() {
        huiYuanGongDanDetailView = null;
    }

    @Override
    public void onCancel() {
        if (call != null && !call.isCanceled())
            call.cancel();
    }
}
