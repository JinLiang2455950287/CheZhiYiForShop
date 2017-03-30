package com.ruanyun.chezhiyi.commonlib.presenter;

import com.ruanyun.chezhiyi.commonlib.base.ResultBase;
import com.ruanyun.chezhiyi.commonlib.data.api.ResponseCallback;
import com.ruanyun.chezhiyi.commonlib.util.LogX;
import com.ruanyun.chezhiyi.commonlib.view.InsteadOrderMvpView;

import retrofit2.Call;

/**
 * 代下单(添加商品)
 * Created by hdl on 2016/9/9.
 */
public class InsteadOrderPresenter implements Presenter<InsteadOrderMvpView> {

    InsteadOrderMvpView insteadOrderMvpView;
    Call<ResultBase> call;


    @Override
    public void attachView(InsteadOrderMvpView mvpView) {
        insteadOrderMvpView = mvpView;
    }

    @Override
    public void detachView() {
        insteadOrderMvpView = null;
    }

    @Override
    public void onCancel() {
        if (call != null && !call.isCanceled())
            call.cancel();
    }


    /**
     * 代下单(添加商品)
     *
     * @param call
     */
    public void setInsteadOrderMvpView(Call<ResultBase> call) {
        this.call = call;
        if (insteadOrderMvpView == null) return;
        insteadOrderMvpView.showLoadingView();
        this.call.enqueue(new ResponseCallback<ResultBase>() {
            @Override
            public void onSuccess(Call call, ResultBase resultBase) {
                if (insteadOrderMvpView == null) return;
                LogX.e("代下单", resultBase.getObj().toString() + "onSuccess");
                insteadOrderMvpView.showInsteadOrderSuccess(resultBase);
            }

            @Override
            public void onError(Call call, ResultBase resultBase, int errorCode) {
                if (insteadOrderMvpView == null) return;
                LogX.e("代下单", errorCode + "errorCode");
                LogX.e("代下单", resultBase.getObj().toString()+"errorCode");
                insteadOrderMvpView.showInsteadOrderTip(resultBase.getMsg());
            }

            @Override
            public void onFail(Call call, String msg) {
                if (insteadOrderMvpView == null) return;
                insteadOrderMvpView.showInsteadOrderTip(msg);
            }

            @Override
            public void onResult() {
                if (insteadOrderMvpView == null) return;
                insteadOrderMvpView.dismissLoadingView();
            }
        });
    }


}
