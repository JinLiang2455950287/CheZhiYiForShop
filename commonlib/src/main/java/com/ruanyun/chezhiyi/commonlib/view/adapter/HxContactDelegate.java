package com.ruanyun.chezhiyi.commonlib.view.adapter;

import android.graphics.drawable.Drawable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.ruanyun.chezhiyi.commonlib.R;
import com.ruanyun.chezhiyi.commonlib.model.HxUser;
import com.ruanyun.chezhiyi.commonlib.util.FileUtil;
import com.ruanyun.chezhiyi.commonlib.view.widget.CircleImageView;
import com.zhy.adapter.recyclerview.base.ItemViewDelegate;
import com.zhy.adapter.recyclerview.base.ViewHolder;
import com.zhy.autolayout.utils.AutoUtils;

/**
 * Description:环信二级展开列表--好友
 * author: jery on 2016/7/27 14:34.
 */
public class HxContactDelegate implements ItemViewDelegate<HxUser> {
    @Override
    public int getItemViewLayoutId() {
        return R.layout.list_item_contact_name;
    }

    @Override
    public boolean isForViewType(HxUser item, int position) {
        return item.getTypeId()==HxUser.TYPE_USER;
    }

    @Override
    public void convert(ViewHolder holder, HxUser hxUser, int position) {
        AutoUtils.auto(holder.getConvertView());
       final CircleImageView imgAvatar=holder.getView(R.id.img_avatar);
        Glide.with(imgAvatar.getContext())
                .load(hxUser.getUserPhoto())
                .error(R.drawable.icon_new_friends)
                .placeholder(R.drawable.icon_new_friends)
                .into(new SimpleTarget<GlideDrawable>() {
                    @Override
                    public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
                        imgAvatar.setImageDrawable(resource);
                    }

                    @Override
                    public void onLoadFailed(Exception e, Drawable errorDrawable) {
                        super.onLoadFailed(e, errorDrawable);
                        imgAvatar.setImageDrawable(errorDrawable);
                    }
                });
       // TextView tvContactName=holder.getView(R.id.tv_contact_name);
      //  tvContactName.setSelected(hxUser.isSelected());
       // tvContactName.setText(hxUser.getUserNick());/
        holder.setText(R.id.tv_contact_name,hxUser.getUserNick());
    }
}
