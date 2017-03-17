package com.ruanyun.chezhiyi.commonlib.view;

import com.ruanyun.chezhiyi.commonlib.base.MvpView;

/**
 * Description:
 * author: zhangsan on 16/10/19 下午5:19.
 */
public interface SetTradePwdMvpView extends MvpView {

    void showToast(String msg);
    void showLoadingView(String msg);
    void disMissLoadingView();

    void setSuccess();
    void setFail();
}
