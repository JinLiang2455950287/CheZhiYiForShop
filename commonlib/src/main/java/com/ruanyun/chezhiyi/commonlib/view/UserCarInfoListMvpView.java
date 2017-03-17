package com.ruanyun.chezhiyi.commonlib.view;

import com.ruanyun.chezhiyi.commonlib.base.MvpView;
import com.ruanyun.chezhiyi.commonlib.model.CarInfo;

import java.util.List;

/**
 * Created by ycw on 2016/8/29.
 */
public interface UserCarInfoListMvpView extends MvpView{
    void showLoadingView();

    void dismissLoadingView();

    void onSuccess(List<CarInfo> carInfos);

    void onError(String msg, int errorCode);

    void onFail();
}
