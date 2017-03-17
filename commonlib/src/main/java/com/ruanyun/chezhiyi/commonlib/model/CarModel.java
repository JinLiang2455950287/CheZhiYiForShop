package com.ruanyun.chezhiyi.commonlib.model;


import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Unique;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Description:
 * author: zhangsan on 16/8/5 下午2:21.
 */
@Entity
public class CarModel {
    private int carModelId;
    /**编号 业务主键  **/
    @Unique@NotNull
    private String carModelNum;
    private String pinyin;
    /** 车型名称 **/
    private String carModelName;
    /**  车型父类编号 与父类carModelNum值相同 000000 为一级**/
    private String parentModelNum;
    /** 车型全称 父类名称+ carModelName **/
    private String carModelAllName;
    /** 排序值 **/
    private int sortNum;

    @Generated(hash = 1117731619)
    public CarModel(int carModelId, @NotNull String carModelNum, String pinyin,
            String carModelName, String parentModelNum, String carModelAllName,
            int sortNum) {
        this.carModelId = carModelId;
        this.carModelNum = carModelNum;
        this.pinyin = pinyin;
        this.carModelName = carModelName;
        this.parentModelNum = parentModelNum;
        this.carModelAllName = carModelAllName;
        this.sortNum = sortNum;
    }

    @Generated(hash = 5810063)
    public CarModel() {
    }

    public int getCarModelId() {
        return carModelId;
    }

    public void setCarModelId(int carModelId) {
        this.carModelId = carModelId;
    }

    public String getCarModelNum() {
        return carModelNum;
    }

    public void setCarModelNum(String carModelNum) {
        this.carModelNum = carModelNum;
    }

    public String getPinyin() {
        return pinyin;
    }

    public void setPinyin(String pinyin) {
        this.pinyin = pinyin;
    }

    public String getCarModelName() {
        return carModelName;
    }

    public void setCarModelName(String carModelName) {
        this.carModelName = carModelName;
    }

    public String getParentModelNum() {
        return parentModelNum;
    }

    public void setParentModelNum(String parentModelNum) {
        this.parentModelNum = parentModelNum;
    }

    public String getCarModelAllName() {
        return carModelAllName;
    }

    public void setCarModelAllName(String carModelAllName) {
        this.carModelAllName = carModelAllName;
    }

    public int getSortNum() {
        return sortNum;
    }

    public void setSortNum(int sortNum) {
        this.sortNum = sortNum;
    }


    @Override
    public String toString() {
        return "CarModel{" +
                "carModelId=" + carModelId +
                ", carModelNum='" + carModelNum + '\'' +
                ", pinyin='" + pinyin + '\'' +
                ", carModelName='" + carModelName + '\'' +
                ", parentModelNum='" + parentModelNum + '\'' +
                ", carModelAllName='" + carModelAllName + '\'' +
                ", sortNum=" + sortNum +
                '}';
    }
}
