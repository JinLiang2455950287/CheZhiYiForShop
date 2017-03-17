package com.ruanyun.chezhiyi.commonlib.model;

/**
 * Created by msq on 2016/9/8.
 */
public class FeedBackInfo {

    private int feedId;	//	主键
    private String linkTel;	//	联系电话
    private String content;	//	反馈内容
    private String createUserNum;	//	反馈人
    private String feedTime;	//	反馈时间

    public int getFeedId() {
        return feedId;
    }

    public void setFeedId(int feedId) {
        this.feedId = feedId;
    }

    public String getLinkTel() {
        return linkTel;
    }

    public void setLinkTel(String linkTel) {
        this.linkTel = linkTel;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCreateUserNum() {
        return createUserNum;
    }

    public void setCreateUserNum(String createUserNum) {
        this.createUserNum = createUserNum;
    }

    public String getFeedTime() {
        return feedTime;
    }

    public void setFeedTime(String feedTime) {
        this.feedTime = feedTime;
    }
}
