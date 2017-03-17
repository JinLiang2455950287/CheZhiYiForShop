package com.ruanyun.chezhiyi.commonlib.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.ruanyun.chezhiyi.commonlib.R;

import java.util.List;

/**
 * Description:
 * author: zhangsan on 16/7/28 下午2:10.
 */
public class User implements Parcelable {
    private String userNum;
    /**昵称【如果为好友时，该值为好友备注值】 **/
    private String nickName;
    /** 用户好友备注【有则优先显示 无则显示nickName 二者只显示一个】 */
    private String friendNickName;
    /** 登录名 */
    private String loginName;
    /** 用户所在好友组的编号 **/
    private String groupNum;
    /** 登录密码 */
    private String loginPass;
    /** 用户的性别 */
    private int userSex;
    /** 头像 **/
    private String userPhoto;
    /**  用户生日   */
    private String userBirth;
    /**   职业 */
    private String profession;
    /**    1 系统用户 2 技师用户 3 客户端用户  */
    private int userType;
    /** 1 好友2 好友【未同意，暂无用处】 3 黑名单【暂无用处】 4 非好友 **/
    private int friendStatus;
    /**   联系电话   */
    private String linkTel;
    /**   个人爱好  */
    private String userInterest;
    /**   服务类型   */
    private List<ProjectType> projectTypeList;

     /**工作状态**/
    private String workStatus;
    /**个人说明**/
    private String personalNote;

    private String personalSign;
    /**  技师的    个人标签   **/
    private String labelCode;

    private boolean isSelected=false;
    /** 判断是否是质检员 1是 2否 **/
    private int isQcPerson;
    /** 判断是否可以接单 1是 2否   */
    private int isOrder;

    public int getIsOrder() {
        return isOrder;
    }

    public void setIsOrder(int isOrder) {
        this.isOrder = isOrder;
    }

    public int getIsQcPerson() {
        return isQcPerson;
    }

