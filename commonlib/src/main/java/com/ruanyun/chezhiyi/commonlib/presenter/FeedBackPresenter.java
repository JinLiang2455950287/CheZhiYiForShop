package com.ruanyun.chezhiyi.commonlib.presenter;

import com.ruanyun.chezhiyi.commonlib.base.ResultBase;
import com.ruanyun.chezhiyi.commonlib.data.api.ResponseCallback;
import com.ruanyun.chezhiyi.commonlib.model.FeedBackInfo;
import com.ruanyun.chezhiyi.commonlib.util.LogX;
import com.ruanyun.chezhiyi.commonlib.view.FeedBackMvpView;

import retrofit2.Call;


/**
 * Created by msq on 2016/9/9.
 */
public class FeedBackPresenter implements Presenter<FeedBackMvpView>{

    FeedBackMvpView feedBackMvpView;
    Call feedBackCall;
    @Override
    public void attachView(FeedBackMvpView mvpView) {
        feedBackMvpView = mvpView;
    }

    @Override
    public void detachView() {
        feedBackMvpView = null;
    }

    @Override
    public void onCancel() {
        if(feedBackCall != null && !feedBackCall.isCanceled()){
            feedBackCall.cancel();
        }
    }

    public void onFeedBackApply(Call call){
        feedBackMvpView.onFeedBackShowLoading();
        feedBackCall = call;
        feedBackCall.enqueue(new ResponseCallback<ResultBase<FeedBackInfo>>() {
            @Override
            public void onSuccess(Call call, ResultBase<FeedBackInfo> resultBase) {
                feedBackMvpView.onFeedBackSuccess(resultBase);
            }

            @Override
            public void onError(Call call, ResultBase<FeedBackInfo> resultBase, int errorCode) {
                feedBackMvpView.onLoginLoadingDissMiss();
                feedBackMvpView.onFeedBackError(resultBase,errorCode);
            }

            @Override
            public void onFail(Call call, String msg) {
                feedBackMvpView.onLoginLoadingDissMiss();
                feedBackMvpView.onFeedBackFail(msg);
            }

            @Override
            public void onResult() {
                feedBackMvpView.onFeedBackResponse();
            }
        });
    }
}
