package com.ruanyun.chezhiyi.commonlib.presenter;

import com.ruanyun.chezhiyi.commonlib.App;
import com.ruanyun.chezhiyi.commonlib.base.ResultBase;
import com.ruanyun.chezhiyi.commonlib.data.api.ResponseCallback;
import com.ruanyun.chezhiyi.commonlib.model.AgreementContentInfo;
import com.ruanyun.chezhiyi.commonlib.view.RegisterMvpView;

import retrofit2.Call;

/**
 * Description ：
 * <p>
 * Created by ycw on 2016/8/9.
 */
public class RegisterPresenter implements Presenter<RegisterMvpView> {

    RegisterMvpView registerMvpView;
    Call registerCall;

    @Override
    public void attachView(RegisterMvpView mvpView) {
        registerMvpView = mvpView;
    }

    @Override
    public void detachView() {
        registerMvpView = null;
    }

    @Override
    public void onCancel() {
        if (registerCall != null && !registerCall.isCanceled())
            registerCall.cancel();
    }

    /**
     * 用户注册接口
     *
     * @param registerCall
     */
    public void userRegister(Call registerCall) {
        registerMvpView.onRegisterShowLoading();
        this.registerCall = registerCall;
        this.registerCall.enqueue(
                new ResponseCallback<ResultBase>() {
                    @Override
                    public void onSuccess(Call call, ResultBase resultBase) {
                        registerMvpView.onRegisterSuccess(resultBase);
                    }

                    @Override
                    public void onError(Call call, ResultBase resultBase, int errorCode) {
                        registerMvpView.onRegisterError(resultBase, errorCode);
                    }

                    @Override
                    public void onFail(Call call, String msg) {
                        registerMvpView.onRegisterFail(msg);
                    }

                    @Override
                    public void onResult() {
                        registerMvpView.onRegisterResponse();
                    }
                }
        );
    }



    /**
     * 用户协议
     *
     */
    public void agreement() {
        registerMvpView.onRegisterShowLoading();
        App.getInstance().getApiService().agreement("agreement", "1").enqueue(
                new ResponseCallback<ResultBase<AgreementContentInfo>>() {
                    @Override
                    public void onSuccess(Call call, ResultBase<AgreementContentInfo> resultBase) {
                        registerMvpView.onAgreementSuccess(resultBase.getObj());
                    }

                    @Override
                    public void onError(Call call, ResultBase<AgreementContentInfo> resultBase, int errorCode) {
//                        registerMvpView.onRegisterError(resultBase, errorCode);
                    }

                    @Override
                    public void onFail(Call call, String msg) {
//                        registerMvpView.onRegisterFail(msg);
                    }

                    @Override
                    public void onResult() {
                        registerMvpView.onRegisterResponse();
                    }
                }
        );
    }
}
