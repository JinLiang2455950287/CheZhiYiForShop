package com.ruanyun.chezhiyi.commonlib.view;

import com.ruanyun.chezhiyi.commonlib.model.MyListCount;

/**
 * Description ：
 * <p/>
 * Created by ycw on 2016/11/28.
 */
public interface ShopMyMvpView {
    void getSystemRemindListSuccess(int totalCount);

    /**
     *  服务工单的数量
     * @param obj
     */
    void getmyListCountSuccess(MyListCount obj);
}
