package com.ruanyun.chezhiyi.commonlib.model.params;

/**
 * Description ：获取预约列表Params
 * <p/>
 * Created by hdl on 2016/9/19.
 */
public class BookingListParams extends PageParamsBase {
    private String makeStatusString;//工单状态　全部　不传值　待确认１,２　待服务　３，已完成４

    public String getMakeStatusString() {
        return makeStatusString;
    }

    public void setMakeStatusString(String makeStatusString) {
        this.makeStatusString = makeStatusString;
    }
}
