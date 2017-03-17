package com.ruanyun.chezhiyi.commonlib.presenter;

import com.ruanyun.chezhiyi.commonlib.base.ResultBase;
import com.ruanyun.chezhiyi.commonlib.data.api.ResponseCallback;
import com.ruanyun.chezhiyi.commonlib.model.SeckillHeadInfo;
import com.ruanyun.chezhiyi.commonlib.view.SeckillHeadDateMvpView;

import java.util.List;

import retrofit2.Call;

/**
 * Description ：秒杀头部日期获取
 * <p/>
 * Created by hdl on 2016/9/13.
 */
public class SeckillHeadDatePresentr implements Presenter<SeckillHeadDateMvpView> {

    SeckillHeadDateMvpView seckillHeadDateMvpView;
    Call<ResultBase<List<SeckillHeadInfo>>> call;

    @Override
    public void attachView(SeckillHeadDateMvpView mvpView) {
        seckillHeadDateMvpView = mvpView;
    }

    @Override
    public void detachView() {
        seckillHeadDateMvpView = null;
    }

    @Override
    public void onCancel() {
        if (call != null && !call.isCanceled())
            call.cancel();
    }

    /**
     * 获取秒杀头部日期
     * @param call
     */
    public void getSeckillHeadDate(Call<ResultBase<List<SeckillHeadInfo>>> call) {
        this.call = call;
        seckillHeadDateMvpView.showLoadingView("正在获取...");
        this.call.enqueue(new ResponseCallback<ResultBase<List<SeckillHeadInfo>>>() {
            @Override
            public void onSuccess(Call call, ResultBase<List<SeckillHeadInfo>> result) {
                   if(seckillHeadDateMvpView!=null)
                   seckillHeadDateMvpView.onHeadDateResult(result.getObj());
            }

            @Override
            public void onError(Call call, ResultBase<List<SeckillHeadInfo>> resultBase, int errorCode) {
                if(seckillHeadDateMvpView!=null)
                seckillHeadDateMvpView.showToast(resultBase.getMsg());
            }

            @Override
            public void onFail(Call call, String msg) {
                if(seckillHeadDateMvpView!=null)
                    seckillHeadDateMvpView.showToast(msg);
            }

            @Override
            public void onResult() {
                if(seckillHeadDateMvpView!=null)
                seckillHeadDateMvpView.dismissLoadingView();
            }
        });
    }

}
