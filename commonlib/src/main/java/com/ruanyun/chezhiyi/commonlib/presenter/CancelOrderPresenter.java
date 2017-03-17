package com.ruanyun.chezhiyi.commonlib.presenter;

import com.ruanyun.chezhiyi.commonlib.base.ResultBase;
import com.ruanyun.chezhiyi.commonlib.data.api.ResponseCallback;
import com.ruanyun.chezhiyi.commonlib.view.UpStatusMvpView;

import retrofit2.Call;

/**
 * Description ：取消订单的 （修改订单状态）
 * <p/>
 * Created by ycw on 2016/10/11.
 */
public class CancelOrderPresenter implements Presenter<UpStatusMvpView> {

    private UpStatusMvpView upStatusMvpView;
    private Call<ResultBase> call;
    private Call<ResultBase> delCall;

    @Override
    public void attachView(UpStatusMvpView mvpView) {
        upStatusMvpView = mvpView;
    }

    @Override
    public void detachView() {
        upStatusMvpView = null;
    }

    @Override
    public void onCancel() {
        if (call != null && !call.isCanceled())
            call.cancel();
    }

    /**
     * @param call
     */
    public void  updateOrderStatus(Call<ResultBase> call){
        this.call = call;
        if (upStatusMvpView == null) return;
        upStatusMvpView.showLoadingView("取消订单中...");
        this.call.enqueue(new ResponseCallback<ResultBase>() {
            @Override
            public void onSuccess(Call call, ResultBase resultBase) {
                if (upStatusMvpView == null) return;
                upStatusMvpView.showTip(resultBase.getMsg());
                upStatusMvpView.cancelSuccess();
            }

            @Override
            public void onError(Call call, ResultBase resultBase, int errorCode) {
                if (upStatusMvpView == null) return;
                upStatusMvpView.showTip(resultBase.getMsg());
            }

            @Override
            public void onFail(Call call, String msg) {
                if (upStatusMvpView == null) return;
                upStatusMvpView.showTip(msg);
            }

            @Override
            public void onResult() {
                if (upStatusMvpView == null) return;
                upStatusMvpView.dismissLoadingView();
            }
        });
    }

    /**
     * 删除订单
     * @param call
     */
    public void  delOrder(Call<ResultBase> call){
        this.delCall = call;
        if (upStatusMvpView == null) return;
        upStatusMvpView.showLoadingView("删除订单中...");
        this.delCall.enqueue(new ResponseCallback<ResultBase>() {
            @Override
            public void onSuccess(Call call, ResultBase resultBase) {
                if (upStatusMvpView == null) return;
                upStatusMvpView.showTip(resultBase.getMsg());
                upStatusMvpView.delOrderSuccess();
            }

            @Override
            public void onError(Call call, ResultBase resultBase, int errorCode) {
                if (upStatusMvpView == null) return;
                upStatusMvpView.showTip(resultBase.getMsg());
            }

            @Override
            public void onFail(Call call, String msg) {
                if (upStatusMvpView == null) return;
                upStatusMvpView.showTip(msg);
            }

            @Override
            public void onResult() {
                if (upStatusMvpView == null) return;
                upStatusMvpView.dismissLoadingView();
            }
        });
    }


    /**
     * 订单支付校验
     * @param call
     */
    public void  valiteOrder(Call<ResultBase> call){
        this.delCall = call;
        if (upStatusMvpView == null) return;
//        upStatusMvpView.showLoadingView("删除订单中...");
        this.delCall.enqueue(new ResponseCallback<ResultBase>() {
            @Override
            public void onSuccess(Call call, ResultBase resultBase) {
                if (upStatusMvpView == null) return;
//                upStatusMvpView.showTip(resultBase.getMsg());
                upStatusMvpView.valiteOrderSuccess();
            }

            @Override
            public void onError(Call call, ResultBase resultBase, int errorCode) {
                if (upStatusMvpView == null) return;
                upStatusMvpView.showTip(resultBase.getMsg());
            }

            @Override
            public void onFail(Call call, String msg) {
                if (upStatusMvpView == null) return;
                upStatusMvpView.showTip(msg);
            }

            @Override
            public void onResult() {
                if (upStatusMvpView == null) return;
//                upStatusMvpView.dismissLoadingView();
            }
        });
    }

}
