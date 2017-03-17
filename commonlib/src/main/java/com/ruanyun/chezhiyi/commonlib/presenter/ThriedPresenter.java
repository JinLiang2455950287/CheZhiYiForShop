package com.ruanyun.chezhiyi.commonlib.presenter;

import com.ruanyun.chezhiyi.commonlib.base.ResultBase;
import com.ruanyun.chezhiyi.commonlib.data.api.ResponseCallback;
import com.ruanyun.chezhiyi.commonlib.model.ThirdInfo;
import com.ruanyun.chezhiyi.commonlib.model.User;
import com.ruanyun.chezhiyi.commonlib.view.ThirdMvpView;

import java.util.List;

import retrofit2.Call;

/**
 * Description ：
 * <p/>
 * Created by ycw on 2016/10/17.
 */
public class ThriedPresenter implements Presenter<ThirdMvpView> {

    ThirdMvpView thirdMvpView;

    Call call;
    Call<ResultBase<User>> thirdCall;


    @Override
    public void attachView(ThirdMvpView mvpView) {
        thirdMvpView = mvpView;
    }

    @Override
    public void detachView() {
        thirdMvpView = null;
    }

    @Override
    public void onCancel() {

    }

    /**
     * 获取第三方的登录列表
     * @param call
     */
    public void getThirdList(Call call){
        if (thirdMvpView == null) return;
        thirdMvpView.showLoadingView();
        this.call = call;
        this.call.enqueue(new ResponseCallback<ResultBase<List<ThirdInfo>>>() {
            @Override
            public void onSuccess(Call call, ResultBase<List<ThirdInfo>> listResultBase) {
                if (thirdMvpView == null) return;
                thirdMvpView.getListSuccess(listResultBase.getObj());
            }

            @Override
            public void onError(Call call, ResultBase<List<ThirdInfo>> listResultBase, int errorCode) {

            }

            @Override
            public void onFail(Call call, String msg) {

            }

            @Override
            public void onResult() {
                if (thirdMvpView == null) return;
                thirdMvpView.dismissLoadingView();
            }
        });

    }


    /**
     * 删除第三方
     * @param call
     */
    public void delThird(Call<ResultBase> call) {
        this.call = call;
        if (thirdMvpView == null) return;
        thirdMvpView.showLoadingView();
        this.call.enqueue(new ResponseCallback<ResultBase>() {
            @Override
            public void onSuccess(Call call, ResultBase resultBase) {
                if (thirdMvpView == null) return;
                thirdMvpView.showTip(resultBase.getMsg());
                thirdMvpView.delThirdSuccess();
            }

            @Override
            public void onError(Call call, ResultBase resultBase, int errorCode) {

            }

            @Override
            public void onFail(Call call, String msg) {

            }

            @Override
            public void onResult() {
                if (thirdMvpView == null) return;
                thirdMvpView.dismissLoadingView();
            }
        });
    }




    /**
     *  第三方注册绑定接口
     * @param thirdCall
     */
    public void addThird(Call<ResultBase<User>> thirdCall) {
        if (thirdMvpView == null) return;
        thirdMvpView.showLoadingView();
        this.thirdCall = thirdCall;
        this.thirdCall.enqueue(new ResponseCallback<ResultBase<User>>() {
            @Override
            public void onSuccess(Call call, ResultBase<User> userResultBase) {
                if (thirdMvpView == null) return;
//                thirdMvpView.showTip(userResultBase.getMsg());
                thirdMvpView.thirdBindLogin(userResultBase.getObj());
            }

            @Override
            public void onError(Call call, ResultBase<User> userResultBase, int errorCode) {
                if (thirdMvpView == null) return;
                thirdMvpView.showTip(userResultBase.getMsg());

            }

            @Override
            public void onFail(Call call, String msg) {
                if (thirdMvpView == null) return;
                thirdMvpView.showTip(msg);
            }

            @Override
            public void onResult() {
                if (thirdMvpView == null) return;
                thirdMvpView.dismissLoadingView();
            }
        });
    }

}
