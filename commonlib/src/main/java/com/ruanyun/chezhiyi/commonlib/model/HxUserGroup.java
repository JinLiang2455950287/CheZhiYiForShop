package com.ruanyun.chezhiyi.commonlib.model;

import android.os.Parcel;
import android.os.Parcelable;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Unique;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Description:用户加入群列表实体类
 * author: jery on 2016/7/27 13:53.
 */
@Entity
public class HxUserGroup implements Parcelable {
    /** 群编号 **/
    @NotNull @Unique
    private String groupInfoNum;
    /** 群名称 **/
    private String groupName;
    /** 群图片路径
     file/groupinfo **/
    private String groupPath;
    /** 群人数 **/
    private int groupTotalNum;
    /** 环信编号 **/
    private String huanxinNum;
    /** 用户与群关系
     1-群主 2-群员【可为空】 **/
    private int relationType;
    /** 创建用户编号 **/
    private String userNum;

    public String getUserNum() {
        return userNum;
    }

    public void setUserNum(String userNum) {
        this.userNum = userNum;
    }

    public String getGroupInfoNum() {
        return groupInfoNum;
    }

    public void setGroupInfoNum(String groupInfoNum) {
        this.groupInfoNum = groupInfoNum;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getGroupPath() {
        return groupPath;
    }

    public void setGroupPath(String groupPath) {
        this.groupPath = groupPath;
    }

    public int getGroupTotalNum() {
        return groupTotalNum;
    }

    public void setGroupTotalNum(int groupTotalNum) {
        this.groupTotalNum = groupTotalNum;
    }

    public String getHuanxinNum() {
        return huanxinNum;
    }

    public void setHuanxinNum(String huanxinNum) {
        this.huanxinNum = huanxinNum;
    }

    public int getRelationType() {
        return relationType;
    }

    public void setRelationType(int relationType) {
        this.relationType = relationType;
    }

    public HxUserGroup() {
    }

    @Generated(hash = 1584522761)
    public HxUserGroup(@NotNull String groupInfoNum, String groupName, String groupPath, int groupTotalNum,
            String huanxinNum, int relationType, String userNum) {
        this.groupInfoNum = groupInfoNum;
        this.groupName = groupName;
        this.groupPath = groupPath;
        this.groupTotalNum = groupTotalNum;
        this.huanxinNum = huanxinNum;
        this.relationType = relationType;
        this.userNum = userNum;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.groupInfoNum);
        dest.writeString(this.groupName);
        dest.writeString(this.groupPath);
        dest.writeInt(this.groupTotalNum);
        dest.writeString(this.huanxinNum);
        dest.writeInt(this.relationType);
        dest.writeString(this.userNum);
    }

    protected HxUserGroup(Parcel in) {
        this.groupInfoNum = in.readString();
        this.groupName = in.readString();
        this.groupPath = in.readString();
        this.groupTotalNum = in.readInt();
        this.huanxinNum = in.readString();
        this.relationType = in.readInt();
        this.userNum = in.readString();
    }

    public static final Creator<HxUserGroup> CREATOR = new Creator<HxUserGroup>() {
        @Override
        public HxUserGroup createFromParcel(Parcel source) {
            return new HxUserGroup(source);
        }

        @Override
        public HxUserGroup[] newArray(int size) {
            return new HxUserGroup[size];
        }
    };
}
