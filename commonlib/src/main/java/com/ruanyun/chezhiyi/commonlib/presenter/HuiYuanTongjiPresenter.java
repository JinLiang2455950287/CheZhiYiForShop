package com.ruanyun.chezhiyi.commonlib.presenter;

import com.ruanyun.chezhiyi.commonlib.base.ResultBase;
import com.ruanyun.chezhiyi.commonlib.data.api.ResponseCallback;
import com.ruanyun.chezhiyi.commonlib.model.MenDianHuiYuanInfo;
import com.ruanyun.chezhiyi.commonlib.util.LogX;
import com.ruanyun.chezhiyi.commonlib.view.HuiYuanTongJiView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 会员统计
 * Created by czy on 2017/4/18.
 */

public class HuiYuanTongjiPresenter implements Presenter<HuiYuanTongJiView> {

    private HuiYuanTongJiView huiYuanTongJiView;

    private Call<ResultBase<MenDianHuiYuanInfo>> call;

    @Override
    public void attachView(HuiYuanTongJiView mvpView) {
        this.huiYuanTongJiView = mvpView;
    }

    public void getTongJiInfo(Call<ResultBase<MenDianHuiYuanInfo>> call) {
        this.call = call;
        call.enqueue(new ResponseCallback<ResultBase<MenDianHuiYuanInfo>>() {
            @Override
            public void onSuccess(Call call, ResultBase<MenDianHuiYuanInfo> menDianHuiYuanInfoResultBase) {
                LogX.e("会员persenter", menDianHuiYuanInfoResultBase.getObj().toString());
                huiYuanTongJiView.getHSuccess(menDianHuiYuanInfoResultBase.getObj());
            }

            @Override
            public void onError(Call call, ResultBase<MenDianHuiYuanInfo> menDianHuiYuanInfoResultBase, int errorCode) {

            }

            @Override
            public void onFail(Call call, String msg) {
                LogX.e("会员persenteronError", msg.toString() + "");
            }

            @Override
            public void onResult() {

            }
        });

    }

    @Override
    public void detachView() {
        huiYuanTongJiView = null;
    }

    @Override
    public void onCancel() {
        if (call != null && !call.isCanceled())
            call.cancel();
    }
}
