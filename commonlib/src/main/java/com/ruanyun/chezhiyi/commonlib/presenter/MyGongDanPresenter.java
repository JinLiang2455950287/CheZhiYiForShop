package com.ruanyun.chezhiyi.commonlib.presenter;

import com.ruanyun.chezhiyi.commonlib.base.ResultBase;
import com.ruanyun.chezhiyi.commonlib.data.api.ResponseCallback;
import com.ruanyun.chezhiyi.commonlib.model.MyGongDanInfo;
import com.ruanyun.chezhiyi.commonlib.util.LogX;
import com.ruanyun.chezhiyi.commonlib.view.MyGongDanTongJiView;

import java.util.List;

import retrofit2.Call;

/**
 * 我的工单统计
 * Created by czy on 2017/4/18.
 */

public class MyGongDanPresenter implements Presenter<MyGongDanTongJiView> {

    private MyGongDanTongJiView huiYuanMyGongDanTongJiView;

    private Call<ResultBase<MyGongDanInfo>> call;

    @Override
    public void attachView(MyGongDanTongJiView mvpView) {
        this.huiYuanMyGongDanTongJiView = mvpView;
    }

    public void getGongDanMyTongJiInfo(Call<ResultBase<MyGongDanInfo>> call) {
        this.call = call;
        call.enqueue(new ResponseCallback<ResultBase<MyGongDanInfo>>() {
            @Override
            public void onSuccess(Call call, ResultBase<MyGongDanInfo> resultBase) {
                LogX.e("工单Mypersenter", resultBase.getObj().toString());
                huiYuanMyGongDanTongJiView.getGongDanSuccess(resultBase.getObj());
            }

            @Override
            public void onError(Call call, ResultBase<MyGongDanInfo> resultBase, int errorCode) {

            }

            @Override
            public void onFail(Call call, String msg) {
                LogX.e("工单MypersenteronError", msg.toString() + "");
            }

            @Override
            public void onResult() {

            }
        });

    }

    @Override
    public void detachView() {
        huiYuanMyGongDanTongJiView = null;
    }

    @Override
    public void onCancel() {
        if (call != null && !call.isCanceled())
            call.cancel();
    }
}
