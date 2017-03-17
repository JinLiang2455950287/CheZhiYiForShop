package com.ruanyun.chezhiyi.commonlib.presenter;

import com.ruanyun.chezhiyi.commonlib.base.ResultBase;
import com.ruanyun.chezhiyi.commonlib.data.api.ResponseCallback;
import com.ruanyun.chezhiyi.commonlib.model.AssistUserInfo;
import com.ruanyun.chezhiyi.commonlib.model.User;
import com.ruanyun.chezhiyi.commonlib.model.WorkOrderInfo;
import com.ruanyun.chezhiyi.commonlib.model.params.WorkOrderSubmitInfo;
import com.ruanyun.chezhiyi.commonlib.view.WorkOrderDetailedMvpView;

import java.util.List;

import retrofit2.Call;

/**
 * Description ：
 * <p>
 * Created by ycw on 2016/10/19.
 */
public class WorkOrderDetailedPresenter implements Presenter<WorkOrderDetailedMvpView> {

    WorkOrderDetailedMvpView workOrderDetailedMvpView;
    Call<ResultBase<WorkOrderInfo>> call;
    private Call<ResultBase<List<WorkOrderSubmitInfo.WorkOrderGoods>>> workorderGoodsCall;
    private Call<ResultBase<List<AssistUserInfo>>> assistListCall;
    private Call<ResultBase> updateStatusCall;
    private Call<ResultBase<User>> getUserCall;

    @Override
    public void attachView(WorkOrderDetailedMvpView mvpView) {
        workOrderDetailedMvpView = mvpView;
    }

    @Override
    public void detachView() {
        workOrderDetailedMvpView = null;
    }

    @Override
    public void onCancel() {

    }


    /**
     * 获取工单详情
     * @param call
     */
    public void getWorkorderInfo(Call<ResultBase<WorkOrderInfo>> call){
        if (workOrderDetailedMvpView == null) return;
        workOrderDetailedMvpView.showLoadingView();
        this.call = call;
        this.call.enqueue(new ResponseCallback<ResultBase<WorkOrderInfo>>() {
            @Override
            public void onSuccess(Call call, ResultBase<WorkOrderInfo> workOrderInfoResultBase) {
                if (workOrderDetailedMvpView == null) return;
                workOrderDetailedMvpView.getWorkorderInfoSuccess(workOrderInfoResultBase.getObj());
            }

            @Override
            public void onError(Call call, ResultBase<WorkOrderInfo> workOrderInfoResultBase, int
                    errorCode) {
                if (workOrderDetailedMvpView == null) return;
                //workOrderDetailedMvpView.showTip(workOrderInfoResultBase.getMsg());
                workOrderDetailedMvpView.showError();

            }

            @Override
            public void onFail(Call call, String msg) {
                if (workOrderDetailedMvpView == null) return;
                //workOrderDetailedMvpView.showTip(msg);
                workOrderDetailedMvpView.showError();
            }

            @Override
            public void onResult() {
                if (workOrderDetailedMvpView == null) return;
                workOrderDetailedMvpView.dismissLoadingView();
            }
        });
    }


    /**
     * 获取工单商品
     * @param call
     */
    @Deprecated
    public void getWorkorderGoods(Call<ResultBase<List<WorkOrderSubmitInfo.WorkOrderGoods>>> call) {
        this.workorderGoodsCall = call;
        if (workOrderDetailedMvpView==null) return;
        this.workorderGoodsCall.enqueue(new ResponseCallback<ResultBase<List<WorkOrderSubmitInfo.WorkOrderGoods>>>() {

            @Override
            public void onSuccess(Call call, ResultBase<List<WorkOrderSubmitInfo.WorkOrderGoods>> workBayInfoResultBase) {
                if (workOrderDetailedMvpView==null) return;
                workOrderDetailedMvpView.getWorkorderGoodsSuccess(workBayInfoResultBase);
            }

            @Override
            public void onError(Call call, ResultBase<List<WorkOrderSubmitInfo.WorkOrderGoods>> workBayInfoResultBase, int
                    errorCode) {
                if (workOrderDetailedMvpView==null) return;
                workOrderDetailedMvpView.showTip(workBayInfoResultBase.getMsg());
            }

            @Override
            public void onFail(Call call, String msg) {
                if (workOrderDetailedMvpView==null) return;
                workOrderDetailedMvpView.showTip(msg);
            }

            @Override
            public void onResult() {
            }

        });
    }

    /**
     * 获取工单协助技师
     * @param call
     */
    @Deprecated
    public void getAssistList(Call<ResultBase<List<AssistUserInfo>>> call) {
        this.assistListCall = call;
        if (workOrderDetailedMvpView==null) return;
        this.assistListCall.enqueue(new ResponseCallback<ResultBase<List<AssistUserInfo>>>() {
            @Override
            public void onSuccess(Call call, ResultBase<List<AssistUserInfo>> assistUserInfoResultBase) {
                if (workOrderDetailedMvpView==null) return;
                workOrderDetailedMvpView.getAssistListSuccess(assistUserInfoResultBase.getObj());
            }

            @Override
            public void onError(Call call, ResultBase<List<AssistUserInfo>> assistUserInfoResultBase,
                                int errorCode) {

            }

            @Override
            public void onFail(Call call, String msg) {

            }

            @Override
            public void onResult() {

            }
        });
    }

    /**
     * 修改工单状态
     *
     * @param updateStatusCall
     */
    public void updateStatus(Call<ResultBase> updateStatusCall) {
        this.updateStatusCall = updateStatusCall;
        if (workOrderDetailedMvpView==null) return;
        workOrderDetailedMvpView.showLoadingView();
        this.updateStatusCall.enqueue(new ResponseCallback<ResultBase>() {
            @Override
            public void onSuccess(Call call, ResultBase resultBase) {
                if (workOrderDetailedMvpView==null) return;
//                workOrderDetailedMvpView.showTip(resultBase.getMsg());
                workOrderDetailedMvpView.updateStatusSuccess();

            }

            @Override
            public void onError(Call call, ResultBase resultBase, int errorCode) {

            }

            @Override
            public void onFail(Call call, String msg) {

            }

            @Override
            public void onResult() {
                if (workOrderDetailedMvpView==null) return;
                workOrderDetailedMvpView.dismissLoadingView();
            }
        });
    }


    /**
     * 获取用户的朋友关系
     * @param call
     */
    public void getFriendShipInfo(Call<ResultBase<User>> call) {
        if (workOrderDetailedMvpView == null) return;
        workOrderDetailedMvpView.showLoadingView();
        this.getUserCall = call;
        this.getUserCall.enqueue(new ResponseCallback<ResultBase<User>>() {
            @Override
            public void onSuccess(Call call, ResultBase<User> userResultBase) {
                if (workOrderDetailedMvpView == null) return;
                workOrderDetailedMvpView.getFriendShipInfoSuccess(userResultBase.getObj());
            }

            @Override
            public void onError(Call call, ResultBase<User> userResultBase, int errorCode) {
                if (workOrderDetailedMvpView == null) return;
                workOrderDetailedMvpView.showTip(userResultBase.getMsg());
            }

            @Override
            public void onFail(Call call, String msg) {
                if (workOrderDetailedMvpView == null) return;
                workOrderDetailedMvpView.showTip(msg);
            }

            @Override
            public void onResult() {
                if (workOrderDetailedMvpView == null) return;
                workOrderDetailedMvpView.dismissLoadingView();
            }
        });
    }

}
