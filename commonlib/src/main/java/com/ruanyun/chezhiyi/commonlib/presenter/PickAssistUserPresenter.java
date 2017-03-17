package com.ruanyun.chezhiyi.commonlib.presenter;

import com.ruanyun.chezhiyi.commonlib.base.ResultBase;
import com.ruanyun.chezhiyi.commonlib.data.api.ResponseCallback;
import com.ruanyun.chezhiyi.commonlib.model.User;
import com.ruanyun.chezhiyi.commonlib.view.PickAssistUserMvpView;

import java.util.List;

import retrofit2.Call;

/**
 * Description ：
 * <p/>
 * Created by ycw on 2016/10/27.
 */
public class PickAssistUserPresenter implements Presenter<PickAssistUserMvpView>  {

    Call<ResultBase<List<User>>> call;
    Call<ResultBase> addCall;
    PickAssistUserMvpView mvpView;

    @Override
    public void attachView(PickAssistUserMvpView mvpView) {
        this.mvpView = mvpView;
    }

    @Override
    public void detachView() {
        this.mvpView = null;
    }

    @Override
    public void onCancel() {

    }

    /**
     * 获取空闲技师
     * @param call
     */
    public void getLeisureTechnician(Call<ResultBase<List<User>>> call){
        if (mvpView == null) return;
        mvpView.showLoadingView("获取技师...");
        this.call = call;
        this.call.enqueue(new ResponseCallback<ResultBase<List<User>>>() {
            @Override
            public void onSuccess(Call call, ResultBase<List<User>> listResultBase) {
                if (mvpView == null) return;
                mvpView.getLeisureTechnicianSuccess(listResultBase.getObj());

            }

            @Override
            public void onError(Call call, ResultBase<List<User>> listResultBase, int errorCode) {
                if (mvpView == null) return;
                mvpView.showTips(listResultBase.getMsg());
            }

            @Override
            public void onFail(Call call, String msg) {

            }

            @Override
            public void onResult() {
                if (mvpView == null) return;
                mvpView.disMissLoadingView();
            }
        });
    }

    /**
     * 添加协助技师
     * @param call
     */
    public void addAssist(Call<ResultBase> call){
        if (mvpView == null) return;
        mvpView.showLoadingView("添加中...");
        this.addCall = call;
        this.addCall.enqueue(new ResponseCallback<ResultBase>() {
            @Override
            public void onSuccess(Call call, ResultBase resultBase) {
                if (mvpView == null) return;
//                mvpView.showTips(resultBase.getMsg());
                mvpView.addAssistSuccess();

            }

            @Override
            public void onError(Call call, ResultBase resultBase, int errorCode) {
                if (mvpView == null) return;
                mvpView.showTips(resultBase.getMsg());
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
