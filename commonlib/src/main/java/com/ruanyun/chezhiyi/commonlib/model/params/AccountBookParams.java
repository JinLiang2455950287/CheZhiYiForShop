package com.ruanyun.chezhiyi.commonlib.model.params;

/**
 * @author hdl
 * @ClassName: 获取记账本列表
 * @Description:
 * @date ${date}${time}
 */
public class AccountBookParams extends PageParamsBase {

    /**时间月份 格式为【yyyyMM 201606】**/
    private String month;

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }
}
