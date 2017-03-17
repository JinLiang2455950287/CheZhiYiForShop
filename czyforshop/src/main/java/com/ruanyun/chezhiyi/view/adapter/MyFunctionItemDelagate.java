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
import com.zhy.adapter.recyclerview.base.ItemViewDelegate;
import com.zhy.adapter.recyclerview.base.ViewHolder;
import com.zhy.autolayout.utils.AutoUtils;

import cn.bingoogolapple.badgeview.BGABadgeFrameLayout;
import cn.bingoogolapple.badgeview.BGABadgeImageView;

/**
 * @author hdl
 * 我的界面 功能adapter ItemDelagate
 */
public class MyFunctionItemDelagate implements ItemViewDelegate<HomeIconInfo> {
    Context context;

    public MyFunctionItemDelagate(Context context) {
        this.context = context;
    }
    @Override
    public int getItemViewLayoutId() {
        return R.layout.item_shop_my_rv_function;
    }
    @Override
    public boolean isForViewType(HomeIconInfo item, int position) {
        return position>=5;
    }

    @Override
    public void convert(ViewHolder holder, HomeIconInfo homeIconInfo, int position) {
        AutoUtils.auto(holder.getConvertView());
        ImageView imageView = holder.getView(R.id.iv_my_function_photo);
        TextView bgNumber = holder.getView(R.id.bg_number);
        Glide.with(context).load(FileUtil.getImageUrl(homeIconInfo.getAndroidPic())).placeholder(R.drawable.my_adorder).into(imageView);
        if (homeIconInfo.getNumber() > 0) {
            bgNumber.setText(homeIconInfo.getNumber() + "");
            bgNumber.setVisibility(View.VISIBLE);
        } else {
//            bgNumber.setText("0");
            bgNumber.setVisibility(View.GONE);
        }
        holder.setText(R.id.tv_my_function_name, homeIconInfo.getHomeIconName());
        holder.setText(R.id.tv_my_introduce, homeIconInfo.getRemark());
    }
}
