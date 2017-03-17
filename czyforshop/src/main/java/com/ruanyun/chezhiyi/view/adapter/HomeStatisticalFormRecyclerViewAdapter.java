package com.ruanyun.chezhiyi.view.adapter;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.ruanyun.chezhiyi.R;
import com.ruanyun.chezhiyi.commonlib.model.HomeIconInfo;
import com.ruanyun.chezhiyi.commonlib.util.FileUtil;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;
import com.zhy.adapter.recyclerview.base.ItemViewDelegate;
import com.zhy.adapter.recyclerview.base.ViewHolder;
import com.zhy.autolayout.utils.AutoUtils;

import java.util.List;

/**
 * @author ycw
 * @ClassName: ${file_name}
 * @Description:   技师端的 首页 统计报表的按钮
 * @date ${date}${time}
 */
public class HomeStatisticalFormRecyclerViewAdapter extends MultiItemTypeAdapter<HomeIconInfo> {

    public HomeStatisticalFormRecyclerViewAdapter(Context context, List<HomeIconInfo> datas) {
        super(context, datas);
        addItemViewDelegate(new StatisticalFormItemDelagate());
        addItemViewDelegate(new HomeStatementItemDelagate());
    }

    private class HomeStatementItemDelagate implements ItemViewDelegate<HomeIconInfo> {

        @Override
        public int getItemViewLayoutId() {
            return R.layout.item_shop_my_rv_service_state;
        }

        @Override
        public boolean isForViewType(HomeIconInfo item, int position) {
            return position != 0;
        }

        @Override
        public void convert(ViewHolder holder, HomeIconInfo homeIconInfo, int position) {
            AutoUtils.auto(holder.getConvertView());
            Glide.with(mContext).load(FileUtil.getImageUrl(homeIconInfo.getAndroidPic())).into((ImageView) holder.getView(R.id.iv_my_state_photo));
            holder.setText(R.id.tv_my_state_name, homeIconInfo.getHomeIconName());
        }
    }

    private class StatisticalFormItemDelagate implements ItemViewDelegate<HomeIconInfo> {

        @Override
        public int getItemViewLayoutId() {
            return R.layout.layout_statistical_form_view;
        }

        @Override
        public boolean isForViewType(HomeIconInfo item, int position) {
//        return item.getSelectPosition() == HomeIconInfo.SHOP_SERVICE_WORK_ORDER;
            return position == 0;
        }

        @Override
        public void convert(ViewHolder holder, HomeIconInfo homeIconInfo, int position) {
            AutoUtils.auto(holder.getConvertView());
            Glide.with(mContext).load(FileUtil.getImageUrl(homeIconInfo.getAndroidPic())).into((ImageView) holder.getView(R.id.iv_statistical_form));
            holder.setText(R.id.tv_statistical_form,homeIconInfo.getHomeIconName());
        }
    }
}
