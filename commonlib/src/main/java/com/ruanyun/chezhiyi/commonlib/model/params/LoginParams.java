package com.ruanyun.chezhiyi.commonlib.model.params;

/**
 * Description ：
 *
 * Created by ycw on 2016/8/3.
 */
public class LoginParams {
    private String loginPass;//	String	登陆密码【MD5】
    private String userType;//	String	1 系统用户 2 技师用户 3 客户端用户

    public String getLoginPass () {
        return loginPass;
    }

    public void setLoginPass (String loginPass) {
        this.loginPass = loginPass;
    }

    public String getUserType () {
        return userType;
    }

    /**
     * String	1 系统用户 2 技师用户 3 客户端用户
     * @param userType
     */
    public void setUserType (String userType) {
        this.userType = userType;
    }
}
