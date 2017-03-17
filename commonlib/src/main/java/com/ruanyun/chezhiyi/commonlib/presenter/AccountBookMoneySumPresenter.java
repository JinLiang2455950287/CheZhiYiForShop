package com.ruanyun.chezhiyi.commonlib.presenter;

import com.ruanyun.chezhiyi.commonlib.App;
import com.ruanyun.chezhiyi.commonlib.base.ResultBase;
import com.ruanyun.chezhiyi.commonlib.data.api.ResponseCallback;
import com.ruanyun.chezhiyi.commonlib.model.OrderInfo;
import com.ruanyun.chezhiyi.commonlib.view.AccountBookMoneySumMvpView;
import retrofit2.Call;

import java.math.BigDecimal;

/**
 * 记账本金额
 * Created by ycw on 2016/9/9.
 */
public class AccountBookMoneySumPresenter implements Presenter<AccountBookMoneySumMvpView> {

    AccountBookMoneySumMvpView rememberingBookMvpView;
    Call<ResultBase<BigDecimal>> call;


    @Override
    public void attachView(AccountBookMoneySumMvpView mvpView) {
        rememberingBookMvpView = mvpView;
    }

    @Override
    public void detachView() {
        rememberingBookMvpView = null;
    }

    @Override
    public void onCancel() {
        if (call != null && !call.isCanceled())
            call.cancel();
    }
   /**
     * 获取订单信息
     *@author zhangsan
     *@date   16/10/20 下午8:01
     */
    public void getOrderDetail(String commonNum){
        if(rememberingBookMvpView!=null)
            rememberingBookMvpView.showLoadingView("正在获取详情信息...");
        App.getInstance().getApiService().getOrderDetail(App.getInstance().getCurrentUserNum(),commonNum).enqueue(new ResponseCallback<ResultBase<OrderInfo>>() {
            @Override
            public void onSuccess(Call call, ResultBase<OrderInfo> orderInfoResultBase) {
                if(rememberingBookMvpView!=null){
                    rememberingBookMvpView.orderDetailResult(orderInfoResultBase.getObj());
                }
            }

            @Override
            public void onError(Call call, ResultBase<OrderInfo> orderInfoResultBase, int errorCode) {

            }

            @Override
            public void onFail(Call call, String msg) {

            }

            @Override
            public void onResult() {
                if(rememberingBookMvpView!=null)
                    rememberingBookMvpView.dismissLoadingView();
            }
        });
    }

    /**
     * 记账本金额
     * @param call
     */
    public void getAccountBookMoneySum(Call<ResultBase<BigDecimal>> call) {
        this.call = call;
//        if (rememberingBookMvpView==null) return;
//        rememberingBookMvpView.showLoadingView("正在获取详情信息...");
        this.call.enqueue(new ResponseCallback<ResultBase<BigDecimal>>() {

            @Override
            public void onSuccess(Call call, ResultBase<BigDecimal> pageInfoBaseResultBase) {
                if (rememberingBookMvpView==null) return;
                rememberingBookMvpView.showAccountBookMoneySumSuccessTip(pageInfoBaseResultBase.getObj());
            }

            @Override
            public void onError(Call call, ResultBase<BigDecimal> pageInfoBaseResultBase, int
                    errorCode) {
                if (rememberingBookMvpView==null) return;
                rememberingBookMvpView.showAccountBookMoneySumFail();
            }

            @Override
            public void onFail(Call call, String msg) {
                if (rememberingBookMvpView==null) return;
                rememberingBookMvpView.showAccountBookMoneySumFail();
            }

            @Override
            public void onResult() {
//                if (rememberingBookMvpView==null) return;
//                rememberingBookMvpView.dismissLoadingView();
            }

        });
    }



}
