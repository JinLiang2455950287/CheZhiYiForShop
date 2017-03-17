package com.ruanyun.chezhiyi.commonlib.presenter;

import com.ruanyun.chezhiyi.commonlib.App;
import com.ruanyun.chezhiyi.commonlib.base.ResultBase;
import com.ruanyun.chezhiyi.commonlib.data.api.ResponseCallback;
import com.ruanyun.chezhiyi.commonlib.model.User;
import com.ruanyun.chezhiyi.commonlib.view.AccountMangementMvpView;

import retrofit2.Call;

/**
 * Created by Sxq on 2016/9/9.
 */
public class AccountMangementPresenter implements Presenter<AccountMangementMvpView> {
    private AccountMangementMvpView accountMangementMvpView;
    private Call call;

    @Override
    public void attachView(AccountMangementMvpView mvpView) {
        accountMangementMvpView = mvpView;
    }

    @Override
    public void detachView() {
        accountMangementMvpView = null;
    }

    @Override
    public void onCancel() {
        if (call != null && !call.isCanceled()) {
            call.cancel();
        }
    }

    /**
     * 修改个人信息
     * @param accountCall
     */
    public void accountMangement(Call accountCall) {
        if (accountMangementMvpView == null) return;
        accountMangementMvpView.showLoadingView();
        call = accountCall;
        call.enqueue(new ResponseCallback<ResultBase<User>>() {
            @Override
            public void onSuccess(Call call, ResultBase<User> userResultBase) {
                if (accountMangementMvpView == null) return;
                accountMangementMvpView.accountMangementSuccess(userResultBase.getObj());
            }

            @Override
            public void onError(Call call, ResultBase<User> userResultBase, int errorCode) {
                if (accountMangementMvpView == null) return;
                accountMangementMvpView.showTipMsg(userResultBase.getMsg());
                accountMangementMvpView.accountMangementFail();
            }

            @Override
            public void onFail(Call call, String msg) {
                if (accountMangementMvpView == null) return;
                accountMangementMvpView.showTipMsg(msg);
                accountMangementMvpView.accountMangementFail();
            }

            @Override
            public void onResult() {
                if (accountMangementMvpView == null) return;
                accountMangementMvpView.dismissLoadingView();
            }
        });

    }


    /**
     * 根据用户编号获取用户的信息
     * @param userNum
     */
    public void getUserByNum(final String userNum) {
        Call<ResultBase<User>> call = App.getInstance().getHxApiService().getUserByNum(App.getInstance().getCurrentUserNum(), userNum);
        call.enqueue(new ResponseCallback<ResultBase<User>>() {
            @Override
            public void onSuccess(Call call, ResultBase<User> userResult) {
                if (accountMangementMvpView == null) return;
                accountMangementMvpView.accountMangementSuccess(userResult.getObj());

            }

            @Override
            public void onError(Call call, ResultBase<User> userResult, int errorCode) {
                if (accountMangementMvpView == null) return;
                accountMangementMvpView.accountMangementFail();
            }

            @Override
            public void onFail(Call call, String msg) {
                if (accountMangementMvpView == null) return;
                accountMangementMvpView.accountMangementFail();

            }

            @Override
            public void onResult() {

            }
        });
    }


}
