package com.ruanyun.chezhiyi.commonlib.view;

import com.ruanyun.chezhiyi.commonlib.model.JieSuanInfo;

/**
 * Description ：
 * <p/>
 * Created by ycw on 2016/11/1.
 */
public interface JieSuanMvpView {
    void showLoadingView();

    void disMissLoadingView();

    void showTips(String msg);

    void getJieSuanSuccess(JieSuanInfo jieSuanInfo);
}
