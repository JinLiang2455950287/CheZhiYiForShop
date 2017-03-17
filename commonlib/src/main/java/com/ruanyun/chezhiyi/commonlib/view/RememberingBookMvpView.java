package com.ruanyun.chezhiyi.commonlib.view;

import com.ruanyun.chezhiyi.commonlib.base.ResultBase;

/**
 * 记账本记一笔的视图
 * Created by hdl on 2016/9/10.
 */
public interface RememberingBookMvpView {

    void showLoadingView();

    void dismissLoadingView();

    void showAddOrUpAccountBookSuccessTip(ResultBase resultBase);

    void showAddOrUpAccountBookErrorOrFailTip(String msg);

}
