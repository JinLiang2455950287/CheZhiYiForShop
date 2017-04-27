package com.ruanyun.chezhiyi.commonlib.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by czy on 2017/4/27.
 * 工位/技师 bean
 */

public class GongWeiJiShiBean implements Parcelable {
    private String jishiname;
    private String gongweiname;
    private String jishiid;
    private String gongweiid;
    private String projectNumber;

    public GongWeiJiShiBean() {
    }

    public String getJishiname() {
        return jishiname;
    }

    public void setJishiname(String jishiname) {
        this.jishiname = jishiname;
    }

    public String getGongweiname() {
        return gongweiname;
    }

    public void setGongweiname(String gongweiname) {
        this.gongweiname = gongweiname;
    }

    public String getJishiid() {
        return jishiid;
    }

    public void setJishiid(String jishiid) {
        this.jishiid = jishiid;
    }

    public String getGongweiid() {
        return gongweiid;
    }

    public void setGongweiid(String gongweiid) {
        this.gongweiid = gongweiid;
    }

    public String getProjectNumber() {
        return projectNumber;
    }

    public void setProjectNumber(String projectNumber) {
        this.projectNumber = projectNumber;
    }

    @Override
    public String toString() {
        return "GongWeiJiShiBean{" +
                "jishiname='" + jishiname + '\'' +
                ", gongweiname='" + gongweiname + '\'' +
                ", jishiid='" + jishiid + '\'' +
                ", gongweiid='" + gongweiid + '\'' +
                ", projectNumber='" + projectNumber + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.jishiname);
        dest.writeString(this.gongweiname);
        dest.writeString(this.jishiid);
        dest.writeString(this.gongweiid);
        dest.writeString(this.projectNumber);
    }

    protected GongWeiJiShiBean(Parcel in) {
        this.jishiname = in.readString();
        this.gongweiname = in.readString();
        this.jishiid = in.readString();
        this.gongweiid = in.readString();
        this.projectNumber = in.readString();
    }

    public static final Parcelable.Creator<GongWeiJiShiBean> CREATOR = new Parcelable.Creator<GongWeiJiShiBean>() {
        @Override
        public GongWeiJiShiBean createFromParcel(Parcel source) {
            return new GongWeiJiShiBean(source);
        }

        @Override
        public GongWeiJiShiBean[] newArray(int size) {
            return new GongWeiJiShiBean[size];
        }
    };
}
