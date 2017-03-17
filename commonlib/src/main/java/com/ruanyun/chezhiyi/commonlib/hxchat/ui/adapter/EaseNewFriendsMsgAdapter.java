/**
 * Copyright (C) 2016 Hyphenate Inc. All rights reserved.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.ruanyun.chezhiyi.commonlib.hxchat.ui.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ruanyun.chezhiyi.commonlib.App;
import com.ruanyun.chezhiyi.commonlib.R;
import com.ruanyun.chezhiyi.commonlib.hxchat.domain.InviteMessage;
import com.ruanyun.chezhiyi.commonlib.hxchat.domain.InviteMessage.InviteMesageStatus;
import com.ruanyun.chezhiyi.commonlib.util.C;
import com.ruanyun.chezhiyi.commonlib.view.widget.CircleImageView;
import com.zhy.adapter.abslistview.MultiItemTypeAdapter;
import com.zhy.adapter.abslistview.base.ItemViewDelegate;

import java.util.List;

/**
 * description:		新的好友
 * author ycw
 * date 2016/8/11 9:23
 */
public class EaseNewFriendsMsgAdapter extends MultiItemTypeAdapter<InviteMessage> {

    private InvitationAction invitationAction;

    public void setInvitationAction(InvitationAction invitationAction) {
        this.invitationAction = invitationAction;
    }

    public EaseNewFriendsMsgAdapter(Context context, List<InviteMessage> datas) {
        super(context, datas);
        addItemViewDelegate(new ItemViewDelegate<InviteMessage>() {
            @Override
            public int getItemViewLayoutId() {
                return R.layout.ease_row_invite_msg;
            }

            @Override
            public boolean isForViewType(InviteMessage item, int position) {
                return true;
            }

            @Override
            public void convert(final com.zhy.adapter.abslistview.ViewHolder holder, final InviteMessage inviteMessage, int position) {
                CircleImageView avatar = holder.getView(R.id.avatar);//头像
                TextView message = holder.getView(R.id.message);    //附加消息
                TextView name = holder.getView(R.id.name);      //用户名
                TextView agree = holder.getView(R.id.agree);  //  按钮  状态

                if (inviteMessage != null) {
                    Glide.with(mContext)
                            .load(inviteMessage.getUserAvatar())
                            .error(R.drawable.icon_new_friends)
                            .into(avatar);
                    message.setText(inviteMessage.getReason());
                    name.setText(inviteMessage.getUserNick());
                    agree.setVisibility(View.VISIBLE);
                    if (inviteMessage.getStatus() == InviteMesageStatus.BEAGREED) {
                        message.setText(mContext.getResources().getString(R.string.Has_agreed_to_your_friend_request));
                        agree.setVisibility(View.INVISIBLE);
                    } else if (inviteMessage.getStatus() == InviteMesageStatus.AGREED) {
                        setTextByStatus(agree, mContext.getResources().getString(R.string.Has_agreed_to), null, false);
                    } else if (inviteMessage.getStatus() == InviteMesageStatus.REFUSED) {
                        setTextByStatus(agree, mContext.getResources().getString(R.string.Has_refused_to), null, false);
                    } else if (inviteMessage.getStatus() == InviteMesageStatus.GROUPINVITATION_ACCEPTED) {
                        String str = inviteMessage.getGroupInviter() + mContext.getResources().getString(R.string.accept_join_group) + inviteMessage.getGroupName();
                        setTextByStatus(agree, str, null, false);
                    } else if (inviteMessage.getStatus() == InviteMesageStatus.GROUPINVITATION_DECLINED) {
                        String str = inviteMessage.getGroupInviter() + mContext.getResources().getString(R.string.refuse_join_group) + inviteMessage.getGroupName();
                        setTextByStatus(agree, str, null, false);
                    } else if (inviteMessage.getStatus() == InviteMesageStatus.BEINVITEED ||
                            inviteMessage.getStatus() == InviteMesageStatus.BEAPPLYED ||
                            inviteMessage.getStatus() == InviteMesageStatus.GROUPINVITATION) {
                        message.setText(getMessageStr(inviteMessage));
                        setTextByStatus(agree, mContext.getResources().getString(R.string.agree), mContext.getResources().getDrawable(R.drawable.button_selector_default), true);
                        // set click listener
                        agree.setOnClickListener(new OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                //好友申请
                                if (invitationAction != null) {
                                    invitationAction.acceptInviteAction(inviteMessage);
                                }
                                App.getInstance().beanCacheHelper.getFriendShipInfo(inviteMessage.getFrom(), (FragmentActivity) mContext, C.EventKey.KEY_USER_APPLY);
                            }
                        });
                    }
                }
            }
        });
    }

    /**
     * 设置  按钮的文字
     * @param agree
     * @param string
     * @param backgroundResource
     * @param enabled
     */
    private void setTextByStatus(TextView agree, String string, Drawable backgroundResource, boolean enabled) {
        agree.setText(string);
        agree.setTextColor(backgroundResource == null ? (mContext.getResources().getColor(R.color.text_default)) : mContext.getResources().getColor(R.color.white));
        agree.setBackgroundDrawable(backgroundResource);
        agree.setEnabled(enabled);
    }

    /**
     * 设置  附加信息
     * @param inviteMessage
     * @return
     */
    private String getMessageStr(InviteMessage inviteMessage) {
        String str = inviteMessage.getReason();
        if (inviteMessage.getStatus() == InviteMesageStatus.BEINVITEED) {
            if (TextUtils.isEmpty(str)) {
                // use default text
                str = mContext.getResources().getString(R.string.Request_to_add_you_as_a_friend);
            }
        } else if (inviteMessage.getStatus() == InviteMesageStatus.BEAPPLYED) { //application to join group
            if (TextUtils.isEmpty(inviteMessage.getReason())) {
                str = mContext.getResources().getString(R.string.Apply_to_the_group_of) + inviteMessage.getGroupName();
            }
        } else if (inviteMessage.getStatus() == InviteMesageStatus.GROUPINVITATION) {
            if (TextUtils.isEmpty(inviteMessage.getReason())) {
                str = mContext.getResources().getString(R.string.invite_join_group) + inviteMessage.getGroupName();
            }
        }
        return str;
    }

    public void setData(List<InviteMessage> datas) {
        mDatas = datas;
    }

    public interface InvitationAction {
        void acceptInviteAction(InviteMessage inviteMessage);
    }

}
