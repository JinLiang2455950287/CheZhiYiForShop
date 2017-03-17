package com.ruanyun.chezhiyi.commonlib.model.params;

/**
 * Created by ycw on 2016/9/7.
 */
public class AddOrPraiseParams {
    private String glNum;//	String	关联主键【评论的信息的业务主键，例如评论案例，该字段是案例的业务主键】
    private String praiseType;//	String	类型【t_case_library 案例,t_article_info 文章】

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
}
