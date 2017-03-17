package com.ruanyun.chezhiyi.commonlib.view;

import com.ruanyun.chezhiyi.commonlib.base.MvpView;
import com.ruanyun.chezhiyi.commonlib.model.HuiYuanKuaiChaInfo;

import java.util.List;

/**
 * Created by 创智 on 2017/3/13.
 * 会员快查
 */

public interface MemberSearchMvpView extends MvpView {
    void getDataSuccess(List<HuiYuanKuaiChaInfo> listinfo);

    void dismissLoadingView();

    void showLoadingView();

    void cancelSuccess();
}
