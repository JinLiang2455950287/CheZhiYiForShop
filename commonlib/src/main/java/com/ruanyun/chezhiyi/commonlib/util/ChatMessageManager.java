package com.ruanyun.chezhiyi.commonlib.util;

import android.text.TextUtils;

import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.chat.EMImageMessageBody;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.chat.EMVoiceMessageBody;
import com.hyphenate.easeui.EaseConstant;
import com.ruanyun.chezhiyi.commonlib.hxchat.domain.EmojiconExampleGroupData;
import com.ruanyun.chezhiyi.commonlib.model.ChatMessage;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Description:
 * author: zhangsan on 16/8/19 上午9:38.
 */
public class ChatMessageManager {
    static final int MSG_PAGE_SIZE=10;
    private List<ChatMessage> messageList;
    private String currentUserNum;
    static final File voiceFile=new File("file:///android_asset/voice.amr");
    static final File imageFile=new File("file:///android_asset/head.jpg");
   //static final Pattern  EMOJI_PATTER=Pattern.compile("^[表情");
    public ChatMessageManager() {

    }

    public ChatMessageManager(String currentUserNum) {
        this.currentUserNum = currentUserNum;
    }

    public ChatMessageManager(List<ChatMessage> messageList, String currentUserNum) {
        this.messageList = messageList;
        this.currentUserNum = currentUserNum;
    }

    public void setCurrentUserNum(String currentUserNum) {
        this.currentUserNum = currentUserNum;
    }

    public void setMessageList(List<ChatMessage> messageList) {
        this.messageList = messageList;
    }

    /**
     * 创建文本消息
     *
     * @author zhangsan
     * @date 16/8/19 下午2:39
     */
    private EMMessage createTextMessage(String content, String toChatUsername, String userNum, String chatType) {
        EMMessage textMsg = EMMessage.createTxtSendMessage(content, toChatUsername);
        if (toChatUsername.equals(currentUserNum)) {
            //textMsg=EMMessage.createFileSendMessage()
            textMsg.setDirection(EMMessage.Direct.RECEIVE);
            textMsg.setFrom(userNum);
            textMsg.setTo(currentUserNum);
        }
        if (chatType.equals("groupchat")) {
            textMsg.setChatType(EMMessage.ChatType.GroupChat);
        }

        return textMsg;
    }

    public EMMessage createTextMessage(ChatMessage chatMessage) {
        EMMessage textMsg = createTextMessage(chatMessage.getHuanxinSendMsg(),
                chatMessage.getReceiveUsernumGroupid(), chatMessage.getChatType(), chatMessage.getChatType());
        textMsg.setMsgTime(chatMessage.getTimestamp());
        textMsg.setMsgId(chatMessage.getHuanxinMsgId());
        textMsg.setStatus(EMMessage.Status.SUCCESS);
        //textMsg.setDelivered(true);
        if(isEmojiText(chatMessage.getHuanxinSendMsg())){
           textMsg .setAttribute(EaseConstant.MESSAGE_ATTR_IS_BIG_EXPRESSION, true);
           String identiyCode=EmojiconExampleGroupData.getIdentiyCode(chatMessage.getHuanxinSendMsg());
           if(!TextUtils.isEmpty(identiyCode)){
               textMsg.setAttribute(EaseConstant.MESSAGE_ATTR_EXPRESSION_ID, identiyCode);
           }

        }
        return textMsg;
    }

    public EMMessage createVoiceMessage(ChatMessage chatMessage) {
        EMMessage voiceMsg=createVoiceMessage(chatMessage.getFilePath(), chatMessage.getHuanxinLength()
                , chatMessage.getReceiveUsernumGroupid(), chatMessage.getUserNum(), chatMessage.getChatType(),chatMessage.getHuanxinSecret());
        voiceMsg.setMsgId(chatMessage.getHuanxinMsgId());
        voiceMsg.setMsgTime(chatMessage.getTimestamp());
     //   voiceMsg.addBody(EMVoiceMessageBody);
       // voiceMsg.setDelivered(true);
        return voiceMsg;
    }

   /* public EMMessage createVoiceMsg(){
        Class c=cl
    }*/

    /**
     * 创建语音消息
     *
     * @author zhangsan
     * @date 16/8/19 下午2:49
     */
    private EMMessage createVoiceMessage(String filePath, int length, String toChatUsername, String userNum, String chatType,String secret) {

       // EMMessage voiceMsg = EMMessage.createVoiceSendMessage(filePath, length, toChatUsername);
        EMMessage voiceMsg = EMMessage.createReceiveMessage(EMMessage.Type.VOICE);
        EMVoiceMessageBody body=new EMVoiceMessageBody(voiceFile,length);
        body.setLocalUrl("");
        body.setRemoteUrl(FileUtil.getFileUrl(filePath));
        body.setSecret(secret);
        voiceMsg.addBody(body);
        if (toChatUsername.equals(currentUserNum)) {
            voiceMsg.setDirection(EMMessage.Direct.RECEIVE);
            voiceMsg.setFrom(userNum);
            voiceMsg.setTo(currentUserNum);
        }else {
            voiceMsg.setDirection(EMMessage.Direct.SEND);
            voiceMsg.setFrom(currentUserNum);
            voiceMsg.setTo(toChatUsername);
            voiceMsg.setReceipt(toChatUsername);
        }
        if (chatType.equals("groupchat")) {
            voiceMsg.setChatType(EMMessage.ChatType.GroupChat);
        }
        return voiceMsg;
    }


    /**
     * 创建图片消息
     *
     * @author zhangsan
     * @date 16/8/19 下午3:07
     */

