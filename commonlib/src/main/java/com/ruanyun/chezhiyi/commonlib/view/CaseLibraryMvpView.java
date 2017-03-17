package com.ruanyun.chezhiyi.commonlib.view;

import com.ruanyun.chezhiyi.commonlib.base.ResultBase;

/**
 * 案例库的视图
 * Created by ycw on 2016/9/9.
 */
public interface CaseLibraryMvpView {


    void showLoadingView();

    void dismissLoadingView();

    void showAddOrUpCaseLibSuccessTip(ResultBase resultBase);

    void showAddOrUpCaseLibErrorTip(ResultBase resultBase);

    void showAddOrUpCaseLibFailTip(String msg);
}
