package com.ruanyun.czy.client.view.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.widget.ImageView;
import android.widget.TextView;

import com.ruanyun.chezhiyi.commonlib.base.CommonAdapter;
import com.ruanyun.chezhiyi.commonlib.model.HomeIconInfo;
import com.ruanyun.chezhiyi.commonlib.util.FileUtil;
import com.ruanyun.chezhiyi.commonlib.util.ImageUtil;
import com.ruanyun.czy.client.R;
import com.zhy.adapter.recyclerview.base.ViewHolder;
import com.zhy.autolayout.utils.AutoUtils;

import java.util.List;

/**
 * 首页功能图标
 * Created by ycw on 2016/9/8.
 */
public class HomeFunctionAdapter extends CommonAdapter<HomeIconInfo> {


    public HomeFunctionAdapter(Context context, int layoutId, List<HomeIconInfo> datas) {
        super(context, layoutId, datas);
    }

    @Override
    protected void convert(ViewHolder holder, HomeIconInfo homeIconInfo, int position) {
        AutoUtils.auto(holder.getConvertView());
        TextView tvMyFunctionName = holder.getView(R.id.tv_my_function_name);
        ImageView ivMyFunctionPhoto = holder.getView(R.id.iv_my_function_photo);

        tvMyFunctionName.setText(homeIconInfo.getHomeIconName());
        //tvMyFunctionName.setTextSize(10);
        tvMyFunctionName.setTextColor(ContextCompat.getColor(mContext, R.color.text_black));
        ImageUtil.loadImage(mContext, FileUtil.getImageUrl(homeIconInfo.getAndroidPic()), ivMyFunctionPhoto);
    }
}
