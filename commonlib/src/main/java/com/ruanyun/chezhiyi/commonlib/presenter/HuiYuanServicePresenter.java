package com.ruanyun.chezhiyi.commonlib.presenter;

import com.ruanyun.chezhiyi.commonlib.base.ResultBase;
import com.ruanyun.chezhiyi.commonlib.data.api.ResponseCallback;
import com.ruanyun.chezhiyi.commonlib.model.MenDianGongDanInfo;
import com.ruanyun.chezhiyi.commonlib.model.MenDianServiceGoodsInfo;
import com.ruanyun.chezhiyi.commonlib.util.LogX;
import com.ruanyun.chezhiyi.commonlib.view.HuiYuanGongDanTongJiView;
import com.ruanyun.chezhiyi.commonlib.view.HuiYuanServiceGoodTongJiView;

import retrofit2.Call;

/**
 * 门店 服务商品统计
 * Created by czy on 2017/4/20.
 */

public class HuiYuanServicePresenter implements Presenter<HuiYuanServiceGoodTongJiView> {

    private HuiYuanServiceGoodTongJiView huiYuanServiceGoodTongJiView;

    private Call<ResultBase<MenDianServiceGoodsInfo>> call;

    @Override
    public void attachView(HuiYuanServiceGoodTongJiView mvpView) {
        this.huiYuanServiceGoodTongJiView = mvpView;
    }

    public void getServiceGoodsInfo(Call<ResultBase<MenDianServiceGoodsInfo>> call) {
        this.call = call;
        call.enqueue(new ResponseCallback<ResultBase<MenDianServiceGoodsInfo>>() {
            @Override
            public void onSuccess(Call call, ResultBase<MenDianServiceGoodsInfo> resultBase) {
                LogX.e("服务商品persenter", resultBase.getObj().toString());
                huiYuanServiceGoodTongJiView.getServiceGoodsSuccess(resultBase.getObj());
            }

            @Override
            public void onError(Call call, ResultBase resultBase, int errorCode) {

            }

            @Override
            public void onFail(Call call, String msg) {
                LogX.e("服务商品persenteronError", msg.toString() + "");
            }

            @Override
            public void onResult() {

            }
        });

    }

    @Override
    public void detachView() {
        huiYuanServiceGoodTongJiView = null;
    }

    @Override
    public void onCancel() {
        if (call != null && !call.isCanceled())
            call.cancel();
    }
}
