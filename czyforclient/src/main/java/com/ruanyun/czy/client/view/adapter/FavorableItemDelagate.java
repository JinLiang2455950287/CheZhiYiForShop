package com.ruanyun.czy.client.view.adapter;

import android.content.Context;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.ruanyun.chezhiyi.commonlib.model.FunctionMessage;
import com.ruanyun.chezhiyi.commonlib.model.HomeIconInfo;
import com.ruanyun.chezhiyi.commonlib.util.FileUtil;
import com.ruanyun.czy.client.R;
import com.zhy.adapter.recyclerview.base.ItemViewDelegate;
import com.zhy.adapter.recyclerview.base.ViewHolder;
import com.zhy.autolayout.utils.AutoUtils;

/**
 * @author hdl
 * 我的界面图标
 */
public class FavorableItemDelagate implements ItemViewDelegate<HomeIconInfo> {
    Context context;

    public FavorableItemDelagate(Context context) {
        this.context = context;
    }

    @Override
    public int getItemViewLayoutId() {
        return R.layout.item_client_my_rv_account;
    }

    @Override
    public boolean isForViewType(HomeIconInfo item, int position) {
//        return item.getSelectPosition()== HomeIconInfo.CLIENT_ACCOUNT;
        if (position < 3) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void convert(ViewHolder holder, HomeIconInfo homeIconInfo, int position) {
        AutoUtils.auto(holder.getConvertView());
        Glide.with(context).load(FileUtil.getImageUrl(homeIconInfo.getAndroidPic())).into((ImageView) holder.getView(R.id.iv_my_account_photo));
        holder.setText(R.id.tv_my_account_name,homeIconInfo.getHomeIconName());
    }
}
