package com.ruanyun.chezhiyi.commonlib.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 评论的数据结构
 * Created by ycw on 2016/9/8.
 */
public class CommentBase implements Parcelable {
    private int commentId;//	Int	主键
    private String commentNum;//	String	评论编码【业务主键】
    private String userNum;//	String	用户编号
    private String glNum;//	String	关联主键【评论的信息的业务主键，例如评论案例，该字段是案例的业务主键】
    private String commentType;//	String	类型【t_case_library 案例,t_article_info 文章】
    private String commentContent;//	String	评论内容
    private String createTime;//	Date	评论时间

    public int getCommentId() {
        return commentId;
    }

    public void setCommentId(int commentId) {
        this.commentId = commentId;
    }

    public String getCommentNum() {
        return commentNum;
    }

    public void setCommentNum(String commentNum) {
        this.commentNum = commentNum;
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

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.commentId);
        dest.writeString(this.commentNum);
        dest.writeString(this.userNum);
        dest.writeString(this.glNum);
        dest.writeString(this.commentType);
        dest.writeString(this.commentContent);
        dest.writeString(this.createTime);
    }

    public CommentBase() {
    }

    protected CommentBase(Parcel in) {
        this.commentId = in.readInt();
        this.commentNum = in.readString();
        this.userNum = in.readString();
        this.glNum = in.readString();
        this.commentType = in.readString();
        this.commentContent = in.readString();
        this.createTime = in.readString();
    }

    public static final Parcelable.Creator<CommentBase> CREATOR = new Parcelable.Creator<CommentBase>() {
        @Override
        public CommentBase createFromParcel(Parcel source) {
            return new CommentBase(source);
        }

        @Override
        public CommentBase[] newArray(int size) {
            return new CommentBase[size];
        }
    };
}
