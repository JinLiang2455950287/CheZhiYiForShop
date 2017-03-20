package com.ruanyun.chezhiyi.commonlib.presenter;

import com.ruanyun.chezhiyi.commonlib.base.ResultBase;
import com.ruanyun.chezhiyi.commonlib.data.api.ResponseCallback;
import com.ruanyun.chezhiyi.commonlib.model.GongGaoInfo;
import com.ruanyun.chezhiyi.commonlib.util.LogX;
import com.ruanyun.chezhiyi.commonlib.view.YuYueDealMvpView;

import java.util.List;

import retrofit2.Call;

/**
 * Created by czy on 2017/3/20.
 */

public class YuYueDealPresenter implements Presenter<YuYueDealMvpView> {
    private YuYueDealMvpView yuYueDealMvpView;
    private Call<ResultBase> call;

    @Override
    public void attachView(YuYueDealMvpView mvpView) {
        this.yuYueDealMvpView = mvpView;
    }

    @Override
    public void detachView() {
        yuYueDealMvpView = null;
    }

    /**
     * 预约处理
     *
     * @param call
     */
    public void getGongGao(Call<ResultBase> call) {
        this.call = call;
        this.call.enqueue(new ResponseCallback<ResultBase>() {


            @Override
            public void onSuccess(Call call, ResultBase resultBase) {
                yuYueDealMvpView.getDataSuccess(resultBase);
            }

            @Override
            public void onError(Call call, ResultBase resultBase, int errorCode) {

            }

            @Override
            public void onFail(Call call, String msg) {

            }

            @Override
            public void onResult() {

            }
        });
    }

    @Override
    public void onCancel() {
        if (call != null && call.isExecuted()) {
            call.cancel();
        }
    }
}
