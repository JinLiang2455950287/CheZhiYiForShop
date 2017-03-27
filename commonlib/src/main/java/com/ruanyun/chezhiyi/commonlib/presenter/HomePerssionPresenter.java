package com.ruanyun.chezhiyi.commonlib.presenter;


import com.ruanyun.chezhiyi.commonlib.base.ResultBase;
import com.ruanyun.chezhiyi.commonlib.data.api.ResponseCallback;
import com.ruanyun.chezhiyi.commonlib.model.PerssionBean;
import com.ruanyun.chezhiyi.commonlib.util.LogX;
import com.ruanyun.chezhiyi.commonlib.view.HomePerssionMvpView;

import java.util.List;

import retrofit2.Call;

/**
 * Created by czy on 2017/3/27.
 * 主页面权限
 */

public class HomePerssionPresenter implements Presenter<HomePerssionMvpView> {
    private HomePerssionMvpView homePerssionMvpView;
    private Call<ResultBase<List<PerssionBean>>> call;

    @Override
    public void attachView(HomePerssionMvpView mvpView) {
        this.homePerssionMvpView = mvpView;
    }

    /*获取当前用户主页面的权限*/
    public void getPerssionData(Call<ResultBase<List<PerssionBean>>> call) {
        this.call = call;
        call.enqueue(new ResponseCallback<ResultBase<List<PerssionBean>>>() {
            @Override
            public void onSuccess(Call call, ResultBase<List<PerssionBean>> listResultBase) {
                homePerssionMvpView.getDataSuccess(listResultBase.getObj());
                LogX.e("权限", listResultBase.getObj().toString());
            }

            @Override
            public void onError(Call call, ResultBase<List<PerssionBean>> listResultBase, int errorCode) {

            }

            @Override
            public void onFail(Call call, String msg) {

            }

            @Override
            public void onResult() {

            }
        });
    }


    @Override
    public void detachView() {
        homePerssionMvpView = null;
    }

    @Override
    public void onCancel() {
        if (call != null && !call.isCanceled())
            call.cancel();
    }
}
