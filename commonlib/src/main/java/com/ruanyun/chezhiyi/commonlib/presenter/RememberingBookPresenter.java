package com.ruanyun.chezhiyi.commonlib.presenter;

import com.ruanyun.chezhiyi.commonlib.base.PageInfoBase;
import com.ruanyun.chezhiyi.commonlib.base.ResultBase;
import com.ruanyun.chezhiyi.commonlib.data.api.ResponseCallback;
import com.ruanyun.chezhiyi.commonlib.model.AccountBookInfo;
import com.ruanyun.chezhiyi.commonlib.view.RememberingBookMvpView;
import retrofit2.Call;

/**
 * 记账本记一笔的
 * Created by ycw on 2016/9/9.
 */
public class RememberingBookPresenter implements Presenter<RememberingBookMvpView> {

    RememberingBookMvpView rememberingBookMvpView;
    Call<ResultBase<PageInfoBase<AccountBookInfo>>> call;


    @Override
    public void attachView(RememberingBookMvpView mvpView) {
        rememberingBookMvpView = mvpView;
    }

    @Override
    public void detachView() {
        rememberingBookMvpView = null;
    }

    @Override
    public void onCancel() {
        if (call != null && !call.isCanceled())
            call.cancel();
    }

    /**
     * 添加 或 修改 记账本
     * @param call
     */
    public void addAndUpdateAccountBook(Call<ResultBase<PageInfoBase<AccountBookInfo>>> call) {
        this.call = call;
        if (rememberingBookMvpView==null) return;
        rememberingBookMvpView.showLoadingView();
        this.call.enqueue(new ResponseCallback<ResultBase<PageInfoBase<AccountBookInfo>>>() {

            @Override
            public void onSuccess(Call call, ResultBase<PageInfoBase<AccountBookInfo>> pageInfoBaseResultBase) {
                if (rememberingBookMvpView==null) return;
                rememberingBookMvpView.showAddOrUpAccountBookSuccessTip(pageInfoBaseResultBase);
            }

            @Override
            public void onError(Call call, ResultBase<PageInfoBase<AccountBookInfo>> pageInfoBaseResultBase, int
                    errorCode) {
                if (rememberingBookMvpView==null) return;
                rememberingBookMvpView.showAddOrUpAccountBookErrorOrFailTip(pageInfoBaseResultBase.getMsg());
            }

            @Override
            public void onFail(Call call, String msg) {
                if (rememberingBookMvpView==null) return;
                rememberingBookMvpView.showAddOrUpAccountBookErrorOrFailTip(msg);
            }

            @Override
            public void onResult() {
                if (rememberingBookMvpView==null) return;
                rememberingBookMvpView.dismissLoadingView();
            }

        });
    }



}
