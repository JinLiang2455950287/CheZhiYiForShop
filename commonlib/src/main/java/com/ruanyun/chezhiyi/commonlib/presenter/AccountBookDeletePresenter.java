package com.ruanyun.chezhiyi.commonlib.presenter;

import com.ruanyun.chezhiyi.commonlib.base.ResultBase;
import com.ruanyun.chezhiyi.commonlib.data.api.ResponseCallback;
import com.ruanyun.chezhiyi.commonlib.view.AccountBookDeleteMvpView;
import retrofit2.Call;

/**
 * 记账本删除
 * Created by ycw on 2016/9/9.
 */
public class AccountBookDeletePresenter implements Presenter<AccountBookDeleteMvpView> {

    AccountBookDeleteMvpView accountBookDeleteMvpView;
    Call<ResultBase> call;


    @Override
    public void attachView(AccountBookDeleteMvpView mvpView) {
        accountBookDeleteMvpView = mvpView;
    }

    @Override
    public void detachView() {
        accountBookDeleteMvpView = null;
    }

    @Override
    public void onCancel() {
        if (call != null && !call.isCanceled())
            call.cancel();
    }


    /**
     * 删除记账本
     * @param call
     */
    public void deleteAccountBook(Call<ResultBase> call) {
        this.call = call;
        if (accountBookDeleteMvpView==null)return;
        accountBookDeleteMvpView.showLoadingView();
        this.call.enqueue(new ResponseCallback<ResultBase>() {
            @Override
            public void onSuccess(Call call, ResultBase resultBase) {
                if (accountBookDeleteMvpView==null)return;
                accountBookDeleteMvpView.showDeleteAccountBookTip(resultBase.getMsg());
            }

            @Override
            public void onError(Call call, ResultBase resultBase, int errorCode) {
                if (accountBookDeleteMvpView==null)return;
                accountBookDeleteMvpView.showDeleteAccountBookTip(resultBase.getMsg());
            }

            @Override
            public void onFail(Call call, String msg) {
                if (accountBookDeleteMvpView==null)return;
                accountBookDeleteMvpView.showDeleteAccountBookTip(msg);
            }

            @Override
            public void onResult() {
                if (accountBookDeleteMvpView==null)return;
                accountBookDeleteMvpView.dismissLoadingView();
            }
        });
    }



}
