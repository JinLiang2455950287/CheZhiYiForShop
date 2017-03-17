package com.ruanyun.chezhiyi.commonlib.view;

import com.ruanyun.chezhiyi.commonlib.model.User;

import java.util.List;

/**
 * Description ：
 * <p/>
 * Created by ycw on 2016/10/27.
 */
public interface PickAssistUserMvpView {


    void getLeisureTechnicianSuccess(List<User> users);

    void showTips(String msg);

    void addAssistSuccess();

    void disMissLoadingView();

    void showLoadingView(String msg);
}
