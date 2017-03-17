package com.ruanyun.chezhiyi.commonlib.hxchat.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.chat.EMConversation.EMConversationType;
import com.hyphenate.chat.EMGroup;
import com.hyphenate.easeui.adapter.EaseConversationAdapater;
import com.hyphenate.easeui.model.EaseAtMessageHelper;
import com.hyphenate.easeui.ui.EaseConversationListFragment;
import com.hyphenate.util.NetUtils;
import com.ruanyun.chezhiyi.commonlib.App;
import com.ruanyun.chezhiyi.commonlib.R;
import com.ruanyun.chezhiyi.commonlib.base.ResultBase;
import com.ruanyun.chezhiyi.commonlib.data.api.ResponseCallback;
import com.ruanyun.chezhiyi.commonlib.hxchat.Constant;
import com.ruanyun.chezhiyi.commonlib.hxchat.db.InviteMessgeDao;
import com.ruanyun.chezhiyi.commonlib.model.Event;
import com.ruanyun.chezhiyi.commonlib.model.HxUserGroup;
import com.ruanyun.chezhiyi.commonlib.model.params.GroupDetailParams;
import com.ruanyun.chezhiyi.commonlib.util.AppUtility;
import com.ruanyun.chezhiyi.commonlib.util.C;
import com.ruanyun.chezhiyi.commonlib.util.DbHelper;
import com.ruanyun.chezhiyi.commonlib.util.FileUtil;
import com.ruanyun.chezhiyi.commonlib.util.PrefUtility;

import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;
import retrofit2.Call;

