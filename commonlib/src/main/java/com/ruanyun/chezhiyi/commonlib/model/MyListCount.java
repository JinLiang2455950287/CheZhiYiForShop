package com.ruanyun.chezhiyi.commonlib.model;

/**
 * Description ：
 * <p/>
 * Created by ycw on 2016/11/28.
 */
public class MyListCount {
    private  int jszCount;//  14,  结算中数量
    private  int fwzCount;//  :3,  服务中数量
    private  int dfwCount;//  :9  代服务数量

    public int getFwzCount() {
        return fwzCount;
    }

    public void setFwzCount(int fwzCount) {
        this.fwzCount = fwzCount;
    }

    public int getDfwCount() {
        return dfwCount;
    }

    public void setDfwCount(int dfwCount) {
        this.dfwCount = dfwCount;
    }

    public int getJszCount() {
        return jszCount;
    }

    public void setJszCount(int jszCount) {
        this.jszCount = jszCount;
    }
}
