package com.ruanyun.chezhiyi.commonlib.hxchat.ui.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.ruanyun.chezhiyi.commonlib.R;
import com.ruanyun.chezhiyi.commonlib.model.User;
import com.zhy.adapter.abslistview.MultiItemTypeAdapter;
import com.zhy.adapter.abslistview.ViewHolder;
import com.zhy.adapter.abslistview.base.ItemViewDelegate;

import java.util.List;

/**
 * Description ：
 * <p>
 * Created by ycw on 2016/8/12.
 */
public class EaseBlackListAdapter extends MultiItemTypeAdapter<User> {
    public EaseBlackListAdapter(Context context, List<User> datas) {
        super(context, datas);

        addItemViewDelegate(new ItemViewDelegate<User>() {
            @Override
            public int getItemViewLayoutId() {
                return R.layout.ease_item_black_list;
            }

            @Override
            public boolean isForViewType(User item, int position) {
                return true;
            }

            @Override
            public void convert(ViewHolder holder, final User user, final int position) {
                holder.setText(R.id.tv_user_name, user.getNickName());
                ImageView ivUserHeadeAvatar = holder.getView(R.id.iv_user_head_avatar);
                Glide.with(mContext).load(user.getUserPhoto()).placeholder(R.drawable.em_default_avatar).into(ivUserHeadeAvatar);
                holder.getView(R.id.tv_delete).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (deleteBlackList != null) {
                            deleteBlackList.deleteBlack(user, position);
                        }
                    }
                });
            }
        });
    }

    public void  setData(List<User> users){
        mDatas = users;
    }

    private IDeleteBlackList deleteBlackList;

    public void setDeleteBlackList(IDeleteBlackList deleteBlackList) {
        this.deleteBlackList = deleteBlackList;
    }

    public interface IDeleteBlackList{
        void deleteBlack(User user, int position);
    }
}
