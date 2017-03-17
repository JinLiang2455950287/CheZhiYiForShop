package com.ruanyun.chezhiyi.commonlib.view;

import com.ruanyun.chezhiyi.commonlib.base.ResultBase;

/**
 * 记账本明细的视图 删除记账本
 * Created by hdl on 2016/9/10.
 */
public interface AccountBookDeleteMvpView {

    void showLoadingView();

    void dismissLoadingView();

    void showDeleteAccountBookTip(String msg);

}
