package com.ruanyun.chezhiyi.commonlib.view;

/**
 * Description ：
 * <p>
 * Created by ycw on 2016/10/14.
 */
public interface AppraiseMvpView {
    void showTip(String msg);

    void addCommentSuccess();

    void dismissLoadingView();

    void showLoadingView();
}
