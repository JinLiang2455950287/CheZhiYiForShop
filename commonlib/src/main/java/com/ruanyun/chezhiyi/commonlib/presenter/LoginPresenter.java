package com.ruanyun.chezhiyi.commonlib.presenter;

import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.ruanyun.chezhiyi.commonlib.App;
import com.ruanyun.chezhiyi.commonlib.base.ResultBase;
import com.ruanyun.chezhiyi.commonlib.data.api.ResponseCallback;
import com.ruanyun.chezhiyi.commonlib.hxchat.HXHelper;
import com.ruanyun.chezhiyi.commonlib.model.AccountInfo;
import com.ruanyun.chezhiyi.commonlib.model.CarInfo;
import com.ruanyun.chezhiyi.commonlib.model.User;
import com.ruanyun.chezhiyi.commonlib.util.C;
import com.ruanyun.chezhiyi.commonlib.util.DbHelper;
import com.ruanyun.chezhiyi.commonlib.util.FileUtil;
import com.ruanyun.chezhiyi.commonlib.util.PrefUtility;
import com.ruanyun.chezhiyi.commonlib.view.LoginMvpView;

import java.util.List;

import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.wechat.friends.Wechat;
import retrofit2.Call;


/**
 * Description ：用户登陆的 Presenter
 * <p>
 * Created by ycw on 2016/8/4.
 */
public class LoginPresenter implements Presenter<LoginMvpView> {

    public static final String QQ_LOGIN = QQ.NAME;
    public static final String WEIXIN_LOGIN = Wechat.NAME;
    public static final String SINAWEIBO_LOGIN = SinaWeibo.NAME;
    LoginMvpView mLoginMvpView;
    Call mLoginCall;
    Call<ResultBase<User>> thirdCall;

    @Override
    public void attachView(LoginMvpView mvpView) {
        mLoginMvpView = mvpView;
    }

    @Override
    public void detachView() {
        mLoginMvpView = null;
    }

    @Override
    public void onCancel() {
        if (mLoginCall != null && !mLoginCall.isCanceled())
            mLoginCall.cancel();
    }

    /**
     * 用户登录接口
     *
     * @param loginCall
     */
    public void userLogin(Call loginCall) {
        if (mLoginMvpView == null) return;
        mLoginMvpView.showLoadingView();
        mLoginCall = loginCall;
        mLoginCall.enqueue(
                new ResponseCallback<ResultBase<User>>() {
                    @Override
                    public void onSuccess(Call call, ResultBase<User> userResult) {
                        loginWithUser(userResult.getObj());
                    }

                    @Override
                    public void onError(Call call, ResultBase<User> userResult, int errorCode) {
                        if (mLoginMvpView == null) return;
                        mLoginMvpView.dismissLoadingView();
                        mLoginMvpView.showTip(userResult.getMsg());
                        mLoginMvpView.onUserLoginError();
                    }

                    @Override
                    public void onFail(Call call, String msg) {
                        if (mLoginMvpView == null) return;
                        mLoginMvpView.dismissLoadingView();
                        mLoginMvpView.showTip(msg);
                        mLoginMvpView.onUserLoginFail();
                    }

                    @Override
                    public void onResult() {
                    }
                }
        );
    }

    /**
     * 使用 User 登录
     *
     * @param user
     */
    public void loginWithUser(User user) {
        String loginName = user.getLoginName();
        String loginPass = user.getLoginPass();
        String userNum = user.getUserNum();

        //保存用户信息
        AccountInfo accountInfo = new AccountInfo(1L, loginName, loginPass,
                userNum);
        //保存用户信息到数据库
        DbHelper.getInstance().insertAccountInfo(accountInfo);
        //保持登陆用户和是否第一次登陆到SharedPreferences
        PrefUtility.put(C.PrefName.PREF_LOGIN_NAME, loginName);
        PrefUtility.put(C.PrefName.PREF_IS_LOGIN, true);
        //保持登陆用户和是否第一次登陆的json字符串到SharedPreferences和保存信息到环信数据库
        App.getInstance().setUser(user);
        if (mLoginMvpView == null) return;
        //mLoginMvpView.onUserLoginSuccess(userResult);
        // 成功后 环信登录并设置默认密码
        hxLogin(userNum, "123456");
    }

