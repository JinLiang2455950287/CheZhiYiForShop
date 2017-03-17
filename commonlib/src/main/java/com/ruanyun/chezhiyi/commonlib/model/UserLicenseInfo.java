package com.ruanyun.chezhiyi.commonlib.model;

/**
 * Description ： 驾驶证信息
 * <p>
 * Created by ycw on 2016/8/6.
 */
public class UserLicenseInfo {

    /**
     * 主键【添加时不传  修改时必传】
     */
    private int licenseId;
    private String userNum;
    /**
     *  真实姓名
     */
    private String realName;
    /**
     *  驾驶证号
     */
    private String licenseNum;
    /**
     *  有效结束时间格式为yyyy-MM-dd
     */
    private String effectiveStartTime;
    /**
     *  有效结束时间格式为yyyy-MM-dd
     */
    private String effectiveEndTime;
    /**
     *  初次领证时间格式为yyyy-MM-dd
     */
    private String cclzTime;
    /**
     *  地址
     */
    private String address;
    /**
     *  驾驶证正面照片     file/userlicense/
     */
    private String obverseSidePhoto;
    /**
     *  驾驶证反面照片     file/userlicense/
     */
    private String otherSidePhoto;
    /**
     *  1-有效 2-无效【暂时不用 后台默认为1】
     */
    private int status;

    public int getLicenseId() {
        return licenseId;
    }

    public void setLicenseId(int licenseId) {
        this.licenseId = licenseId;
    }

    public String getUserNum() {
        return userNum;
    }

    public void setUserNum(String userNum) {
        this.userNum = userNum;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getLicenseNum() {
        return licenseNum;
    }

    public void setLicenseNum(String licenseNum) {
        this.licenseNum = licenseNum;
    }

    public String getEffectiveStartTime() {
        return effectiveStartTime;
    }

    public void setEffectiveStartTime(String effectiveStartTime) {
        this.effectiveStartTime = effectiveStartTime;
    }

    public String getEffectiveEndTime() {
        return effectiveEndTime;
    }

    public void setEffectiveEndTime(String effectiveEndTime) {
        this.effectiveEndTime = effectiveEndTime;
    }

    public String getCclzTime() {
        return cclzTime;
    }

    public void setCclzTime(String cclzTime) {
        this.cclzTime = cclzTime;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getObverseSidePhoto() {
        return obverseSidePhoto;
    }

    public void setObverseSidePhoto(String obverseSidePhoto) {
        this.obverseSidePhoto = obverseSidePhoto;
    }

    public String getOtherSidePhoto() {
        return otherSidePhoto;
    }

    public void setOtherSidePhoto(String otherSidePhoto) {
        this.otherSidePhoto = otherSidePhoto;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
