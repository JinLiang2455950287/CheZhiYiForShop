package com.ruanyun.chezhiyi.commonlib.presenter;

import com.ruanyun.chezhiyi.commonlib.base.ResultBase;
import com.ruanyun.chezhiyi.commonlib.data.api.ResponseCallback;
import com.ruanyun.chezhiyi.commonlib.model.User;
import com.ruanyun.chezhiyi.commonlib.view.RelationShipMvpView;

import retrofit2.Call;

/**
 * 获取用户关系
 * Created by ycw on 2016/8/31.
 */
public class UserRelationShipPresenter implements Presenter<RelationShipMvpView> {

    RelationShipMvpView relationShipMvpView;
    Call<ResultBase<User>> call;

    @Override
    public void attachView(RelationShipMvpView mvpView) {
        relationShipMvpView = mvpView;
    }

    @Override
    public void detachView() {
        relationShipMvpView = null;
    }

    @Override
    public void onCancel() {
        if (call != null && !call.isCanceled())
            call.cancel();
    }

    /**
     * 获取用户的朋友关系
     * @param call
     */
    public void getFriendShipInfo(Call<ResultBase<User>> call) {
        relationShipMvpView.showLoadingView();
        this.call = call;
        this.call.enqueue(new ResponseCallback<ResultBase<User>>() {
            @Override
            public void onSuccess(Call call, ResultBase<User> userResultBase) {
                relationShipMvpView.getFriendShipInfoSuccess(userResultBase.getObj());
            }

            @Override
            public void onError(Call call, ResultBase<User> userResultBase, int errorCode) {
                relationShipMvpView.getFriendShipInfoError();
            }

            @Override
            public void onFail(Call call, String msg) {
                relationShipMvpView.getFriendShipInfoFail();
            }

            @Override
            public void onResult() {
                relationShipMvpView.disMissLoadingView();
            }
        });
    }
}
