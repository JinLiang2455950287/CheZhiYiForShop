package com.ruanyun.chezhiyi.commonlib.model.params;

/**
 * 获取限时促销列表
 * Created by hdl on 2016/9/12
 */
public class GetPromotionParams extends PageParamsBase {

    private Integer isHome;//1 首页显示 2-否

    public static final int HOME_SHOW = 1;
    public static final int HOME_NO_SHOW = 2;

    public Integer getIsHome() {
        return isHome;
    }

    public void setIsHome(int isHome) {
        this.isHome = isHome;
    }
}
