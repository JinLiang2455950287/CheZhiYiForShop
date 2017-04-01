package com.ruanyun.chezhiyi.commonlib.view;


import com.ruanyun.chezhiyi.commonlib.model.CardPackageDetailInfo;

/**
 * Created by czy on 2017/3/21.
 * 卡套餐详情
 */

public interface CardPackageInfoView {
    void getDataSuccess(CardPackageDetailInfo cardPackageDetailInfo);

    void dismissLoadingView();

    void showLoadingView();

    void cancelSuccess();
}
