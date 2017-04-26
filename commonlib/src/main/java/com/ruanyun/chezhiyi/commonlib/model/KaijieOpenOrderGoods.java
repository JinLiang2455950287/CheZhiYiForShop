package com.ruanyun.chezhiyi.commonlib.model;


import java.util.List;

/**
 * Created by czy on 2017/4/26.
 * 快捷开单服务产品一级/二级
 */

public class KaijieOpenOrderGoods {
    private int projectId;
    private String projectNum;
    private String projectName;
    private String parentNum;
    private String projectAllName;
    private int sortNum;
    private boolean isSelected;

    private List<ProductInfo> productInfoList;

    public KaijieOpenOrderGoods(String projectNum, String projectName, List<ProductInfo> productInfoList) {
        this.projectNum = projectNum;
        this.projectName = projectName;
        this.productInfoList = productInfoList;
    }

    public int getProjectId() {
        return projectId;
    }

    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }

    public String getProjectNum() {
        return projectNum;
    }

    public void setProjectNum(String projectNum) {
        this.projectNum = projectNum;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getParentNum() {
        return parentNum;
    }

    public void setParentNum(String parentNum) {
        this.parentNum = parentNum;
    }

    public String getProjectAllName() {
        return projectAllName;
    }

    public void setProjectAllName(String projectAllName) {
        this.projectAllName = projectAllName;
    }

    public int getSortNum() {
        return sortNum;
    }

    public void setSortNum(int sortNum) {
        this.sortNum = sortNum;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public List<ProductInfo> getProductInfoList() {
        return productInfoList;
    }

    public void setProductInfoList(List<ProductInfo> productInfoList) {
        this.productInfoList = productInfoList;
    }

    @Override
    public String toString() {
        return "KaijieOpenOrderGoods{" +
                "projectId=" + projectId +
                ", projectNum='" + projectNum + '\'' +
                ", projectName='" + projectName + '\'' +
                ", parentNum='" + parentNum + '\'' +
                ", projectAllName='" + projectAllName + '\'' +
                ", sortNum=" + sortNum +
                ", isSelected=" + isSelected +
                ", productInfoList=" + productInfoList +
                '}';
    }
}