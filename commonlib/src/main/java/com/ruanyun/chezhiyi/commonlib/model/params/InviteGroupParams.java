package com.ruanyun.chezhiyi.commonlib.model.params;

/**
 * Description ：1.6.6	加入群 参数
 *
 * Created by ycw on 2016/8/3.
 */
public class InviteGroupParams {
    private String groupNum;//	String	群编号
    private String friendNum;//	String	好友编号【需要加入群的用户编号】
    private String nickName;//	String	昵称

    public String getGroupNum () {
        return groupNum;
    }

    public void setGroupNum (String groupNum) {
        this.groupNum = groupNum;
    }

    public String getFriendNum () {
        return friendNum;
    }

    public void setFriendNum (String friendNum) {
        this.friendNum = friendNum;
    }

    public String getNickName () {
        return nickName;
    }

    public void setNickName (String nickName) {
        this.nickName = nickName;
    }
}
