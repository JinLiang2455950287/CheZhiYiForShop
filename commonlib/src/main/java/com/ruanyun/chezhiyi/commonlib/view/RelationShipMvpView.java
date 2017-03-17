package com.ruanyun.chezhiyi.commonlib.view;

import com.ruanyun.chezhiyi.commonlib.base.MvpView;
import com.ruanyun.chezhiyi.commonlib.model.User;

/**
 * 用户关系
 * Created by ycw on 2016/8/31.
 */
public interface RelationShipMvpView extends MvpView {
    void showLoadingView();

    void disMissLoadingView();

    void getFriendShipInfoSuccess(User user);

    void getFriendShipInfoError();

    void getFriendShipInfoFail();
}
