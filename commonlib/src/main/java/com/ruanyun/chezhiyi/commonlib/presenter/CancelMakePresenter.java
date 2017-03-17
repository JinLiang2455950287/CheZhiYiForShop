package com.ruanyun.chezhiyi.commonlib.presenter;

import com.ruanyun.chezhiyi.commonlib.base.ResultBase;
import com.ruanyun.chezhiyi.commonlib.data.api.ResponseCallback;
import com.ruanyun.chezhiyi.commonlib.view.CancelMakeMvpView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Description ：取消预约
 * <p/>
 * Created by ycw on 2016/10/10.
 */
public class CancelMakePresenter implements Presenter<CancelMakeMvpView> {

    CancelMakeMvpView cancelMvpView;
    Call<ResultBase> call;

    @Override
    public void attachView(CancelMakeMvpView mvpView) {
        cancelMvpView = mvpView;
    }

    @Override
    public void detachView() {
        cancelMvpView= null;
    }

    @Override
    public void onCancel() {
        if (call != null && !call.isCanceled())
            call.cancel();
    }


    /**
     * @param call
     */
    public void  CancelMake(Call<ResultBase> call){
        this.call = call;
        if (cancelMvpView == null) return;
        cancelMvpView.showLoadingView();
        this.call.enqueue(new ResponseCallback<ResultBase>() {
            @Override
            public void onSuccess(Call call, ResultBase resultBase) {
                if (cancelMvpView == null) return;
                cancelMvpView.showTip(resultBase.getMsg());
                cancelMvpView.cancelSuccess();
            }

            @Override
            public void onError(Call call, ResultBase resultBase, int errorCode) {
                if (cancelMvpView == null) return;
                cancelMvpView.showTip(resultBase.getMsg());
            }

            @Override
            public void onFail(Call call, String msg) {
                if (cancelMvpView == null) return;
                cancelMvpView.showTip(msg);
            }

            @Override
            public void onResult() {
                if (cancelMvpView == null) return;
                cancelMvpView.dismissLoadingView();
            }
        });
    }

}
