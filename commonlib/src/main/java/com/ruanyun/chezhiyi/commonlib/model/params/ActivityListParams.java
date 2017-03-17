package com.ruanyun.chezhiyi.commonlib.model.params;

/**
 * Created by msq on 2016/9/10.
 */
public class ActivityListParams extends PageParamsBase{

    private String type;	//	1-进行中 2-即将开始 3-已结束 -1-已取消【type有值时signStatus 传1 】
    private Integer signStatus;	//	是否报名成功 1-报名成功 2-取消报名【对应字典表】

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getSignStatus() {
        return signStatus;
    }

    public void setSignStatus(Integer signStatus) {
        this.signStatus = signStatus;
    }
}
