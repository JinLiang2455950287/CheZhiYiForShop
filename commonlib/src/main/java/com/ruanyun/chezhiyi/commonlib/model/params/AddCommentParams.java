package com.ruanyun.chezhiyi.commonlib.model.params;

/**
 * Created by ycw on 2016/9/7.
 */
public class AddCommentParams {
    private String glNum;//	String	关联主键【评论的信息的业务主键，例如评论案例，该字段是案例的业务主键】
    private String commentType;//	String	类型【t_case_library 案例,t_article_info 文章】
    private String commentContent;//	String	评论内容

    public String getGlNum() {
        return glNum;
    }

    public void setGlNum(String glNum) {
        this.glNum = glNum;
    }

    public String getCommentType() {
        return commentType;
    }

    public void setCommentType(String commentType) {
        this.commentType = commentType;
    }

    public String getCommentContent() {
        return commentContent;
    }

    public void setCommentContent(String commentContent) {
        this.commentContent = commentContent;
    }
}
