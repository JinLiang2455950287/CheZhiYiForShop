package com.ruanyun.chezhiyi.commonlib.view.adapter;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.ruanyun.chezhiyi.commonlib.R;
import com.ruanyun.chezhiyi.commonlib.base.CommonAdapter;
import com.ruanyun.chezhiyi.commonlib.model.HomeIconInfo;
import com.ruanyun.chezhiyi.commonlib.util.FileUtil;
import com.zhy.adapter.recyclerview.base.ViewHolder;
import com.zhy.autolayout.utils.AutoUtils;

import java.util.List;

/**
 * Description ：发现页的功能图标
 * <p>
 * Created by ycw on 2016/9/10.
 */
public class FindFunctionAdapter extends CommonAdapter<HomeIconInfo> {


    public FindFunctionAdapter(Context context, int layoutId, List<HomeIconInfo> datas) {
        super(context, layoutId, datas);
    }

    @Override
    protected void convert(ViewHolder holder, HomeIconInfo homeIconInfo, int position) {
        AutoUtils.auto(holder.getConvertView());
        Glide.with(mContext).load(FileUtil.getImageUrl(homeIconInfo.getAndroidPic())).into((ImageView) holder.getView(R.id.iv_my_function_photo));
        holder.setText(R.id.tv_my_function_name,homeIconInfo.getHomeIconName());
    }
}
