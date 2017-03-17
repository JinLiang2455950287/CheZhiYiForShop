package com.ruanyun.chezhiyi.commonlib.model;

/**
 * Description ：已绑定的第三方
 * <p>
 * Created by ycw on 2016/10/14.
 */
public class ThirdInfo {
    private String thirdNum;//	String	第三方唯一标识
    private int thirdType;//	int	登陆类型 1-QQ 2-微信 3-新浪微博
    private String userNum;//	String	用户编号

    public String getThirdNum() {
        return thirdNum;
    }

    public void setThirdNum(String thirdNum) {
        this.thirdNum = thirdNum;
    }

    /**
     * @return  登陆类型 1-QQ 2-微信 3-新浪微博
     */
    public int getThirdType() {
        return thirdType;
    }

    /**
     * 登陆类型 1-QQ 2-微信 3-新浪微博
     * @param thirdType
     */
    public void setThirdType(int thirdType) {
        this.thirdType = thirdType;
    }

    public String getUserNum() {
        return userNum;
    }

    public void setUserNum(String userNum) {
        this.userNum = userNum;
    }
}
