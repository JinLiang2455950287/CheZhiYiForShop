package com.ruanyun.chezhiyi.commonlib.view;


import com.ruanyun.chezhiyi.commonlib.model.MyGongDanInfo;

import java.util.List;

/**
 * Created by czy on 2017/4/18.
 * 我的模块工单统计
 */

public interface MyGongDanTongJiView {
    void getGongDanSuccess(MyGongDanInfo myGongDanInfo);

    void cancelGongDanTiChengErr();
}
