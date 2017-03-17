package com.ruanyun.chezhiyi.commonlib.presenter;

import com.ruanyun.chezhiyi.commonlib.base.PageInfoBase;
import com.ruanyun.chezhiyi.commonlib.base.ResultBase;
import com.ruanyun.chezhiyi.commonlib.data.api.ResponseCallback;
import com.ruanyun.chezhiyi.commonlib.model.MyListCount;
import com.ruanyun.chezhiyi.commonlib.model.RemindInfo;
import com.ruanyun.chezhiyi.commonlib.view.ShopMyMvpView;

import retrofit2.Call;

/**
 * Description ：
 * <p/>
 * Created by ycw on 2016/11/28.
 */
public class ShopMyPresenter implements Presenter<ShopMyMvpView> {

    ShopMyMvpView myMvpView;
    private Call<ResultBase<PageInfoBase<RemindInfo>>> call;
    private Call<ResultBase<MyListCount>> myListCountCall;

    @Override
    public void attachView(ShopMyMvpView mvpView) {
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

    /**
     * 获取系统消息
     * @param resultBaseCall
     */
    public void myListCount(Call<ResultBase<MyListCount>> resultBaseCall) {
        if (myMvpView == null ) return;
        this.myListCountCall = resultBaseCall;
        resultBaseCall.enqueue(new ResponseCallback<ResultBase<MyListCount>>() {
                    @Override
                    public void onSuccess(Call call, ResultBase<MyListCount> resultBase) {
                        if (myMvpView == null) return;
                        myMvpView.getmyListCountSuccess(resultBase.getObj());
                    }

                    @Override
                    public void onError(Call call, ResultBase<MyListCount> resultBase, int errorCode) {
                        if (myMvpView == null) return;
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
