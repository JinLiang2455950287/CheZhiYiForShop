package com.ruanyun.chezhiyi.commonlib.view;

import com.ruanyun.chezhiyi.commonlib.model.YuYueItemBean;

import java.util.List;

/**
 * Created by 创智 on 2017/3/13.
 * 预约处理
 */

public interface AppointMentMvpView {
    void getDataSuccess(List<YuYueItemBean> listinfo,String string);

    void dismissLoadingView();
    void showEmptyView();

    void showLoadingView();

    void cancelSuccess();
}
