package com.ruanyun.chezhiyi.commonlib.view;

import com.ruanyun.chezhiyi.commonlib.base.MvpView;
import com.ruanyun.chezhiyi.commonlib.base.ResultBase;
import com.ruanyun.chezhiyi.commonlib.model.User;

/**
 * 忘记密码
 * Created by Sxq on 2016/9/21.
 */
public interface ForgetPasswordMvpView extends MvpView{
  void onResetSuccess(ResultBase<User> userResult);
  void onResetMsg(String msg);//onError/ onFail
  void showLoadingView();
  void onResetResponse();
}
