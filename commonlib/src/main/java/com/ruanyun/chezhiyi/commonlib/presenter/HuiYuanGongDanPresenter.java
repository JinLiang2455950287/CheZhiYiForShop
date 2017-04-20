package com.ruanyun.chezhiyi.commonlib.presenter;

import com.ruanyun.chezhiyi.commonlib.base.ResultBase;
import com.ruanyun.chezhiyi.commonlib.data.api.ResponseCallback;
import com.ruanyun.chezhiyi.commonlib.model.MenDianGongDanInfo;
import com.ruanyun.chezhiyi.commonlib.model.MenDianHuiYuanInfo;
import com.ruanyun.chezhiyi.commonlib.util.LogX;
import com.ruanyun.chezhiyi.commonlib.view.HuiYuanGongDanTongJiView;
import com.ruanyun.chezhiyi.commonlib.view.HuiYuanTongJiView;

import retrofit2.Call;

/**
 * 门店 工单统计
 * Created by czy on 2017/4/18.
 */

public class HuiYuanGongDanPresenter implements Presenter<HuiYuanGongDanTongJiView> {

    private HuiYuanGongDanTongJiView huiYuanGongDanTongJiView;

    private Call<ResultBase<MenDianGongDanInfo>> call;

    @Override
    public void attachView(HuiYuanGongDanTongJiView mvpView) {
        this.huiYuanGongDanTongJiView = mvpView;
    }

    public void getGongDanTongJiInfo(Call<ResultBase<MenDianGongDanInfo>> call) {
        this.call = call;
        call.enqueue(new ResponseCallback<ResultBase<MenDianGongDanInfo>>() {
            @Override
            public void onSuccess(Call call, ResultBase<MenDianGongDanInfo> resultBase) {
                LogX.e("工单persenter", resultBase.getObj().toString());
                huiYuanGongDanTongJiView.getGongDanSuccess(resultBase.getObj());
            }

            @Override
            public void onError(Call call, ResultBase<MenDianGongDanInfo> resultBase, int errorCode) {

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
        huiYuanGongDanTongJiView = null;
    }

    @Override
    public void onCancel() {
        if (call != null && !call.isCanceled())
            call.cancel();
    }
}
