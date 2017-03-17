package com.ruanyun.chezhiyi.commonlib.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.math.BigDecimal;

/**充值套餐数据结构
 * Created by Sxq on 2016/10/10.
 */
public class RechargeListInfo implements Parcelable {
    /** 主键**/
    private int discountMealId;
    /** 套餐编号**/
    private String discountMealNum;
    /** 门店编号【暂无使用】**/
    private String storeNum;
    /** 充值金额**/
    private BigDecimal amount;
    /** 赠送积分**/
    private int score;
    /** 备注/套餐名称**/
    private String remark;
    /** 套餐状态1有效 2无效**/
    private String status;

    public int getDiscountMealId() {
        return discountMealId;
    }

    public void setDiscountMealId(int discountMealId) {
        this.discountMealId = discountMealId;
    }

    public String getDiscountMealNum() {
        return discountMealNum;
    }

    public void setDiscountMealNum(String discountMealNum) {
        this.discountMealNum = discountMealNum;
    }

    public String getStoreNum() {
        return storeNum;
    }

    public void setStoreNum(String storeNum) {
        this.storeNum = storeNum;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.discountMealId);
        dest.writeString(this.discountMealNum);
        dest.writeString(this.storeNum);
        dest.writeSerializable(this.amount);
        dest.writeInt(this.score);
        dest.writeString(this.remark);
        dest.writeString(this.status);
    }

    public RechargeListInfo() {
    }

    protected RechargeListInfo(Parcel in) {
        this.discountMealId = in.readInt();
        this.discountMealNum = in.readString();
        this.storeNum = in.readString();
        this.amount = (BigDecimal) in.readSerializable();
        this.score = in.readInt();
        this.remark = in.readString();
        this.status = in.readString();
    }

    public static final Parcelable.Creator<RechargeListInfo> CREATOR = new Parcelable.Creator<RechargeListInfo>() {
        @Override
        public RechargeListInfo createFromParcel(Parcel source) {
            return new RechargeListInfo(source);
        }

        @Override
        public RechargeListInfo[] newArray(int size) {
            return new RechargeListInfo[size];
        }
    };
}
