package com.ruanyun.chezhiyi.commonlib.view;

import com.ruanyun.chezhiyi.commonlib.model.MenDianHuiYuanInfo;

/**
 * Created by czy on 2017/4/18.
 * 会员统计
 */

public interface HuiYuanTongJiView {
    void getHSuccess(MenDianHuiYuanInfo menDianHuiYuanInfo);

    void cancelTiChengErr();
}
