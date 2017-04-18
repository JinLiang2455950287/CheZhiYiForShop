package com.ruanyun.chezhiyi.commonlib.view;

import com.ruanyun.chezhiyi.commonlib.model.TiChengListModel;

import java.util.List;

/**
 * Created by czy on 2017/4/18.
 * 施工/销售List详情提成
 */

public interface TiChengDetailListView {
    void getTiChengListSuccess(List<TiChengListModel> resultBase);

    void dismissListLoadingView();

    void cancelTiChengListErr();
}
