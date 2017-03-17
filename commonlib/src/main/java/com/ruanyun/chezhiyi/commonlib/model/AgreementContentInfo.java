package com.ruanyun.chezhiyi.commonlib.model;

/**
 * Description ：
 * <p/>
 * Created by ycw on 2016/12/19.
 */
public class AgreementContentInfo {

    /**
     * agreementId : 1
     * content : 用户注册协议
     * storeNum :
     * type : 1
     */

    private int agreementId;
    private String content;
    private String storeNum;
    private int type;

    public int getAgreementId() {
        return agreementId;
    }

    public void setAgreementId(int agreementId) {
        this.agreementId = agreementId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getStoreNum() {
        return storeNum;
    }

    public void setStoreNum(String storeNum) {
        this.storeNum = storeNum;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
