package com.ruanyun.chezhiyi.commonlib.presenter;

import com.ruanyun.chezhiyi.commonlib.base.ResultBase;
import com.ruanyun.chezhiyi.commonlib.data.api.ResponseCallback;
import com.ruanyun.chezhiyi.commonlib.model.HxUserGroup;
import com.ruanyun.chezhiyi.commonlib.view.HXUserGroupMvpView;

import java.util.List;

import retrofit2.Call;

/**
 * Description ：
 * <p/>
 * Created by ycw on 2016/8/10.
 */
public class HXUserGroupPresenter implements Presenter<HXUserGroupMvpView> {

    HXUserGroupMvpView groupMvpView;
    Call<ResultBase<List<HxUserGroup>>> groupCall;

    @Override
    public void attachView(HXUserGroupMvpView mvpView) {
        groupMvpView = mvpView;
    }

    @Override
    public void detachView() {
        groupMvpView = null;
    }

    @Override
    public void onCancel() {
        if (groupCall != null && !groupCall.isCanceled())
            groupCall.cancel();
    }


    /**
     * 获取用户群组列表
     * @param call
     */
    public void getUserGroupList(Call<ResultBase<List<HxUserGroup>>> call){
        groupCall = call;
        groupCall.enqueue(new ResponseCallback<ResultBase<List<HxUserGroup>>>() {
            @Override
            public void onSuccess(Call call, ResultBase<List<HxUserGroup>> resultBase) {
                if(groupMvpView!=null)
                groupMvpView.onSuccess(resultBase);
            }

            @Override
            public void onError(Call call, ResultBase<List<HxUserGroup>> resultBase, int errorCode) {
                if(groupMvpView!=null)
                groupMvpView.onError(resultBase, errorCode);
            }

            @Override
            public void onFail(Call call, String msg) {
                if(groupMvpView!=null)
                groupMvpView.onFail(msg);
            }

            @Override
            public void onResult() {
                if(groupMvpView!=null)
                groupMvpView.onResult();
            }
        });
    }
}
