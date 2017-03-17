package com.ruanyun.chezhiyi.commonlib.presenter;

import com.ruanyun.chezhiyi.commonlib.base.ResultBase;
import com.ruanyun.chezhiyi.commonlib.data.api.ResponseCallback;
import com.ruanyun.chezhiyi.commonlib.model.TuiKuanInfo;
import com.ruanyun.chezhiyi.commonlib.util.LogX;
import com.ruanyun.chezhiyi.commonlib.view.RePayMvpView;

import java.util.List;

import retrofit2.Call;

/**
 * Created by 创智 on 2017/3/13.
 * 退款
 */

public class RePayPresenter implements Presenter<RePayMvpView> {
    private RePayMvpView rePayMvpView;
    private Call<ResultBase<List<TuiKuanInfo>>> call;

    @Override
    public void attachView(RePayMvpView mvpView) {
        this.rePayMvpView = mvpView;
    }

    public void getRePayData(Call<ResultBase<List<TuiKuanInfo>>> call) {
        this.call = call;
        call.enqueue(new ResponseCallback<ResultBase<List<TuiKuanInfo>>>() {

            @Override
            public void onSuccess(Call call, ResultBase<List<TuiKuanInfo>> listResultBase) {
                LogX.e("测试啦", listResultBase.getObj().toString());
                rePayMvpView.getDataSuccess(listResultBase.getObj());
                rePayMvpView.dismissLoadingView();
            }

            @Override
            public void onError(Call call, ResultBase<List<TuiKuanInfo>> listResultBase, int errorCode) {

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
        rePayMvpView = null;
    }

    @Override
    public void onCancel() {
        if (call != null && !call.isCanceled())
            call.cancel();

    }
}
