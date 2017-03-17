package com.ruanyun.chezhiyi.commonlib.model.params;

/**
 * 注册参数
 * Created by ycw on 2016/8/27.
 */
public class RegisterParams  {
    private String nickName;//	String	昵称
    private String userName;//	String	昵称
    private String loginPass;//	String	登陆密码【MD5】
    private String userType;//	String	1 系统用户 2 技师用户 3 客户端用户

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getLoginPass() {
        return loginPass;
    }

    public void setLoginPass(String loginPass) {
        this.loginPass = loginPass;
    }

    public String getUserType() {
        return userType;
    }

    /**	String	1 系统用户 2 技师用户 3 客户端用户*/
    public void setUserType(String userType) {
        this.userType = userType;
    }
}
