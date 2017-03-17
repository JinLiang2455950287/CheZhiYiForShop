package com.ruanyun.chezhiyi.commonlib.presenter;

import com.ruanyun.chezhiyi.commonlib.base.PageInfoBase;
import com.ruanyun.chezhiyi.commonlib.base.ResultBase;
import com.ruanyun.chezhiyi.commonlib.data.api.ResponseCallback;
import com.ruanyun.chezhiyi.commonlib.model.RecommendInfo;
import com.ruanyun.chezhiyi.commonlib.model.StoreInfo;
import com.ruanyun.chezhiyi.commonlib.model.User;
import com.ruanyun.chezhiyi.commonlib.util.LogX;
import com.ruanyun.chezhiyi.commonlib.view.StoreInfoMvpView;

import retrofit2.Call;

/**
 * 门店的详细
 * Created by ycw on 2016/9/6.
 */
public class StoreInfoPresenter implements Presenter<StoreInfoMvpView> {

    StoreInfoMvpView storeInfoMvpView;
    private Call<ResultBase<StoreInfo>> call;
    private Call<ResultBase<PageInfoBase<RecommendInfo>>> recommendCall;
    private Call<ResultBase<User>> resultBaseCall;


    @Override
    public void attachView(StoreInfoMvpView mvpView) {
        storeInfoMvpView = mvpView;
    }

    @Override
    public void detachView() {
        storeInfoMvpView = null;
    }

    @Override
    public void onCancel() {
        if (call != null && call.isExecuted()) {
            call.cancel();
        }
    }


    /**
     * 获取门店详细
     * @param call
     */
    public void  getStoreInfo(Call<ResultBase<StoreInfo>> call) {
        this.call = call;
        call.enqueue(new ResponseCallback<ResultBase<StoreInfo>>() {
            @Override
            public void onSuccess(Call call, ResultBase<StoreInfo> storeInfoResultBase) {
                if (storeInfoMvpView == null) return;
                LogX.e("=====门店",storeInfoResultBase.getObj().toString());
                storeInfoMvpView.setHeadViewData(storeInfoResultBase.getObj());
            }

            @Override
            public void onError(Call call, ResultBase<StoreInfo> storeInfoResultBase, int errorCode) {

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
     * 获取推荐列表
     * @param call
     */
    public void  getRecommendList(Call<ResultBase<PageInfoBase<RecommendInfo>>> call) {
        this.recommendCall = call;
        this.recommendCall.enqueue(new ResponseCallback<ResultBase<PageInfoBase<RecommendInfo>>>() {
            @Override
            public void onSuccess(Call call, ResultBase<PageInfoBase<RecommendInfo>> pageInfoBaseResultBase) {
                if (storeInfoMvpView == null) return;
                storeInfoMvpView.getRecommendInfoOnSuccess(pageInfoBaseResultBase.getObj().getResult());
            }

            @Override
            public void onError(Call call, ResultBase<PageInfoBase<RecommendInfo>> pageInfoBaseResultBase, int errorCode) {

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
     * 根据用户编号获取  含有朋友的关系 的用户信息
     * @param call
     */
    public void getFriendShipInfo(Call<ResultBase<User>> call) {
        if (storeInfoMvpView == null) return;
        storeInfoMvpView.showLoadingView();
        resultBaseCall = call;
        resultBaseCall.enqueue(new ResponseCallback<ResultBase<User>>() {
            @Override
            public void onSuccess(Call call, ResultBase<User> userResult) {
                if (storeInfoMvpView == null) return;
                storeInfoMvpView.getFriendShipInfoSuccess(userResult.getObj());
            }

            @Override
            public void onError(Call call, ResultBase<User> userResult, int errorCode) {
                if (storeInfoMvpView == null) return;
            }

            @Override
            public void onFail(Call call, String msg) {
                if (storeInfoMvpView == null) return;
            }

            @Override
            public void onResult() {
                if (storeInfoMvpView == null) return;
                storeInfoMvpView.disMissLoadingView();
            }
        });
    }



}
