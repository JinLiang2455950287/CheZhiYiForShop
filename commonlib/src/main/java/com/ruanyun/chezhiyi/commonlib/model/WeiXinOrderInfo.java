package com.ruanyun.chezhiyi.commonlib.model;

/**
 * description ${}
 * Created by ycw 
 * date 2016/5/17
 */
public class WeiXinOrderInfo {

    /**
     * sign : 9713328252C282B3044BCCA9EB009363
     * timestamp : 1463466089
     * prepay_id : wx2016051714212948d7694ade0541678868
     * nonce_str : ddac1f6f13bb372a177804adcd3b8a31
     */

    private String sign;
    private String timestamp;
    private String prepay_id;
    private String nonce_str;

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getPrepay_id() {
        return prepay_id;
    }

    public void setPrepay_id(String prepay_id) {
        this.prepay_id = prepay_id;
    }

    public String getNonce_str() {
        return nonce_str;
    }

    public void setNonce_str(String nonce_str) {
        this.nonce_str = nonce_str;
    }
}
