package com.ruanyun.chezhiyi.commonlib.presenter;

import com.ruanyun.chezhiyi.commonlib.base.ResultBase;
import com.ruanyun.chezhiyi.commonlib.data.api.ResponseCallback;
import com.ruanyun.chezhiyi.commonlib.util.LogX;
import com.ruanyun.chezhiyi.commonlib.view.HomeWaitAreaCountView;

import retrofit2.Call;

/**
 * Created by czy on 2017/3/27.
 */

public class HomeWaitAreaCountPresenter implements Presenter<HomeWaitAreaCountView> {
    private HomeWaitAreaCountView homeWaitAreaCountView;
    private Call<ResultBase> call;

    @Override
    public void attachView(HomeWaitAreaCountView mvpView) {
        this.homeWaitAreaCountView = mvpView;
    }

    /*获取主页面等候区的数量*/
    public void getWaitAreaCountData(Call<ResultBase> call) {
        this.call = call;
        call.enqueue(new ResponseCallback<ResultBase>() {
            @Override
            public void onSuccess(Call call, ResultBase resultBase) {
                homeWaitAreaCountView.getWaitAreaCount(resultBase);
                LogX.e("等候区", resultBase.toString());
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
    public void detachView() {
        homeWaitAreaCountView = null;
    }

    @Override
    public void onCancel() {
        if (call != null && !call.isExecuted())
            call.cancel();

    }
}
