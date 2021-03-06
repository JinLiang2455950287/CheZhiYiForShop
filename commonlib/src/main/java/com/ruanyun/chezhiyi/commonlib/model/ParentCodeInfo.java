package com.ruanyun.chezhiyi.commonlib.model;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT. Enable "keep" sections if you want to edit. 

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Transient;
import org.greenrobot.greendao.annotation.Unique;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Entity mapped to table "PARENT_CODE_INFO".
 */
@Entity
public class ParentCodeInfo {

    @Unique
    private Long parentId;
    private String parentName;
    private String parentCode;
    private String itemCode;
    private String itemName;
    @Transient
    private boolean isSelected;
    @Transient
    private Integer orderby;

    public boolean getSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public Integer getOrderby() {
        return orderby;
    }

    public void setOrderby(Integer orderby) {
        this.orderby = orderby;
    }

    public String getItemName() {
        return this.itemName;
    }
    public void setItemName(String itemName) {
        this.itemName = itemName;
    }
    public String getItemCode() {
        return this.itemCode;
    }
    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }
    public String getParentCode() {
        return this.parentCode;
    }
    public void setParentCode(String parentCode) {
        this.parentCode = parentCode;
    }
    public String getParentName() {
        return this.parentName;
    }
    public void setParentName(String parentName) {
        this.parentName = parentName;
    }
    public Long getParentId() {
        return this.parentId;
    }
    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }
    @Generated(hash = 1332210339)
    public ParentCodeInfo(Long parentId, String parentName, String parentCode, String itemCode,
            String itemName) {
        this.parentId = parentId;
        this.parentName = parentName;
        this.parentCode = parentCode;
        this.itemCode = itemCode;
        this.itemName = itemName;
    }
    @Generated(hash = 1211960592)
    public ParentCodeInfo() {
    }

    @Override
    public String toString() {
        return "ParentCodeInfo{" +
                "parentId=" + parentId +
                ", parentName='" + parentName + '\'' +
                ", parentCode='" + parentCode + '\'' +
                ", itemCode='" + itemCode + '\'' +
                ", itemName='" + itemName + '\'' +
                ", isSelected=" + isSelected +
                ", orderby=" + orderby +
                '}';
    }
}
