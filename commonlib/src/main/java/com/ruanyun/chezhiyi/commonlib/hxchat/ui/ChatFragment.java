package com.ruanyun.chezhiyi.commonlib.hxchat.ui;

import android.app.Activity;
import android.content.ClipData;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextWatcher;
import android.text.style.BackgroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMGroup;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.chat.EMTextMessageBody;
import com.hyphenate.easeui.EaseConstant;
import com.hyphenate.easeui.ui.EaseChatFragment;
import com.hyphenate.easeui.ui.EaseChatFragment.EaseChatFragmentHelper;
import com.hyphenate.easeui.utils.EaseUserUtils;
import com.hyphenate.easeui.widget.chatrow.EaseChatRow;
import com.hyphenate.easeui.widget.chatrow.EaseChatRowText;
import com.hyphenate.easeui.widget.chatrow.EaseCustomChatRowProvider;
import com.hyphenate.easeui.widget.emojicon.EaseEmojiconMenu;
import com.hyphenate.util.EasyUtils;
import com.hyphenate.util.PathUtil;
import com.ruanyun.chezhiyi.commonlib.App;
import com.ruanyun.chezhiyi.commonlib.R;
import com.ruanyun.chezhiyi.commonlib.base.ResultBase;
import com.ruanyun.chezhiyi.commonlib.data.api.ResponseCallback;
import com.ruanyun.chezhiyi.commonlib.hxchat.Constant;
import com.ruanyun.chezhiyi.commonlib.hxchat.HXHelper;
import com.ruanyun.chezhiyi.commonlib.hxchat.domain.EmojiconExampleGroupData;
import com.ruanyun.chezhiyi.commonlib.hxchat.domain.RobotUser;
import com.ruanyun.chezhiyi.commonlib.hxchat.widget.ChatRowVoiceCall;
import com.ruanyun.chezhiyi.commonlib.hxchat.widget.ShareGoodsChatRow;
import com.ruanyun.chezhiyi.commonlib.model.Event;
import com.ruanyun.chezhiyi.commonlib.model.HxUserGroup;
import com.ruanyun.chezhiyi.commonlib.util.AppUtility;
import com.ruanyun.chezhiyi.commonlib.util.C;
import com.ruanyun.chezhiyi.commonlib.util.ChatMessageManager;
import com.ruanyun.chezhiyi.commonlib.util.LogX;
import com.ruanyun.chezhiyi.commonlib.view.ui.common.WebViewActivity;

import java.io.File;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;
import de.greenrobot.event.ThreadMode;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;

public class ChatFragment extends EaseChatFragment implements EaseChatFragmentHelper, EaseChatRowText.UrlSpanClickListener {

    // constant start from 11 to avoid conflict with constant in base class
    private static final int ITEM_VIDEO = 11;
    private static final int ITEM_FILE = 12;
    private static final int ITEM_VOICE_CALL = 13;
    private static final int ITEM_VIDEO_CALL = 14;

    private static final int REQUEST_CODE_SELECT_VIDEO = 11;
    private static final int REQUEST_CODE_SELECT_FILE = 12;
    private static final int REQUEST_CODE_GROUP_DETAIL = 13;
    private static final int REQUEST_CODE_CONTEXT_MENU = 14;
    private static final int REQUEST_CODE_SELECT_AT_USER = 15;
    private static final int REQ_EDIT_USERGOUP = 17;

    private static final int MESSAGE_TYPE_SENT_VOICE_CALL = 1;
    private static final int MESSAGE_TYPE_RECV_VOICE_CALL = 2;
    private static final int MESSAGE_TYPE_SENT_VIDEO_CALL = 3;
    private static final int MESSAGE_TYPE_RECV_VIDEO_CALL = 4;

    private static final int MESSAGE_TYPE_SEND_GOODS_SHARE=9;
    private static final int MESSAGE_TYPE_RECV_GOODS_SHARE=10;

    //red packet code : 红包功能使用的常量
    private static final int MESSAGE_TYPE_RECV_RED_PACKET = 5;
    private static final int MESSAGE_TYPE_SEND_RED_PACKET = 6;
    private static final int MESSAGE_TYPE_SEND_RED_PACKET_ACK = 7;
    private static final int MESSAGE_TYPE_RECV_RED_PACKET_ACK = 8;
    private static final int REQUEST_CODE_SEND_RED_PACKET = 16;
    private static final int ITEM_RED_PACKET = 16;
    //end of red packet code
    static final String GROUP_TIPS = "你添加了%s进入群组.为方便后续查找,给群组取个名字吧";
    /**
     * if it is chatBot
     */
    private boolean isRobot;

