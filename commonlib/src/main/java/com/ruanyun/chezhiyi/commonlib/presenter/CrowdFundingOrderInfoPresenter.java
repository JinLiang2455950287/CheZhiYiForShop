package com.ruanyun.chezhiyi.commonlib.presenter;

import com.ruanyun.chezhiyi.commonlib.App;
import com.ruanyun.chezhiyi.commonlib.base.ResultBase;
import com.ruanyun.chezhiyi.commonlib.data.api.ResponseCallback;
import com.ruanyun.chezhiyi.commonlib.model.OrderInfo;
import com.ruanyun.chezhiyi.commonlib.view.CFOrderInfoMvpView;

import retrofit2.Call;

/**
 * Description ：
 * <p/>
 * Created by ycw on 2016/10/29.
 */
public class CrowdFundingOrderInfoPresenter implements Presenter<CFOrderInfoMvpView> {

    CFOrderInfoMvpView cfOrderInfoMvpView;


    @Override
    public void attachView(CFOrderInfoMvpView mvpView) {
        cfOrderInfoMvpView = mvpView;
    }

    @Override
    public void detachView() {
        cfOrderInfoMvpView = null;
    }

    @Override
    public void onCancel() {

    }




    /**
     * 获取订单信息
     *@author zhangsan
     *@date   16/10/20 下午8:01
     */
    public void getOrderDetail(String commonNum){
        if(cfOrderInfoMvpView!=null)
            cfOrderInfoMvpView.showLoadingView("正在获取详情信息...");
        App.getInstance().getApiService().getOrderDetail(App.getInstance().getCurrentUserNum(),commonNum).enqueue(new ResponseCallback<ResultBase<OrderInfo>>() {
            @Override
            public void onSuccess(Call call, ResultBase<OrderInfo> orderInfoResultBase) {
                if(cfOrderInfoMvpView!=null){
                    cfOrderInfoMvpView.orderDetailResult(orderInfoResultBase.getObj());
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
                if(cfOrderInfoMvpView!=null)
                    cfOrderInfoMvpView.dismissLoadingView();
            }
        });
    }


}
