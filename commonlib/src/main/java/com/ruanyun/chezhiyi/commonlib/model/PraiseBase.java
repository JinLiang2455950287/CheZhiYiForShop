package com.ruanyun.chezhiyi.commonlib.model;

/**
 * 点赞数据结构
 * Created by ycw on 2016/9/7.
 */
public class PraiseBase {
    private int praiseId;//	Int	主键
    private String praiseNum;//	String	评论编码【业务主键】
    private String userNum;//	String	用户编号
    private String glNum;//	String	关联主键【评论的信息的业务主键，例如评论案例，该字段是案例的业务主键】与评论中的glNum值一致
    private String praiseType;//	String	类型【t_case_library 案例,t_article_info 文章】与评论中的commentType值一致
    private String createTime;//	Date	点赞时间

    public int getPraiseId() {
        return praiseId;
    }

    public void setPraiseId(int praiseId) {
        this.praiseId = praiseId;
    }

    public String getPraiseNum() {
        return praiseNum;
    }

    public void setPraiseNum(String praiseNum) {
        this.praiseNum = praiseNum;
    }

    public String getUserNum() {
        return userNum;
    }

    public void setUserNum(String userNum) {
        this.userNum = userNum;
    }

    public String getGlNum() {
        return glNum;
    }

    public void setGlNum(String glNum) {
        this.glNum = glNum;
    }

    public String getPraiseType() {
        return praiseType;
    }

    public void setPraiseType(String praiseType) {
        this.praiseType = praiseType;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}
