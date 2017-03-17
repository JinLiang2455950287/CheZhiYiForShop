package com.ruanyun.chezhiyi.commonlib.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by msq on 2016/9/7.
 */
public class AttachInfo implements Parcelable {

    private int attachId;	//	主键【暂无用处】
    private String filePath;	//	附件路径
    private String fileName;	//	附件原名称【暂无用处】
    private String fileSize;	//	附件大小【暂无用处】
    private String userNum;	//	上传附件人【暂无用处】
    private String createTime;	//	附件上传时间【暂无用处】
    private int attachType;	//	1、产品  2、案例 3、记账本【暂无用处】
    private String glNum;	//	关联编号【暂无用处】

    public int getAttachId() {
        return attachId;
    }

    public void setAttachId(int attachId) {
        this.attachId = attachId;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileSize() {
        return fileSize;
    }

    public void setFileSize(String fileSize) {
        this.fileSize = fileSize;
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

    public int getAttachType() {
        return attachType;
    }

    public void setAttachType(int attachType) {
        this.attachType = attachType;
    }

    public String getGlNum() {
        return glNum;
    }

    public void setGlNum(String glNum) {
        this.glNum = glNum;
    }

    public AttachInfo() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.attachId);
        dest.writeString(this.filePath);
        dest.writeString(this.fileName);
        dest.writeString(this.fileSize);
        dest.writeString(this.userNum);
        dest.writeString(this.createTime);
        dest.writeInt(this.attachType);
        dest.writeString(this.glNum);
    }

    protected AttachInfo(Parcel in) {
        this.attachId = in.readInt();
        this.filePath = in.readString();
        this.fileName = in.readString();
        this.fileSize = in.readString();
        this.userNum = in.readString();
        this.createTime = in.readString();
        this.attachType = in.readInt();
        this.glNum = in.readString();
    }

    public static final Creator<AttachInfo> CREATOR = new Creator<AttachInfo>() {
        @Override
        public AttachInfo createFromParcel(Parcel source) {
            return new AttachInfo(source);
        }

        @Override
        public AttachInfo[] newArray(int size) {
            return new AttachInfo[size];
        }
    };
}
