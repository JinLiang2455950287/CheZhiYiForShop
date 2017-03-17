package com.ruanyun.chezhiyi.commonlib.presenter;

import com.ruanyun.chezhiyi.commonlib.base.PageInfoBase;
import com.ruanyun.chezhiyi.commonlib.base.ResultBase;
import com.ruanyun.chezhiyi.commonlib.data.api.ResponseCallback;
import com.ruanyun.chezhiyi.commonlib.model.RemindInfo;
import com.ruanyun.chezhiyi.commonlib.view.MyMvpView;

import retrofit2.Call;

/**
 * Description ：
 * <p/>
 * Created by ycw on 2016/11/28.
 */
public class MyPresenter implements Presenter<MyMvpView> {

    MyMvpView myMvpView;
    private Call<ResultBase<PageInfoBase<RemindInfo>>> call;

    @Override
    public void attachView(MyMvpView mvpView) {
        this.myMvpView = mvpView;
    }

    @Override
    public void detachView() {
        this.myMvpView = null;
    }

    @Override
    public void onCancel() {

    }

    /**
     * 获取系统消息
     * @param systemRemindList
     */
    public void getSystemRemindList(Call<ResultBase<PageInfoBase<RemindInfo>>> systemRemindList) {
        if (myMvpView == null ) return;
        this.call = systemRemindList;
        systemRemindList.enqueue(new ResponseCallback<ResultBase<PageInfoBase<RemindInfo>>>() {
            @Override
            public void onSuccess(Call call, ResultBase<PageInfoBase<RemindInfo>> pageInfoBaseResultBase) {
                if (myMvpView == null) return;
                myMvpView.getSystemRemindListSuccess(pageInfoBaseResultBase.getObj().getTotalCount());

            }

            @Override
            public void onError(Call call, ResultBase<PageInfoBase<RemindInfo>> pageInfoBaseResultBase, int errorCode) {

            }

            @Override
            public void onFail(Call call, String msg) {

            }

            @Override
            public void onResult() {

            }
        });
    }

}
