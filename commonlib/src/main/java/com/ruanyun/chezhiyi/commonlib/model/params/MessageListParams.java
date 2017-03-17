package com.ruanyun.chezhiyi.commonlib.model.params;



/**
 * Description:
 * author: zhangsan on 16/8/18 下午3:31.
 */
public class MessageListParams extends PageParamsBase{
    /**  发送消息用户编号**/
    private String sendUserNum;
    /** 接受的用户编号 **/
    private String receiveUsernumGroupid;
    /** 发送的内容【搜索的内容】 **/
    private String huanxinSendMsg;

    private Long startTime;
    private Long endTime;

    public Long getStartTime() {
        return startTime;
    }

    public void setStartTime(Long startTime) {
        this.startTime = startTime;
    }

    public Long getEndTime() {
        return endTime;
    }

    public void setEndTime(Long endTime) {
        this.endTime = endTime;
    }

    public String getSendUserNum() {
        return sendUserNum;
    }

    public void setSendUserNum(String sendUserNum) {
        this.sendUserNum = sendUserNum;
    }

    public String getReceiveUsernumGroupid() {
        return receiveUsernumGroupid;
    }

    public void setReceiveUsernumGroupid(String receiveUsernumGroupid) {
        this.receiveUsernumGroupid = receiveUsernumGroupid;
    }

    public String getHuanxinSendMsg() {
        return huanxinSendMsg;
    }

    public void setHuanxinSendMsg(String huanxinSendMsg) {
        this.huanxinSendMsg = huanxinSendMsg;
    }
}
