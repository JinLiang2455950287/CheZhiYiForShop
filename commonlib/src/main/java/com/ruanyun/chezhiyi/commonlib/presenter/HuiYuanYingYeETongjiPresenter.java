package com.ruanyun.chezhiyi.commonlib.presenter;

import com.ruanyun.chezhiyi.commonlib.base.ResultBase;
import com.ruanyun.chezhiyi.commonlib.data.api.ResponseCallback;
import com.ruanyun.chezhiyi.commonlib.model.MenDianHuiYuanInfo;
import com.ruanyun.chezhiyi.commonlib.model.MenDianYinYeEInfo;
import com.ruanyun.chezhiyi.commonlib.util.LogX;
import com.ruanyun.chezhiyi.commonlib.view.HuiYuanTongJiView;
import com.ruanyun.chezhiyi.commonlib.view.HuiYuanYingYeETongJiView;

import retrofit2.Call;

/**
 * 营业额统计
 * Created by czy on 2017/4/20.
 */

public class HuiYuanYingYeETongjiPresenter implements Presenter<HuiYuanYingYeETongJiView> {

    private HuiYuanYingYeETongJiView huiYuanYingYeETongJiViewi;

    private Call<ResultBase<MenDianYinYeEInfo>> call;

    @Override
    public void attachView(HuiYuanYingYeETongJiView mvpView) {
        this.huiYuanYingYeETongJiViewi = mvpView;
    }

    public void getYingYeEInfo(Call<ResultBase<MenDianYinYeEInfo>> call) {
        this.call = call;
        call.enqueue(new ResponseCallback<ResultBase<MenDianYinYeEInfo>>() {
            @Override
            public void onSuccess(Call call, ResultBase<MenDianYinYeEInfo> resultBase) {
                LogX.e("营业额persenter prea", resultBase.getObj().toString());
                huiYuanYingYeETongJiViewi.getYinYeESuccess(resultBase.getObj());
            }

            @Override
            public void onError(Call call, ResultBase menDianHuiYuanInfoResultBase, int errorCode) {
            }

            @Override
            public void onFail(Call call, String msg) {
                LogX.e("营业额persenteronError", msg.toString() + "");
            }

            @Override
            public void onResult() {
            }
        });

    }

    @Override
    public void detachView() {
        huiYuanYingYeETongJiViewi = null;
    }

    @Override
    public void onCancel() {
        if (call != null && !call.isCanceled())
            call.cancel();
    }
}
