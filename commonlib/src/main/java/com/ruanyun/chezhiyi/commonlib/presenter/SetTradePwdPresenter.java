package com.ruanyun.chezhiyi.commonlib.presenter;

import com.ruanyun.chezhiyi.commonlib.App;
import com.ruanyun.chezhiyi.commonlib.base.ResultBase;
import com.ruanyun.chezhiyi.commonlib.data.api.ResponseCallback;
import com.ruanyun.chezhiyi.commonlib.model.params.SetTradePwdParams;
import com.ruanyun.chezhiyi.commonlib.view.SetTradePwdMvpView;

import retrofit2.Call;

/**
 * Description:
 * author: zhangsan on 16/10/19 下午5:33.
 */
public class SetTradePwdPresenter implements Presenter<SetTradePwdMvpView> {

    SetTradePwdMvpView setTradePwdMvpView;
    @Override
    public void attachView(SetTradePwdMvpView mvpView) {
        setTradePwdMvpView=mvpView;
    }

    @Override
    public void detachView() {
       setTradePwdMvpView=null;
    }

    @Override
    public void onCancel() {

    }


    public void submitSetting(SetTradePwdParams pwdParams){
        if(setTradePwdMvpView!=null)
         setTradePwdMvpView.showLoadingView("处理中...");
        App.getInstance().getApiService().setTradePwd(App.getInstance().getCurrentUserNum(),pwdParams).enqueue(new ResponseCallback<ResultBase>() {
            @Override
            public void onSuccess(Call call, ResultBase resultBase) {
                if(setTradePwdMvpView!=null) {
                    setTradePwdMvpView.showLoadingView("处理中...");
                    setTradePwdMvpView.showToast(resultBase.getMsg());
                    setTradePwdMvpView.setSuccess();
                }
            }

            @Override
            public void onError(Call call, ResultBase resultBase, int errorCode) {
                if(setTradePwdMvpView!=null) {
                    setTradePwdMvpView.showToast(resultBase.getMsg());
                    setTradePwdMvpView.setFail();
                }
            }

            @Override
            public void onFail(Call call, String msg) {
                if(setTradePwdMvpView!=null) {
                    setTradePwdMvpView.showToast("请求失败");
                    setTradePwdMvpView.setFail();
                }
            }

            @Override
            public void onResult() {
                if(setTradePwdMvpView!=null)
                    setTradePwdMvpView.disMissLoadingView();
            }
        });
    }
}
