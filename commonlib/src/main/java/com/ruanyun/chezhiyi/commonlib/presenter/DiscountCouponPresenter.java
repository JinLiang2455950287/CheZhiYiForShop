package com.ruanyun.chezhiyi.commonlib.presenter;

import com.ruanyun.chezhiyi.commonlib.base.ResultBase;
import com.ruanyun.chezhiyi.commonlib.data.api.ResponseCallback;
import com.ruanyun.chezhiyi.commonlib.model.OrderGoodsInfo;
import com.ruanyun.chezhiyi.commonlib.model.ProductInfo;
import com.ruanyun.chezhiyi.commonlib.view.DiscountCouponMvpView;
import retrofit2.Call;

import java.util.List;

/**
 * Description ：优惠券Presenter
 * <p/>
 * Created by hdl on 2016/9/20.
 */
public class DiscountCouponPresenter implements Presenter<DiscountCouponMvpView> {

    DiscountCouponMvpView discountCouponMvpView;
    Call<ResultBase<List<OrderGoodsInfo>>> ClienCall;
    Call<ResultBase<List<ProductInfo>>> shopCall;
    @Override
    public void attachView(DiscountCouponMvpView mvpView) {
        discountCouponMvpView = mvpView;
    }

    @Override
    public void detachView() {
        discountCouponMvpView = null;
    }

    @Override
    public void onCancel() {
        if (ClienCall != null && !ClienCall.isCanceled())
            ClienCall.cancel();
        if (shopCall != null && !shopCall.isCanceled())
            shopCall.cancel();
    }

    /**
     * 司机端优惠券
     * @param call
     */
    public void setDiscountCouponMvpView(Call<ResultBase<List<OrderGoodsInfo>>> call){
        this.ClienCall = call;
        if (discountCouponMvpView==null) return;
        discountCouponMvpView.showLoadingView();
        this.ClienCall.enqueue(new ResponseCallback<ResultBase<List<OrderGoodsInfo>>>() {
            @Override
            public void onSuccess(Call call, ResultBase<List<OrderGoodsInfo>> orderGoodsInfoResultBase) {
                if (discountCouponMvpView==null) return;
                discountCouponMvpView.showDiscountCouponSuccess(orderGoodsInfoResultBase);
            }

            @Override
            public void onError(Call call, ResultBase<List<OrderGoodsInfo>> orderGoodsInfoResultBase, int errorCode) {
                if (discountCouponMvpView==null) return;
                discountCouponMvpView.showDiscountCouponErrer(orderGoodsInfoResultBase.getMsg());
            }

            @Override
            public void onFail(Call call, String msg) {
                if (discountCouponMvpView==null) return;
                discountCouponMvpView.showDiscountCouponErrer(msg);
            }

            @Override
            public void onResult() {
                if (discountCouponMvpView==null) return;
                discountCouponMvpView.dismissLoadingView();
            }
        });
    }

    /**
     * 技师端优惠券
     * @param call
     */
    public void setTechnicianDiscountCouponMvpView(Call<ResultBase<List<ProductInfo>>> call){
        this.shopCall = call;
        if (discountCouponMvpView==null) return;
        discountCouponMvpView.showLoadingView();
        this.shopCall.enqueue(new ResponseCallback<ResultBase<List<ProductInfo>>>() {
            @Override
            public void onSuccess(Call call, ResultBase<List<ProductInfo>> productInfoResultBase) {
                if (discountCouponMvpView==null) return;
                discountCouponMvpView.showTechnicianDiscountCouponSuccess(productInfoResultBase);
            }

            @Override
            public void onError(Call call, ResultBase<List<ProductInfo>> productInfoResultBase, int errorCode) {
                if (discountCouponMvpView==null) return;
                discountCouponMvpView.showDiscountCouponErrer(productInfoResultBase.getMsg());
            }

            @Override
            public void onFail(Call call, String msg) {
                if (discountCouponMvpView==null) return;
                discountCouponMvpView.showDiscountCouponErrer(msg);
            }

            @Override
            public void onResult() {
                if (discountCouponMvpView==null) return;
                discountCouponMvpView.dismissLoadingView();
            }
        });
    }
}
