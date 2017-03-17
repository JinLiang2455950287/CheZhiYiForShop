package com.ruanyun.chezhiyi.commonlib.view;

import com.ruanyun.chezhiyi.commonlib.model.CaseInfo;

import java.util.List;

/**
 * Description ：
 * <p/>
 * Created by ycw on 2016/10/21.
 */
public interface GetCaseMvpView {
    void showLodingView(String msg);

    void getCaseListOnSuccess(List<CaseInfo> caseInfoList);

    void dismissLoadingView();
}
