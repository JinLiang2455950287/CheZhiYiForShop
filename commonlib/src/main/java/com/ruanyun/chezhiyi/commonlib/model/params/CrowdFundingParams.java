package com.ruanyun.chezhiyi.commonlib.model.params;

/**
 * 获取众筹列表或我的众筹
 * Created by hdl on 2016/9/12
 */
public class CrowdFundingParams extends PageParamsBase {

    /**(1众筹成功 2，众筹失败  3、已上架 众筹中)  获取众筹列表是传值3*/
    private Integer status;
    /**状态的多个值传 例如 1,2*/
    private String statusString;
    /**2—获取我的众筹 1-获取所有众筹*/
    private String type;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getStatusString() {
        return statusString;
    }

    public void setStatusString(String statusString) {
        this.statusString = statusString;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
