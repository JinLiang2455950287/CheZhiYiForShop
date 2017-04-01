package com.ruanyun.chezhiyi.commonlib.view;

import com.ruanyun.chezhiyi.commonlib.model.CardPackageListModel;

import java.util.List;

/**
 * Created by czy on 2017/3/21.
 * 卡套餐view
 */

public interface CardPackageView {
    void getDataSuccess(List<CardPackageListModel> listinfo);

    void dismissLoadingView();

    void showLoadingView();

    void cancelSuccess();
}
