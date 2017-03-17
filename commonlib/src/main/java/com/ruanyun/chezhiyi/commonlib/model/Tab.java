package com.ruanyun.chezhiyi.commonlib.model;

/**
 * Created by msq on 2016/9/10.
 */
public class Tab {

    public String getTabNum() {
        return tabNum;
    }

    public void setTabNum(String tabNum) {
        this.tabNum = tabNum;
    }

    private String tabNum;//活动名称

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    private String type;//活动类型

    public Tab(String itemNum,String types){
        tabNum = itemNum;
        type = types;
    }
}
