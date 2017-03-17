package com.ruanyun.chezhiyi.commonlib.presenter;

import com.ruanyun.chezhiyi.commonlib.base.ResultBase;
import com.ruanyun.chezhiyi.commonlib.data.api.ResponseCallback;
import com.ruanyun.chezhiyi.commonlib.model.HuiYuanKuaiChaInfo;
import com.ruanyun.chezhiyi.commonlib.util.LogX;
import com.ruanyun.chezhiyi.commonlib.view.MemberSearchMvpView;

import java.util.List;

import retrofit2.Call;

/**
 * Created by 创智 on 2017/3/13.
 * 会员快查
 */

public class MemberSearchPresenter implements Presenter<MemberSearchMvpView> {
    Call<ResultBase<List<HuiYuanKuaiChaInfo>>> call;
    MemberSearchMvpView mvpView;

    @Override
    public void attachView(MemberSearchMvpView mvpView) {
        this.mvpView = mvpView;
    }

    public void loadMemberData(Call<ResultBase<List<HuiYuanKuaiChaInfo>>> call) {
        this.call = call;
        call.enqueue(new ResponseCallback<ResultBase<List<HuiYuanKuaiChaInfo>>>() {

            @Override
            public void onSuccess(Call call, ResultBase<List<HuiYuanKuaiChaInfo>> listResultBase) {
                LogX.e("====1111111111111", listResultBase.getObj().toString());
                mvpView.getDataSuccess(listResultBase.getObj());
                mvpView.dismissLoadingView();
            }

            @Override
            public void onError(Call call, ResultBase<List<HuiYuanKuaiChaInfo>> listResultBase, int errorCode) {
                LogX.e("1111111111112", listResultBase.getObj().toString());
            }

            @Override
            public void onFail(Call call, String msg) {
                LogX.e("1111111111113", msg);
            }

            @Override
            public void onResult() {
                LogX.e("1111111111114", "msg");
            }
        });
    }

    @Override
    public void detachView() {
        this.mvpView = null;
    }

    @Override
    public void onCancel() {
        if (call != null && !call.isCanceled())
            call.cancel();
    }
}
