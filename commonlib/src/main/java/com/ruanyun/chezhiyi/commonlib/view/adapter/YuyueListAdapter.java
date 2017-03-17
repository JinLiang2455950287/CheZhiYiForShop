package com.ruanyun.chezhiyi.commonlib.view.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.ruanyun.chezhiyi.commonlib.R;
import com.ruanyun.chezhiyi.commonlib.model.YuYueItemBean;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;
import com.zhy.autolayout.utils.AutoUtils;

import java.util.List;

/**
 * 预约处理 Adapter
 * Created by hdl on 2017/3/9
 * jin
 */
public class YuyueListAdapter extends CommonAdapter<YuYueItemBean> {

    public YuyueListAdapter(Context context, int layoutId, List<YuYueItemBean> datas) {
        super(context, layoutId, datas);
    }


    public void setData(List<YuYueItemBean> datas) {
        mDatas = datas;
    }

    @Override
    protected void convert(ViewHolder holder, final YuYueItemBean item, final int position) {
        AutoUtils.auto(holder.getConvertView());
//        ImageUtil.loadImage(mContext, FileUtil.getImageUrl("http://201703/4_58c1f4ec634b70.jpg"),
//                (ImageView) holder.getView(R.id.iv_product_photo), R.drawable.default_img);

        holder.setText(R.id.tv_number, item.getMakeNum());
        holder.setText(R.id.tv_project, item.getProjectNum());
        holder.setText(R.id.tv_time, item.getPredictShopTime());
        holder.setText(R.id.tv_detail, item.getRemark());
        TextView dealwith_btn = holder.getView(R.id.dealwith_btn);

        //处理按钮的回掉
        dealwith_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onProductBuyClickListener.onProductBuyItemClick(item);
            }
        });
    }

    public interface OnProductBuyClickListener {
        void onProductBuyItemClick(YuYueItemBean yuYueItemBean);
    }

    // click callback
    OnProductBuyClickListener onProductBuyClickListener;

    public void setOnPopupClickListener(OnProductBuyClickListener onProductBuyClickListener) {
        this.onProductBuyClickListener = onProductBuyClickListener;
    }
}
