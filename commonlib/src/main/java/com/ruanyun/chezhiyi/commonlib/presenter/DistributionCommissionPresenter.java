package com.ruanyun.chezhiyi.commonlib.presenter;

import com.ruanyun.chezhiyi.commonlib.base.ResultBase;
import com.ruanyun.chezhiyi.commonlib.data.api.ResponseCallback;
import com.ruanyun.chezhiyi.commonlib.model.User;
import com.ruanyun.chezhiyi.commonlib.model.WorkOrderCommissionInfo;
import com.ruanyun.chezhiyi.commonlib.view.DistributionCommissionMvpView;

import java.util.List;

import retrofit2.Call;

/**
 * Description ：
 * <p/>
 * Created by ycw on 2016/11/4.
 */

public class DistributionCommissionPresenter implements Presenter<DistributionCommissionMvpView> {


    DistributionCommissionMvpView mvpView;
    Call<ResultBase<WorkOrderCommissionInfo>> baseCall;
    private Call<ResultBase> call;

    @Override
    public void attachView(DistributionCommissionMvpView mvpView) {
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
     * 获取分配提成
     */
    public void getDistrubuteUser(Call<ResultBase<WorkOrderCommissionInfo>> call){
        if (mvpView == null) return;
        mvpView.showLoadingView();
        this.baseCall = call;
        this.baseCall.enqueue(new ResponseCallback<ResultBase<WorkOrderCommissionInfo>>() {
            @Override
            public void onSuccess(Call call, ResultBase<WorkOrderCommissionInfo> listResultBase) {
                if (mvpView == null) return;
                mvpView.getDistrubuteUserSuccess(listResultBase.getObj());
            }

            @Override
            public void onError(Call call, ResultBase<WorkOrderCommissionInfo> listResultBase, int errorCode) {
                if (mvpView == null) return;
            }

            @Override
            public void onFail(Call call, String msg) {
                if (mvpView == null) return;
            }

            @Override
            public void onResult() {
                if (mvpView == null) return;
                mvpView.disMissLoadingView();
            }
        });
    }

    /**
     * 保存分配提成
     */
    public void saveDistrubution(Call<ResultBase> call){
        if (mvpView == null) return;
        mvpView.showLoadingView();
        this.call = call;
        this.call.enqueue(new ResponseCallback<ResultBase>() {
            @Override
            public void onSuccess(Call call, ResultBase listResultBase) {
                if (mvpView == null) return;
                mvpView.showTips(listResultBase.getMsg());
                mvpView.saveDistrubutionSuccess();
            }

            @Override
            public void onError(Call call, ResultBase listResultBase, int errorCode) {
                if (mvpView == null) return;
                mvpView.showTips(listResultBase.getMsg());
            }

            @Override
            public void onFail(Call call, String msg) {
                if (mvpView == null) return;
                mvpView.showTips(msg);
            }

            @Override
            public void onResult() {
                if (mvpView == null) return;
                mvpView.disMissLoadingView();
            }
        });
    }


}
