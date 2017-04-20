package com.ruanyun.chezhiyi.commonlib.view;

import com.ruanyun.chezhiyi.commonlib.model.MenDianYinYeEInfo;

/**
 * Created by czy on 2017/4/20.
 * 营业额统计
 */

public interface HuiYuanYingYeETongJiView {
    void getYinYeESuccess(MenDianYinYeEInfo resultBase);

    void cancelYinYeETiChengErr();
}
