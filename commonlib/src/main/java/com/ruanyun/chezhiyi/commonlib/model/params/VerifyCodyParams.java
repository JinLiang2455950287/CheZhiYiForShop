package com.ruanyun.chezhiyi.commonlib.model.params;

/**
 * 手机号验证参数  和  短信验证码校验  参数
 * Created by Sxq on 2016/9/21.
 */
public class VerifyCodyParams {
    private String userType;//用户类型1 系统用户 2 技师用户 3 客户端用户
    private String loginName;
    /**
     * 1 –给用户发短信 必须传用户类型 找回密码等情况用
     * 2 给不是平台用户发短信 注册时用  3 不做手机号码验证
     */
    private String validate;//	String	验证码
    private String type;

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getValidate() {
        return validate;
    }

    public void setValidate(String validate) {
        this.validate = validate;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }
}
