package com.ruanyun.chezhiyi.commonlib.hxchat.ui.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ruanyun.chezhiyi.commonlib.R;
import com.ruanyun.chezhiyi.commonlib.model.User;
import com.ruanyun.chezhiyi.commonlib.util.FileUtil;
import com.ruanyun.chezhiyi.commonlib.view.widget.CircleImageView;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.List;

/**
 * Description ：
 * <p/>
 * Created by ycw on 2016/12/21.
 */
public class ShopNewFriendsMsgAdapter extends CommonAdapter<User> {

    public ShopNewFriendsMsgAdapter(Context context, int layoutId, List<User> userList) {
        super(context, layoutId, userList);
    }

    @Override
    protected void convert(ViewHolder holder, User user, int position) {
        CircleImageView avatar = holder.getView(R.id.avatar);//头像
        TextView message = holder.getView(R.id.message);    //附加消息
        TextView name = holder.getView(R.id.name);      //用户名
        TextView agree = holder.getView(R.id.agree);  //  按钮  状态
        Glide.with(mContext)
                .load(FileUtil.getImageUrl(user.getUserPhoto()))
                .error(R.drawable.icon_new_friends)
                .into(avatar);
        name.setText(user.getNickName());
        agree.setBackgroundColor(ContextCompat.getColor(mContext, R.color.transparent));
        if (user.getFriendStatus() == 1) {
            agree.setText("已同意");
            agree.setTextColor(ContextCompat.getColor(mContext, R.color.text_gray));
        } else {
            agree.setText("未同意");
            agree.setTextColor(ContextCompat.getColor(mContext, R.color.text_red));
        }
    }

    public void setData(List<User> userList) {
        this.mDatas = userList;
    }
}
