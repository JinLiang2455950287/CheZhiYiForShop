package com.ruanyun.chezhiyi.commonlib.presenter;

import android.os.AsyncTask;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ruanyun.chezhiyi.commonlib.App;
import com.ruanyun.chezhiyi.commonlib.base.PageInfoBase;
import com.ruanyun.chezhiyi.commonlib.base.ResultBase;
import com.ruanyun.chezhiyi.commonlib.data.api.ResponseCallback;
import com.ruanyun.chezhiyi.commonlib.model.CarInfo;
import com.ruanyun.chezhiyi.commonlib.model.CaseInfo;
import com.ruanyun.chezhiyi.commonlib.util.C;
import com.ruanyun.chezhiyi.commonlib.util.DbHelper;
import com.ruanyun.chezhiyi.commonlib.view.AddCarMvpView;
import com.ruanyun.chezhiyi.commonlib.view.GetCaseMvpView;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;

/**
 * Description:
 * author: zhangsan on 16/9/22 下午2:39.
 */
public class GetCasePresenter implements Presenter<GetCaseMvpView> {

    GetCaseMvpView getCaseMvpView;
    Call<ResultBase<PageInfoBase<CaseInfo>>> caseInfoCall;

    @Override
    public void attachView(GetCaseMvpView mvpView) {
        getCaseMvpView = mvpView;
    }

    @Override
    public void detachView() {
        getCaseMvpView = null;
    }

    @Override
    public void onCancel() {
    }

    /**
     * 获取案例信息
     * @param caseInfoCall
     */
    public void getCaseList(Call<ResultBase<PageInfoBase<CaseInfo>>> caseInfoCall) {
        if (getCaseMvpView != null)
            getCaseMvpView.showLodingView("正在获取案例信息...");
        this.caseInfoCall = caseInfoCall;
        this.caseInfoCall.enqueue(new ResponseCallback<ResultBase<PageInfoBase<CaseInfo>>>() {
            @Override
            public void onSuccess(Call call, ResultBase<PageInfoBase<CaseInfo>> pageInfoBaseResultBase) {
                if (getCaseMvpView == null) return;
                getCaseMvpView.getCaseListOnSuccess(pageInfoBaseResultBase.getObj().getResult());
            }

            @Override
            public void onError(Call call, ResultBase<PageInfoBase<CaseInfo>> pageInfoBaseResultBase, int errorCode) {

            }

            @Override
            public void onFail(Call call, String msg) {

            }

            @Override
            public void onResult() {
                if (getCaseMvpView != null)
                    getCaseMvpView.dismissLoadingView();
            }
        });
    }

}
