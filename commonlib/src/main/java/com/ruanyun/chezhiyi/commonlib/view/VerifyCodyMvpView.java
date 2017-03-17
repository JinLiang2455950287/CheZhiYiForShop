package com.ruanyun.chezhiyi.commonlib.view;

import com.ruanyun.chezhiyi.commonlib.base.MvpView;
import com.ruanyun.chezhiyi.commonlib.model.AgreementContentInfo;
import com.ruanyun.chezhiyi.commonlib.model.User;

/**
 * 获取验证码
 * Created by Sxq on 2016/9/21.
 */
public interface VerifyCodyMvpView extends MvpView {
    void verifyCodySuccess();
    void showVerifyCodyMsg(String msg);

    void addThirdSuccess(User user);

    void showLoadingView();

    void dismissLoadingView();

    /**
     * 校验短信验证码成功
     */
    void validateSuccess();

    /**
     * 调用短信验证接口 结束
     */
    void verifyCodyResult();

    void onAgreementSuccess(AgreementContentInfo agreementContentInfo);
//    void verifyCodyError(String msg);
}
