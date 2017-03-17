package com.ruanyun.chezhiyi.commonlib.presenter;

import com.ruanyun.chezhiyi.commonlib.base.ResultBase;
import com.ruanyun.chezhiyi.commonlib.data.api.ResponseCallback;
import com.ruanyun.chezhiyi.commonlib.view.RefundApplicationMvpView;

import retrofit2.Call;

/**
 * 申请退款
 * Created by Sxq on 2016/9/22.
 */
public class RefundApplicationPresenter implements Presenter<RefundApplicationMvpView> {
    private RefundApplicationMvpView refundApplicationMvpView;
    private Call refundApplicateCall;

    @Override
    public void attachView(RefundApplicationMvpView mvpView) {
        refundApplicationMvpView = mvpView;
    }

    @Override
    public void detachView() {
        refundApplicationMvpView = null;
    }

    @Override
    public void onCancel() {
        if (refundApplicateCall != null && !refundApplicateCall.isCanceled()) {
            refundApplicateCall.cancel();
        }
    }

    /**
     * 申请退款接口
     * @param call
     */
    public void refundApplicate(Call call) {
        if (refundApplicationMvpView == null) return;
        refundApplicationMvpView.showLoadingView();
        refundApplicateCall = call;
        refundApplicateCall.enqueue(new ResponseCallback() {
            @Override
            public void onSuccess(Call call, ResultBase resultBase) {
                if (refundApplicationMvpView == null) return;
                refundApplicationMvpView.refundApplicationMsg(resultBase.getMsg());
                refundApplicationMvpView.refundApplicationSuccess();
            }

            @Override
            public void onError(Call call, ResultBase resultBase, int errorCode) {
                if (refundApplicationMvpView == null) return;
                refundApplicationMvpView.refundApplicationMsg(resultBase.getMsg());
            }

            @Override
            public void onFail(Call call, String msg) {
                if (refundApplicationMvpView == null) return;
                refundApplicationMvpView.refundApplicationMsg(msg);
            }

            @Override
            public void onResult() {
                if (refundApplicationMvpView == null) return;
                refundApplicationMvpView.dismissLoadingView();
            }
        });
    }
}
