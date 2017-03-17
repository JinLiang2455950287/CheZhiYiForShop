package com.ruanyun.chezhiyi.commonlib.view;

import com.ruanyun.chezhiyi.commonlib.base.MvpView;
import com.ruanyun.chezhiyi.commonlib.model.AccountMoneyInfo;

/**
 * Description ：
 * <p>
 * Created by ycw on 2016/10/17.
 */
public interface AccountMoneyMvpView extends MvpView {
    void getAccountMoneySuccess(AccountMoneyInfo moneyInfo);
}
