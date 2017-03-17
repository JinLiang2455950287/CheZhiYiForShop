package com.ruanyun.chezhiyi.commonlib.model;


import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by msq on 2016/9/6.
 */
public class CaseInfo implements Parcelable {

    private int libraryId;	//	主键
    private String libraryNum;	//	案例编码【业务主键】
    private String libraryName;	//	案例名称
    private String mainPhoto;	//	主图【暂无使用】
    private String libraryIntroduce;	//	案例介绍
    private String userNum;	//	用户编号【查询技师案例时使用】
    private int isHomeShow;	//	是否首页显示(1是 2否)
    private int praiseCount;	//	点赞量
//    commentCount	Int	评论量
//    createTime	Date	创建时间
//    status	Int	审核状态1通过 2待审核 -1未通过 【技师个人案例审核成功后可在企业案例中使用】
    private String libraryType;	//	案例库分类【读取服务类型一级】
    private String storeNum;	//	门店num【暂无使用】
    private int userType;	//	用户类型 1技师案例  2企业案例
    private String createTime;
    private int status; //审核状态1通过 2待审核 -1未通过
    private List<AttachInfo> attachInfoList;	//	案例图片附件数据结构（TAttachInfo）

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public int getLibraryId() {
        return libraryId;
    }

    public void setLibraryId(int libraryId) {
        this.libraryId = libraryId;
    }

    public String getLibraryNum() {
        return libraryNum;
    }

    public void setLibraryNum(String libraryNum) {
        this.libraryNum = libraryNum;
    }

    public String getLibraryName() {
        return libraryName;
    }

    public void setLibraryName(String libraryName) {
        this.libraryName = libraryName;
    }

    public String getMainPhoto() {
        return mainPhoto;
    }

    public void setMainPhoto(String mainPhoto) {
        this.mainPhoto = mainPhoto;
    }

    public String getLibraryIntroduce() {
        return libraryIntroduce;
    }

    public void setLibraryIntroduce(String libraryIntroduce) {
        this.libraryIntroduce = libraryIntroduce;
    }

    public String getUserNum() {
        return userNum;
    }

    public void setUserNum(String userNum) {
        this.userNum = userNum;
    }

    public int getIsHomeShow() {
        return isHomeShow;
    }

    public void setIsHomeShow(int isHomeShow) {
        this.isHomeShow = isHomeShow;
    }

    public int getPraiseCount() {
        return praiseCount;
    }

    public void setPraiseCount(int praiseCount) {
        this.praiseCount = praiseCount;
    }

    public String getLibraryType() {
        return libraryType;
    }

    public void setLibraryType(String libraryType) {
        this.libraryType = libraryType;
    }

    public String getStoreNum() {
        return storeNum;
    }

    public void setStoreNum(String storeNum) {
        this.storeNum = storeNum;
    }

    public int getUserType() {
        return userType;
    }

    public void setUserType(int userType) {
        this.userType = userType;
    }

    public List<AttachInfo> getAttachInfoList() {
        return attachInfoList;
    }

    public void setAttachInfoList(List<AttachInfo> attachInfoList) {
        this.attachInfoList = attachInfoList;
    }

    public CaseInfo() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.libraryId);
        dest.writeString(this.libraryNum);
        dest.writeString(this.libraryName);
        dest.writeString(this.mainPhoto);
        dest.writeString(this.libraryIntroduce);
        dest.writeString(this.userNum);
        dest.writeInt(this.isHomeShow);
        dest.writeInt(this.praiseCount);
        dest.writeString(this.libraryType);
        dest.writeString(this.storeNum);
        dest.writeInt(this.userType);
        dest.writeString(this.createTime);
        dest.writeInt(this.status);
        dest.writeTypedList(this.attachInfoList);
    }

    protected CaseInfo(Parcel in) {
        this.libraryId = in.readInt();
        this.libraryNum = in.readString();
        this.libraryName = in.readString();
        this.mainPhoto = in.readString();
        this.libraryIntroduce = in.readString();
        this.userNum = in.readString();
        this.isHomeShow = in.readInt();
        this.praiseCount = in.readInt();
        this.libraryType = in.readString();
        this.storeNum = in.readString();
        this.userType = in.readInt();
        this.createTime = in.readString();
        this.status = in.readInt();
        this.attachInfoList = in.createTypedArrayList(AttachInfo.CREATOR);
    }

    public static final Creator<CaseInfo> CREATOR = new Creator<CaseInfo>() {
        @Override
        public CaseInfo createFromParcel(Parcel source) {
            return new CaseInfo(source);
        }

        @Override
        public CaseInfo[] newArray(int size) {
            return new CaseInfo[size];
        }
    };
}
