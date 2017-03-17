package com.ruanyun.chezhiyi.commonlib.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Description:聊天消息
 * author: zhangsan on 16/8/18 下午2:47.
 */
public class ChatMessage implements Parcelable {
    /** 本地消息主键 **/
    private int chatMessageId;
     /** 发送时间【long类型 需要转换date】 **/
    private long timestamp;
    /** 消息类型暂无处理【chatmessage聊天消息】 **/
    private String type;

    private String userNum;
    /**环信消息主键  **/
    private String huanxinMsgId;
    /** 用来判断单聊还是群聊。chat: 单聊；groupchat: 群聊 **/
    private String chatType;
    /** 发送消息内容【文本有值】 **/
    private String huanxinSendMsg;
    /** 消息类型。txt: 文本消息；img: 图片；loc: 位置；audio: 语音 **/
    private String huanxinType;
     /** 非文本时有值【主要用于语音】 **/
    private int huanxinLength;
    /** 消息URL【非文本时 文件的环信访问地址可以与filePath联合使用当该条消息为非文本时先用filePath来获取 如filePath为空，用url直接从环信访问】  **/
    private String huanxinUrl;
     /** 文件名称 **/
    private String huanxinFilename;
    /** 获取文件的secret **/
    private String huanxinSecret;
    /** 纬度 **/
    private double huanxinLat;
    /**  经度**/
    private double huanxinLng;
    /** 地址 **/
    private String huanxinAddr;
    /** 接受标识【好友聊天，为好友编号，群消息为群编号】 **/
    private String receiveUsernumGroupid;
    private String uuid;
    /** 本地文件路径【/file/chats】 **/
    private String filePath;
    /**用户头像  **/
    private String userAvatar;

    private String userNick;

    public int getHxPageChatType() {
        return hxPageChatType;
    }

    public void setHxPageChatType(int hxPageChatType) {
        this.hxPageChatType = hxPageChatType;
    }

    private int hxPageChatType;

    private String toChatUserNum;



    public String getToChatUserNum() {
        return toChatUserNum;
    }

    public void setToChatUserNum(String toChatUserNum) {
        this.toChatUserNum = toChatUserNum;
    }

    public int getChatMessageId() {
        return chatMessageId;
    }

    public void setChatMessageId(int chatMessageId) {
        this.chatMessageId = chatMessageId;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUserNum() {
        return userNum;
    }

    public void setUserNum(String userNum) {
        this.userNum = userNum;
    }

    public String getHuanxinMsgId() {
        return huanxinMsgId;
    }

    public void setHuanxinMsgId(String huanxinMsgId) {
        this.huanxinMsgId = huanxinMsgId;
    }

    public String getChatType() {
        return chatType;
    }

    public void setChatType(String chatType) {
        this.chatType = chatType;
    }

    public String getHuanxinSendMsg() {
        return huanxinSendMsg;
    }

    public void setHuanxinSendMsg(String huanxinSendMsg) {
        this.huanxinSendMsg = huanxinSendMsg;
    }

    public String getHuanxinType() {
        return huanxinType;
    }

    public void setHuanxinType(String huanxinType) {
        this.huanxinType = huanxinType;
    }

    public int getHuanxinLength() {
        return huanxinLength;
    }

    public void setHuanxinLength(int huanxinLength) {
        this.huanxinLength = huanxinLength;
    }

    public String getHuanxinUrl() {
        return huanxinUrl;
    }

    public void setHuanxinUrl(String huanxinUrl) {
        this.huanxinUrl = huanxinUrl;
    }

    public String getHuanxinFilename() {
        return huanxinFilename;
    }

    public void setHuanxinFilename(String huanxinFilename) {
        this.huanxinFilename = huanxinFilename;
    }

    public String getHuanxinSecret() {
        return huanxinSecret;
    }

    public void setHuanxinSecret(String huanxinSecret) {
        this.huanxinSecret = huanxinSecret;
    }

    public double getHuanxinLat() {
        return huanxinLat;
    }

    public void setHuanxinLat(double huanxinLat) {
        this.huanxinLat = huanxinLat;
    }

    public double getHuanxinLng() {
        return huanxinLng;
    }

    public void setHuanxinLng(double huanxinLng) {
        this.huanxinLng = huanxinLng;
    }

    public String getHuanxinAddr() {
        return huanxinAddr;
    }

    public void setHuanxinAddr(String huanxinAddr) {
        this.huanxinAddr = huanxinAddr;
    }

    public String getReceiveUsernumGroupid() {
        return receiveUsernumGroupid;
    }

    public void setReceiveUsernumGroupid(String receiveUsernumGroupid) {
        this.receiveUsernumGroupid = receiveUsernumGroupid;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getUserAvatar() {
        return userAvatar;
    }

    public void setUserAvatar(String userAvatar) {
        this.userAvatar = userAvatar;
    }

    public String getUserNick() {
        return userNick;
    }

    public void setUserNick(String userNick) {
        this.userNick = userNick;
    }

    public ChatMessage() {
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.chatMessageId);
        dest.writeLong(this.timestamp);
        dest.writeString(this.type);
        dest.writeString(this.userNum);
        dest.writeString(this.huanxinMsgId);
        dest.writeString(this.chatType);
        dest.writeString(this.huanxinSendMsg);
        dest.writeString(this.huanxinType);
        dest.writeInt(this.huanxinLength);
        dest.writeString(this.huanxinUrl);
        dest.writeString(this.huanxinFilename);
        dest.writeString(this.huanxinSecret);
        dest.writeDouble(this.huanxinLat);
        dest.writeDouble(this.huanxinLng);
        dest.writeString(this.huanxinAddr);
        dest.writeString(this.receiveUsernumGroupid);
        dest.writeString(this.uuid);
        dest.writeString(this.filePath);
        dest.writeString(this.userAvatar);
        dest.writeString(this.userNick);
        dest.writeInt(this.hxPageChatType);
        dest.writeString(this.toChatUserNum);
    }

    protected ChatMessage(Parcel in) {
        this.chatMessageId = in.readInt();
        this.timestamp = in.readLong();
        this.type = in.readString();
        this.userNum = in.readString();
        this.huanxinMsgId = in.readString();
        this.chatType = in.readString();
        this.huanxinSendMsg = in.readString();
        this.huanxinType = in.readString();
        this.huanxinLength = in.readInt();
        this.huanxinUrl = in.readString();
        this.huanxinFilename = in.readString();
        this.huanxinSecret = in.readString();
        this.huanxinLat = in.readDouble();
        this.huanxinLng = in.readDouble();
        this.huanxinAddr = in.readString();
        this.receiveUsernumGroupid = in.readString();
        this.uuid = in.readString();
        this.filePath = in.readString();
        this.userAvatar = in.readString();
        this.userNick = in.readString();
        this.hxPageChatType = in.readInt();
        this.toChatUserNum = in.readString();
    }

    public static final Creator<ChatMessage> CREATOR = new Creator<ChatMessage>() {
        @Override
        public ChatMessage createFromParcel(Parcel source) {
            return new ChatMessage(source);
        }

        @Override
        public ChatMessage[] newArray(int size) {
            return new ChatMessage[size];
        }
    };
}
