package com.ruanyun.chezhiyi.commonlib.presenter;

import com.ruanyun.chezhiyi.commonlib.App;
import com.ruanyun.chezhiyi.commonlib.base.ResultBase;
import com.ruanyun.chezhiyi.commonlib.data.api.ResponseCallback;
import com.ruanyun.chezhiyi.commonlib.model.OrderInfo;
import com.ruanyun.chezhiyi.commonlib.model.WeiXinOrderInfo;
import com.ruanyun.chezhiyi.commonlib.view.PayMvpView;

import retrofit2.Call;

/**
 * Description ：支付的
 * <p/>
 * Created by ycw on 2016/10/11.
 */
public class PayPresenter implements Presenter<PayMvpView> {

    PayMvpView payMvpView;
    Call<ResultBase> baseCall;
    Call<ResultBase<WeiXinOrderInfo>> call;
    private Call<ResultBase<String>> aliPayCall;

    @Override
    public void attachView(PayMvpView mvpView) {
        payMvpView = mvpView;
    }

    @Override
    public void detachView() {
        payMvpView=null;
    }

    @Override
    public void onCancel() {
        if (baseCall != null && !baseCall.isCanceled()) {
            baseCall.cancel();
        }
    }

    /**
     * 账户支付
     * @param call
     */
    public void accountPay(Call<ResultBase> call) {
        if (payMvpView == null) return;
        payMvpView.showLoadingView("账户支付中...");
        this.baseCall = call;
        this.baseCall.enqueue(new ResponseCallback<ResultBase>() {
            @Override
            public void onSuccess(Call call, ResultBase resultBase) {
                if (payMvpView == null) return;
                payMvpView.showTip(resultBase.getMsg());
                payMvpView.accountPaySuccess();
            }

            @Override
            public void onError(Call call, ResultBase resultBase, int errorCode) {
                if (payMvpView == null) return;
                payMvpView.showTip(resultBase.getMsg());
            }

            @Override
            public void onFail(Call call, String msg) {

            }

            @Override
            public void onResult() {
                if (payMvpView == null) return;
                payMvpView.dismissLoadingView();
            }
        });
    }


    /**
     * 微信支付
     * @param call
     */
    public void weiXinPay(Call<ResultBase<WeiXinOrderInfo>> call) {
        if (payMvpView == null) return;
        payMvpView.showLoadingView("微信支付中...");
        this.call = call;
        this.call.enqueue(new ResponseCallback<ResultBase<WeiXinOrderInfo>>() {
            @Override
            public void onSuccess(Call call, ResultBase<WeiXinOrderInfo> weiXinOrderInfoResultBase) {
                if (payMvpView == null) return;
                payMvpView.weiXinPaySuccess(weiXinOrderInfoResultBase.getObj());
            }

            @Override
            public void onError(Call call, ResultBase<WeiXinOrderInfo> weiXinOrderInfoResultBase,
                                int errorCode) {
                if (payMvpView == null) return;
                payMvpView.showTip(weiXinOrderInfoResultBase.getMsg());
            }

            @Override
            public void onFail(Call call, String msg) {

            }

            @Override
            public void onResult() {
                if (payMvpView == null) return;
                payMvpView.dismissLoadingView();
            }
        });
    }


    /**
     * 支付宝支付
     * @param call
     */
    public void alipayConfig(Call<ResultBase<String>> call) {
        if (payMvpView == null) return;
        // payMvpView.showLoadingView("支付宝支付中...");
        this.aliPayCall = call;
        this.aliPayCall.enqueue(new ResponseCallback<ResultBase<String>>() {
            @Override
            public void onSuccess(Call call, ResultBase<String> configInfoResultBase) {
                if (payMvpView == null) return;
                payMvpView.alipayConfigSuccess(configInfoResultBase.getObj());
            }

            @Override
            public void onError(Call call, ResultBase<String> configInfoResultBase,
                                int errorCode) {
                if (payMvpView == null) return;
                payMvpView.showTip(configInfoResultBase.getMsg());
                payMvpView.showAlipayConfigError();
            }

            @Override
            public void onFail(Call call, String msg) {
                if (payMvpView == null) return;
                payMvpView.showTip(msg);
                payMvpView.showAlipayConfigError();
            }

            @Override
            public void onResult() {
                if (payMvpView == null) return;
                payMvpView.dismissLoadingView();
            }
        });
    }

    /**
     * 获取订单信息
     *@author zhangsan
     *@date   16/10/20 下午8:01
     */
    public void getOrderDetail(String commonNum) {
        if(payMvpView!=null)
            payMvpView.showLoadingView("正在获取详情信息...");
        App.getInstance().getApiService().getOrderDetail(App.getInstance().getCurrentUserNum(),commonNum).enqueue(new ResponseCallback<ResultBase<OrderInfo>>() {
            @Override
            public void onSuccess(Call call, ResultBase<OrderInfo> orderInfoResultBase) {
                if(payMvpView!=null){
                    payMvpView.orderDetailResult(orderInfoResultBase.getObj());
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
                if(payMvpView!=null)
                    payMvpView.dismissLoadingView();
            }
        });
    }
}
