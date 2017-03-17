package com.ruanyun.chezhiyi.commonlib.view;

import com.ruanyun.chezhiyi.commonlib.model.CarInfo;

import java.util.List;

/**
 * Description:
 * author: zhangsan on 16/9/22 上午11:30.
 */
public interface AddCarMvpView {
    int TYPE_GET=454;
    int TYPE_ADD_UPDATE=4545;
    void showLodingView(String msg);
    void disMissLoadingView();
    void showToast(String msg);
    void carListResult(List<CarInfo> carInfos,int type);
    void carResult(CarInfo carInfo);
    void onPostEditSuccess();
    void onPostEditFail();
    void onDeleteSuccess(CarInfo carInfo);
}
