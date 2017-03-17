package com.ruanyun.chezhiyi.commonlib.hxchat.ui.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.ruanyun.chezhiyi.commonlib.R;
import com.ruanyun.chezhiyi.commonlib.model.HxUser;
import com.ruanyun.chezhiyi.commonlib.util.NoDoubleClicksListener;
import com.ruanyun.chezhiyi.commonlib.view.widget.CircleImageView;
import com.zhy.adapter.abslistview.ViewHolder;
import com.zhy.adapter.abslistview.base.ItemViewDelegate;
import com.zhy.autolayout.utils.AutoUtils;

import de.greenrobot.event.EventBus;


/**
 * Description:
 * author: zhangsan on 16/8/10 下午4:21.
 */
public class ClientContactDelegate implements ItemViewDelegate<HxUser> {
    public boolean isOpenChoose = false;//是否显示选择按钮
    public boolean isCouponChoose = true;//选择好友赠送优惠券，超过数量不可再选择
    private Context mContext;

    public ClientContactDelegate(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public int getItemViewLayoutId() {
        return R.layout.ease_list_item_contact;
    }

    @Override
    public boolean isForViewType(HxUser item, int position) {
        return item.getTypeId() == HxUser.TYPE_USER;
    }

    @Override
    public void convert(final ViewHolder holder, final HxUser hxUser, int position) {
        AutoUtils.auto(holder.getConvertView());
        final ImageView imgChooser = holder.getView(R.id.img_selector);
        //if(hxUser)
         CircleImageView avatar = holder.getView(R.id.img_avatar);
        Glide.with(mContext)
                .load(hxUser.getUserPhoto())
                .error(R.drawable.icon_new_friends)
               // .placeholder(R.drawable.icon_new_friends)
                .into(avatar);

        if (isOpenChoose) {
            imgChooser.setVisibility(View.VISIBLE);
            if (hxUser.isInvited()) {
                imgChooser.setEnabled(false);
                imgChooser.setImageResource(R.drawable.btn_addmember_disabled);
                holder.getConvertView().setOnClickListener(null);
            } else {
                imgChooser.setSelected(hxUser.isSelected());
                imgChooser.setEnabled(true);
                imgChooser.setImageResource(R.drawable.img_contact_pick_statu_selector);
                holder.getConvertView().setOnClickListener(new NoDoubleClicksListener() {
                    @Override
                    public void noDoubleClick(View v) {
                        if(!imgChooser.isSelected()){
                            if(!isCouponChoose) {
                                EventBus.getDefault().postSticky("reachMaxCount");
                                return;
                            }
                        }
                        // AppUtility.showToastMsg("click");
                        imgChooser.setSelected(!imgChooser.isSelected());
                        hxUser.setSelected(imgChooser.isSelected());
                        onPostSelectAction(hxUser.isSelected());

                    }
                });
            }
        } else {
            imgChooser.setVisibility(View.GONE);
        }

        holder.setText(R.id.tv_contact_name, hxUser.getUserNick());
    }
   /**
     * 判断是选中还是反选
     *@author zhangsan
     *@date   16/11/6 下午8:10
     */
    private void onPostSelectAction(boolean f) {
        if (f) {
            EventBus.getDefault().postSticky("select");
        } else {
            EventBus.getDefault().postSticky("deselect");
        }
    }
}
