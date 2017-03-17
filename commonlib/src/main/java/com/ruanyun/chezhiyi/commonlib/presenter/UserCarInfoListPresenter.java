package com.ruanyun.chezhiyi.commonlib.presenter;

import com.ruanyun.chezhiyi.commonlib.base.ResultBase;
import com.ruanyun.chezhiyi.commonlib.data.api.ResponseCallback;
import com.ruanyun.chezhiyi.commonlib.model.CarInfo;
import com.ruanyun.chezhiyi.commonlib.view.UserCarInfoListMvpView;

import java.util.List;

import retrofit2.Call;

/**
 * 获取用户车辆信息
 * Created by ycw on 2016/8/29.
 */
public class UserCarInfoListPresenter implements Presenter<UserCarInfoListMvpView> {

    UserCarInfoListMvpView userCarInfoListMvpView;
    Call<ResultBase<List<CarInfo>>> call;

    @Override
    public void attachView(UserCarInfoListMvpView mvpView) {
        userCarInfoListMvpView = mvpView;
    }

    @Override
    public void detachView() {
        userCarInfoListMvpView = null;
    }

    @Override
    public void onCancel() {
        if (call != null && !call.isCanceled())
            call.cancel();
    }

    /**
     * 获取用户的车辆信息
     * @param call
     */
    public void getCarInfoList(Call call) {
        if (userCarInfoListMvpView == null) return;
        userCarInfoListMvpView.showLoadingView();
        this.call = call;
        this.call.enqueue(new ResponseCallback<ResultBase<List<CarInfo>>>() {
            @Override
            public void onSuccess(Call call, ResultBase<List<CarInfo>> listResultBase) {
                if (userCarInfoListMvpView == null) return;
                userCarInfoListMvpView.onSuccess(listResultBase.getObj());
            }

            @Override
            public void onError(Call call, ResultBase<List<CarInfo>> listResultBase, int errorCode) {
                if (userCarInfoListMvpView == null) return;
                userCarInfoListMvpView.onError(listResultBase.getMsg(), errorCode);
            }

            @Override
            public void onFail(Call call, String msg) {
                if (userCarInfoListMvpView == null) return;
                userCarInfoListMvpView.onFail();
            }

            @Override
            public void onResult() {
                if (userCarInfoListMvpView == null) return;
                userCarInfoListMvpView.dismissLoadingView();
            }
        });
    }
}
