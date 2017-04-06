package com.ruanyun.chezhiyi.commonlib.view;

import com.ruanyun.chezhiyi.commonlib.model.CustomerAccountModel;

/**
 * Created by czy on 2017/4/6.
 * 获取获取客户余额会员余额
 */

public interface CustomerAccountView {
    void getRemainSuccess(CustomerAccountModel customerAccount);

    void getRemainFalse();

}
