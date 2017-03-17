package com.ruanyun.chezhiyi.commonlib.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Description ：1.16.1	通知公告表结构
 * <p/>
 * Created by ycw on 2016/10/10.
 */
public class NoticeInfo implements Parcelable {
    private int infoId;//     	Integer 	主键
    private String title;//      	String  	标题
    private String content;//    	String  	内容
    private int status;//     	Integer 	状态 1-发布 2-未发布
    private String pubTime;//    	Date    	发布时间
    private String userNum;//    	String  	创建用户编号
    private String createTime;// 	Date    	创建时间
    private String storeNum;//   	String  	店铺编号 暂无用处

    public int getInfoId() {
        return infoId;
    }

    public void setInfoId(int infoId) {
        this.infoId = infoId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getPubTime() {
        return pubTime;
    }

    public void setPubTime(String pubTime) {
        this.pubTime = pubTime;
    }

    public String getUserNum() {
        return userNum;
    }

    public void setUserNum(String userNum) {
        this.userNum = userNum;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getStoreNum() {
        return storeNum;
    }

    public void setStoreNum(String storeNum) {
        this.storeNum = storeNum;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.infoId);
        dest.writeString(this.title);
        dest.writeString(this.content);
        dest.writeInt(this.status);
        dest.writeString(this.pubTime);
        dest.writeString(this.userNum);
        dest.writeString(this.createTime);
        dest.writeString(this.storeNum);
    }

    public NoticeInfo() {
    }

    protected NoticeInfo(Parcel in) {
        this.infoId = in.readInt();
        this.title = in.readString();
        this.content = in.readString();
        this.status = in.readInt();
        this.pubTime = in.readString();
        this.userNum = in.readString();
        this.createTime = in.readString();
        this.storeNum = in.readString();
    }

    public static final Parcelable.Creator<NoticeInfo> CREATOR = new Parcelable.Creator<NoticeInfo>() {
        @Override
        public NoticeInfo createFromParcel(Parcel source) {
            return new NoticeInfo(source);
        }

        @Override
        public NoticeInfo[] newArray(int size) {
            return new NoticeInfo[size];
        }
    };

    @Override
    public String toString() {
        return "NoticeInfo{" +
                "infoId=" + infoId +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", status=" + status +
                ", pubTime='" + pubTime + '\'' +
                ", userNum='" + userNum + '\'' +
                ", createTime='" + createTime + '\'' +
                ", storeNum='" + storeNum + '\'' +
                '}';
    }
}
