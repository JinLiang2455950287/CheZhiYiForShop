package com.ruanyun.chezhiyi.commonlib.model.params;

/**
 * Description ：添加在线预约的参数
 * <p>
 * Created by ycw on 2016/9/13.
 */
public class MakeAppointmentParams {
    private String projectNum;//      	String  	服务内容 json格式 [{projectNum:"",childProjectTypeList:[{projectNum:"",childProjectTypeList:[]}]}]
    private String predictShopTime;// 	String  	预计到店时间【yyyy-MM-dd HH:mm】
    private String remark;//          	String  	备注
    private String storeNum;//	String	门店num【暂无使用】

    public String getProjectNum() {
        return projectNum;
    }

    public void setProjectNum(String projectNum) {
        this.projectNum = projectNum;
    }

    public String getPredictShopTime() {
        return predictShopTime;
    }

    public void setPredictShopTime(String predictShopTime) {
        this.predictShopTime = predictShopTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getStoreNum() {
        return storeNum;
    }

    public void setStoreNum(String storeNum) {
        this.storeNum = storeNum;
    }
}