    private HxUserGroup groupInfo;
    private TextView tvTips;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
        //   Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    public void setTvTips(String text) {
        tvTips.setVisibility(View.VISIBLE);
        SpannableStringBuilder sp = new SpannableStringBuilder(String.format(GROUP_TIPS, text));
        sp.setSpan(new BackgroundColorSpan(getResources().getColor(R.color.theme_color_default)), sp.length() - 5, sp.length() - 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        tvTips.setText(sp);
    }

    public void setTitle(String title) {
        titleBar.setTitle(title);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }


    @Override
    protected void setUpView() {
        groupInfo = getArguments().getParcelable(C.IntentKey.GROUP_INFO);
        setChatFragmentListener(this);
        if (chatType == Constant.CHATTYPE_SINGLE) {
            Map<String, RobotUser> robotMap = HXHelper.getInstance().getRobotList();
            if (robotMap != null && robotMap.containsKey(toChatUsername)) {
                isRobot = true;
            }
            titleBar.setRightImageResource(R.drawable.icon_person);
            if(EaseUserUtils.getUserInfo(toChatUsername) != null){
                titleBar.setTitle(EaseUserUtils.getUserInfo(toChatUsername).getNick());
            }
            if(App.getInstance().getStoreInfo()!=null&&toChatUsername.equals(App.getInstance().getStoreInfo().getUserNumSecretary())){
                titleBar.setTitle("小秘书");
               // titleBar.setRightImageResource(R.color.transparent);
               // titleBar.setRightLayoutClickListener(null);
            }
        }else {
            if(groupInfo!=null){
                setTitle(groupInfo.getGroupName());
            }else {
                 EMGroup group = EMClient.getInstance().groupManager().getGroup(toChatUsername);
                if (group != null) {
                    titleBar.setTitle(group.getGroupName());
                }else {
                    setTitle(toChatUsername);
                }
            }
        }
        super.setUpView();
        messageList.setUrlSpanClickListener(this);
        // set click listener
        tvTips = (TextView) getView().findViewById(R.id.tv_tips);
        tvTips.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                tvTips.setVisibility(View.GONE);
                Intent i = new Intent(getActivity(), EaseModifyGroupNameActivity.class);
                i.putExtra(C.IntentKey.UPDATE_TYPE, EaseModifyGroupNameActivity.TYPE_UPDATE_GROUP_NAME);
                i.putExtra(C.IntentKey.GROUP_NAME, groupInfo.getGroupName());
                startActivityForResult(i, REQ_EDIT_USERGOUP);

            }
        });

        titleBar.setLeftLayoutClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if (EasyUtils.isSingleActivity(getActivity())) {
                  /*  Intent intent = new Intent(getActivity(), MainActivity.class);
                    startActivity(intent);*/
                }
                onBackPressed();
            }
        });
        ((EaseEmojiconMenu) inputMenu.getEmojiconMenu()).addEmojiconGroup(EmojiconExampleGroupData.getData());
        if (chatType == EaseConstant.CHATTYPE_GROUP) {
            inputMenu.getPrimaryMenu().getEditText().addTextChangedListener(new TextWatcher() {

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                  /*  if(count == 1 && "@".equals(String.valueOf(s.charAt(start)))){
                        startActivityForResult(new Intent(getActivity(), PickAtUserActivity.class).
                                putExtra("groupId", toChatUsername), REQUEST_CODE_SELECT_AT_USER);
                    }*/
                }

                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });
        }
        EventBus.getDefault().register(this);
        //LogX.d("retrofit", new Gson().toJson(conversation.getLastMessage().get));
       // String s=conversation.getLastMessage().getStringAttribute("commonNum","1");
        //String ss=conversation.getLastMessage().getStringAttribute("remindType","");

    }

    @Override
    protected void registerExtendMenuItem() {
        //use the menu in base class
        super.registerExtendMenuItem();
        //extend menu items
       // inputMenu.registerExtendMenuItem(R.string.attach_video, R.drawable.em_chat_video_selector, ITEM_VIDEO, extendMenuItemClickListener);
      //  inputMenu.registerExtendMenuItem(R.string.attach_file, R.drawable.em_chat_file_selector, ITEM_FILE, extendMenuItemClickListener);
        if (chatType == Constant.CHATTYPE_SINGLE) {
            inputMenu.registerExtendMenuItem(R.string.attach_voice_call, R.drawable.em_chat_voice_call_selector, ITEM_VOICE_CALL, extendMenuItemClickListener);
            inputMenu.registerExtendMenuItem(R.string.attach_video_call, R.drawable.em_chat_video_call_selector, ITEM_VIDEO_CALL, extendMenuItemClickListener);
        }
        //聊天室暂时不支持红包功能
        //red packet code : 注册红包菜单选项
    /*    if (chatType != Constant.CHATTYPE_CHATROOM) {
            inputMenu.registerExtendMenuItem(R.string.attach_red_packet, R.drawable.em_chat_red_packet_selector, ITEM_RED_PACKET, extendMenuItemClickListener);
        }*/
        //end of red packet code
    }

