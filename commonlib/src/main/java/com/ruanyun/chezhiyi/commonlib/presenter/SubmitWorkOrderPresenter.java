package com.ruanyun.chezhiyi.commonlib.presenter;

import com.ruanyun.chezhiyi.commonlib.base.ResultBase;
import com.ruanyun.chezhiyi.commonlib.data.api.ResponseCallback;
import com.ruanyun.chezhiyi.commonlib.model.OrderInfo;
import com.ruanyun.chezhiyi.commonlib.view.SubmitWorkOrderMvpView;

import retrofit2.Call;

/**
 * Description ：
 * <p/>
 * Created by ycw on 2016/11/2.
 */

public class SubmitWorkOrderPresenter implements Presenter<SubmitWorkOrderMvpView> {

    SubmitWorkOrderMvpView submitWorkOrderMvpView;
    Call<ResultBase<OrderInfo>> call;


    @Override
    public void attachView(SubmitWorkOrderMvpView mvpView) {
        this.submitWorkOrderMvpView = mvpView;
    }

    @Override
    public void detachView() {
        this.submitWorkOrderMvpView = null;
    }

    @Override
    public void onCancel() {

    }


    /**
     * 工单结算
     * @param baseCall
     */
    public void addJieSuan(Call<ResultBase<OrderInfo>> baseCall){
        if (submitWorkOrderMvpView == null) return;
        submitWorkOrderMvpView.showLoadingView();
        this.call = baseCall;
        this.call.enqueue(new ResponseCallback<ResultBase<OrderInfo>>() {
            @Override
            public void onSuccess(Call call, ResultBase<OrderInfo> orderInfoResultBase) {
                if (submitWorkOrderMvpView == null) return;
                submitWorkOrderMvpView.addJieSuanSuccess(orderInfoResultBase.getObj());
            }

            @Override
            public void onError(Call call, ResultBase<OrderInfo> orderInfoResultBase, int errorCode) {

            }

            @Override
            public void onFail(Call call, String msg) {

            }

            @Override
            public void onResult() {
                if (submitWorkOrderMvpView == null) return;
                submitWorkOrderMvpView.disMissLoadingView();
            }
        });

    }

}
