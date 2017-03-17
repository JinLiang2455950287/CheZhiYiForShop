package com.ruanyun.chezhiyi.commonlib.presenter;


import com.ruanyun.chezhiyi.commonlib.App;
import com.ruanyun.chezhiyi.commonlib.base.ResultBase;
import com.ruanyun.chezhiyi.commonlib.data.api.ResponseCallback;
import com.ruanyun.chezhiyi.commonlib.model.CarBookingInfo;
import com.ruanyun.chezhiyi.commonlib.model.User;
import com.ruanyun.chezhiyi.commonlib.model.WorkBayInfo;
import com.ruanyun.chezhiyi.commonlib.model.params.SaveWashCarParams;
import com.ruanyun.chezhiyi.commonlib.view.QuikWashCarMvpView;


import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;

/**
 * Description:
 * author: zhangsan on 16/11/3 下午1:46.
 */
public class QuikWashCarPresenter implements Presenter<QuikWashCarMvpView> {
    private String currentUserNum = App.getInstance().getCurrentUserNum();
    QuikWashCarMvpView quikWashCarMvpView;

    @Override
    public void attachView(QuikWashCarMvpView mvpView) {
        quikWashCarMvpView = mvpView;
    }

    @Override
    public void detachView() {
        quikWashCarMvpView=null;
    }

    @Override
    public void onCancel() {

    }


    /**
     * 获取扫描车牌工单详情信息
     *
     * @author zhangsan
     * @date 16/10/9 下午5:22
     */
    public void getScanCustomerInfo(String carPlateNum) {
        if (quikWashCarMvpView != null)
            quikWashCarMvpView.showLoadingView("处理中...");
        App.getInstance().getApiService().scanLicenseGetBookingInfo(currentUserNum, carPlateNum).enqueue(new ResponseCallback<ResultBase<CarBookingInfo>>() {
            @Override
            public void onSuccess(Call call, ResultBase<CarBookingInfo> result) {
                if (quikWashCarMvpView != null){
                    quikWashCarMvpView.onScanCarBookingResult(result.getObj());
                }
            }

            @Override
            public void onError(Call call, ResultBase<CarBookingInfo> carBookingInfoResultBase, int errorCode) {

            }

            @Override
            public void onFail(Call call, String msg) {

            }

            @Override
            public void onResult() {
                if (quikWashCarMvpView != null)
                    quikWashCarMvpView.disMissLoadingView();
            }
        });
    }
    /**
      * 提交洗车信息
      *@author zhangsan
      *@date   16/11/3 下午5:13
      */
    public void submitWashCarInfo(SaveWashCarParams params) {
        if(quikWashCarMvpView!=null)
            quikWashCarMvpView.showLoadingView("处理中...");
        App.getInstance().getApiService().saveWashCar(App.getInstance().getCurrentUserNum(), params).enqueue(new ResponseCallback<ResultBase>() {
            @Override
            public void onSuccess(Call call, ResultBase resultBase) {
                if(quikWashCarMvpView!=null)
                    quikWashCarMvpView.submitWashCarSuccess();
            }

            @Override
            public void onError(Call call, ResultBase resultBase, int errorCode) {

            }

            @Override
            public void onFail(Call call, String msg) {

            }

            @Override
            public void onResult() {
                if(quikWashCarMvpView!=null)
                 quikWashCarMvpView.disMissLoadingView();
            }
        });
    }


    /**
     * 获取空闲技师
     *
     * @author zhangsan
     * @date 16/10/9 下午5:12
     */
    public void getFreeTechnian(final String projectNum) {
        if (quikWashCarMvpView != null)
            quikWashCarMvpView.showLoadingView("正在空闲技师...");
        App.getInstance().getApiService().getLeisureTechnician(currentUserNum, projectNum).enqueue(new ResponseCallback<ResultBase<List<User>>>() {
            @Override
            public void onSuccess(Call call, ResultBase<List<User>> result) {
                if (quikWashCarMvpView != null)
                    quikWashCarMvpView.onFreeTechnicanResult(result.getObj());
            }

            @Override
            public void onError(Call call, ResultBase<List<User>> result, int errorCode) {
                if (quikWashCarMvpView != null)
                    quikWashCarMvpView.showToast(result.getMsg());
            }

            @Override
            public void onFail(Call call, String msg) {
                if (quikWashCarMvpView != null)
                    quikWashCarMvpView.showToast(msg);
            }

            @Override
            public void onResult() {
                if (quikWashCarMvpView != null)
                    quikWashCarMvpView.disMissLoadingView();
            }
        });
    }

}
