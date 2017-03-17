package com.ruanyun.chezhiyi.commonlib.view;

import com.ruanyun.chezhiyi.commonlib.base.MvpView;
import com.ruanyun.chezhiyi.commonlib.base.ResultBase;
import com.ruanyun.chezhiyi.commonlib.model.RechangeInfo;

/**
 * 充值
 * Created by Sxq on 2016/10/10.
 */
public interface RechargeMvpView extends MvpView {


    void onSuccess(ResultBase<RechangeInfo> rechargeInfos);

    void onError(ResultBase resultBase);

    void onFail();
}
