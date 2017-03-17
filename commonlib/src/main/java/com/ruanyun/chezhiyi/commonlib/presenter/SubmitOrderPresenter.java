package com.ruanyun.chezhiyi.commonlib.presenter;

import com.ruanyun.chezhiyi.commonlib.base.PageInfoBase;
import com.ruanyun.chezhiyi.commonlib.base.ResultBase;
import com.ruanyun.chezhiyi.commonlib.data.api.ResponseCallback;
import com.ruanyun.chezhiyi.commonlib.model.OrderGoodsInfo;
import com.ruanyun.chezhiyi.commonlib.model.OrderInfo;
import com.ruanyun.chezhiyi.commonlib.view.SubmitOrderMvpView;

import java.util.List;

import retrofit2.Call;

/**
 * Description ： 下单页面的  preserter
 * <p/>
 * Created by ycw on 2016/9/20.
 */
public class SubmitOrderPresenter implements Presenter<SubmitOrderMvpView> {

    SubmitOrderMvpView mvpView;
    Call<ResultBase<OrderInfo>> makeCall;
    Call<ResultBase<List<OrderGoodsInfo>>> ClienCall;

    @Override
    public void attachView(SubmitOrderMvpView mvpView) {
        this.mvpView = mvpView;
    }

    @Override
    public void detachView() {
        this.mvpView = null;
    }

    @Override
    public void onCancel() {

    }

    /**
     * 下单
     * @param call
     */
    public void makeOrder(Call<ResultBase<OrderInfo>> call) {
        this.makeCall = call;
        if (mvpView == null) return;
        mvpView.showLoadingView();
        this.makeCall.enqueue(new ResponseCallback<ResultBase<OrderInfo>>() {
            @Override
            public void onSuccess(Call call, ResultBase<OrderInfo> orderInfoResultBase) {
                if (mvpView == null) return;
                //mvpView.showTip(orderInfoResultBase.getMsg());
                mvpView.makeOrderSuccess(orderInfoResultBase.getObj());
            }

            @Override
            public void onError(Call call, ResultBase<OrderInfo> orderInfoResultBase, int errorCode) {
                if (mvpView == null) return;
                mvpView.showTip(orderInfoResultBase.getMsg());
                mvpView.makeOrderError();
            }

            @Override
            public void onFail(Call call, String msg) {
                if (mvpView == null) return;
                mvpView.showTip(msg);
            }

            @Override
            public void onResult() {
                if (mvpView == null) return;
                mvpView.dismissLoadingView();
            }
        });
    }


    /**
     * 司机端优惠券
     * @param call
     */
    public void getDiscountCoupon(Call<ResultBase<List<OrderGoodsInfo>>> call){
        this.ClienCall = call;
        this.ClienCall.enqueue(new ResponseCallback<ResultBase<List<OrderGoodsInfo>>>() {
            @Override
            public void onSuccess(Call call, ResultBase<List<OrderGoodsInfo>> orderGoodsInfoResultBase) {
                if (mvpView==null) return;
                mvpView.showDiscountCouponSuccess(orderGoodsInfoResultBase);
            }

            @Override
            public void onError(Call call, ResultBase<List<OrderGoodsInfo>> orderGoodsInfoResultBase, int errorCode) {
                if (mvpView==null) return;
            }

            @Override
            public void onFail(Call call, String msg) {
                if (mvpView==null) return;
            }

            @Override
            public void onResult() {
            }
        });
    }
}
