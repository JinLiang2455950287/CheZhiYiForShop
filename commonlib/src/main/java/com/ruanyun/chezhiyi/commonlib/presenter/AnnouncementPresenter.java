package com.ruanyun.chezhiyi.commonlib.presenter;

import com.ruanyun.chezhiyi.commonlib.base.PageInfoBase;
import com.ruanyun.chezhiyi.commonlib.base.ResultBase;
import com.ruanyun.chezhiyi.commonlib.data.api.ResponseCallback;
import com.ruanyun.chezhiyi.commonlib.model.GongGaoInfo;
import com.ruanyun.chezhiyi.commonlib.model.RemindInfo;
import com.ruanyun.chezhiyi.commonlib.util.LogX;
import com.ruanyun.chezhiyi.commonlib.view.AnnouncementMvpView;

import java.util.List;

import retrofit2.Call;

/**
 * 首页的公告
 * Created by ycw on 2017/3/10.
 */
public class AnnouncementPresenter implements Presenter<AnnouncementMvpView> {

    AnnouncementMvpView announcementMvpView;
    private Call<ResultBase<List<GongGaoInfo>>> call;
    private Call<ResultBase> call2;
    private Call<ResultBase> call3;
    private Call<ResultBase<PageInfoBase<RemindInfo>>> call4;

    @Override
    public void attachView(AnnouncementMvpView mvpView) {
        announcementMvpView = mvpView;
    }

    @Override
    public void detachView() {
        announcementMvpView = null;
    }

    @Override
    public void onCancel() {
        if (call != null && call.isExecuted()) {
            call.cancel();
        }
        if (call2 != null && call2.isExecuted()) {
            call2.cancel();
        }

        if (call3 != null && call3.isExecuted()) {
            call3.cancel();
        }
    }

    /**
     * 首页的公告
     *
     * @param call
     */
    public void getGongGao(Call<ResultBase<List<GongGaoInfo>>> call) {
        this.call = call;
        this.call.enqueue(new ResponseCallback<ResultBase<List<GongGaoInfo>>>() {

            @Override
            public void onSuccess(Call call, ResultBase<List<GongGaoInfo>> gongGaoInfoResultBase) {
                LogX.e("====getGongGall", "------技师端   公告 ---> \n" + gongGaoInfoResultBase.getObj().toString());
                announcementMvpView.getGongGaoInfoSuccess(gongGaoInfoResultBase.getObj());
            }

            @Override
            public void onError(Call call, ResultBase<List<GongGaoInfo>> gongGaoInfoResultBase, int errorCode) {

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
     * 首页的今日预约数量
     *
     * @param call2
     */
    public void getAppointMentCount(Call<ResultBase> call2) {
        this.call2 = call2;
        call2.enqueue(new ResponseCallback<ResultBase>() {
            @Override
            public void onSuccess(Call call, ResultBase resultBase) {
                LogX.e("AnnouncementPresentergetAppointMentCount", resultBase.getObj().toString());
                announcementMvpView.getAppointMentCountSuccess(resultBase.getObj().toString());
            }

            @Override
            public void onError(Call call, ResultBase resultBase, int errorCode) {

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
     * 首页的今日申请退款数量
     *
     * @param call3
     */
    public void getRePayCount(Call<ResultBase> call3) {
        this.call3 = call3;
        call3.enqueue(new ResponseCallback<ResultBase>() {
            @Override
            public void onSuccess(Call call, ResultBase resultBase) {
                LogX.e("AnnouncementPresentergetGongGallgetAppointMentCount", resultBase.getObj().toString());
                announcementMvpView.getRePayApplyCountSuccess(resultBase.getObj().toString());
            }

            @Override
            public void onError(Call call, ResultBase resultBase, int errorCode) {

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
     * 首页的提醒数量
     *
     * @param call4
     */
    public void getWakeCount(Call<ResultBase<PageInfoBase<RemindInfo>>> call4) {
        this.call4 = call4;
        call4.enqueue(new ResponseCallback<ResultBase<PageInfoBase<RemindInfo>>>() {
            @Override
            public void onSuccess(Call call, ResultBase<PageInfoBase<RemindInfo>> resultBase) {
                LogX.e("AnnouncementPresentergetGongGallgetWakeCount", resultBase.getObj().toString());
                announcementMvpView.getWaitCountSuccess(resultBase.getObj().getTotalCount()+"");
//                announcementMvpView.getRePayApplyCountSuccess(resultBase.getObj().toString());
            }

            @Override
            public void onError(Call call, ResultBase resultBase, int errorCode) {

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
