package com.ruanyun.chezhiyi.commonlib.presenter;

import com.ruanyun.chezhiyi.commonlib.base.ResultBase;
import com.ruanyun.chezhiyi.commonlib.data.api.ResponseCallback;
import com.ruanyun.chezhiyi.commonlib.model.OrderGoodsInfo;
import com.ruanyun.chezhiyi.commonlib.model.WorkBayInfo;
import com.ruanyun.chezhiyi.commonlib.model.params.WorkOrderSubmitInfo;
import com.ruanyun.chezhiyi.commonlib.view.WaitingAreaDetailsMvpView;
import retrofit2.Call;

import java.util.List;

/**
 * 等候区详情视图
 * Created by hdl on 2016/10/09.
 */
public class LeisureStationPresenter implements Presenter<WaitingAreaDetailsMvpView> {

    WaitingAreaDetailsMvpView waitingAreaDetailsMvpView;
    /**获取空闲工位*/
    Call<ResultBase<List<WorkBayInfo>>> stationCall;
    /**获取工单商品*/
    Call<ResultBase<List<OrderGoodsInfo>>> workorderGoodsCall;
    /**接单*/
    Call<ResultBase> call;


    @Override
    public void attachView(WaitingAreaDetailsMvpView mvpView) {
        waitingAreaDetailsMvpView = mvpView;
    }

    @Override
    public void detachView() {
        waitingAreaDetailsMvpView = null;
    }

    @Override
    public void onCancel() {
        if (stationCall != null && !stationCall.isCanceled())
            stationCall.cancel();
        if (workorderGoodsCall != null && !workorderGoodsCall.isCanceled())
            workorderGoodsCall.cancel();
        if (call != null && !call.isCanceled())
            call.cancel();
    }

    /**
     * 获取空闲工位
     * @param call
     */
    public void setLeisureStationMvpView(Call<ResultBase<List<WorkBayInfo>>> call) {
        this.stationCall = call;
        if (waitingAreaDetailsMvpView==null) return;
        //waitingAreaDetailsMvpView.showLoadingView();
        this.stationCall.enqueue(new ResponseCallback<ResultBase<List<WorkBayInfo>>>() {

            @Override
            public void onSuccess(Call call, ResultBase<List<WorkBayInfo>> workBayInfoResultBase) {
                if (waitingAreaDetailsMvpView==null) return;
                waitingAreaDetailsMvpView.showLeisureStationSuccess(workBayInfoResultBase);
            }

            @Override
            public void onError(Call call, ResultBase<List<WorkBayInfo>> workBayInfoResultBase, int
                    errorCode) {
                if (waitingAreaDetailsMvpView==null) return;
                waitingAreaDetailsMvpView.showLeisureStationErrer(workBayInfoResultBase.getMsg());
            }

            @Override
            public void onFail(Call call, String msg) {
                if (waitingAreaDetailsMvpView==null) return;
                waitingAreaDetailsMvpView.showLeisureStationErrer(msg);
            }

            @Override
            public void onResult() {
                if (waitingAreaDetailsMvpView==null) return;
                //waitingAreaDetailsMvpView.dismissLoadingView();
            }

        });
    }

    /**
     * 获取工单商品
     * @param call
     */
    public void setworkorderGoodsMvpView(Call<ResultBase<List<OrderGoodsInfo>>> call) {
        this.workorderGoodsCall = call;
        if (waitingAreaDetailsMvpView==null) return;
        waitingAreaDetailsMvpView.showLoadingView();
        this.workorderGoodsCall.enqueue(new ResponseCallback<ResultBase<List<OrderGoodsInfo>>>() {

            @Override
            public void onSuccess(Call call, ResultBase<List<OrderGoodsInfo>> workBayInfoResultBase) {
                if (waitingAreaDetailsMvpView==null) return;
                waitingAreaDetailsMvpView.showWorkorderGoodsSuccess(workBayInfoResultBase);
            }

            @Override
            public void onError(Call call, ResultBase<List<OrderGoodsInfo>> workBayInfoResultBase, int
                    errorCode) {
                if (waitingAreaDetailsMvpView==null) return;
                waitingAreaDetailsMvpView.showLeisureStationErrer(workBayInfoResultBase.getMsg());
            }

            @Override
            public void onFail(Call call, String msg) {
                if (waitingAreaDetailsMvpView==null) return;
                waitingAreaDetailsMvpView.showLeisureStationErrer(msg);
            }

            @Override
            public void onResult() {
                if (waitingAreaDetailsMvpView==null) return;
                waitingAreaDetailsMvpView.dismissLoadingView();
            }

        });
    }

    /**
     * 接单
     * @param call
     */
    public void setOrderReceivingMvpView(Call<ResultBase> call) {
        this.call = call;
        if (waitingAreaDetailsMvpView==null) return;
        waitingAreaDetailsMvpView.showLoadingView();
        this.call.enqueue(new ResponseCallback<ResultBase>() {

            @Override
            public void onSuccess(Call call, ResultBase resultBase) {
                if (waitingAreaDetailsMvpView==null) return;
                waitingAreaDetailsMvpView.showOrderReceivingSuccess(resultBase);
            }

            @Override
            public void onError(Call call, ResultBase resultBase, int
                    errorCode) {
                if (waitingAreaDetailsMvpView==null) return;
                waitingAreaDetailsMvpView.showLeisureStationErrer(resultBase.getMsg());
            }

            @Override
            public void onFail(Call call, String msg) {
                if (waitingAreaDetailsMvpView==null) return;
                waitingAreaDetailsMvpView.showLeisureStationErrer(msg);
            }

            @Override
            public void onResult() {
                if (waitingAreaDetailsMvpView==null) return;
                waitingAreaDetailsMvpView.dismissLoadingView();
            }

        });
    }



}
