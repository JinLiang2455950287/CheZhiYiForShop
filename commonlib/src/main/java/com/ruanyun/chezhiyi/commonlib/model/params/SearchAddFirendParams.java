package com.ruanyun.chezhiyi.commonlib.model.params;


/**
 * Created by Sxq on 2016/8/23.
 */
public class SearchAddFirendParams extends PageParamsBase {
    /**
     * userType	String 类型 【默认传值 3 司机用户 】
     * wordkey	String 关键词【昵称与登录名称模糊查询】
     */
    private String userType;
    private String wordkey;

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getWordkey() {
        return wordkey;
    }

    public void setWordkey(String wordkey) {
        this.wordkey = wordkey;
    }
}
