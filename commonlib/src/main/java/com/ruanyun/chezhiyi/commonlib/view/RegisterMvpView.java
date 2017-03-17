package com.ruanyun.chezhiyi.commonlib.view;

import com.ruanyun.chezhiyi.commonlib.base.MvpView;
import com.ruanyun.chezhiyi.commonlib.base.ResultBase;
import com.ruanyun.chezhiyi.commonlib.model.AgreementContentInfo;

/**
 * Description ：
 * <p/>
 * Created by ycw on 2016/8/9.
 */
public interface RegisterMvpView extends MvpView {
    void onRegisterShowLoading();

    void onRegisterSuccess(ResultBase resultBase);

    void onRegisterError(ResultBase resultBase, int errorCode);

    void onRegisterFail(String msg);

    void onRegisterResponse();

    void onAgreementSuccess(AgreementContentInfo agreementContentInfo);
}
