package com.ruanyun.chezhiyi.commonlib.model;

/**
 * Created by msq on 2016/9/19.
 */
public class CommentUserTelephoneInfo {

    private int commonPhoneId; 	 // 	主键
    private String commonPhoneNum;	 //  	编号
    private String storeNum;      	 //  	门店编号
    private String title;         	 //  	标题【名称】
    private String content;       	 //  	电话号码

    public int getCommonPhoneId() {
        return commonPhoneId;
    }

    public void setCommonPhoneId(int commonPhoneId) {
        this.commonPhoneId = commonPhoneId;
    }

    public String getCommonPhoneNum() {
        return commonPhoneNum;
    }

    public void setCommonPhoneNum(String commonPhoneNum) {
        this.commonPhoneNum = commonPhoneNum;
    }

    public String getStoreNum() {
        return storeNum;
    }

    public void setStoreNum(String storeNum) {
        this.storeNum = storeNum;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
