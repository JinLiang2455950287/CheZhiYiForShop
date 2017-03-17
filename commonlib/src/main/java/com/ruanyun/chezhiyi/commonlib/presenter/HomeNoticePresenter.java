package com.ruanyun.chezhiyi.commonlib.presenter;

import com.ruanyun.chezhiyi.commonlib.base.ResultBase;
import com.ruanyun.chezhiyi.commonlib.data.api.ResponseCallback;
import com.ruanyun.chezhiyi.commonlib.model.NoticeInfo;
import com.ruanyun.chezhiyi.commonlib.model.ReportInfo;
import com.ruanyun.chezhiyi.commonlib.util.LogX;
import com.ruanyun.chezhiyi.commonlib.view.NoticeMvpView;

import retrofit2.Call;

/**
 * 技师端   首页的通知
 * Created by ycw on 2016/9/6.
 */
public class HomeNoticePresenter implements Presenter<NoticeMvpView> {

    NoticeMvpView noticeMvpView;
    private Call<ResultBase<NoticeInfo>> call;
    private Call<ResultBase<ReportInfo>> reportCall;

    @Override
    public void attachView(NoticeMvpView mvpView) {
        noticeMvpView = mvpView;
    }

    @Override
    public void detachView() {
        noticeMvpView = null;
    }

    @Override
    public void onCancel() {
        if (call != null && call.isExecuted()) {
            call.cancel();
        }
    }


    /**
     * 获取门店详细
     * @param call
     */
    public void getNoticeInfo(Call<ResultBase<NoticeInfo>> call) {
        this.call = call;
        this.call.enqueue(new ResponseCallback<ResultBase<NoticeInfo>>() {
            @Override
            public void onSuccess(Call call, ResultBase<NoticeInfo> noticeInfoResultBase) {
                if (noticeMvpView == null) return;
                LogX.e("=======", "------技师端   公告 ---> \n" + noticeInfoResultBase.getObj().toString());
//                noticeMvpView.setNoticeInfo(noticeInfoResultBase.getObj());
            }

            @Override
            public void onError(Call call, ResultBase<NoticeInfo> noticeInfoResultBase, int
                    errorCode) {

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
     * 获取门店的人数等
     * @param call
     */
    public void getReportInfo(Call<ResultBase<ReportInfo>> call) {
        this.reportCall = call;
        this.reportCall.enqueue(new ResponseCallback<ResultBase<ReportInfo>>() {
            @Override
            public void onSuccess(Call call, ResultBase<ReportInfo> reportInfoResultBase) {
                if (noticeMvpView == null) return;
                LogX.e("=======", "------技师端   销售提成/施工提成/用户数 ---> \n" + reportInfoResultBase.getObj().toString());
                noticeMvpView.getReportInfoSuccess(reportInfoResultBase.getObj());
            }

            @Override
            public void onError(Call call, ResultBase<ReportInfo> reportInfoResultBase, int
                    errorCode) {
                if (noticeMvpView == null) return;
                noticeMvpView.getReportInfoError();
            }

            @Override
            public void onFail(Call call, String msg) {
                if (noticeMvpView == null) return;
                noticeMvpView.getReportInfoError();
            }

            @Override
            public void onResult() {
                if (noticeMvpView == null) return;
                noticeMvpView.getReportInfoResult();
            }
        });
    }

}
