package com.ruanyun.chezhiyi.commonlib.model.params;

/**
 * Description ：
 *
 * Created by ycw on 2016/8/3.
 */
public class QuitGroupParams {
    private String groupNum;//	String	群编号
    private String friendNum;//	String	退群用户编号【自己退群 传自己编号】

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
}
