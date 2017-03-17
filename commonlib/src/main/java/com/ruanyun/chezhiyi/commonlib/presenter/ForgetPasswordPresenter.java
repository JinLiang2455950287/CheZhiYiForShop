package com.ruanyun.chezhiyi.commonlib.presenter;

import com.ruanyun.chezhiyi.commonlib.base.ResultBase;
import com.ruanyun.chezhiyi.commonlib.data.api.ResponseCallback;
import com.ruanyun.chezhiyi.commonlib.model.User;
import com.ruanyun.chezhiyi.commonlib.view.ForgetPasswordMvpView;

import retrofit2.Call;

/**
 * 忘记密码
 * Created by Sxq on 2016/9/21.
 */
public class ForgetPasswordPresenter implements Presenter<ForgetPasswordMvpView> {
    private ForgetPasswordMvpView forgetPasswordMvpView;
    private Call forgetpwdCall;

    @Override
    public void attachView(ForgetPasswordMvpView mvpView) {
        this.forgetPasswordMvpView = mvpView;
    }

    @Override
    public void detachView() {
        forgetPasswordMvpView = null;
    }

    @Override
    public void onCancel() {
        if (forgetpwdCall != null && !forgetpwdCall.isCanceled()) {
            forgetpwdCall.cancel();
        }
    }

    /**
     * 忘记密码
     *
     * @param call
     */
    public void forgetPassword(Call call) {
        forgetPasswordMvpView.showLoadingView();
        forgetpwdCall = call;
        forgetpwdCall.enqueue(new ResponseCallback<ResultBase<User>>() {

            @Override
            public void onSuccess(Call call, ResultBase<User> userResultBase) {
                forgetPasswordMvpView.onResetSuccess(userResultBase);
            }

            @Override
            public void onError(Call call, ResultBase<User> userResultBase, int errorCode) {
                forgetPasswordMvpView.onResetMsg(userResultBase.getMsg());
            }

            @Override
            public void onFail(Call call, String msg) {
                forgetPasswordMvpView.onResetMsg(msg);
            }

            @Override
            public void onResult() {
                forgetPasswordMvpView.onResetResponse();
            }
        });
    }
}
