package com.ruanyun.chezhiyi.commonlib.model;

import android.os.Parcel;
import android.os.Parcelable;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Transient;
import org.greenrobot.greendao.annotation.Unique;
import org.greenrobot.greendao.annotation.
    Generated;

/**
 * Description:
 * author: jery on 2016/7/27 11:58.
 */
@Entity
public class HxUser implements Parcelable {
    public static final int TYPE_GROUP=45521;//分组
    public static final int TYPE_USER=465654;//用户

    private String userNick;
    private String userPhoto;
    @NotNull
    @Unique
    private String userNum;
    private String groupNum;
    private String groupName;
    private int userType;
    @Transient
    private boolean isSelected=false;
    @Transient
    private boolean isInvited=false;//选取联系人时判断是否邀请过
    @Transient
    private int typeId; //区分是分组还是用户

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public boolean isInvited() {
        return isInvited;
    }

    public void setInvited(boolean invited) {
        isInvited = invited;
    }

    public int getUserType() {
        return userType;
    }

    public void setUserType(int userType) {
        this.userType = userType;
    }

    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }

    public String getGroupName() {
        return this.groupName;
    }
    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }
    public String getGroupNum() {
        return this.groupNum;
    }
    public void setGroupNum(String groupNum) {
        this.groupNum = groupNum;
    }
    public String getUserNum() {
        return this.userNum;
    }
    public void setUserNum(String userNum) {
        this.userNum = userNum;
    }
    public String getUserPhoto() {
        return this.userPhoto;
    }
    public void setUserPhoto(String userPhoto) {
        this.userPhoto = userPhoto;
    }
    public String getUserNick() {
        return this.userNick;
    }
    public void setUserNick(String userNick) {
        this.userNick = userNick;
    }
    @Generated(hash = 1137826999)
    public HxUser(String userNick, String userPhoto, @NotNull String userNum,
            String groupNum, String groupName, int userType) {
        this.userNick = userNick;
        this.userPhoto = userPhoto;
        this.userNum = userNum;
        this.groupNum = groupNum;
        this.groupName = groupName;
        this.userType = userType;
    }

    @Generated(hash = 126941542)
    public HxUser() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.userNick);
        dest.writeString(this.userPhoto);
        dest.writeString(this.userNum);
        dest.writeString(this.groupNum);
        dest.writeString(this.groupName);
        dest.writeInt(this.userType);
        dest.writeByte(this.isSelected ? (byte) 1 : (byte) 0);
        dest.writeByte(this.isInvited ? (byte) 1 : (byte) 0);
        dest.writeInt(this.typeId);
    }

    protected HxUser(Parcel in) {
        this.userNick = in.readString();
        this.userPhoto = in.readString();
        this.userNum = in.readString();
        this.groupNum = in.readString();
        this.groupName = in.readString();
        this.userType = in.readInt();
        this.isSelected = in.readByte() != 0;
        this.isInvited = in.readByte() != 0;
        this.typeId = in.readInt();
    }

    public static final Parcelable.Creator<HxUser> CREATOR = new Parcelable.Creator<HxUser>() {
        @Override
        public HxUser createFromParcel(Parcel source) {
            return new HxUser(source);
        }

        @Override
        public HxUser[] newArray(int size) {
            return new HxUser[size];
        }
    };
}
