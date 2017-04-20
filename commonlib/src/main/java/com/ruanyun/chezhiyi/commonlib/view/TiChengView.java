package com.ruanyun.chezhiyi.commonlib.view;

import com.ruanyun.chezhiyi.commonlib.model.TiChengInfoModel;

/**
 * Created by czy on 2017/4/18.
 * 施工/销售提成
 */

public interface TiChengView {
    void getTiChengSuccess(TiChengInfoModel tiChengInfoModel);

    void cancelTiChengErr();
}
