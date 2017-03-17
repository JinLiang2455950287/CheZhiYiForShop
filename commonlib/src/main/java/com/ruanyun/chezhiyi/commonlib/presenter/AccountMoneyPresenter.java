package com.ruanyun.chezhiyi.commonlib.presenter;

import com.ruanyun.chezhiyi.commonlib.base.ResultBase;
import com.ruanyun.chezhiyi.commonlib.data.api.ResponseCallback;
import com.ruanyun.chezhiyi.commonlib.model.AccountMoneyInfo;
import com.ruanyun.chezhiyi.commonlib.view.AccountMoneyMvpView;

import retrofit2.Call;

/**
 * Description ：获取账户信息
 * <p>
 * Created by ycw on 2016/10/17.
 */
public class AccountMoneyPresenter implements Presenter<AccountMoneyMvpView> {

    AccountMoneyMvpView accountMoneyMvpView;
    Call<ResultBase<AccountMoneyInfo>> call;

    @Override
    public void attachView(AccountMoneyMvpView mvpView) {
        accountMoneyMvpView = mvpView;
    }

    @Override
    public void detachView() {
        accountMoneyMvpView = null;
    }

    @Override
    public void onCancel() {

    }

    /**
     * 获取用户账户信息
     * @param call
     */
    public void getAccountMoney(Call<ResultBase<AccountMoneyInfo>> call){
        this.call = call;
        this.call.enqueue(new ResponseCallback<ResultBase<AccountMoneyInfo>>() {
            @Override
            public void onSuccess(Call call, ResultBase<AccountMoneyInfo> accountMoneyInfoResultBase) {
                if (accountMoneyMvpView == null) return;
                accountMoneyMvpView.getAccountMoneySuccess(accountMoneyInfoResultBase.getObj());
            }

            @Override
            public void onError(Call call, ResultBase<AccountMoneyInfo> accountMoneyInfoResultBase, int errorCode) {

            }

            @Override
            public void onFail(Call call, String msg) {

            }

            @Override
            public void onResult() {

            }
        });
    }
}
