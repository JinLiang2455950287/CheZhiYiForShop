package com.ruanyun.chezhiyi.commonlib.model.params;

/**
 * Description:1.1.1	快速洗车请求参数
 * author: zhangsan on 16/11/3 上午11:33.
 */
public class SaveWashCarParams  {
    private String servicePlateNumber;//车牌号
    private String jsUserNum;//技师usernum
    private String phone;//手机号
    private String remark;//备注

    public String getServicePlateNumber() {
        return servicePlateNumber;
    }

    public void setServicePlateNumber(String servicePlateNumber) {
        this.servicePlateNumber = servicePlateNumber;
    }

    public String getJsUserNum() {
        return jsUserNum;
    }

    public void setJsUserNum(String jsUserNum) {
        this.jsUserNum = jsUserNum;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
