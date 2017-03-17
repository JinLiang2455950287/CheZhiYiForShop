package com.ruanyun.chezhiyi.commonlib.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Sxq on 2016/9/1.
 */
public class ArticleInfo implements Parcelable {

    private int articleId;
    /**
     * 编号
     **/
    private String articleNum;
    /**
     * 标题
     **/
    private String title;
    /**
     * 摘要
     **/
    private String summary;
    /** 评论量 **/
    private int commentCount;

    private String content;
    /**
     * 点赞量
     **/
    private int praiseCount;

    private String userNum;
    /**
     * 是否首页   1 -是 2-否
     **/
    private int isHome;
    /**
     * 文章分类 【字典表读取ARTICLE_TYPE】
     **/
    private int articleType;

    private String articleTypeName;

    private String createTime;

    private String mainPhoto;

    public String getMainPhoto() {
        return mainPhoto;
    }

    public void setMainPhoto(String mainPhoto) {
        this.mainPhoto = mainPhoto;
    }

    public int getArticleId() {
        return articleId;
    }

    public void setArticleId(int articleId) {
        this.articleId = articleId;
    }

    public String getArticleNum() {
        return articleNum;
    }

    public void setArticleNum(String articleNum) {
        this.articleNum = articleNum;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getPraiseCount() {
        return praiseCount;
    }

    public void setPraiseCount(int praiseCount) {
        this.praiseCount = praiseCount;
    }

    public String getUserNum() {
        return userNum;
    }

    public void setUserNum(String userNum) {
        this.userNum = userNum;
    }

    public int getIsHome() {
        return isHome;
    }

    public void setIsHome(int isHome) {
        this.isHome = isHome;
    }

    public int getArticleType() {
        return articleType;
    }

    public void setArticleType(int articleType) {
        this.articleType = articleType;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getArticleTypeName() {
        return articleTypeName;
    }

    public void setArticleTypeName(String articleTypeName) {
        this.articleTypeName = articleTypeName;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.articleId);
        dest.writeString(this.articleNum);
        dest.writeString(this.title);
        dest.writeString(this.summary);
        dest.writeInt(this.commentCount);
        dest.writeString(this.content);
        dest.writeInt(this.praiseCount);
        dest.writeString(this.userNum);
        dest.writeInt(this.isHome);
        dest.writeInt(this.articleType);
        dest.writeString(this.articleTypeName);
        dest.writeString(this.createTime);
        dest.writeString(this.mainPhoto);
    }

    public ArticleInfo() {
    }

    protected ArticleInfo(Parcel in) {
        this.articleId = in.readInt();
        this.articleNum = in.readString();
        this.title = in.readString();
        this.summary = in.readString();
        this.commentCount = in.readInt();
        this.content = in.readString();
        this.praiseCount = in.readInt();
        this.userNum = in.readString();
        this.isHome = in.readInt();
        this.articleType = in.readInt();
        this.articleTypeName = in.readString();
        this.createTime = in.readString();
        this.mainPhoto = in.readString();
    }

    public static final Parcelable.Creator<ArticleInfo> CREATOR = new Parcelable.Creator<ArticleInfo>() {
        @Override
        public ArticleInfo createFromParcel(Parcel source) {
            return new ArticleInfo(source);
        }

        @Override
        public ArticleInfo[] newArray(int size) {
            return new ArticleInfo[size];
        }
    };
}
