package com.ruanyun.chezhiyi.commonlib.view;

import com.ruanyun.chezhiyi.commonlib.base.ResultBase;
import com.ruanyun.chezhiyi.commonlib.model.RechangeInfo;
import com.ruanyun.chezhiyi.commonlib.model.YuYueItemBean;

import java.util.List;

/**
 * Created by czy on 2017/3/20.
 * 预约处理
 */

public interface YuYueDealMvpView {

    void getDataSuccess(ResultBase resultBase);

    void dismissLoadingView();

    void showLoadingView();

    void cancelSuccess();
}
