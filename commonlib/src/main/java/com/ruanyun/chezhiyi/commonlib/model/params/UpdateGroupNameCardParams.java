package com.ruanyun.chezhiyi.commonlib.model.params;

/**
 * Description ：1.6.3	修改群名片 参数
 *
 * Created by ycw on 2016/8/3.
 */
public class UpdateGroupNameCardParams {
    private String groupNum;//	String	群编号
    private String nickName;//	String	昵称
    private String relationType;//	Int	用户与群关系 【暂不用】

    public String getGroupNum () {
        return groupNum;
    }

    public void setGroupNum (String groupNum) {
        this.groupNum = groupNum;
    }

    public String getNickName () {
        return nickName;
    }

    public void setNickName (String nickName) {
        this.nickName = nickName;
    }

    public String getRelationType () {
        return relationType;
    }

    public void setRelationType (String relationType) {
        this.relationType = relationType;
    }
}
