package com.ruanyun.chezhiyi.commonlib.view;


import com.ruanyun.chezhiyi.commonlib.model.MenDianGongDanInfo;

/**
 * Created by czy on 2017/4/18.
 * 工单统计
 */

public interface HuiYuanGongDanTongJiView {
    void getGongDanSuccess(MenDianGongDanInfo menDianGongDanInfo);

    void cancelGongDanTiChengErr();
}
