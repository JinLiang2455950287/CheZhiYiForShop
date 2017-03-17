package com.ruanyun.chezhiyi.commonlib.model.params;

/**
 * Description ： 取消预约的参数
 * <p/>
 * Created by ycw on 2016/10/10.
 */
public class CancelMakeParams {

    private String makeNum;//	String	预约编号
    private Integer makeStatus;//	Int	预约状态预约状态（-1已取消1待确认2带付款3带服务4已完成） 字典表TMAKEINFO_STATUS   传值-1 取消

    public String getMakeNum() {
        return makeNum;
    }

    public void setMakeNum(String makeNum) {
        this.makeNum = makeNum;
    }

    public Integer getMakeStatus() {
        return makeStatus;
    }

    /**
     * 	预约状态预约状态（-1已取消1待确认2带付款3带服务4已完成） 字典表TMAKEINFO_STATUS   传值-1 取消
     * @param makeStatus
     */
    public void setMakeStatus(Integer makeStatus) {
        this.makeStatus = makeStatus;
    }
}
