package com.ruanyun.chezhiyi.commonlib.view.adapter;

import android.content.Context;

import com.ruanyun.chezhiyi.commonlib.R;
import com.ruanyun.chezhiyi.commonlib.model.MenDianGongDanDetailInfo;
import com.ruanyun.chezhiyi.commonlib.model.MenDianGongDanInfo;
import com.ruanyun.chezhiyi.commonlib.model.TuiKuanInfo;
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
//        ImageUtil.loadImage(mContext, FileUtil.getImageUrl(item.getUserPhoto()),
//                (ImageView) holder.getView(R.id.iv_product_photo), R.drawable.default_img);

//        String price = "¥" + item.getSalePrice();
//        SpannableString spStr = new SpannableString(price);
//        spStr.setSpan(new RelativeSizeSpan(0.7f), 0, 1, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
//        TextView tvPrice = holder.getView(R.id.tv_price);
//        tvPrice.setText(spStr);//销售价
//
//        holder.setText(R.id.tv_time, item.getCreateTime());
//        holder.setText(R.id.tv_count, "工单数：" + item.getHyCount());
//        holder.setText(R.id.tv_money, "营业额：¥" + item.getAmount());
//        holder.setText(R.id.tv_time, item.getRefundTime());
//        holder.setText(R.id.tv_goods, item.getRefundReason());
//        holder.setText(R.id.tv_detail, item.getRefundRemark());
//        TextView tvPurchase = holder.getView(R.id.tv_purchase);
//        TextView refalse_btn = holder.getView(R.id.refalse_btn);
//        TextView dealwith_btn = holder.getView(R.id.dealwith_btn);
//        if (C.OrderType.ORDER_TYPE_CP.equals(item.getGoodsType())) {
//            tvPurchase.setText("立即购买");
//        } else {
//            tvPurchase.setText("立即抢购");
//        }
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
