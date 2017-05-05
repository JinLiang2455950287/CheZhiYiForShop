package com.ruanyun.chezhiyi.commonlib.presenter;

import com.ruanyun.chezhiyi.commonlib.App;
import com.ruanyun.chezhiyi.commonlib.base.ResultBase;
import com.ruanyun.chezhiyi.commonlib.data.api.ResponseCallback;
import com.ruanyun.chezhiyi.commonlib.util.LogX;
import com.ruanyun.chezhiyi.commonlib.util.compressimage.CompressImageTask;
import com.ruanyun.chezhiyi.commonlib.util.compressimage.CompressTaskCallback;
import com.ruanyun.chezhiyi.commonlib.view.CaseLibraryMvpView;
import com.ruanyun.imagepicker.bean.CompressImageInfoGetter;
import com.ruanyun.imagepicker.bean.ImageItem;

import java.util.HashMap;
import java.util.List;

import okhttp3.RequestBody;
import retrofit2.Call;

/**
 * 按案例库的
 * Created by ycw on 2016/9/9.
 */
public class CaseLibraryPresenter implements Presenter<CaseLibraryMvpView> {

    CaseLibraryMvpView caseLibraryMvpView;
    Call<ResultBase> call;
    private CompressImageTask imageTask;


    @Override
    public void attachView(CaseLibraryMvpView mvpView) {
        caseLibraryMvpView = mvpView;
    }

    @Override
    public void detachView() {
        caseLibraryMvpView = null;
        if (imageTask != null)
            imageTask.cancel();
    }

    @Override
    public void onCancel() {
        if (call != null && !call.isCanceled())
            call.cancel();
    }


    /**
     * 新建  或  修改案例库  有图片的
     *
     * @param caseMapParams
     * @param addItemList
     */
    public void addAndUpdateCaseLibrary(final HashMap<String, RequestBody> caseMapParams, List<ImageItem> addItemList) {
        if (caseLibraryMvpView == null) return;
        caseLibraryMvpView.showLoadingView();
        imageTask = App.getInstance().imageProxyService.getCompressTask("attachInfoPic", (CompressImageInfoGetter[]) addItemList.toArray(new ImageItem[0]));
        LogX.i("imageTask", "--------------->" + imageTask.toString());
        imageTask.start(new CompressTaskCallback<HashMap<String, RequestBody>>() {
            @Override
            public void onCompresComplete(HashMap<String, RequestBody> compressResults) {
                caseMapParams.putAll(compressResults);
                updateCaseInfo(caseMapParams);
                LogX.e("图片呀", caseMapParams.toString());
            }

            @Override
            public void onCompresFail(Throwable throwable) {

            }
        });
    }

    /**
     * 新增  修改  案例库
     *
     * @param caseMapParams
     */
    public void updateCaseInfo(HashMap<String, RequestBody> caseMapParams) {
        this.call = App.getInstance().getApiService().getCaseAddUpdateInfo(App.getInstance().getCurrentUserNum(), caseMapParams);
        this.call.enqueue(new ResponseCallback<ResultBase>() {
            @Override
            public void onSuccess(Call call, ResultBase resultBase) {
                if (caseLibraryMvpView == null) return;
                caseLibraryMvpView.showAddOrUpCaseLibSuccessTip(resultBase);
            }

            @Override
            public void onError(Call call, ResultBase resultBase, int errorCode) {
                if (caseLibraryMvpView == null) return;
                caseLibraryMvpView.showAddOrUpCaseLibErrorTip(resultBase);
            }

            @Override
            public void onFail(Call call, String msg) {
                if (caseLibraryMvpView == null) return;
                caseLibraryMvpView.showAddOrUpCaseLibFailTip(msg);
            }

            @Override
            public void onResult() {
                if (caseLibraryMvpView == null) return;
                caseLibraryMvpView.dismissLoadingView();
            }
        });
    }

}
