package com.ruanyun.chezhiyi.commonlib.model.params;


/**
 * Description:
 * author: zhangsan on 16/10/27 上午10:36.
 */
public class SystemRemindParams extends PageParamsBase{
    /**    是否推送 默认传1代表已推送    */
    private Integer isPush = 1;
    private Integer isRead;//	Integer	是否已读 1是 2否
    private String orderBy;
    private String order;
    private String remindType;

    public String getRemindType() {
        return remindType;
    }

    public void setRemindType(String remindType) {
        this.remindType = remindType;
    }

    public Integer getIsRead() {
        return isRead;
    }

    public void setIsRead(Integer isRead) {
        this.isRead = isRead;
    }

    public Integer getIsPush() {
        return isPush;
    }

    public void setIsPush(Integer isPush) {
        this.isPush = isPush;
    }

    public String getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }
}
