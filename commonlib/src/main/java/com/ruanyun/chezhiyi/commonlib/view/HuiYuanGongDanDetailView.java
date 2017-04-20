package com.ruanyun.chezhiyi.commonlib.view;


import com.ruanyun.chezhiyi.commonlib.model.MenDianGongDanDetailInfo;

/**
 * Created by czy on 2017/4/20.
 * 工单detail统计
 */

public interface HuiYuanGongDanDetailView {
    void getGongDanDetailSuccess(MenDianGongDanDetailInfo menDianGongDanDetailInfo);

    void cancelGongDanTiDetailChengErr();
}