    private EMMessage createImageMessage(String imagePath, String toChatUsername, String userNum, String chatType,String secret) {
        //EMMessage imageMessage = EMMessage.createImageSendMessage(imagePath, false, toChatUsername);
        EMMessage imageMessage = EMMessage.createReceiveMessage(EMMessage.Type.IMAGE);
        EMImageMessageBody body=new EMImageMessageBody(imageFile);
        body.setRemoteUrl(FileUtil.getFileUrl(imagePath));
        body.setLocalUrl("");
        body.setSecret(secret);
        body.setThumbnailUrl(FileUtil.getFileUrl(imagePath));
        LogX.d("IMGPATH",FileUtil.getFileUrl(imagePath));
        imageMessage.addBody(body);
        if (toChatUsername.equals(currentUserNum)) {
            imageMessage.setDirection(EMMessage.Direct.RECEIVE);
            imageMessage.setFrom(userNum);
            imageMessage.setStatus(EMMessage.Status.SUCCESS);
            imageMessage.setTo(currentUserNum);
        }else {
            imageMessage.setDirection(EMMessage.Direct.SEND);
            imageMessage.setFrom(currentUserNum);
            imageMessage.setTo(toChatUsername);
            imageMessage.setReceipt(toChatUsername);
        }
        if (chatType.equals("groupchat")) {
            imageMessage.setChatType(EMMessage.ChatType.GroupChat);
        }
        return imageMessage;
    }

    private EMMessage createImageMessage(ChatMessage chatMessage) {
        EMMessage imageMsg= createImageMessage(getChatMsgUrl(chatMessage), chatMessage.getReceiveUsernumGroupid(),
                chatMessage.getUserNum(), chatMessage.getChatType(),chatMessage.getHuanxinSecret());
        imageMsg.setMsgTime(chatMessage.getTimestamp());
        imageMsg.setMsgId(chatMessage.getHuanxinMsgId());
        //imageMsg.setDelivered(true);
        return imageMsg;
    }

    /**
     * 创建位置消息
     *
     * @author zhangsan
     * @date 16/8/20 上午9:19
     */
    private EMMessage crateLocationMessage(double latitude, double longitude, String locationAddress,
                                           String toChatUsername, String userNum, String chatType) {
        EMMessage locationMsg = EMMessage.createLocationSendMessage(latitude, longitude, locationAddress, toChatUsername);

        return locationMsg;
    }

    /**
     * 创建文件消息
     *
     * @author zhangsan
     * @date 16/8/20 上午9:19
     */
    private EMMessage creatFileMessage(String filePath, String toChatUsername, String userNum, String chatType) {
        EMMessage fileMessage = EMMessage.createFileSendMessage(filePath, toChatUsername);

        return fileMessage;
    }

    private EMMessage creatFileMessage(ChatMessage chatMessage) {
        return creatFileMessage(getChatMsgUrl(chatMessage), chatMessage.getReceiveUsernumGroupid()
                , chatMessage.getUserNum(), chatMessage.getChatType());
    }


    private String getChatMsgUrl(ChatMessage chatMessage) {
        return TextUtils.isEmpty(chatMessage.getFilePath())
                ? chatMessage.getHuanxinUrl() : chatMessage.getFilePath();
    }

    private boolean isEmojiText(String content){
        /*Matcher m=EMOJI_PATTER.matcher(content);
        return m.find();*/
        return content.contains("[表情");
    }


    /**
     * 导入聊天消息
     *
     * @author zhangsan
     * @date 16/8/20 上午11:14
     */
    public void importChatMessage() {
        List<EMMessage> messages = new ArrayList<EMMessage>();
        for (ChatMessage message : messageList) {
            switch (message.getType()) {
                case "text":
                    messages.add(createTextMessage(message));
                    break;
                case "img":
                    messages.add(createImageMessage(message));
                    break;
                case "loc":
                    break;
                case "audio":
                    messages.add(createVoiceMessage(message));
                    break;
            }
            EMClient.getInstance().chatManager().importMessages(messages);
        }
    }



   public List<EMMessage> getHxChatMsg(List<ChatMessage> msglist){
       List<EMMessage> messages = new ArrayList<EMMessage>();
       for (ChatMessage message : msglist) {
           switch (message.getHuanxinType()) {
               case "txt":
                   messages.add(createTextMessage(message));
                   break;
               case "img":
                   messages.add(createImageMessage(message));
                   break;
               case "loc":
                   break;
               case "audio":
                   messages.add(createVoiceMessage(message));
                   break;
           }

       }
       return  messages;
   }

    public EMMessage getTextHxChatMsg(ChatMessage msg){
        //List<EMMessage> messages = new ArrayList<EMMessage>();
        return  createTextMessage(msg);
    }

    public static final String VALUE_TRUE="yes";
    public static final String VALUE_FALSE="no";
    public EMMessage creatGoodsShareMessage(String goodsUrl,String toUserNum,String goodsJson){
        EMMessage goodsMsg = EMMessage.createTxtSendMessage("[商品分享]", toUserNum);
        goodsMsg.setAttribute(C.IntentKey.IS_GOODS_SHARE,VALUE_TRUE);
        goodsMsg.setAttribute(C.IntentKey.GOODS_JSON,goodsJson);
        goodsMsg.setAttribute(C.IntentKey.GOODS_URL,goodsUrl);
        return goodsMsg;
    }

    public void sendGoodsShareMsg(EMMessage message){
        EMClient.getInstance().chatManager().sendMessage(message);
    }


    public EMConversation loadMsgFromDb(String num, EMConversation.EMConversationType conversationType,String startMsgId){
        EMConversation conversation=EMClient.getInstance().chatManager().getConversation(num,conversationType);
         conversation.loadMoreMsgFromDB(startMsgId,MSG_PAGE_SIZE);
        return conversation;
    }
}
