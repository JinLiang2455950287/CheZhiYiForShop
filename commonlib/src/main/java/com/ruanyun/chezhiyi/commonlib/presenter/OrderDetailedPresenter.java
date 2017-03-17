package com.ruanyun.chezhiyi.commonlib.presenter;

import com.ruanyun.chezhiyi.commonlib.App;
import com.ruanyun.chezhiyi.commonlib.base.ResultBase;
import com.ruanyun.chezhiyi.commonlib.data.api.ResponseCallback;
import com.ruanyun.chezhiyi.commonlib.model.OrderInfo;
import com.ruanyun.chezhiyi.commonlib.model.RefundInfo;
import com.ruanyun.chezhiyi.commonlib.view.OrderDetailedMvpView;

import java.util.List;

import retrofit2.Call;

/**
 * Description ：获取退款信息
 * <p>
 * Created by ycw on 2016/8/9.
 */
public class OrderDetailedPresenter implements Presenter<OrderDetailedMvpView> {

    OrderDetailedMvpView detailedMvpView;
    Call<ResultBase<List<RefundInfo>>> call;

    @Override
    public void attachView(OrderDetailedMvpView mvpView) {
        detailedMvpView = mvpView;
    }

    @Override
    public void detachView() {
        detailedMvpView = null;
    }

    @Override
    public void onCancel() {
        if (call != null && !call.isCanceled())
            call.cancel();
    }

    /**
     * 获取退款信息
     *
     * @param call
     */
    public void tuikuanList(Call<ResultBase<List<RefundInfo>>> call) {
        if (detailedMvpView == null) return;
//        detailedMvpView.showLoadingView("正在获取退款信息...");
        this.call = call;
        this.call.enqueue(new ResponseCallback<ResultBase<List<RefundInfo>>>() {
            @Override
            public void onSuccess(Call call, ResultBase<List<RefundInfo>> listResultBase) {
                if (detailedMvpView == null) return;
                //detailedMvpView.showTip(listResultBase.getMsg());
                detailedMvpView.tuikuanSuccess(listResultBase.getObj());

            }

            @Override
            public void onError(Call call, ResultBase<List<RefundInfo>> listResultBase, int errorCode) {
                if (detailedMvpView == null) return;
                detailedMvpView.showTip(listResultBase.getMsg());
//                detailedMvpView.tuikuanError(listResultBase, errorCode);
            }

            @Override
            public void onFail(Call call, String msg) {
                if (detailedMvpView == null) return;
                detailedMvpView.showTip(msg);
            }

            @Override
            public void onResult() {
                if (detailedMvpView == null) return;
                detailedMvpView.dismissLoadingView();
            }
        });
    }

    /**
     * 获取订单信息
     *@author zhangsan
     *@date   16/10/20 下午8:01
     */
    public void getOrderDetail(String commonNum){
        if(detailedMvpView!=null)
            detailedMvpView.showLoadingView("正在获取详情信息...");
        App.getInstance().getApiService().getOrderDetail(App.getInstance().getCurrentUserNum(),commonNum)
                .enqueue(new ResponseCallback<ResultBase<OrderInfo>>() {
            @Override
            public void onSuccess(Call call, ResultBase<OrderInfo> orderInfoResultBase) {
                if(detailedMvpView!=null){
                    detailedMvpView.orderDetailResult(orderInfoResultBase.getObj());
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
                if(detailedMvpView!=null)
                    detailedMvpView.dismissLoadingView();
            }
        });
    }
}
