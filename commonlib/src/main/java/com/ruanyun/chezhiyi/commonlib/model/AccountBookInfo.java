package com.ruanyun.chezhiyi.commonlib.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author hdl
 * 记账本信息
 */
public class AccountBookInfo implements Parcelable {
    public static int BOOK_TYPE_AUTO = 1;
    public static int BOOK_TYPE_MANUAL = 2;
    /**主键**/
    private int bookId;
    /**业务主键**/
    private String bookNum;
    /**记账类型（1自动创建 2手动创建）**/
    private int bookType;
    /**日期**/
    private String bookDate;
    /**金额【java 对应的金钱类型 小数点两位 】**/
    private BigDecimal bookPrice;
    /**备注**/
    private String remark;
    /**类型  TprojectType 工单服务和技师技能 数据结构 一级数据 的 业务主键【projectNum】**/
    private String projectNum;
    /**记录projectNum所对应的工单名称如：美容**/
    private String projectName;
    /**门店编号【暂不使用】**/
    private String storeNum;
    /**用户编号**/
    private String userNum;
    /**  **/
    private String commonNum;
    /**
     * 附件数据结构,图片地址
     */
    private List<AttachInfo> attachInfoList;

    public String getCommonNum() {
        return commonNum;
    }

    public void setCommonNum(String commonNum) {
        this.commonNum = commonNum;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public String getBookNum() {
        return bookNum;
    }

    public void setBookNum(String bookNum) {
        this.bookNum = bookNum;
    }

    public int getBookType() {
        return bookType;
    }

    public void setBookType(int bookType) {
        this.bookType = bookType;
    }

    public String getBookDate() {
        return bookDate;
    }

    public void setBookDate(String bookDate) {
        this.bookDate = bookDate;
    }

    public BigDecimal getBookPrice() {
        return bookPrice;
    }

    public void setBookPrice(BigDecimal bookPrice) {
        this.bookPrice = bookPrice;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getProjectNum() {
        return projectNum;
    }

    public void setProjectNum(String projectNum) {
        this.projectNum = projectNum;
    }

    public String getStoreNum() {
        return storeNum;
    }

    public void setStoreNum(String storeNum) {
        this.storeNum = storeNum;
    }

    public String getUserNum() {
        return userNum;
    }

    public void setUserNum(String userNum) {
        this.userNum = userNum;
    }

    public List<AttachInfo> getAttachInfoList() {
        return attachInfoList;
    }

    public void setAttachInfoList(List<AttachInfo> attachInfoList) {
        this.attachInfoList = attachInfoList;
    }


    public AccountBookInfo() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.bookId);
        dest.writeString(this.bookNum);
        dest.writeInt(this.bookType);
        dest.writeString(this.bookDate);
        dest.writeSerializable(this.bookPrice);
        dest.writeString(this.remark);
        dest.writeString(this.projectNum);
        dest.writeString(this.projectName);
        dest.writeString(this.storeNum);
        dest.writeString(this.userNum);
        dest.writeString(this.commonNum);
        dest.writeTypedList(this.attachInfoList);
    }

    protected AccountBookInfo(Parcel in) {
        this.bookId = in.readInt();
        this.bookNum = in.readString();
        this.bookType = in.readInt();
        this.bookDate = in.readString();
        this.bookPrice = (BigDecimal) in.readSerializable();
        this.remark = in.readString();
        this.projectNum = in.readString();
        this.projectName = in.readString();
        this.storeNum = in.readString();
        this.userNum = in.readString();
        this.commonNum = in.readString();
        this.attachInfoList = in.createTypedArrayList(AttachInfo.CREATOR);
    }

    public static final Creator<AccountBookInfo> CREATOR = new Creator<AccountBookInfo>() {
        @Override
        public AccountBookInfo createFromParcel(Parcel source) {
            return new AccountBookInfo(source);
        }

        @Override
        public AccountBookInfo[] newArray(int size) {
            return new AccountBookInfo[size];
        }
    };
}