public class
ConversationListFragment extends EaseConversationListFragment {

    private TextView errorText;

    @Override
    protected void initView() {
        super.initView();
        View errorView = (LinearLayout) View.inflate(getActivity(), R.layout.em_chat_neterror_item, null);
        errorItemContainer.addView(errorView);
        errorText = (TextView) errorView.findViewById(R.id.tv_connect_errormsg);
        errorView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EMClient.getInstance().login(App.getInstance().getCurrentUserNum(), "123456", new EMCallBack() {
                    @Override
                    public void onSuccess() {

                    }

                    @Override
                    public void onError(int i, String s) {

                    }

                    @Override
                    public void onProgress(int i, String s) {

                    }
                });
            }
        });
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        EventBus.getDefault().register(this);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void setUpView() {
        super.setUpView();
        // register context menu
        registerForContextMenu(conversationListView);
        conversationListView.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                EMConversation conversation = conversationListView.getItem(position);
                String username = conversation.getUserName();
                if (username.equals(EMClient.getInstance().getCurrentUser()))
                    Toast.makeText(getActivity(), R.string.Cant_chat_with_yourself, Toast.LENGTH_SHORT).show();
                else {
                    // start chat acitivity

                    if (conversation.isGroup()) {
                        App.getInstance().loadingDialogHelper.showIgnoreStatu(getActivity(), "正在获取群信息");
                        getGroupDetail(username);
                    /*    if(conversation.getSelectPosition() == EMConversationType.ChatRoom){
                            // it's group chat
                            intent.putExtra(Constant.EXTRA_CHAT_TYPE, Constant.CHATTYPE_CHATROOM);
                        }else{
                            intent.putExtra(Constant.EXTRA_CHAT_TYPE, Constant.CHATTYPE_GROUP);
                        }*/

                    } else {
                        Intent intent = new Intent(getActivity(), ChatActivity.class);
                        intent.putExtra(Constant.EXTRA_USER_ID, username);
                        startActivity(intent);
                    }
                    // it's single chat

                }
            }
        });
        //red packet code : 红包回执消息在会话列表最后一条消息的展示
     /*   conversationListView.setConversationListHelper(new EaseConversationListHelper() {
            @Override
            public String onSetItemSecondaryText(EMMessage lastMessage) {
                if (lastMessage.getBooleanAttribute(RedPacketConstant.MESSAGE_ATTR_IS_RED_PACKET_ACK_MESSAGE, false)) {
                    String sendNick = lastMessage.getStringAttribute(RedPacketConstant.EXTRA_RED_PACKET_SENDER_NAME, "");
                    String receiveNick = lastMessage.getStringAttribute(RedPacketConstant.EXTRA_RED_PACKET_RECEIVER_NAME, "");
                    String msg;
                    if (lastMessage.direct() == EMMessage.Direct.RECEIVE) {
                        msg = String.format(getResources().getString(R.string.msg_someone_take_red_packet), receiveNick);
                    } else {
                        if (sendNick.equals(receiveNick)) {
                            msg = getResources().getString(R.string.msg_take_red_packet);
                        } else {
                            msg = String.format(getResources().getString(R.string.msg_take_someone_red_packet), sendNick);
                        }
                    }
                    return msg;
                }
                return null;
            }
        });*/
        super.setUpView();
        //end of red packet code
        conversationListView.setIonCreatGroupView(new EaseConversationAdapater.IonCreatGroupView() {
            @Override
            public String getGroupAvatar(String hxGroupId) {
                HxUserGroup group = DbHelper.getInstance().getGroupByHxNum(hxGroupId);
                return group == null ? "" : FileUtil.getFileUrl(group.getGroupPath());
            }

            @Override
            public String getGroupName(String hxGroupId) {
                HxUserGroup groupInfo = DbHelper.getInstance().getGroupByHxNum(hxGroupId);
                if (groupInfo == null) {
                    EMGroup group = EMClient.getInstance().groupManager().getGroup(hxGroupId);
                    return group == null ? hxGroupId : group.getGroupName();
                } else {
                    return groupInfo.getGroupName();
                }
                // EMGroup group = EMClient.getInstance().groupManager().getGroup(toChatUsername);
                //if (group != null)
                //    titleBar.setTitle(group.getGroupName());

            }
        });
    }

    @Override
    protected void onConnectionDisconnected() {
        super.onConnectionDisconnected();
        if (NetUtils.hasNetwork(getActivity())) {
            errorText.setText(R.string.can_not_connect_chat_server_connection);
        } else {
            errorText.setText(R.string.the_current_network);
        }
    }

    @Override
    protected void onRefreshConversation() {
        conversationList.clear();
        conversationList.addAll(loadConversationList());
        sortConversationList();
        conversationListView.refresh();

    }

    /**
     * 聊天置顶筛选
     *
     * @author zhangsan
     * @date 16/10/24 下午2:04
     */
    private void sortConversationList() {
        for (EMConversation conversation : conversationList) {
            if (conversation.getUserName().equals(PrefUtility.get(C.PrefName.PREF_TOP_CHAT, ""))) {
                int i = conversationList.indexOf(conversation);
                conversationList.remove(i);
                conversationList.add(0, conversation);
                break;
            }
        }
    }

    GroupDetailParams params = new GroupDetailParams();

    private void getGroupDetail(final String groupNum) {
        params.setHuanxinNum(groupNum);
        Call<ResultBase<HxUserGroup>> call = App.getInstance().getHxApiService().getGroupDetails(App.getInstance().getCurrentUserNum(), params);
        call.enqueue(new ResponseCallback<ResultBase<HxUserGroup>>() {
            @Override
            public void onSuccess(Call call, ResultBase<HxUserGroup> result) {
                HxUserGroup hxUserGroup = result.getObj();
                Intent intent = AppUtility.getChatIntent(getActivity(), groupNum);
                intent.putExtra(C.IntentKey.GROUP_INFO, hxUserGroup);
                intent.putExtra(Constant.EXTRA_CHAT_TYPE, Constant.CHATTYPE_GROUP);
                startActivity(intent);
                DbHelper.getInstance().saveGroup(result.getObj());
            }

            @Override
            public void onError(Call call, ResultBase<HxUserGroup> hxUserGroupResultBase, int errorCode) {

            }

            @Override
            public void onFail(Call call, String msg) {

            }

            @Override
            public void onResult() {
                App.getInstance().loadingDialogHelper.dissMiss();
            }
        });
    }

    /**
     * 退群 和删群接受的通知回调
     *
     * @author zhangsan
     * @date 16/10/24 下午3:12
     */
    @Subscribe
    public void onRecieveGroupExit(Event<HxUserGroup> result) {
        if (result.key.equals(C.EventKey.EXIT_GROUP)) {
            try {
                // delete conversation
                EMClient.getInstance().chatManager().deleteConversation(result.value.getHuanxinNum(), false);
                InviteMessgeDao inviteMessgeDao = new InviteMessgeDao(getActivity());
                inviteMessgeDao.deleteMessage(result.value.getHuanxinNum());
            } catch (Exception e) {
                e.printStackTrace();
            }
            refresh();
        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
        getActivity().getMenuInflater().inflate(R.menu.em_delete_message, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        boolean deleteMessage = false;
        if (item.getItemId() == R.id.delete_message) {
            deleteMessage = true;
        } else if (item.getItemId() == R.id.delete_conversation) {
            deleteMessage = false;
        }
        EMConversation tobeDeleteCons = conversationListView.getItem(((AdapterContextMenuInfo) item.getMenuInfo()).position);
        if (tobeDeleteCons == null) {
            return true;
        }
        if (tobeDeleteCons.getType() == EMConversationType.GroupChat) {
            EaseAtMessageHelper.get().removeAtMeGroup(tobeDeleteCons.getUserName());
        }
        try {
            // delete conversation
            EMClient.getInstance().chatManager().deleteConversation(tobeDeleteCons.getUserName(), deleteMessage);
            InviteMessgeDao inviteMessgeDao = new InviteMessgeDao(getActivity());
            inviteMessgeDao.deleteMessage(tobeDeleteCons.getUserName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        refresh();

        // update unread count
        // ((MainActivity) getActivity()).updateUnreadLabel();
        return true;
    }

}
