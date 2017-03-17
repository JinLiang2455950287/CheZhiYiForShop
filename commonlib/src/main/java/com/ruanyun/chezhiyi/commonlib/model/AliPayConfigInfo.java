package com.ruanyun.chezhiyi.commonlib.model;

/**
 * Description ：
 * <p/>
 * Created by ycw on 2016/10/19.
 */
public class AliPayConfigInfo {
    private int payInfoId;//	Integer   	主键
    private String storeNum;//    String  	门店编号【暂无使用】
    private String partner;//	String	支付账号
    private String publicKey;//	String	共匙
    private String privateKeyAndroid;//	String	私匙 Android专用
    private String privateKeyIos;//	String    	私匙 iOS专用

    public int getPayInfoId() {
        return payInfoId;
    }

    public void setPayInfoId(int payInfoId) {
        this.payInfoId = payInfoId;
    }

    public String getStoreNum() {
        return storeNum;
    }

    public void setStoreNum(String storeNum) {
        this.storeNum = storeNum;
    }

    public String getPartner() {
        return partner;
    }

    public void setPartner(String partner) {
        this.partner = partner;
    }

    public String getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }

    public String getPrivateKeyAndroid() {
        return privateKeyAndroid;
    }

    public void setPrivateKeyAndroid(String privateKeyAndroid) {
        this.privateKeyAndroid = privateKeyAndroid;
    }

    public String getPrivateKeyIos() {
        return privateKeyIos;
    }

    public void setPrivateKeyIos(String privateKeyIos) {
        this.privateKeyIos = privateKeyIos;
    }
}
