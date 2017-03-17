package com.ruanyun.chezhiyi.commonlib.model;

/**
 * Created by Sxq on 2016/9/29.
 */
public class IconInfo {
    private String title;
    private int icon;

    public IconInfo(int icon, String title) {
        this.icon = icon;
        this.title = title;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