/*    @Override
    public void onMessageListCreated() {
        super.onMessageListCreated();
        messageList.setUrlSpanClickListener(this);
    }*/

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && requestCode == REQ_EDIT_USERGOUP) {
            groupName = data.getStringExtra(C.IntentKey.GROUP_NAME);
            updateGroupInfo();
        }
        if (resultCode == Activity.RESULT_FIRST_USER && requestCode == REQUEST_CODE_GROUP_DETAIL) {
            HxUserGroup userGroup= data.getParcelableExtra(C.IntentKey.GROUP_INFO);
            if (userGroup != null) {
                groupInfo = userGroup;
                setTitle(userGroup.getGroupName());
            }
        }
        if (requestCode == REQUEST_CODE_CONTEXT_MENU) {
            switch (resultCode) {
                case ContextMenuActivity.RESULT_CODE_COPY: // copy
                    clipboard.setPrimaryClip(ClipData.newPlainText(null,
                            ((EMTextMessageBody) contextMenuMessage.getBody()).getMessage()));
                    break;
                case ContextMenuActivity.RESULT_CODE_DELETE: // delete
                    conversation.removeMessage(contextMenuMessage.getMsgId());
                    messageList.refresh();
                    break;

                case ContextMenuActivity.RESULT_CODE_FORWARD: // forward
                   // Intent intent = new Intent(getActivity(), ForwardMessageActivity.class);
                   // intent.putExtra("forward_msg_id", contextMenuMessage.getMsgId());
                   // startActivity(intent);
                    break;

                default:
                    break;
            }
        }
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case REQUEST_CODE_SELECT_VIDEO: //send the video
                    if (data != null) {
                        int duration = data.getIntExtra("dur", 0);
                        String videoPath = data.getStringExtra("path");
                        File file = new File(PathUtil.getInstance().getImagePath(), "thvideo" + System.currentTimeMillis());
                        try {
                            FileOutputStream fos = new FileOutputStream(file);
                            Bitmap ThumbBitmap = ThumbnailUtils.createVideoThumbnail(videoPath, 3);
                            ThumbBitmap.compress(CompressFormat.JPEG, 100, fos);
                            fos.close();
                            sendVideoMessage(videoPath, file.getAbsolutePath(), duration);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    break;
                case REQUEST_CODE_SELECT_FILE: //send the file
                    if (data != null) {
                        Uri uri = data.getData();
                        if (uri != null) {
                            sendFileByUri(uri);
                        }
                    }
                    break;
                case REQUEST_CODE_SELECT_AT_USER:
                    if (data != null) {
                        String username = data.getStringExtra("username");
                        inputAtUsername(username, false);
                    }
                    break;
                //red packet code : 发送红包消息到聊天界面
//                case REQUEST_CODE_SEND_RED_PACKET:
            /*    if (data != null){
                    sendMessage(RedPacketUtil.createRPMessage(getActivity(), data, toChatUsername));
                }*/
//                    break;
                //end of red packet code
                default:
                    break;
            }
        }

    }

    @Override
    public void onSetMessageAttributes(EMMessage message) {
        if (isRobot) {
            //set message extension
            message.setAttribute("em_robot_message", isRobot);
        }
    }

    @Override
    public EaseCustomChatRowProvider onSetCustomChatRowProvider() {
        return new CustomChatRowProvider();
    }


    @Override
    public void onEnterToChatDetails() {
        if (chatType == Constant.CHATTYPE_GROUP) {
            EMGroup group = EMClient.getInstance().groupManager().getGroup(toChatUsername);
            if (group == null) {
                Toast.makeText(getActivity(), R.string.gorup_not_found, Toast.LENGTH_SHORT).show();
                return;
            }
           // startActivityForResult(
            //        (new Intent(getActivity(), GroupDetailsActivity.class).putExtra("groupId", toChatUsername)),
            //        REQUEST_CODE_GROUP_DETAIL);
        } else if (chatType == Constant.CHATTYPE_CHATROOM) {
          //  startActivityForResult(new Intent(getActivity(), ChatRoomDetailsActivity.class).putExtra("roomId", toChatUsername), REQUEST_CODE_GROUP_DETAIL);
        }
    }

    @Override
    public void onAvatarClick(String username) {
        //handling when user click avatar
        /*Intent intent = new Intent(getActivity(), UserProfileActivity.class);
        intent.putExtra("username", username);
        startActivity(intent);*/
        if(App.getInstance().getStoreInfo()!=null&&username.equals(App.getInstance().getStoreInfo().getUserNumSecretary())){
            return;
        }
        if (App.getInstance().getCurrentUserNum().equals(username)) {//看自己资料
            AppUtility.goToUserProfile(getActivity(), App.getInstance().getUser());
        } else {//看人资料
            App.getInstance().beanCacheHelper.getFriendShipInfo(username, getActivity(), C.EventKey.KEY_USER_PROFILE);
        }
    }

    @Override
    public void onAvatarLongClick(String username) {
        inputAtUsername(username);
    }


    @Override
    public boolean onMessageBubbleClick(EMMessage message) {
        //消息框点击事件，demo这里不做覆盖，如需覆盖，return true
        //red packet code : 拆红包页面
      /*  if (message.getBooleanAttribute(RedPacketConstant.MESSAGE_ATTR_IS_RED_PACKET_MESSAGE, false)){
            RedPacketUtil.openRedPacket(getActivity(), chatType, message, toChatUsername, messageList);
            return true;
        }*/
        //end of red packet code
        return false;
    }

    @Override
    public void onCmdMessageReceived(List<EMMessage> messages) {
        //red packet code : 处理红包回执透传消息
      /*  for (EMMessage message : messages) {
            EMCmdMessageBody cmdMsgBody = (EMCmdMessageBody) message.getBody();
            String action = cmdMsgBody.action();//获取自定义action
            if (action.equals(RedPacketConstant.REFRESH_GROUP_RED_PACKET_ACTION)){
                RedPacketUtil.receiveRedPacketAckMessage(message);
                messageList.refresh();
            }
        }*/
        //end of red packet code
        super.onCmdMessageReceived(messages);
    }

    @Override
    public void onMessageBubbleLongClick(EMMessage message) {
        // no message forward when in chat room
        // TODO: 2016/9/30 消息长按事件
        /*startActivityForResult((new Intent(getActivity(), ContextMenuActivity.class)).putExtra("message", message)
                        .putExtra("ischatroom", chatType == EaseConstant.CHATTYPE_CHATROOM),
                REQUEST_CODE_CONTEXT_MENU);*/
    }

    @Override
    public boolean onExtendMenuItemClick(int itemId, View view) {
        switch (itemId) {
            case ITEM_VIDEO:
                Intent intent = new Intent(getActivity(), ImageGridActivity.class);
                startActivityForResult(intent, REQUEST_CODE_SELECT_VIDEO);
                break;
            case ITEM_FILE: //file
                selectFileFromLocal();
                break;
            case ITEM_VOICE_CALL:
                startVoiceCall();
                break;
            case ITEM_VIDEO_CALL:
                startVideoCall();
                break;
            //red packet code : 进入发红包页面
//            case ITEM_RED_PACKET:
                //  RedPacketUtil.startRedPacketActivityForResult(this, chatType, toChatUsername, REQUEST_CODE_SEND_RED_PACKET);
//                break;
            //end of red packet code
            default:
                break;
        }
        //keep exist extend menu
        return false;
    }

    /**
     * select file
     */
    protected void selectFileFromLocal() {
        Intent intent = null;
        if (Build.VERSION.SDK_INT < 19) { //api 19 and later, we can't use this way, demo just select from images
            intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("*/*");
            intent.addCategory(Intent.CATEGORY_OPENABLE);

        } else {
            intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        }
        startActivityForResult(intent, REQUEST_CODE_SELECT_FILE);
    }

    /**
     * make a voice call
     */
    protected void startVoiceCall() {
        if (!EMClient.getInstance().isConnected()) {
            Toast.makeText(getActivity(), R.string.not_connect_to_server, Toast.LENGTH_SHORT).show();
        } else {
            startActivity(new Intent(getActivity(), VoiceCallActivity.class).putExtra("username", toChatUsername)
                    .putExtra("isComingCall", false));
            // voiceCallBtn.setEnabled(false);
            inputMenu.hideExtendMenuContainer();
        }
    }

    @Override
    public void emptyHistory() {
        App.getInstance().beanCacheHelper.getFriendShipInfo(toChatUsername, getActivity(), C.EventKey.KEY_USER_SETTING);
    }

    @Override
    protected void toGroupDetails() {
        if (groupInfo != null) {
            Intent intent = new Intent(getActivity(), EaseGroupChatSettingActivity.class);
            intent.putExtra(C.IntentKey.GROUP_INFO, groupInfo);
//            startActivity(intent);
            startActivityForResult(intent, REQUEST_CODE_GROUP_DETAIL);
        }
    }

    /**
     * make a video call
     */
    protected void startVideoCall() {
        if (!EMClient.getInstance().isConnected())
            Toast.makeText(getActivity(), R.string.not_connect_to_server, Toast.LENGTH_SHORT).show();
        else {
            startActivity(new Intent(getActivity(), VideoCallActivity.class).putExtra("username", toChatUsername)
                    .putExtra("isComingCall", false));
            // videoCallBtn.setEnabled(false);
            inputMenu.hideExtendMenuContainer();
        }
    }

    @Override
    public void onSpanClick(String url, View view) {
        // AppUtility.showToastMsg(url);
        if(App.getInstance().isClient()) {
            url = new StringBuilder(url).append("?buyUserNum=").append(App.getInstance()
                    .getCurrentUserNum()).toString();
        }
        LogX.d(TAG, "-----click  url----->" + url);
        startActivity(AppUtility.getWebIntent(getActivity(), url, WebViewActivity.CP));
    }

    /**
     * chat row provider
     */
    private final class CustomChatRowProvider implements EaseCustomChatRowProvider {
        @Override
        public int getCustomChatRowTypeCount() {
            //here the number is the message type in EMMessage::Type
            //which is used to count the number of different chat row
            return 10;
        }

        @Override
        public int getCustomChatRowType(EMMessage message) {
            if (message.getType() == EMMessage.Type.TXT) {
                //voice call
                if (message.getBooleanAttribute(Constant.MESSAGE_ATTR_IS_VOICE_CALL, false)) {
                    return message.direct() == EMMessage.Direct.RECEIVE ? MESSAGE_TYPE_RECV_VOICE_CALL : MESSAGE_TYPE_SENT_VOICE_CALL;
                } else if (message.getBooleanAttribute(Constant.MESSAGE_ATTR_IS_VIDEO_CALL, false)) {
                    //video call
                    return message.direct() == EMMessage.Direct.RECEIVE ? MESSAGE_TYPE_RECV_VIDEO_CALL : MESSAGE_TYPE_SENT_VIDEO_CALL;
                }else if(message.getStringAttribute(C.IntentKey.IS_GOODS_SHARE,"").equals(ChatMessageManager.VALUE_TRUE)){
                    return message.direct() == EMMessage.Direct.RECEIVE ?MESSAGE_TYPE_RECV_GOODS_SHARE:MESSAGE_TYPE_SEND_GOODS_SHARE;
                }
                //red packet code : 红包消息和红包回执消息的chat row type
           /*     else if (message.getBooleanAttribute(RedPacketConstant.MESSAGE_ATTR_IS_RED_PACKET_MESSAGE, false)) {
                    //发送红包消息
                    return message.direct() == EMMessage.Direct.RECEIVE ? MESSAGE_TYPE_RECV_RED_PACKET : MESSAGE_TYPE_SEND_RED_PACKET;
                } else if (message.getBooleanAttribute(RedPacketConstant.MESSAGE_ATTR_IS_RED_PACKET_ACK_MESSAGE, false)) {
                    //领取红包消息
                    return message.direct() == EMMessage.Direct.RECEIVE ? MESSAGE_TYPE_RECV_RED_PACKET_ACK : MESSAGE_TYPE_SEND_RED_PACKET_ACK;
                }*/
                //end of red packet code

            }
            return Toast.LENGTH_SHORT;
        }


        @Override
        public EaseChatRow getCustomChatRow(EMMessage message, int position, BaseAdapter adapter) {
            if (message.getType() == EMMessage.Type.TXT) {
                // voice call or video call
                if (message.getBooleanAttribute(Constant.MESSAGE_ATTR_IS_VOICE_CALL, false) ||
                        message.getBooleanAttribute(Constant.MESSAGE_ATTR_IS_VIDEO_CALL, false)) {
                    return new ChatRowVoiceCall(getActivity(), message, position, adapter);
                }else if(message.getStringAttribute(C.IntentKey.IS_GOODS_SHARE,"").equals(ChatMessageManager.VALUE_TRUE)){

                    return new ShareGoodsChatRow(getContext(),message,position,adapter);
                }
                //red packet code : 红包消息和红包回执消息的chat row
            /*    else if (message.getBooleanAttribute(RedPacketConstant.MESSAGE_ATTR_IS_RED_PACKET_MESSAGE, false)) {//发送红包消息
                    return new ChatRowRedPacket(getActivity(), message, position, adapter);
                } else if (message.getBooleanAttribute(RedPacketConstant.MESSAGE_ATTR_IS_RED_PACKET_ACK_MESSAGE, false)) {//open redpacket message
                    return new ChatRowRedPacketAck(getActivity(), message, position, adapter);
                }*/
                //end of red packet code
            }
            return null;
        }

    }

    @Subscribe(threadMode = ThreadMode.MainThread, sticky = true, priority = 111)
    public void onCreatGroup(Event<String> event) {
        if (event != null && event.key.equals(C.EventKey.KEY_CREATE_GROUP)) {
            setTvTips(event.value);
            EventBus.getDefault().removeStickyEvent(event);
        }
    }

    private HashMap<String, RequestBody> groupInfoMap = new HashMap<>();
    String groupName;

    /**
     * 修改群信息
     */
    private void updateGroupInfo() {
        App.getInstance().loadingDialogHelper.showLoading(getActivity(), "处理中...");
        //群编号
        groupInfoMap.put("groupNum", RequestBody.create(MediaType.parse("text/plain"), groupInfo.getGroupInfoNum()));

        //群名称【可为空】
        if (AppUtility.isNotEmpty(groupName))
            groupInfoMap.put("groupName", RequestBody.create(MediaType.parse("text/plain"), groupName));
        //群备注 【可为空】
        // if (AppUtility.isNotEmpty(groupRemark))
        //   groupInfoMap.put("remark", RequestBody.create(MediaType.parse("text/plain"), groupRemark));
        Call<ResultBase<HxUserGroup>> call = App.getInstance().getHxApiService().updateGroupInfo(App.getInstance().getCurrentUserNum(), groupInfoMap);
        call.enqueue(new ResponseCallback<ResultBase<HxUserGroup>>() {
            @Override
            public void onSuccess(Call call, ResultBase<HxUserGroup> hxUserGroupResultBase) {
                HxUserGroup hxUserGroup = hxUserGroupResultBase.getObj();
                if (AppUtility.isNotEmpty(hxUserGroup.getGroupName()))
                    setTitle(hxUserGroup.getGroupName());
                AppUtility.showToastMsg(hxUserGroupResultBase.getMsg());
                //updateUI(hxUserGroupResultBase.getObj());
            }

            @Override
            public void onError(Call call, ResultBase<HxUserGroup> hxUserGroupResultBase, int errorCode) {
                AppUtility.showToastMsg(hxUserGroupResultBase.getMsg());
            }

            @Override
            public void onFail(Call call, String msg) {
//                AppUtility.showToastMsg("失败");
            }

            @Override
            public void onResult() {
                App.getInstance().loadingDialogHelper.dissMiss();
            }
        });
    }

}
