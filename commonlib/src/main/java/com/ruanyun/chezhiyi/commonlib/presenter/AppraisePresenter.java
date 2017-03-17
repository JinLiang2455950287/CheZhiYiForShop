package com.ruanyun.chezhiyi.commonlib.presenter;

import com.ruanyun.chezhiyi.commonlib.base.ResultBase;
import com.ruanyun.chezhiyi.commonlib.data.api.ResponseCallback;
import com.ruanyun.chezhiyi.commonlib.presenter.Presenter;
import com.ruanyun.chezhiyi.commonlib.view.AppraiseMvpView;

import retrofit2.Call;

/**
 * Description ：添加评价
 * <p>
 * Created by ycw on 2016/10/14.
 */
public class AppraisePresenter implements Presenter<AppraiseMvpView> {

    Call<ResultBase> baseCall;
    AppraiseMvpView appraiseMvpView;

    @Override
    public void attachView(AppraiseMvpView mvpView) {
        appraiseMvpView= mvpView;
    }

    @Override
    public void detachView() {
        appraiseMvpView = null;
    }

    @Override
    public void onCancel() {

    }


    /**
     * 添加商品的评价
     * @param baseCall
     */
    public void  addOrderComment(Call<ResultBase> baseCall){
        if (appraiseMvpView == null) return;
        appraiseMvpView.showLoadingView();
        this.baseCall = baseCall;
        this.baseCall.enqueue(new ResponseCallback<ResultBase>() {
            @Override
            public void onSuccess(Call call, ResultBase resultBase) {
                if (appraiseMvpView == null) return;
                appraiseMvpView.addCommentSuccess();
                appraiseMvpView.showTip(resultBase.getMsg());

            }

            @Override
            public void onError(Call call, ResultBase resultBase, int errorCode) {

            }

            @Override
            public void onFail(Call call, String msg) {

            }

            @Override
            public void onResult() {
                if (appraiseMvpView == null) return;
                appraiseMvpView.dismissLoadingView();
            }
        });

    }
}
