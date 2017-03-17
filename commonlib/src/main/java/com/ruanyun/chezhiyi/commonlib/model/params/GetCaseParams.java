package com.ruanyun.chezhiyi.commonlib.model.params;

/**
 * Created by msq on 2016/9/6.
 */
public class GetCaseParams extends PageParamsBase{
    private String libraryName;//搜索的关键字
    private Integer userType;//1-技师【技师个人】 2-企业
    private String libraryType;//案例库分类【读取服务类型一级】
    private String createUserNum;// 案例用户编号 传值 获取该技师的案例
    private Integer status;// 审核状态1通过 2待审核 -1未通过

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getCreateUserNum() {
        return createUserNum;
    }

    public void setCreateUserNum(String createUserNum) {
        this.createUserNum = createUserNum;
    }

    public String getLibraryType() {
        return libraryType;
    }

    public void setLibraryType(String libraryType) {
        this.libraryType = libraryType;
    }

    public String getLibraryName() {
        return libraryName;
    }

    public void setLibraryName(String libraryName) {
        this.libraryName = libraryName;
    }

    public Integer getUserType() {
        return userType;
    }

    public void setUserType(Integer userType) {
        this.userType = userType;
    }
}
