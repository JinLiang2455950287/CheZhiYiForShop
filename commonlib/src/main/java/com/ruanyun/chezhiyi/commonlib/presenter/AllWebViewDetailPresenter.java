package com.ruanyun.chezhiyi.commonlib.presenter;

import com.ruanyun.chezhiyi.commonlib.base.ResultBase;
import com.ruanyun.chezhiyi.commonlib.data.api.ResponseCallback;
import com.ruanyun.chezhiyi.commonlib.model.ActivityInfo;
import com.ruanyun.chezhiyi.commonlib.model.OrderInfo;
import com.ruanyun.chezhiyi.commonlib.view.WebMvpView;

import retrofit2.Call;

/**
 * web详情的  present
 * Created by ycw on 2016/9/9.
 */
public class AllWebViewDetailPresenter implements Presenter<WebMvpView> {

    WebMvpView webMvpView;

    /**
     * 秒杀详情
     **/
//    Call<ResultBase<SeckillDetailInfo>> seckCall;
    /**
     * 众筹详情
     **/
//    Call<ResultBase<CrowdFundingInfo>> crowdCall;
    /**
     * 促销详情promotion
     */
//    Call<ResultBase<PromotionInfo>> promotionCall;
    /**
     * 产品详情
     **/
//    Call<ResultBase<ProductInfo>> productCall;

    /**
     * 获取活动详情 判断是否已报名过
     */
    Call<ResultBase<ActivityInfo>> activityCall;

    /**
     * 取消活动报名
     */
    Call<ResultBase> delActivityCall;

    /**
     * 积分兑换
     */
    Call<ResultBase<OrderInfo>> exchangeCall;

    @Override
    public void attachView(WebMvpView mvpView) {
        webMvpView = mvpView;
    }

    @Override
    public void detachView() {
        webMvpView = null;
    }

    @Override
    public void onCancel() {
        if (activityCall != null && !activityCall.isCanceled())
            activityCall.cancel();
    }


    /**
     * 获取秒杀的  商品 详情
     *
     * @param seckCall
     */
//    public void seckillInfo(Call<ResultBase<SeckillDetailInfo>> seckCall) {
//        this.seckCall = seckCall;
//        if (webMvpView == null) return;
//        webMvpView.showLoadingView();
//        this.seckCall.enqueue(new ResponseCallback<ResultBase<SeckillDetailInfo>>() {
//            @Override
//            public void onSuccess(Call call, ResultBase<SeckillDetailInfo> seckillDetailInfoResultBase) {
//                if (webMvpView == null) return;
////                webMvpView.showTip(seckillDetailInfoResultBase.getMsg());
//                webMvpView.getSeckillInfoSuccess(seckillDetailInfoResultBase.getObj());
//
//            }
//
//            @Override
//            public void onError(Call call, ResultBase<SeckillDetailInfo> seckillDetailInfoResultBase, int errorCode) {
//                if (webMvpView == null) return;
//                webMvpView.showTip(seckillDetailInfoResultBase.getMsg());
//
//            }
//
//            @Override
//            public void onFail(Call call, String msg) {
//                if (webMvpView == null) return;
//                webMvpView.showTip(msg);
//
//            }
//
//            @Override
//            public void onResult() {
//                if (webMvpView == null) return;
//                webMvpView.dismissLoadingView();
//
//            }
//        });
//    }


    /**
     * 众筹详情 商品
     *
     * @param crowdCall
     */
//    public void crowdInfo(Call<ResultBase<CrowdFundingInfo>> crowdCall) {
//        this.crowdCall = crowdCall;
//        if (webMvpView == null) return;
//        webMvpView.showLoadingView();
//        this.crowdCall.enqueue(new ResponseCallback<ResultBase<CrowdFundingInfo>>() {
//            @Override
//            public void onSuccess(Call call, ResultBase<CrowdFundingInfo> crowdFundingInfoResultBase) {
//                if (webMvpView == null) return;
////                webMvpView.showTip(crowdFundingInfoResultBase.getMsg());
//                webMvpView.getCrowdInfoSuccess(crowdFundingInfoResultBase.getObj());
//
//            }
//
//            @Override
//            public void onError(Call call, ResultBase<CrowdFundingInfo> crowdFundingInfoResultBase, int errorCode) {
//                if (webMvpView == null) return;
//                webMvpView.showTip(crowdFundingInfoResultBase.getMsg());
//
//            }
//
//            @Override
//            public void onFail(Call call, String msg) {
//                if (webMvpView == null) return;
//                webMvpView.showTip(msg);
//
//            }
//
//            @Override
//            public void onResult() {
//                if (webMvpView == null) return;
//                webMvpView.dismissLoadingView();
//
//            }
//        });
//    }

