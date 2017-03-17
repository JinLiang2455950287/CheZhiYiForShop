package com.ruanyun.chezhiyi.commonlib.presenter;

import com.ruanyun.chezhiyi.commonlib.base.ResultBase;
import com.ruanyun.chezhiyi.commonlib.data.api.ResponseCallback;
import com.ruanyun.chezhiyi.commonlib.model.ActivitySignInfo;
import com.ruanyun.chezhiyi.commonlib.view.ActivityDetailedMvpView;

import retrofit2.Call;

/**
 * Description ：活动详情
 * <p>
 * Created by ycw on 2016/10/24.
 */
public class ActivityPresenter implements Presenter<ActivityDetailedMvpView> {

    ActivityDetailedMvpView mvpView;
    Call<ResultBase<ActivitySignInfo>> baseCall;

    /**
     * 取消活动报名
     */
    Call<ResultBase> delActivityCall;


    @Override
    public void attachView(ActivityDetailedMvpView mvpView) {
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
     * 获取 活动报名 详情
     */
    public void getSignInfo(Call<ResultBase<ActivitySignInfo>> call){
        this.baseCall = call;
        this.baseCall.enqueue(new ResponseCallback<ResultBase<ActivitySignInfo>>() {
            @Override
            public void onSuccess(Call call, ResultBase<ActivitySignInfo> activitySignInfoResultBase) {
                if (mvpView == null) return;
                mvpView.getSignInfoSuccess(activitySignInfoResultBase.getObj());
            }

            @Override
            public void onError(Call call, ResultBase<ActivitySignInfo> activitySignInfoResultBase, int errorCode) {
                if (mvpView == null) return;
            }

            @Override
            public void onFail(Call call, String msg) {
                if (mvpView == null) return;
            }

            @Override
            public void onResult() {
                if (mvpView == null) return;
            }
        });
    }



    /**
     * 取消活动报名
     * @param delActivityCall
     */
    public void delActivity(Call<ResultBase> delActivityCall) {
        this.delActivityCall = delActivityCall;
        if (mvpView == null) return;
        mvpView.showLoadingView();
        this.delActivityCall.enqueue(new ResponseCallback<ResultBase>() {
            @Override
            public void onSuccess(Call call, ResultBase resultBase) {
                if (mvpView == null) return;
                mvpView.showTip(resultBase.getMsg());
                mvpView.delActivitySuccess();
            }

            @Override
            public void onError(Call call, ResultBase resultBase, int errorCode) {
                if (mvpView == null) return;
                mvpView.showTip(resultBase.getMsg());
            }

            @Override
            public void onFail(Call call, String msg) {
                if (mvpView == null) return;
                mvpView.showTip(msg);
            }

            @Override
            public void onResult() {
                if (mvpView == null) return;
                mvpView.dismissLoadingView();
            }
        });
    }


}
