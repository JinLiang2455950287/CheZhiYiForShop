package com.ruanyun.chezhiyi.commonlib.view;

import com.ruanyun.chezhiyi.commonlib.base.ResultBase;
import com.ruanyun.chezhiyi.commonlib.model.PerssionBean;

import java.util.List;

import retrofit2.Call;

/**
 * Created by czy on 2017/3/27.
 * 主页面的权限
 */

public interface HomePerssionMvpView {
    void getDataSuccess(List<PerssionBean> perssionList);

    void getDataFault();

}
