package com.ruanyun.czy.client.view.adapter;

import android.content.Context;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.ruanyun.chezhiyi.commonlib.model.FunctionMessage;
import com.ruanyun.chezhiyi.commonlib.model.HomeIconInfo;
import com.ruanyun.chezhiyi.commonlib.util.FileUtil;
import com.ruanyun.chezhiyi.commonlib.model.HomeIconInfo;
import com.ruanyun.chezhiyi.commonlib.util.FileUtil;
import com.ruanyun.czy.client.R;
import com.zhy.adapter.recyclerview.base.ItemViewDelegate;
import com.zhy.adapter.recyclerview.base.ViewHolder;
import com.zhy.autolayout.utils.AutoUtils;

/**
 * @author hdl
 * 我的界面功能图标
 */
public class MyFunctionItemDelagate implements ItemViewDelegate<HomeIconInfo> {
    Context context;

    public MyFunctionItemDelagate(Context context) {
        this.context = context;
    }

    @Override
    public int getItemViewLayoutId() {
        return R.layout.item_client_my_rv_function;
    }

    @Override
    public boolean isForViewType(HomeIconInfo item, int position) {
        if (position >= 3) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void convert(ViewHolder holder, HomeIconInfo homeIconInfo, int position) {
        AutoUtils.auto(holder.getConvertView());
        Glide.with(context).load(FileUtil.getImageUrl(homeIconInfo.getAndroidPic())).into((ImageView) holder.getView(R.id.iv_my_function_photo));
        holder.setText(R.id.tv_my_function_name,homeIconInfo.getHomeIconName());
    }
}
