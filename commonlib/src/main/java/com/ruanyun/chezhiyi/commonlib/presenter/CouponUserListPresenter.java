package com.ruanyun.chezhiyi.commonlib.presenter;

import com.ruanyun.chezhiyi.commonlib.base.ResultBase;
import com.ruanyun.chezhiyi.commonlib.data.api.ResponseCallback;
import com.ruanyun.chezhiyi.commonlib.model.OrderGoodsInfo;
import com.ruanyun.chezhiyi.commonlib.model.User;
import com.ruanyun.chezhiyi.commonlib.view.CouponUserListMvpView;

import java.util.List;

import retrofit2.Call;

/**
 * Description ：
 * <p/>
 * Created by ycw on 2016/11/28.
 */
public class CouponUserListPresenter implements Presenter<CouponUserListMvpView>{

    CouponUserListMvpView mvpView;
    private Call<ResultBase<User>> call;


    @Override
    public void attachView(CouponUserListMvpView mvpView) {
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
     *  特殊查询  查询 优惠劵用户列表
     */
    public void commonList(Call<ResultBase<List<OrderGoodsInfo>>> baseCall){
        if (mvpView == null) return;
        mvpView.showLoadingView("获取中...");
        baseCall.enqueue(new ResponseCallback<ResultBase<List<OrderGoodsInfo>>>() {
            @Override
            public void onSuccess(Call call, ResultBase<List<OrderGoodsInfo>> pageInfoBaseResultBase) {
                if (mvpView == null) return;
                mvpView.getCommonListSuccess(pageInfoBaseResultBase.getObj());
            }

            @Override
            public void onError(Call call, ResultBase<List<OrderGoodsInfo>> pageInfoBaseResultBase, int errorCode) {
                if (mvpView == null) return;
                mvpView.showLoadingView(pageInfoBaseResultBase.getMsg());
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
     * 获取用户信息
     * @param call
     */
    public void getUser(Call<ResultBase<User>> call) {
        if (mvpView == null) return;
        mvpView.showLoadingView("获取用户信息");
        this.call = call;
        this.call.enqueue(new ResponseCallback<ResultBase<User>>() {
            @Override
            public void onSuccess(Call call, ResultBase<User> userResult) {
                if (mvpView == null) return;
                mvpView.getUserSuccess(userResult.getObj());

            }

            @Override
            public void onError(Call call, ResultBase<User> userResult, int errorCode) {
                if (mvpView == null) return;
                mvpView.showTips(userResult.getMsg());
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
