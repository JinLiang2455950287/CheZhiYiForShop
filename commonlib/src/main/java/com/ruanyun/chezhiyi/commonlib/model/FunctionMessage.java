package com.ruanyun.chezhiyi.commonlib.model;

/**
 * RecyclerView设置我的界面数据类
 */
public class FunctionMessage {
    /**图标**/
    private int icon;
    /**功能名称**/
    private String name;
    /**功能信息**/
    private String introduce;
    /**type**/
    private int type;
    public static final int SHOP_SERVICE_WORK_ORDER = 1;
    public static final int SHOP_SERVICE_WORK_STATE = 2;
    public static final int SHOP_FUNCTION = 3;


    public static final int CLIENT_ACCOUNT = 4;
    public static final int CLIENT_FUNCTION = 5;

    public FunctionMessage(int icon, String name, int type) {
        this.icon = icon;
        this.name = name;
        this.type = type;
    }

    public FunctionMessage(int icon, String name, String introduce, int type) {
        this.icon = icon;
        this.name = name;
        this.introduce = introduce;
        this.type = type;
    }
    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIntroduce() {
        return introduce;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
