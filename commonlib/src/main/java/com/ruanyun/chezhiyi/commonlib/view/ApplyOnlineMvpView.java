package com.ruanyun.chezhiyi.commonlib.view;

import com.ruanyun.chezhiyi.commonlib.base.ResultBase;
import com.ruanyun.chezhiyi.commonlib.model.SignAddInfo;

/**
 * Description ：在线报名
 * <p/>
 * Created by ycw on 2016/9/14.
 */
public interface ApplyOnlineMvpView {
    void showSignAddLoadingView();

    void getSignAddOnSuccess(ResultBase<SignAddInfo> signAddInfoResultBase);

    void dismissSignAddLoadingView();

    void showSignAddTip(String msg);
}
