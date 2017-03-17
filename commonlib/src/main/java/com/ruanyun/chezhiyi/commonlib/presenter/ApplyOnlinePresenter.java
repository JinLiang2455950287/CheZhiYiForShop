package com.ruanyun.chezhiyi.commonlib.presenter;

import com.ruanyun.chezhiyi.commonlib.base.PageInfoBase;
import com.ruanyun.chezhiyi.commonlib.base.ResultBase;
import com.ruanyun.chezhiyi.commonlib.data.api.ResponseCallback;
import com.ruanyun.chezhiyi.commonlib.model.RecommendInfo;
import com.ruanyun.chezhiyi.commonlib.model.SignAddInfo;
import com.ruanyun.chezhiyi.commonlib.util.LogX;
import com.ruanyun.chezhiyi.commonlib.view.ApplyOnlineMvpView;

import java.util.List;

import retrofit2.Call;

/**
 * Description ：
 * <p/>
 * Created by ycw on 2016/9/14.
 */
public class ApplyOnlinePresenter implements Presenter<ApplyOnlineMvpView> {


    ApplyOnlineMvpView applyOnlineMvpView;
    Call<ResultBase<SignAddInfo>> call;

    @Override
    public void attachView(ApplyOnlineMvpView mvpView) {
        applyOnlineMvpView = mvpView;
    }

    @Override
    public void detachView() {
        applyOnlineMvpView = null;
    }

    @Override
    public void onCancel() {

    }

    /**
     * 猜你喜欢
     *
     * @param call
     */
    public void signAdd(Call<ResultBase<SignAddInfo>> call) {
        this.call = call;
        if (applyOnlineMvpView == null) return;
        applyOnlineMvpView.showSignAddLoadingView();
        this.call.enqueue(new ResponseCallback<ResultBase<SignAddInfo>>() {
            @Override
            public void onSuccess(Call call, ResultBase<SignAddInfo> signAddInfoResultBase) {
                if (applyOnlineMvpView == null) return;
                applyOnlineMvpView.showSignAddTip(signAddInfoResultBase.getMsg());
                applyOnlineMvpView.getSignAddOnSuccess(signAddInfoResultBase);
                LogX.d("getRecommendList", "----------onSuccess--------");
            }

            @Override
            public void onError(Call call, ResultBase<SignAddInfo> signAddInfoResultBase, int errorCode) {
                if (applyOnlineMvpView == null) return;
                applyOnlineMvpView.showSignAddTip(signAddInfoResultBase.getMsg());
                LogX.d("getRecommendList", "----------onError--------");
            }

            @Override
            public void onFail(Call call, String msg) {
                if (applyOnlineMvpView == null) return;
                applyOnlineMvpView.showSignAddTip(msg);
                LogX.d("getRecommendList", "----------onFail--------");
            }

            @Override
            public void onResult() {
                if (applyOnlineMvpView == null) return;
                applyOnlineMvpView.dismissSignAddLoadingView();
                LogX.d("getRecommendList", "----------onResult--------");
            }

        });
    }
}