    public void setIsQcPerson(int isQcPerson) {
        this.isQcPerson = isQcPerson;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public String getLabelCode() {
        return labelCode;
    }

    public void setLabelCode(String labelCode) {
        this.labelCode = labelCode;
    }

    public int getFriendStatus() {
        return friendStatus;
    }

    public void setFriendStatus(int friendStatus) {
        this.friendStatus = friendStatus;
    }

    public String getUserNum() {
        return userNum;
    }

    public void setUserNum(String userNum) {
        this.userNum = userNum;
    }

    public String getNickName() {
        return nickName;
    }

    public String getWorkStatus() {
        return workStatus;
    }

    public void setWorkStatus(String workStatus) {
        this.workStatus = workStatus;
    }

    public String getPersonalNote() {
        return personalNote;
    }

    public String getUserInterest() {
        return userInterest;
    }

    public void setUserInterest(String userInterest) {
        this.userInterest = userInterest;
    }

    public void setPersonalNote(String personalNote) {
        this.personalNote = personalNote;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getGroupNum() {
        return groupNum;
    }

    public void setGroupNum(String groupNum) {
        this.groupNum = groupNum;
    }

    public String getLoginPass() {
        return loginPass;
    }

    public void setLoginPass(String loginPass) {
        this.loginPass = loginPass;
    }

    public int getUserSex() {
        return userSex;
    }

    public void setUserSex(int userSex) {
        this.userSex = userSex;
    }

    public String getUserPhoto() {
        return userPhoto;
    }

    public void setUserPhoto(String userPhoto) {
        this.userPhoto = userPhoto;
    }

    public String getUserBirth() {
        return userBirth;
    }

    public void setUserBirth(String userBirth) {
        this.userBirth = userBirth;
    }

    public String getProfession() {
        return profession;
    }

    public List<ProjectType> getProjectTypeList() {
        return projectTypeList;
    }

    public void setProjectTypeList(List<ProjectType> projectTypeList) {
        this.projectTypeList = projectTypeList;
    }

    public String getPersonalSign() {
        return personalSign;
    }

    public void setPersonalSign(String personalSign) {
        this.personalSign = personalSign;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }
    /** 1 系统用户 2 技师用户 3 客户端用户  */
    public int getUserType() {
        return userType;
    }

    public void setUserType(int userType) {
        this.userType = userType;
    }

    public String getFriendNickName() {
        return friendNickName;
    }

    public void setFriendNickName(String friendNickName) {
        this.friendNickName = friendNickName;
    }

    public String getLinkTel() {
        return linkTel;
    }

    public void setLinkTel(String linkTel) {
        this.linkTel = linkTel;
    }

    public User() {
    }

    /**
     * @return 0 -- 不显示
     */
    public int getUserSexResId() {
        int sexResId;
        switch (userSex) {
            case 1:
                sexResId = R.drawable.icon_male;
                break;
            case 2:
                sexResId = R.drawable.icon_female;
                break;
            default:
                sexResId = 0;
                break;
        }
        return sexResId;
    }

    /**
     * @return 0 -- 不显示
     */
    public int getUserSexWhiteResId() {
        int sexResId;
        switch (userSex) {
            case 1:
                sexResId = R.drawable.icon_white_male;
                break;
            case 2:
                sexResId = R.drawable.icon_white_female;
                break;
            default:
                sexResId = 0;
                break;
        }
        return sexResId;
    }


    @Override
    public String toString() {
        return "User{" +
                "userNum='" + userNum + '\'' +
                ", nickName='" + nickName + '\'' +
                ", loginName='" + loginName + '\'' +
                ", groupNum='" + groupNum + '\'' +
                ", loginPass='" + loginPass + '\'' +
                ", userSex=" + userSex +
                ", userPhoto='" + userPhoto + '\'' +
                ", userBirth='" + userBirth + '\'' +
                ", profession='" + profession + '\'' +
                ", userType=" + userType +
                ", friendStatus=" + friendStatus +
                '}';
    }

    @Override
    public int describeContents() { return 0; }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.userNum);
        dest.writeString(this.nickName);
        dest.writeString(this.friendNickName);
        dest.writeString(this.loginName);
        dest.writeString(this.groupNum);
        dest.writeString(this.loginPass);
        dest.writeInt(this.userSex);
        dest.writeString(this.userPhoto);
        dest.writeString(this.userBirth);
        dest.writeString(this.profession);
        dest.writeInt(this.userType);
        dest.writeInt(this.friendStatus);
        dest.writeString(this.linkTel);
        dest.writeString(this.userInterest);
        dest.writeTypedList(this.projectTypeList);
        dest.writeString(this.workStatus);
        dest.writeString(this.personalNote);
        dest.writeString(this.personalSign);
        dest.writeString(this.labelCode);
        dest.writeByte(this.isSelected ? (byte) 1 : (byte) 0);
        dest.writeInt(this.isQcPerson);
    }

    protected User(Parcel in) {
        this.userNum = in.readString();
        this.nickName = in.readString();
        this.friendNickName = in.readString();
        this.loginName = in.readString();
        this.groupNum = in.readString();
        this.loginPass = in.readString();
        this.userSex = in.readInt();
        this.userPhoto = in.readString();
        this.userBirth = in.readString();
        this.profession = in.readString();
        this.userType = in.readInt();
        this.friendStatus = in.readInt();
        this.linkTel = in.readString();
        this.userInterest = in.readString();
        this.projectTypeList = in.createTypedArrayList(ProjectType.CREATOR);
        this.workStatus = in.readString();
        this.personalNote = in.readString();
        this.personalSign = in.readString();
        this.labelCode = in.readString();
        this.isSelected = in.readByte() != 0;
        this.isQcPerson = in.readInt();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel source) {return new User(source);}

        @Override
        public User[] newArray(int size) {return new User[size];}
    };
}
