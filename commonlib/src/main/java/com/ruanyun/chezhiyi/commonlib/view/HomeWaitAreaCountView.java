package com.ruanyun.chezhiyi.commonlib.view;

import com.ruanyun.chezhiyi.commonlib.base.ResultBase;

/**
 * Created by czy on 2017/3/27.
 * 主页面等候区数量
 */

public interface HomeWaitAreaCountView {
    void getWaitAreaCount(ResultBase resultBase);

    void getZhiJianAreaCount(ResultBase resultBase);

    void getJieSuanAreaCount(ResultBase resultBase);

    void getWaitAreaCounterr();
}
