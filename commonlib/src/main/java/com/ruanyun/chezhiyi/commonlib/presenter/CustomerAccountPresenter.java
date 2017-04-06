package com.ruanyun.chezhiyi.commonlib.presenter;

import com.ruanyun.chezhiyi.commonlib.base.ResultBase;
import com.ruanyun.chezhiyi.commonlib.data.api.ResponseCallback;
import com.ruanyun.chezhiyi.commonlib.model.CustomerAccountModel;
import com.ruanyun.chezhiyi.commonlib.view.CustomerAccountView;

import retrofit2.Call;

/**
 * Created by czy on 2017/4/6.
 * 获取会员余
 */

public class CustomerAccountPresenter implements Presenter<CustomerAccountView> {
    private CustomerAccountView customerAccountView;
    private Call<ResultBase<CustomerAccountModel>> call;

    @Override
    public void attachView(CustomerAccountView mvpView) {
        this.customerAccountView = mvpView;
    }

    @Override
    public void detachView() {
        customerAccountView = null;
    }

    public void getCustomerAccountData(Call<ResultBase<CustomerAccountModel>> call) {
        this.call = call;
        call.enqueue(new ResponseCallback<ResultBase<CustomerAccountModel>>() {

            @Override
            public void onSuccess(Call call, ResultBase<CustomerAccountModel> customerAccountModelResultBase) {
                customerAccountView.getRemainSuccess(customerAccountModelResultBase.getObj());
            }

            @Override
            public void onError(Call call, ResultBase<CustomerAccountModel> customerAccountModelResultBase, int errorCode) {

            }

            @Override
            public void onFail(Call call, String msg) {

            }

            @Override
            public void onResult() {

            }
        });
    }


    @Override
    public void onCancel() {
        if (call != null && !call.isCanceled())
            call.cancel();
    }
}