    /**
     * 促销详情 商品
     *
     * @param promotionCall
     */
//    public void promotionInfo(Call<ResultBase<PromotionInfo>> promotionCall) {
//        this.promotionCall = promotionCall;
//        if (webMvpView == null) return;
//        webMvpView.showLoadingView();
//        this.promotionCall.enqueue(new ResponseCallback<ResultBase<PromotionInfo>>() {
//            @Override
//            public void onSuccess(Call call, ResultBase<PromotionInfo> promotionInfoResultBase) {
//                if (webMvpView == null) return;
////                webMvpView.showTip(promotionInfoResultBase.getMsg());
//                webMvpView.getPromotionInfoSuccess(promotionInfoResultBase.getObj());
//            }
//
//            @Override
//            public void onError(Call call, ResultBase<PromotionInfo> promotionInfoResultBase, int errorCode) {
//                if (webMvpView == null) return;
//                webMvpView.showTip(promotionInfoResultBase.getMsg());
//            }
//
//            @Override
//            public void onFail(Call call, String msg) {
//                if (webMvpView == null) return;
//                webMvpView.showTip(msg);
//            }
//
//            @Override
//            public void onResult() {
//                if (webMvpView == null) return;
//                webMvpView.dismissLoadingView();
//            }
//        });
//    }


    /**
     * 1.19.3	产品详细 商品
     *
     * @param productCall
     */
//    public void productInfo(Call<ResultBase<ProductInfo>> productCall) {
//        this.productCall = productCall;
//        if (webMvpView == null) return;
//        webMvpView.showLoadingView();
//        this.productCall.enqueue(new ResponseCallback<ResultBase<ProductInfo>>() {
//            @Override
//            public void onSuccess(Call call, ResultBase<ProductInfo> productInfoResultBase) {
//                if (webMvpView == null) return;
////                webMvpView.showTip(productInfoResultBase.getMsg());
//                webMvpView.getProductInfoSuccess(productInfoResultBase.getObj());
//            }
//
//            @Override
//            public void onError(Call call, ResultBase<ProductInfo> productInfoResultBase, int errorCode) {
//                if (webMvpView == null) return;
//                webMvpView.showTip(productInfoResultBase.getMsg());
//            }
//
//            @Override
//            public void onFail(Call call, String msg) {
//                if (webMvpView == null) return;
//                webMvpView.showTip(msg);
//            }
//
//            @Override
//            public void onResult() {
//                if (webMvpView == null) return;
//                webMvpView.dismissLoadingView();
//            }
//        });
//    }


    /**
     * 获取是否报名
     * @param activityCall
     */
    public void getActiveAddInfo(Call<ResultBase<ActivityInfo>> activityCall) {
        this.activityCall = activityCall;
        if (webMvpView == null) return;
        webMvpView.showLoadingView();
        this.activityCall.enqueue(new ResponseCallback<ResultBase<ActivityInfo>>() {
            @Override
            public void onSuccess(Call call, ResultBase<ActivityInfo> activityListInfoResultBase) {
                if (webMvpView == null) return;
//                webMvpView.showTip(activityListInfoResultBase.getMsg());
                webMvpView.getActivitySuccess(activityListInfoResultBase);
            }

            @Override
            public void onError(Call call, ResultBase<ActivityInfo> activityListInfoResultBase, int errorCode) {
                if (webMvpView == null) return;
                webMvpView.showTip(activityListInfoResultBase.getMsg());
            }

            @Override
            public void onFail(Call call, String msg) {

            }

            @Override
            public void onResult() {
                if (webMvpView == null) return;
                webMvpView.dismissLoadingView();
            }
        });
    }


    /**
     * 取消活动报名
     * @param delActivityCall
     */
    public void delActivity(Call<ResultBase> delActivityCall) {
        this.delActivityCall = delActivityCall;
        if (webMvpView == null) return;
        webMvpView.showLoadingView();
        this.delActivityCall.enqueue(new ResponseCallback<ResultBase>() {
            @Override
            public void onSuccess(Call call, ResultBase resultBase) {
                if (webMvpView == null) return;
                webMvpView.showTip(resultBase.getMsg());
                webMvpView.delActivitySuccess();
            }

            @Override
            public void onError(Call call, ResultBase resultBase, int errorCode) {
                if (webMvpView == null) return;
                webMvpView.showTip(resultBase.getMsg());
            }

            @Override
            public void onFail(Call call, String msg) {
                if (webMvpView == null) return;
                webMvpView.showTip(msg);
            }

            @Override
            public void onResult() {
                if (webMvpView == null) return;
                webMvpView.dismissLoadingView();
            }
        });
    }

    /**
     * 纯积分兑换
     * @param exchangeCall
     */
    public void exchangeOnlyScore(Call<ResultBase<OrderInfo>> exchangeCall) {
        this.exchangeCall = exchangeCall;
        if (webMvpView == null) return;
        webMvpView.showLoadingView();
        this.exchangeCall.enqueue(new ResponseCallback<ResultBase<OrderInfo>>() {
            @Override
            public void onSuccess(Call call, ResultBase<OrderInfo> resultBase) {
                if (webMvpView == null) return;
                webMvpView.showTip(resultBase.getMsg());
                webMvpView.exchangeSuccess(resultBase.getObj());
            }

            @Override
            public void onError(Call call, ResultBase<OrderInfo> resultBase, int errorCode) {
                if (webMvpView == null) return;
                webMvpView.showTip(resultBase.getMsg());
            }

            @Override
            public void onFail(Call call, String msg) {
                if (webMvpView == null) return;
                webMvpView.showTip(msg);
            }

            @Override
            public void onResult() {
                if (webMvpView == null) return;
                webMvpView.dismissLoadingView();
            }
        });
    }



}
