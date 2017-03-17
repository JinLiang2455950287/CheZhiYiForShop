package com.ruanyun.chezhiyi.commonlib.model;

import java.util.List;

/**
 * Description ：服务内容 json格式 [{projectNum:"",childProjectTypeList:[{projectNum:"",childProjectTypeList:[]}]}]
 * <p>
 * Created by ycw on 2016/9/18.
 */
@Deprecated
public class ProjectNumInfo {
    private String projectNum;//
    private List<ProjectNumInfo> childProjectTypeList;//

    public String getProjectNum() {
        return projectNum;
    }

    public void setProjectNum(String projectNum) {
        this.projectNum = projectNum;
    }

    public List<ProjectNumInfo> getChildProjectTypeList() {
        return childProjectTypeList;
    }

    public void setChildProjectTypeList(List<ProjectNumInfo> childProjectTypeList) {
        this.childProjectTypeList = childProjectTypeList;
    }

    @Override
    public String toString() {
        return "ProjectNumInfo{" +
                "projectNum='" + projectNum + '\'' +
                ", childProjectTypeList=" + childProjectTypeList +
                '}'+"\n";
    }
}
