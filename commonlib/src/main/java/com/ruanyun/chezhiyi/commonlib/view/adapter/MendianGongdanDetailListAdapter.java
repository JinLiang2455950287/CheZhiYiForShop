package com.ruanyun.chezhiyi.commonlib.view.adapter;

import android.content.Context;
import android.widget.ImageView;

import com.ruanyun.chezhiyi.commonlib.R;
import com.ruanyun.chezhiyi.commonlib.model.MenDianGongDanDetailInfo;
import com.ruanyun.chezhiyi.commonlib.model.MenDianGongDanInfo;
import com.ruanyun.chezhiyi.commonlib.model.TuiKuanInfo;
import com.ruanyun.chezhiyi.commonlib.util.FileUtil;
import com.ruanyun.chezhiyi.commonlib.util.ImageUtil;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;
import com.zhy.autolayout.utils.AutoUtils;

import java.util.List;

/**
 * 门店工单Adapter
 * Created by hdl on 2017/4/12
 */
public class MendianGongdanDetailListAdapter extends CommonAdapter<MenDianGongDanDetailInfo.ResultBean> {


    public MendianGongdanDetailListAdapter(Context context, int layoutId, List<MenDianGongDanDetailInfo.ResultBean> datas) {
        super(context, layoutId, datas);
    }

    public void setData(List<MenDianGongDanDetailInfo.ResultBean> datas) {
        mDatas = datas;
    }

    @Override
    protected void convert(ViewHolder holder, final MenDianGongDanDetailInfo.ResultBean item, final int position) {
        AutoUtils.auto(holder.getConvertView());
//        ImageUtil.loadImage(mContext, FileUtil.getImageUrl(item.getUser().getUserPhoto()),
//                (ImageView) holder.getView(R.id.cir_picture), R.drawable.default_img);
//
//        String price = "¥" + item.getSalePrice();
//        SpannableString spStr = new SpannableString(price);
//        spStr.setSpan(new RelativeSizeSpan(0.7f), 0, 1, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
//        TextView tvPrice = holder.getView(R.id.tv_price);
//        tvPrice.setText(spStr);//销售价
//
        holder.setText(R.id.tv_car_number, item.getService_plate_number());
        holder.setText(R.id.tv_user_name, item.getService_user_name());
        holder.setText(R.id.tv_time, item.getCreate_time());
        holder.setText(R.id.labelflow_service_item, item.getProject_name());

//        dealwith_btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(mContext, item.toString(), Toast.LENGTH_SHORT).show();
//                onProductBuyClickListener.onProductBuyItemClick(item);
//            }
//        });
    }

    public interface OnProductBuyClickListener {
        void onProductBuyItemClick(TuiKuanInfo tuiKuanInfo);
    }

    // click callback
    OnProductBuyClickListener onProductBuyClickListener;

    public void setOnPopupClickListener(OnProductBuyClickListener onProductBuyClickListener) {
        this.onProductBuyClickListener = onProductBuyClickListener;
    }
}
