package com.ruanyun.chezhiyi.commonlib.model.params;

/**
 * Description ：修改黑名单参数
 * <p/>
 * Created by ycw on 2016/8/11.
 */
public class ModifyBlackParams {

    private String friendNum;//	String	好友编号
    private int friendStatus;//	Int	  【传值为1移除黑名单 传值3加入黑名单】

    public String getFriendNum() {
        return friendNum;
    }

    public void setFriendNum(String friendNum) {
        this.friendNum = friendNum;
    }

    public int getFriendStatus() {
        return friendStatus;
    }

    /**
     * Int	  【传值为1移除黑名单 传值3加入黑名单】
     * @param friendStatus
     */
    public void setFriendStatus(int friendStatus) {
        this.friendStatus = friendStatus;
    }
}
