package com.ruanyun.chezhiyi.commonlib.presenter;

import com.ruanyun.chezhiyi.commonlib.base.ResultBase;
import com.ruanyun.chezhiyi.commonlib.data.api.ResponseCallback;
import com.ruanyun.chezhiyi.commonlib.model.MenDianHuiYuanInfo;
import com.ruanyun.chezhiyi.commonlib.model.WorkBayInfo;
import com.ruanyun.chezhiyi.commonlib.util.LogX;
import com.ruanyun.chezhiyi.commonlib.view.HuiYuanTongJiView;
import com.ruanyun.chezhiyi.commonlib.view.KaiDanGongweiView;

import java.util.List;

import retrofit2.Call;

/**
 * 空闲工位
 * Created by czy on 2017/4/25.
 */

public class KaiDanGongweiPresenter implements Presenter<KaiDanGongweiView> {

    private KaiDanGongweiView kaiDanGongweiView;

    private Call<ResultBase<List<WorkBayInfo>>> call;

    @Override
    public void attachView(KaiDanGongweiView mvpView) {
        this.kaiDanGongweiView = mvpView;
    }

    public void getKaiDanGongWeiInfo(Call<ResultBase<List<WorkBayInfo>>> call) {
        this.call = call;
        call.enqueue(new ResponseCallback<ResultBase<List<WorkBayInfo>>>() {
            @Override
            public void onSuccess(Call call, ResultBase<List<WorkBayInfo>> menDianHuiYuanInfoResultBase) {
                LogX.e("开单工位persenter", menDianHuiYuanInfoResultBase.getObj().toString());
                kaiDanGongweiView.getKaiDanSuccess(menDianHuiYuanInfoResultBase.getObj());
            }

            @Override
            public void onError(Call call, ResultBase<List<WorkBayInfo>> menDianHuiYuanInfoResultBase, int errorCode) {

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
        kaiDanGongweiView = null;
    }

    @Override
    public void onCancel() {
        if (call != null && !call.isCanceled())
            call.cancel();
    }
}
