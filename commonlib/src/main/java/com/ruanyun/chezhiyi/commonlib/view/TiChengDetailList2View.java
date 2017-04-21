package com.ruanyun.chezhiyi.commonlib.view;

import com.ruanyun.chezhiyi.commonlib.model.TiChengListPublicInfo;

/**
 * Created by czy on 2017/4/18.
 * 施工/销售List详情提成
 */

public interface TiChengDetailList2View {
    void getTiChengList2Success(TiChengListPublicInfo tiChengListPublicInfo);

    void dismissList2LoadingView();

    void cancelTiChengList2Err();
}