    private void hxLogin(String userName, String pwd) {
        EMClient.getInstance().login(userName, pwd, new EMCallBack() {

            @Override
            public void onSuccess() {
                if (mLoginMvpView == null) return;
                mLoginMvpView.dismissLoadingView();
                // Log.d(TAG, "login: onSuccess");
                // ** 第一次登录或者之前logout后再登录，加载所有本地群和回话
                // ** manually load all local groups and
                EMClient.getInstance().groupManager().loadAllGroups();
                EMClient.getInstance().chatManager().loadAllConversations();

//                // 更新当前用户的nickname 此方法的作用是在ios离线推送时能够显示用户nick
//                boolean updatenick = EMClient.getInstance().updateCurrentUserNick(
//                        app.currentUserNick.trim());
//                if (!updatenick) {
//                    //Log.e("LoginActivity", "update current user nick fail");
//                }
//                //异步获取当前用户的昵称和头像(从自己服务器获取，demo使用的一个第三方服务)
//                String userNick = app.getUserInfo().getUserName();
//                String avatar = FileUtil.getFileUrl(C.FileUrl.USER_AVATAR,
//                                                    app.getUserInfo().getUserPhoto());
//                //  HXHelper.getInstance().getUserProfileManager().asyncGetCurrentUserInfo();
//                HXHelper.getInstance().getUserProfileManager().uploadUserAvatar()setUserInfo(avatar, userNick);
                //取出之前保存到SharedPreferences的用户信息
                User currentUser = App.getInstance().getUser();
                //保存信息到环信数据库
                HXHelper.getInstance().getUserProfileManager().setUserInfo(FileUtil.getFileUrl(currentUser.getUserPhoto()), currentUser.getNickName());
                if (mLoginMvpView == null) return;
                mLoginMvpView.onHxLoginSuccess();
                //获取工单服务或技师技能并保存到数据库
                App.getInstance().beanCacheHelper.getProjectTypes();
                //获取公共字典表并保存到数据库
                App.getInstance().beanCacheHelper.getParentCode();
                //                App.getInstance().beanCacheHelper.getHomeIconToDb();
            }

            @Override
            public void onProgress(int progress, String status) {

            }

            @Override
            public void onError(final int code, final String message) {
                if (mLoginMvpView == null) return;
                mLoginMvpView.dismissLoadingView();
                mLoginMvpView.onHxLoginFail(message);
            }
        });
    }


    /**
     * 第三方登录接口
     *
     * @param thirdCall
     */
    public void loginThird(Call<ResultBase<User>> thirdCall) {
        if (mLoginMvpView == null) return;
        mLoginMvpView.showLoadingView();
        this.thirdCall = thirdCall;
        this.thirdCall.enqueue(new ResponseCallback<ResultBase<User>>() {
            @Override
            public void onSuccess(Call call, ResultBase<User> userResultBase) {
                if (mLoginMvpView == null) return;
                mLoginMvpView.thirdBindLogin(userResultBase.getObj());
            }

            @Override
            public void onError(Call call, ResultBase<User> userResultBase, int errorCode) {

            }

            @Override
            public void onFail(Call call, String msg) {

            }

            @Override
            public void onResult() {
                if (mLoginMvpView == null) return;
                mLoginMvpView.dismissLoadingView();
            }
        });
    }

    /**
     * 获取用户车辆信息
     */
    public void getUserCarInfos() {
        App.getInstance().getApiService().getCarinfoList(
                App.getInstance().getCurrentUserNum()).enqueue(new
                                                                       ResponseCallback<ResultBase<List<CarInfo>>>() {
                                                                           @Override
                                                                           public void onSuccess(Call call, ResultBase<List<CarInfo>> listResultBase) {
                                                                               if (listResultBase.getObj().isEmpty()) {
                                                                                   mLoginMvpView.userNoCar();
                                                                               } else {
                                                                                   mLoginMvpView.userHaseCar();
                                                                               }
                                                                           }

                                                                           @Override
                                                                           public void onError(Call call, ResultBase<List<CarInfo>> listResultBase, int
                                                                                   errorCode) {
                                                                               mLoginMvpView.showTip("获取车辆信息失败 请重新登录");
                                                                               mLoginMvpView.getCarError();
                                                                           }

                                                                           @Override
                                                                           public void onFail(Call call, String msg) {
                                                                               mLoginMvpView.showTip("获取车辆信息失败 请重新登录");
                                                                               mLoginMvpView.getCarError();
                                                                           }

                                                                           @Override
                                                                           public void onResult() {

                                                                           }
                                                                       });
    }
}
