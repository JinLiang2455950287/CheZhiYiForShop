package com.ruanyun.chezhiyi.commonlib.presenter;

import com.ruanyun.chezhiyi.commonlib.base.ResultBase;
import com.ruanyun.chezhiyi.commonlib.data.api.ResponseCallback;
import com.ruanyun.chezhiyi.commonlib.model.JieSuanInfo;
import com.ruanyun.chezhiyi.commonlib.util.LogX;
import com.ruanyun.chezhiyi.commonlib.view.JieSuanMvpView;

import java.util.List;

import retrofit2.Call;

/**
 * Description ：结算工单
 * <p/>
 * Created by ycw on 2016/11/1.
 */
public class JieSuanPresenter implements Presenter<JieSuanMvpView> {

    JieSuanMvpView mvpView;


    @Override
    public void attachView(JieSuanMvpView mvpView) {
        this.mvpView = mvpView;
    }

    @Override
    public void detachView() {
        mvpView = null;
    }

    @Override
    public void onCancel() {

    }


    /**
     * 结算
     * @param call
     */
    public void  getJieSuan(Call<ResultBase<JieSuanInfo>> call) {
        if (mvpView == null) return;
        mvpView.showLoadingView();
        call.enqueue(new ResponseCallback<ResultBase<JieSuanInfo>>() {
            @Override
            public void onSuccess(Call call, ResultBase<JieSuanInfo> resultBase) {
                if (mvpView == null) return;
                mvpView.getJieSuanSuccess(resultBase.getObj());
                LogX.e("结算onSuccess",resultBase.getObj().toString());

            }

            @Override
            public void onError(Call call, ResultBase<JieSuanInfo> infoResultBase, int
                    errorCode) {
                if (mvpView == null) return;
                mvpView.showTips(infoResultBase.getMsg());
            }

            @Override
            public void onFail(Call call, String msg) {
                if (mvpView == null) return;
                mvpView.showTips(msg);
            }

            @Override
            public void onResult() {
                if (mvpView == null) return;
                mvpView.disMissLoadingView();
            }
        });
    }


}
