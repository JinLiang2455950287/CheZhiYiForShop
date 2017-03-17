package com.ruanyun.chezhiyi.commonlib.view;

import com.ruanyun.chezhiyi.commonlib.base.MvpView;
import com.ruanyun.chezhiyi.commonlib.base.ResultBase;
import com.ruanyun.chezhiyi.commonlib.model.User;

/**
 * 账户管理
 * Created by Sxq on 2016/9/9.
 */
public interface AccountMangementMvpView extends MvpView {
    void accountMangementSuccess(User user);

    /**
     * 显示提示消息
     * @param msg
     */
    void showTipMsg(String msg);

    void showLoadingView();

    void dismissLoadingView();

    /**
     * 修改账户信息失败
     */
    void accountMangementFail();
}
