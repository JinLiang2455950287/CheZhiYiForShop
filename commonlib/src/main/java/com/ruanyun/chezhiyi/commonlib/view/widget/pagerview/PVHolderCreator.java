package com.ruanyun.chezhiyi.commonlib.view.widget.pagerview;

import android.view.View;

public interface PVHolderCreator<T> {
//    public Holder createHolder();

    /**
     * 每行的总数
     * @return
     */
    int perPagerRow();

    /**
     * 每页的行数
     * @return
     */
    int perRowCount();

    View addItemView(T dates);
}