package com.ruanyun.chezhiyi.commonlib.presenter;

import com.ruanyun.chezhiyi.commonlib.App;
import com.ruanyun.chezhiyi.commonlib.base.ResultBase;
import com.ruanyun.chezhiyi.commonlib.data.api.ResponseCallback;
import com.ruanyun.chezhiyi.commonlib.model.AgreementContentInfo;
import com.ruanyun.chezhiyi.commonlib.model.User;
import com.ruanyun.chezhiyi.commonlib.view.VerifyCodyMvpView;

import retrofit2.Call;
import retrofit2.Response;

/**获取手机验证码
 * Created by Sxq on 2016/9/21.
 */
public class VerifyCodyPresenter implements Presenter<VerifyCodyMvpView> {
    private VerifyCodyMvpView verifyCodyMvpView;
    private Call getVerifyCodyCall;
    private Call<ResultBase<User>> addThirdCall;
    private Call<ResultBase<Object>> validateCall;

    @Override
    public void attachView(VerifyCodyMvpView mvpView) {
        verifyCodyMvpView = mvpView;
    }

    @Override
    public void detachView() {
        verifyCodyMvpView = null;
    }

    @Override
    public void onCancel() {
        if (getVerifyCodyCall != null && !getVerifyCodyCall.isCanceled()) {
            getVerifyCodyCall.cancel();
        }
    }

    /**
     * 获取手机验证码
     *
     * @param call
     */
    public String Cookie;
    public void getVerifyCody(Call call) {
        this.getVerifyCodyCall = call;
        this.getVerifyCodyCall.enqueue(new ResponseCallback<ResultBase>() {

            @Override
            public void onResponseResult(Response<ResultBase> response) {
                Cookie = response.headers().value(1);
            }

            @Override
            public void onSuccess(Call call, ResultBase stringResultBase) {
                if (verifyCodyMvpView == null) return;
                verifyCodyMvpView.verifyCodySuccess();
                verifyCodyMvpView.showVerifyCodyMsg(stringResultBase.getMsg());
            }

            @Override
            public void onError(Call call, ResultBase stringResultBase, int errorCode) {
                if (verifyCodyMvpView == null) return;
                verifyCodyMvpView.showVerifyCodyMsg(stringResultBase.getMsg());
            }

            @Override
            public void onFail(Call call, String msg) {
                if (verifyCodyMvpView == null) return;
                verifyCodyMvpView.showVerifyCodyMsg(msg);
            }

            @Override
            public void onResult() {
                verifyCodyMvpView.verifyCodyResult();
            }
        });

    }

    /**
     * 1.8.6	第三方绑定或注册用户
     * @param call
     */
    public void addThird(Call<ResultBase<User>> call) {
        if (verifyCodyMvpView == null) return;
        verifyCodyMvpView.showLoadingView();
        this.addThirdCall = call;
        this.addThirdCall.enqueue(new ResponseCallback<ResultBase<User>>() {
            @Override
            public void onSuccess(Call call, ResultBase<User> userResultBase) {
                if (verifyCodyMvpView == null) return;
                verifyCodyMvpView.addThirdSuccess(userResultBase.getObj());
            }

            @Override
            public void onError(Call call, ResultBase<User> userResultBase, int errorCode) {
                if (verifyCodyMvpView == null) return;
                verifyCodyMvpView.showVerifyCodyMsg(userResultBase.getMsg());

            }

            @Override
            public void onFail(Call call, String msg) {

            }

            @Override
            public void onResult() {
                if (verifyCodyMvpView == null) return;
                verifyCodyMvpView.dismissLoadingView();
            }
        });
    }
    /**
     * 1.8.6	校验验证码
     * @param call
     */
    public void validate(Call<ResultBase<Object>> call) {
        if (verifyCodyMvpView == null) return;
        this.validateCall = call;
        this.validateCall.enqueue(new ResponseCallback<ResultBase<Object>>() {
            @Override
            public void onSuccess(Call call, ResultBase<Object> stringResultBase) {
                if (verifyCodyMvpView == null) return;
                verifyCodyMvpView.validateSuccess();
            }

            @Override
            public void onError(Call call, ResultBase<Object> userResultBase, int errorCode) {
                if (verifyCodyMvpView == null) return;
                verifyCodyMvpView.showVerifyCodyMsg(userResultBase.getMsg());
            }

            @Override
            public void onFail(Call call, String msg) {
                if (verifyCodyMvpView == null) return;
                verifyCodyMvpView.showVerifyCodyMsg(msg);
            }

            @Override
            public void onResult() {

            }
        });
    }

    /**
     * 用户协议
     *
     */
    public void agreement() {
        verifyCodyMvpView.showLoadingView();
        App.getInstance().getApiService().agreement("agreement", "1").enqueue(
                new ResponseCallback<ResultBase<AgreementContentInfo>>() {
                    @Override
                    public void onSuccess(Call call, ResultBase<AgreementContentInfo> resultBase) {
                        verifyCodyMvpView.onAgreementSuccess(resultBase.getObj());
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
                        verifyCodyMvpView.dismissLoadingView();
                    }
                }
        );
    }
}
