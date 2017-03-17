package com.ruanyun.chezhiyi.commonlib.view;

import com.ruanyun.chezhiyi.commonlib.base.MvpView;
import com.ruanyun.chezhiyi.commonlib.model.RecommendInfo;
import com.ruanyun.chezhiyi.commonlib.model.StoreInfo;
import com.ruanyun.chezhiyi.commonlib.model.User;

import java.util.List;

/**
 * Created by ycw on 2016/9/6.
 */
public interface StoreInfoMvpView extends MvpView {
    /**
     * 获取门店信息成功
     * @param storeInfo
     */
    void setHeadViewData(StoreInfo storeInfo);

    /**
     * 获取推荐项目成功
     * @param result
     */
    void getRecommendInfoOnSuccess(List<RecommendInfo> result);

    void disMissLoadingView();

    void showLoadingView();

    void getFriendShipInfoSuccess(User user);
}
