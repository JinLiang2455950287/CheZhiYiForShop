package com.ruanyun.chezhiyi.commonlib.view;

import com.ruanyun.chezhiyi.commonlib.model.TuiKuanInfo;

import java.util.List;

/**
 * Created by 创智 on 2017/3/13.
 * 退款
 */

public interface RePayMvpView {
    void getDataSuccess(List<TuiKuanInfo> listinfo);

    void dismissLoadingView();

    void showLoadingView();

    void cancelSuccess();
}
