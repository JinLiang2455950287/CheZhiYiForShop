package com.ruanyun.chezhiyi.commonlib.view;


import com.ruanyun.chezhiyi.commonlib.model.WorkBayInfo;

import java.util.List;

/**
 * Created by czy on 2017/4/18.
 * 工位
 */

public interface KaiDanGongweiView {
    void getKaiDanSuccess(List<WorkBayInfo> workBayInfo);

    void cancelKaiDanTiChengErr();
}
