package com.ruanyun.chezhiyi.commonlib.model;

import android.os.Parcel;
import android.os.Parcelable;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Transient;
import org.greenrobot.greendao.annotation.Unique;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by ycw on 2016/9/5.
 */

/**
 * {
 * "androidPic":"file//homeiconpic//7527949104177.png",
 * "homeIconId":8,
 * "homeIconName":"门店详情",
 * "homeIconNum":"cl18480000000008",
 * "iosPic":"file//homeiconpic//7527949104177.png",
 * "isSortNum":1,
 * "moduleNum":1,
 * "moduleType":1,
 * "remark":"暂无",
 * "sortNum":1,
 * "storeNum":"st30390000000011",
 * "userNum":"sys46370000010001"
 * }
 */
@Entity
public class HomeIconInfo implements Parcelable {
//    public static final int SHOP_SERVICE_WORK_ORDER = 1;
//    public static final int SHOP_SERVICE_WORK_STATE = 2;
//    public static final int SHOP_FUNCTION = 3;
  //  public static final int CLIENT_ACCOUNT = 4;
 //   public static final int CLIENT_FUNCTION = 5;
    @Transient
    private int homeIconId;//	int	主键
    private String homeIconName;//	String	图标名称
    @Unique
    private String homeIconNum;
    //private String iosPic;//	String	图标图片file/homeiconpic/
    private String androidPic;//	String	android图片file/homeiconpic/
    @NotNull
    private int moduleType;//	Int	模块类型（从字典表中读取） parent_code=’ MODULE_TYPE’ 司机首页 1司机发现2 司机我的 3 技师端首页4 技师端电商5 技师端我的6
    @NotNull
    private String moduleNum;//	String 	模块编号
    private String remark;//	String	备注
    private int sortNum;//	Int	排序【倒序】
    @Transient
    private String storeNum;//	String	门店【暂无用处】
    @Transient
    private int isSortNum;//	int	是否可排序1是2否
    /**type**/
    @Transient
    private int type;
    /**
     * 数量
     */
    @Transient
    private int number = 0;

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getStoreNum() {
        return storeNum;
    }

    public void setStoreNum(String storeNum) {
        this.storeNum = storeNum;
    }

    public int getIsSortNum() {
        return isSortNum;
    }

    public void setIsSortNum(int isSortNum) {
        this.isSortNum = isSortNum;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getRemark() {
        return this.remark;
    }
    public void setRemark(String remark) {
        this.remark = remark;
    }
    public String getModuleNum() {
        return this.moduleNum;
    }
    public void setModuleNum(String moduleNum) {
        this.moduleNum = moduleNum;
    }
    public int getModuleType() {
        return this.moduleType;
    }
    public void setModuleType(int moduleType) {
        this.moduleType = moduleType;
    }
    public String getAndroidPic() {
        return this.androidPic;
    }
    public void setAndroidPic(String androidPic) {
        this.androidPic = androidPic;
    }
    public String getHomeIconNum() {
        return this.homeIconNum;
    }
    public void setHomeIconNum(String homeIconNum) {
        this.homeIconNum = homeIconNum;
    }
    public String getHomeIconName() {
        return this.homeIconName;
    }
    public void setHomeIconName(String homeIconName) {
        this.homeIconName = homeIconName;
    }
    public int getHomeIconId() {
        return this.homeIconId;
    }
    public void setHomeIconId(int homeIconId) {
        this.homeIconId = homeIconId;
    }
    public int getSortNum() {
        return this.sortNum;
    }
    public void setSortNum(int sortNum) {
        this.sortNum = sortNum;
    }
    @Generated(hash = 950851102)
    public HomeIconInfo(String homeIconName, String homeIconNum, String androidPic, int moduleType,
            @NotNull String moduleNum, String remark, int sortNum) {
        this.homeIconName = homeIconName;
        this.homeIconNum = homeIconNum;
        this.androidPic = androidPic;
        this.moduleType = moduleType;
        this.moduleNum = moduleNum;
        this.remark = remark;
        this.sortNum = sortNum;
    }
    @Generated(hash = 1004816967)
    public HomeIconInfo() {
    }

    @Override
    public String toString() {
        return "HomeIconInfo{" +
                "homeIconId=" + homeIconId +
                ", homeIconName='" + homeIconName + '\'' +
                ", homeIconNum='" + homeIconNum + '\'' +
                ", androidPic='" + androidPic + '\'' +
                ", moduleType=" + moduleType +
                ", moduleNum='" + moduleNum + '\'' +
                ", remark='" + remark + '\'' +
                ", sortNum=" + sortNum +
                ", storeNum='" + storeNum + '\'' +
                ", isSortNum=" + isSortNum +
                ", type=" + type +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.homeIconId);
        dest.writeString(this.homeIconName);
        dest.writeString(this.homeIconNum);
        dest.writeString(this.androidPic);
        dest.writeInt(this.moduleType);
        dest.writeString(this.moduleNum);
        dest.writeString(this.remark);
        dest.writeInt(this.sortNum);
        dest.writeString(this.storeNum);
        dest.writeInt(this.isSortNum);
        dest.writeInt(this.type);
    }

    protected HomeIconInfo(Parcel in) {
        this.homeIconId = in.readInt();
        this.homeIconName = in.readString();
        this.homeIconNum = in.readString();
        this.androidPic = in.readString();
        this.moduleType = in.readInt();
        this.moduleNum = in.readString();
        this.remark = in.readString();
        this.sortNum = in.readInt();
        this.storeNum = in.readString();
        this.isSortNum = in.readInt();
        this.type = in.readInt();
    }

    public static final Parcelable.Creator<HomeIconInfo> CREATOR = new Parcelable.Creator<HomeIconInfo>() {
        @Override
        public HomeIconInfo createFromParcel(Parcel source) {
            return new HomeIconInfo(source);
        }

        @Override
        public HomeIconInfo[] newArray(int size) {
            return new HomeIconInfo[size];
        }
    };

}
