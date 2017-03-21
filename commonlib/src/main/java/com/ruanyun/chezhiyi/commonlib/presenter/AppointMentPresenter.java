package com.ruanyun.chezhiyi.commonlib.presenter;

import com.ruanyun.chezhiyi.commonlib.base.ResultBase;
import com.ruanyun.chezhiyi.commonlib.data.api.ResponseCallback;
import com.ruanyun.chezhiyi.commonlib.model.YuYueItemBean;
import com.ruanyun.chezhiyi.commonlib.util.LogX;
import com.ruanyun.chezhiyi.commonlib.view.AppointMentMvpView;

import java.util.List;

import retrofit2.Call;

/**
 * Created by 创智 on 2017/3/13.
 * 预约处理
 */

public class AppointMentPresenter implements Presenter<AppointMentMvpView> {
    AppointMentMvpView appointMentMvpView;
    Call<ResultBase<List<YuYueItemBean>>> call;

    @Override
    public void attachView(AppointMentMvpView mvpView) {
        appointMentMvpView = mvpView;
    }

    @Override
    public void detachView() {
        this.appointMentMvpView = null;
    }

    public void getYuYueData(Call<ResultBase<List<YuYueItemBean>>> call) {
        this.call = call;
        call.enqueue(new ResponseCallback<ResultBase<List<YuYueItemBean>>>() {

            @Override
            public void onSuccess(Call call, ResultBase<List<YuYueItemBean>> pageInfoBaseResultBase) {
                LogX.e("测试", pageInfoBaseResultBase.getObj().toString());
                appointMentMvpView.getDataSuccess(pageInfoBaseResultBase.getObj(),pageInfoBaseResultBase.getObj().toString());
                if (pageInfoBaseResultBase.getObj().size()==0) {
                    appointMentMvpView.showEmptyView();
                }
                appointMentMvpView.dismissLoadingView();
            }

            @Override
            public void onError(Call call, ResultBase<List<YuYueItemBean>> pageInfoBaseResultBase, int errorCode) {

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
    public void onCancel() {
        if (call != null && !call.isCanceled())
            call.cancel();
    }
}
