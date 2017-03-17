package com.ruanyun.chezhiyi.commonlib.model;

import android.os.Parcel;
import android.os.Parcelable;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Transient;
import org.greenrobot.greendao.annotation.Unique;
import org.greenrobot.greendao.annotation.Generated;

import java.util.List;

/**
 * Description:工单服务和技师技能
 * author: zhangsan on 16/8/5 下午2:44.
 */
@Entity
public class ProjectType implements Parcelable {
    /** 主键 **/
    @Unique
    private  int projectId;
    /**业务主键  **/
    @NotNull
    private  String projectNum;
    /** 服务名称或技师名称 **/
    private String projectName;
    /** 父类编号 与父类的projectNum相同 000000 为一级 **/
    private String parentNum;
    /** 工单服务或技师技能父类编号 与父类carModelNum值相同 000000 为一级 **/
    private String projectAllName;
    /** 排序值（升序） **/
    private int sortNum;
    /** 是否支持预约 1是2否**/
    private int isMake;
    @Transient
    private List<ProjectType> childProjectTypeList;

    @Transient
    private String projectAllSelectName;// 预约  选中子项的名字  string

    @Transient
    private boolean isSelected=false;

    @Transient
    private boolean isParent=false;


    public String getProjectAllSelectName() {
        return projectAllSelectName;
    }

    public void setProjectAllSelectName(String projectAllSelectName) {
        this.projectAllSelectName = projectAllSelectName;
    }

    public List<ProjectType> getChildProjectTypeList() {
        return childProjectTypeList;
    }

    public void setChildProjectTypeList(List<ProjectType> childProjectTypeList) {
        this.childProjectTypeList = childProjectTypeList;
    }

    public int getIsMake() {
        return isMake;
    }

    public void setIsMake(int isMake) {
        this.isMake = isMake;
    }


//    @Transient
//    private int technicianProjectId;
//
//    public int getTechnicianProjectId() {
//        return technicianProjectId;
//    }
//
//    public void setTechnicianProjectId(int technicianProjectId) {
//        this.technicianProjectId = technicianProjectId;
//    }

    @Override
    public String toString() {
        return "ProjectType{" +
                "projectId=" + projectId +
                ", projectNum='" + projectNum + '\'' +
                ", projectName='" + projectName + '\'' +
                ", parentNum='" + parentNum + '\'' +
                ", projectAllName='" + projectAllName + '\'' +
                ", sortNum=" + sortNum +
                ", isSelected=" + isSelected +
                '}';
    }


    public boolean isParent() {
        return isParent;
    }

    public void setParent(boolean parent) {
        isParent = parent;
    }

    @Generated(hash = 1122313167)
    public ProjectType(int projectId, @NotNull String projectNum, String projectName,
            String parentNum, String projectAllName, int sortNum, int isMake) {
        this.projectId = projectId;
        this.projectNum = projectNum;
        this.projectName = projectName;
        this.parentNum = parentNum;
        this.projectAllName = projectAllName;
        this.sortNum = sortNum;
        this.isMake = isMake;
    }

    @Generated(hash = 807885465)
    public ProjectType() {
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
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


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.projectId);
        dest.writeString(this.projectNum);
        dest.writeString(this.projectName);
        dest.writeString(this.parentNum);
        dest.writeString(this.projectAllName);
        dest.writeInt(this.sortNum);
        dest.writeInt(this.isMake);
        dest.writeTypedList(this.childProjectTypeList);
        dest.writeByte(this.isSelected ? (byte) 1 : (byte) 0);
        dest.writeByte(this.isParent ? (byte) 1 : (byte) 0);
    }

    protected ProjectType(Parcel in) {
        this.projectId = in.readInt();
        this.projectNum = in.readString();
        this.projectName = in.readString();
        this.parentNum = in.readString();
        this.projectAllName = in.readString();
        this.sortNum = in.readInt();
        this.isMake = in.readInt();
        this.childProjectTypeList = in.createTypedArrayList(ProjectType.CREATOR);
        this.isSelected = in.readByte() != 0;
        this.isParent = in.readByte() != 0;
    }

    public static final Creator<ProjectType> CREATOR = new Creator<ProjectType>() {
        @Override
        public ProjectType createFromParcel(Parcel source) {
            return new ProjectType(source);
        }

        @Override
        public ProjectType[] newArray(int size) {
            return new ProjectType[size];
        }
    };
}
