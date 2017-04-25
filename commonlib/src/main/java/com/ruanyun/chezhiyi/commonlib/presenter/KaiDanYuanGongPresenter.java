package com.ruanyun.chezhiyi.commonlib.presenter;

import com.ruanyun.chezhiyi.commonlib.base.ResultBase;
import com.ruanyun.chezhiyi.commonlib.data.api.ResponseCallback;
import com.ruanyun.chezhiyi.commonlib.model.User;
import com.ruanyun.chezhiyi.commonlib.model.WorkBayInfo;
import com.ruanyun.chezhiyi.commonlib.util.LogX;
import com.ruanyun.chezhiyi.commonlib.view.KaiDanGongweiView;
import com.ruanyun.chezhiyi.commonlib.view.KaiDanJiShiView;

import java.util.List;

import retrofit2.Call;

/**
 * 空闲技师
 * Created by czy on 2017/4/25.
 */

public class KaiDanYuanGongPresenter implements Presenter<KaiDanJiShiView> {

    private KaiDanJiShiView kaiDanJiShiViewai;

    private Call<ResultBase<List<User>>> call;

    @Override
    public void attachView(KaiDanJiShiView mvpView) {
        this.kaiDanJiShiViewai = mvpView;
    }

    public void getKaiDanJiShiInfo(Call<ResultBase<List<User>>> call) {
        this.call = call;
        call.enqueue(new ResponseCallback<ResultBase<List<User>>>() {
            @Override
            public void onSuccess(Call call, ResultBase<List<User>> menDianHuiYuanInfoResultBase) {
                LogX.e("开单工位persenter", menDianHuiYuanInfoResultBase.getObj().toString());
                kaiDanJiShiViewai.getKaiDanJiShiSuccess(menDianHuiYuanInfoResultBase.getObj());
            }

            @Override
            public void onError(Call call, ResultBase<List<User>> menDianHuiYuanInfoResultBase, int errorCode) {

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
        kaiDanJiShiViewai = null;
    }

    @Override
    public void onCancel() {
        if (call != null && !call.isCanceled())
            call.cancel();
    }
}
