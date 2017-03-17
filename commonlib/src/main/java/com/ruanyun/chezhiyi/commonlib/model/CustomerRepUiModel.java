package com.ruanyun.chezhiyi.commonlib.model;

import android.text.TextUtils;

/**
 * Description:客户接待列表实体类模型
 * author: zhangsan on 16/10/8 上午8:13.
 */
public class CustomerRepUiModel {
    public static final int TYPE_PROJECT_TYPE= 12;
    public static final int TYPE_TAB = 13;//切换选工位技师商品
    public static final int TYPE_GOODS = 14;//商品
    public static final int TYPE_WORK_STATION = 19;//工位
    public static final int TYPE_TECHNICIAN = 15;//技师
    public static final int TYPE_BUTTON=16;//新增商品按钮
    public static final int TYPE_EMPTY_VIEW=17;//
    public static final int EMPTY_TYPE_GOODS=18;//选商品emptyview
    public static final int EMPTY_TYPE_TECHNICIAN=20;//技师emptyview
    public static final int EMPTY_TYPE_WORKBAY=21;//空位为空
    static final String ITEM_SEL_DES="(%s/%s/%s)";
    private int selecTab;//记录切换标签当前选择第几个tab
    private int count=1;
    private int itemType;
    private String itemNum;
    public String parentNum;
    private String itemName;
    private boolean isSelected;
    private String description;
    private String emptyString;//列表为空提示
    private int emptyType=EMPTY_TYPE_GOODS;
    private boolean isServiceSelected=false;
    /** 商品是否过期   1是   2否  */
    private int isOverdue = 2;
    /** 判断是否是新添加的item **/
    public  boolean isNewItem=false;
    /**  绑定服务端的数据bean 方便获取数据**/
    public Object relativeBean;

    public String selectedWorkStation="";
    public String selectedGoods="";
    public String selecedTechnicanName="";
    /**
      * 选择商品 技师 工位时添加描述
      *@author zhangsan
      *@date   16/10/29 上午11:38
      */
    public void setSelDescription(){
//      description=String.format(ITEM_SEL_DES, TextUtils.isEmpty(selectedWorkStation)?"未选择":selectedWorkStation
//              ,TextUtils.isEmpty(selecedTechnicanName)?"未选择":selecedTechnicanName
//              ,TextUtils.isEmpty(selectedGoods)?"":selectedGoods);
        StringBuilder stringBuilder = new StringBuilder("(");
        description= "";
        if (!TextUtils.isEmpty(selectedWorkStation)) {
            stringBuilder.append(selectedWorkStation);
        }
        if (!TextUtils.isEmpty(selecedTechnicanName)) {
            if (stringBuilder.length() > 1) {
                stringBuilder.append("/");
            }
            stringBuilder.append(selecedTechnicanName);
        }
        if (!TextUtils.isEmpty(selectedGoods)) {
            if (stringBuilder.length() > 1) {
                stringBuilder.append("/");
            }
            stringBuilder.append(selectedGoods);
        }
        if (stringBuilder.length() > 1) {
            stringBuilder.append(")");
            description=stringBuilder.toString();
        } else {
            description="";
        }
    }
    public boolean isServiceSelected() {
        return isServiceSelected;
    }

    public void setServiceSelected(boolean serviceSelected) {
        isServiceSelected = serviceSelected;
    }

    public int getEmptyType() {
        return emptyType;
    }

    public void setEmptyType(int emptyType) {
        this.emptyType = emptyType;
    }

    public String getEmptyString() {
        return emptyString;
    }

    public void setEmptyString(String emptyString) {
        this.emptyString = emptyString;
    }

    public int getSelecTab() {
        return selecTab;
    }

    public void setSelecTab(int selecTab) {
        this.selecTab = selecTab;
    }

    public String getItemNum() {
        return itemNum;
    }

    public void setItemNum(String itemNum) {
        this.itemNum = itemNum;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getItemType() {
        return itemType;
    }

    public void setItemType(int itemType) {
        this.itemType = itemType;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public void setIsOverdue(int isOverdue) {
        this.isOverdue = isOverdue;
    }

    public int getIsOverdue() {
        return isOverdue;
    }
}
