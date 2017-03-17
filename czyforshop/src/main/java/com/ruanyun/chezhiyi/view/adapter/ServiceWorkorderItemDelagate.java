package com.ruanyun.chezhiyi.view.adapter;

import android.content.Context;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.ruanyun.chezhiyi.R;
import com.ruanyun.chezhiyi.commonlib.model.FunctionMessage;
import com.ruanyun.chezhiyi.commonlib.model.HomeIconInfo;
import com.ruanyun.chezhiyi.commonlib.util.FileUtil;
import com.zhy.adapter.recyclerview.base.ItemViewDelegate;
import com.zhy.adapter.recyclerview.base.ViewHolder;
import com.zhy.autolayout.utils.AutoUtils;

/**
 * @author hdl
 * 服务工单
 */
public class ServiceWorkorderItemDelagate implements ItemViewDelegate<HomeIconInfo> {
    Context context;

    public ServiceWorkorderItemDelagate(Context context) {
        this.context = context;
    }

    @Override
    public int getItemViewLayoutId() {
        return R.layout.item_shop_my_rv_service_workorder;
    }

    @Override
    public boolean isForViewType(HomeIconInfo item, int position) {
//        return item.getSelectPosition() == HomeIconInfo.SHOP_SERVICE_WORK_ORDER;
        return position == 0;
    }

    @Override
    public void convert(ViewHolder holder, HomeIconInfo homeIconInfo, int position) {
        AutoUtils.auto(holder.getConvertView());
        Glide.with(context).load(FileUtil.getImageUrl(homeIconInfo.getAndroidPic())).into((ImageView) holder.getView(R.id.iv_my_workorder_photo));
        holder.setText(R.id.tv_my_service_workorder,homeIconInfo.getHomeIconName());
    }
}
