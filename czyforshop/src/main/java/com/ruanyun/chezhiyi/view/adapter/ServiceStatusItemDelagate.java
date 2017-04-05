package com.ruanyun.chezhiyi.view.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ruanyun.chezhiyi.R;
import com.ruanyun.chezhiyi.commonlib.model.FunctionMessage;
import com.ruanyun.chezhiyi.commonlib.model.HomeIconInfo;
import com.ruanyun.chezhiyi.commonlib.util.FileUtil;
import com.ruanyun.chezhiyi.commonlib.util.ImageUtil;
import com.zhy.adapter.recyclerview.base.ItemViewDelegate;
import com.zhy.adapter.recyclerview.base.ViewHolder;
import com.zhy.autolayout.utils.AutoUtils;

import cn.bingoogolapple.badgeview.BGABadgeFrameLayout;
import cn.bingoogolapple.badgeview.BGABadgeImageView;

/**
 * @author hdl
 *         我的界面 服务状态 adapter ItemDelagate
 */
public class ServiceStatusItemDelagate implements ItemViewDelegate<HomeIconInfo> {
    Context context;

    public ServiceStatusItemDelagate(Context context) {
        this.context = context;
    }

    @Override
    public int getItemViewLayoutId() {
        return R.layout.item_shop_my_rv_service_state;
    }

    @Override
    public boolean isForViewType(HomeIconInfo item, int position) {
        return position > 0 && position < 5;
    }

    @Override
    public void convert(ViewHolder holder, HomeIconInfo homeIconInfo, int position) {
        AutoUtils.auto(holder.getConvertView());
        holder.setText(R.id.tv_my_state_name, homeIconInfo.getHomeIconName());
//        ImageView imageView = holder.getView(R.id.iv_my_state_photo);
        TextView bgNumber = holder.getView(R.id.bg_number);
//        Glide.with(context).load(FileUtil.getImageUrl(homeIconInfo.getAndroidPic())).dontTransform().placeholder(R.drawable.my_workorder_ser).into(imageView);
        ImageUtil.loadImage(context, FileUtil.getImageUrl(homeIconInfo.getAndroidPic()), (ImageView) holder.getView(R.id.iv_my_state_photo), R.drawable.my_workorder_ser);

        if (homeIconInfo.getNumber() > 0) {
            bgNumber.setText(homeIconInfo.getNumber() + "");
            bgNumber.setVisibility(View.VISIBLE);
        } else {
//            bgNumber.setText("0");
            bgNumber.setVisibility(View.GONE);
        }
    }
}
