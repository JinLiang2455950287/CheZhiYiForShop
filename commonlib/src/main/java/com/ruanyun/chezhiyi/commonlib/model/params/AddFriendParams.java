package com.ruanyun.chezhiyi.commonlib.model.params;

/**
 * Description ：添加好友参数
 *
 * Created by ycw on 2016/7/28.
 */
public class AddFriendParams {
    private String friendNum;
    private String groupNum;
    private String nickName;
    private String remark;

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getFriendNum () {
        return friendNum;
    }

    public void setFriendNum (String friendNum) {
        this.friendNum = friendNum;
    }

    public String getGroupNum() {
        return groupNum;
    }

    public void setGroupNum(String groupNum) {
        this.groupNum = groupNum;
    }
}
